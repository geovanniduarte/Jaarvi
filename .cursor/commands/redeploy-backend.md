---
name: /redeploy-backend
id: redeploy-backend
category: Deployment
description: Rebuild and redeploy backend to Kubernetes (faster than full deploy)
---

## Goal

Quickly rebuild and redeploy just the Jaarvi backend after code changes, without touching the database or recreating the entire cluster.

This is much faster than running `/deploy-kubernetes` because it:
- ✅ Only transfers backend code (not k8s templates)
- ✅ Reuses existing PostgreSQL deployment
- ✅ Reuses existing kind cluster
- ✅ Only rebuilds the backend Docker image

## Arguments

- `$ARGUMENTS` (optional): SSH target (default: `jaarvi`)
  - Examples: `jaarvi`, `jaarvi@192.168.1.73`, `192.168.1.73`
  - Empty: redeploy locally

## Prerequisites

- Initial deployment must have been done with `/deploy-kubernetes`
- SSH connection to remote server must be configured (see `/deploy-kubernetes` prerequisites)
- Backend code changes are ready to deploy

## Guardrails

- This command does NOT update:
  - Database schema (use migrations: `npm run prisma:migrate`)
  - Kubernetes configurations (namespace, services, secrets)
  - PostgreSQL deployment
- If you changed `k8s/` templates or `backend/.env`, run `/deploy-kubernetes` instead

## Workflow

### Detection Phase

1. **Parse deployment target**
   - If `$ARGUMENTS` contains `@` → Remote deployment (e.g., `user@192.168.1.73`)
   - If `$ARGUMENTS` matches SSH config host (e.g., `jaarvi`) → Remote deployment
   - If `$ARGUMENTS` is empty → Local deployment

2. **Determine SSH target**
   - Use `$ARGUMENTS` if provided
   - Default to `jaarvi` for remote deployments

### Remote Deployment Path

3. **Transfer updated backend code**
   ```bash
   rsync -avz --delete \
     --exclude 'node_modules' \
     --exclude 'dist' \
     --exclude '.git' \
     backend/ $SSH_TARGET:~/jaarvi-deploy/backend/
   ```

4. **Rebuild backend Docker image**
   ```bash
   ssh $SSH_TARGET 'cd ~/jaarvi-deploy && docker build -t jaarvi-backend:local -f backend/Dockerfile backend'
   ```

5. **Load image into kind cluster**
   ```bash
   ssh $SSH_TARGET 'kind load docker-image jaarvi-backend:local --name jaarvi'
   ```

6. **Restart backend deployment**
   
   This forces Kubernetes to pull the new image and restart pods:
   ```bash
   ssh $SSH_TARGET "kubectl -n jaarvi rollout restart deployment/jaarvi-backend"
   ```

7. **Wait for readiness**
   ```bash
   ssh $SSH_TARGET "kubectl -n jaarvi rollout status deployment/jaarvi-backend --timeout=120s"
   ```

8. **Verify deployment**
   
   Extract IP from SSH target and test health endpoint:
   ```bash
   # Test from inside cluster
   ssh $SSH_TARGET "docker exec jaarvi-control-plane curl -s http://localhost:30080/api/health"
   
   # Test from local machine (via port-forward)
   curl -s http://$REMOTE_IP:30080/api/health
   ```
   
   Expected response: `{"success":true,"message":"Hola, soy Jaarvi",...}`

9. **Open in browser**
   ```bash
   open "http://$REMOTE_IP:30080/api/health"  # macOS
   ```

### Local Deployment Path

3. **Rebuild backend Docker image**
   ```bash
   docker build -t jaarvi-backend:local -f backend/Dockerfile backend
   ```

4. **Load image into kind cluster**
   ```bash
   kind load docker-image jaarvi-backend:local --name jaarvi
   ```

5. **Restart backend deployment**
   ```bash
   kubectl -n jaarvi rollout restart deployment/jaarvi-backend
   ```

6. **Wait for readiness**
   ```bash
   kubectl -n jaarvi rollout status deployment/jaarvi-backend --timeout=120s
   ```

7. **Verify deployment**
   ```bash
   # Via port-forward
   kubectl -n jaarvi port-forward svc/jaarvi-backend 8080:80 &
   sleep 2
   curl -s http://localhost:8080/api/health
   ```

## When to Use This Command

✅ **Use `/redeploy-backend` when:**
- You changed backend TypeScript code
- You updated dependencies in `package.json`
- You modified Dockerfile
- You want to quickly test code changes

❌ **Use `/deploy-kubernetes` instead when:**
- First time deployment
- You changed `backend/.env` (need to update secrets)
- You changed `k8s/` templates
- You need to recreate the cluster
- Database migrations required

## Troubleshooting

**"Backend not accessible after redeploy"**
```bash
# Check if port-forward is running
ssh jaarvi "ps aux | grep port-forward | grep -v grep"

# If not running, restart it
ssh jaarvi "nohup kubectl -n jaarvi port-forward --address 0.0.0.0 svc/jaarvi-backend 30080:80 > /tmp/port-forward.log 2>&1 &"
```

**"Pods stuck in ImagePullBackOff"**
```bash
# The image wasn't loaded into kind properly, reload it
ssh jaarvi "kind load docker-image jaarvi-backend:local --name jaarvi"
ssh jaarvi "kubectl -n jaarvi rollout restart deployment/jaarvi-backend"
```

**"Build fails with dependency errors"**
```bash
# Node modules might be corrupted, do a full rebuild
ssh jaarvi "cd ~/jaarvi-deploy/backend && rm -rf node_modules package-lock.json"
# Then run /redeploy-backend again
```

**"Database connection errors after redeploy"**
- Check if PostgreSQL is running: `ssh jaarvi "kubectl -n jaarvi get pods | grep postgres"`
- This command doesn't touch the database, so existing data should be preserved
- If database is down, run `/deploy-kubernetes` for a full redeploy

## Performance Comparison

| Task | `/deploy-kubernetes` | `/redeploy-backend` |
|------|---------------------|---------------------|
| Transfer files | ~30s (all) | ~5s (backend only) |
| Build image | ~2min | ~2min |
| Load image | ~1.5min | ~1.5min |
| Deploy k8s | ~30s | ~10s (restart only) |
| **Total time** | **~4-5 minutes** | **~3-4 minutes** |

Plus `/redeploy-backend` doesn't risk database data or configuration changes.

## Notes

- The backend deployment uses a rolling update strategy, so there's minimal downtime
- Old pods stay running until new pods are healthy
- If the new deployment fails health checks, Kubernetes won't kill the old pods
- Port-forward must be running for external access (not managed by this command)
