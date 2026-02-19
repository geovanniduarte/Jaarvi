---
name: /deploy-kubernetes
id: deploy-kubernetes
category: Deployment
description: Deploy backend + PostgreSQL to Kubernetes and verify via browser.
---

## Goal

Deploy the Jaarvi backend API and its PostgreSQL database to a Kubernetes cluster in the local network.

- If a remote SSH target is provided (e.g., `user@192.168.1.73`), deploy to that remote Linux server via SSH
- If only an IP is provided (e.g., `192.168.1.73`), attempt SSH with current user
- If no argument is provided, deploy locally and verify via localhost (port-forward)
- All configuration values MUST be sourced from `backend/.env` (do not hardcode secrets)

## Arguments

- `$ARGUMENTS` (optional): 
  - Remote SSH target: `user@ip` (e.g., `jaarvi@192.168.1.73`)
  - Or just IP: `192.168.1.73` (uses current user)
  - Empty: local deployment

## Guardrails

- Do NOT print secret values to the console or in chat output.
- Read `backend/README.md`, `backend/package.json`, and `backend/.env` to understand how the backend runs and which port/health endpoint to verify.
- Prefer minimal, reproducible steps and deterministic ports.
- Verify the deployment using a browser (open the deployed URL) and an automated check against `GET /api/health`.
- This command MUST NOT rely on repository scripts (no `./scripts/*`). It should run the required `docker`/`kubectl` commands directly.
- Run the deployment as SMALL commands (one step per command). Avoid long multi-line "do everything" commands that can be aborted.

## Prerequisites for Remote SSH Deployment

Before using this command to deploy to a remote server, you must set up passwordless SSH authentication. This is a **one-time setup** per remote server.

### Development Computer Setup

**1. Create a dedicated SSH key for Jaarvi deployments:**

```bash
ssh-keygen -t ed25519 -f ~/.ssh/id_ed25519_jaarvi -C "jaarvi-deployment"
```

- When prompted for passphrase: Enter a passphrase (recommended) OR leave empty for full automation
- This creates two files:
  - `~/.ssh/id_ed25519_jaarvi` (private key - keep secret)
  - `~/.ssh/id_ed25519_jaarvi.pub` (public key - safe to share)

**2. Configure SSH to use this key automatically:**

Create or edit `~/.ssh/config` and add:

```ssh-config
Host jaarvi
    HostName 192.168.1.73
    User jaarvi
    IdentityFile ~/.ssh/id_ed25519_jaarvi
    IdentitiesOnly yes
    AddKeysToAgent yes
    UseKeychain yes
```

Configuration breakdown:
- `Host jaarvi` - Friendly alias (use `ssh jaarvi` instead of `ssh jaarvi@192.168.1.73`)
- `HostName` - Remote server IP address (change to match your server)
- `User` - Username on the remote server (change to match your setup)
- `IdentityFile` - Path to the private key created in step 1
- `IdentitiesOnly yes` - Only try this key (security best practice)
- `AddKeysToAgent yes` - Automatically add key to SSH agent when first used
- `UseKeychain yes` - (macOS only) Store passphrase in macOS Keychain

**3. Add the key to your SSH agent:**

```bash
ssh-add ~/.ssh/id_ed25519_jaarvi
```

- If you set a passphrase, enter it when prompted
- This unlocks the key for automated use during your session
- On macOS with `UseKeychain yes`, the passphrase is saved and reloaded automatically on reboot

**4. Test the SSH alias works:**

```bash
ssh jaarvi "echo 'SSH connection successful!'"
```

✅ **Expected**: Connects immediately without prompting for password  
❌ **If it asks for passphrase**: The key isn't in the agent - run step 3 again  
❌ **If it asks for password**: The public key isn't on the remote server yet - continue to Remote Server Setup

### Remote Server Setup

⚠️ **Prerequisites**: You must be able to access the remote server via SSH with password authentication. If you cannot connect at all (connection refused), see the "Connection refused" troubleshooting section below to install and configure SSH server first.

**1. Get your public key** (run on your development computer):

```bash
cat ~/.ssh/id_ed25519_jaarvi.pub
```

Copy the entire output (starts with `ssh-ed25519 AAAA...` and ends with `jaarvi-deployment`)

**2. Add the public key to the remote server** (run on the remote server via existing SSH session):

```bash
# Create .ssh directory with secure permissions
mkdir -p ~/.ssh
chmod 700 ~/.ssh

# Add your public key
# Replace the example key below with the output from step 1
cat >> ~/.ssh/authorized_keys << 'EOF'
ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAA... jaarvi-deployment
EOF

# Set correct file permissions
chmod 600 ~/.ssh/authorized_keys
```

⚠️ **Important**: 
- Permissions MUST be exactly 700 for `.ssh` and 600 for `authorized_keys`
- SSH will silently refuse keys if permissions are too open

**3. Verify passwordless SSH works** (run on your development computer):

```bash
ssh jaarvi "hostname"
```

✅ **Expected**: Connects immediately and prints the remote hostname  
❌ **If it still asks for password**:
- Check the public key was added correctly: `ssh jaarvi "cat ~/.ssh/authorized_keys"`
- Verify permissions: `ssh jaarvi "ls -la ~/.ssh/"`
- Check SSH agent has the key loaded: `ssh-add -l` (should show your jaarvi key)

### Troubleshooting SSH Setup

**"Connection refused" or "No route to host"**

If you can't connect to the remote server at all, SSH server might not be installed or running:

```bash
# On the remote server (via direct console access or monitor/keyboard):

# 1. Install SSH server
sudo apt-get update
sudo apt-get install openssh-server

# 2. Start SSH service
sudo systemctl start ssh

# 3. Enable SSH to start on boot
sudo systemctl enable ssh

# 4. Verify SSH is running
sudo systemctl status ssh
# Should show "active (running)" in green

# 5. Configure firewall (if UFW is active)
sudo ufw allow 22
sudo ufw reload

# 6. Get the server's IP address
ip addr show
# Look for "inet" under your network interface (e.g., eth0, wlan0)
```

**"Permission denied (publickey,password)"**
```bash
# On remote server, check permissions
ls -la ~/.ssh/
# Should show: drwx------ for .ssh and -rw------- for authorized_keys

# On dev computer, check key is loaded
ssh-add -l
# Should list your ed25519 key
```

**"ssh-add: Could not open a connection to your authentication agent"**
```bash
# Start SSH agent
eval "$(ssh-agent -s)"

# Then add the key again
ssh-add ~/.ssh/id_ed25519_jaarvi
```

**Passphrase prompt every time (macOS)**
- Make sure `UseKeychain yes` is in your `~/.ssh/config`
- Run: `ssh-add --apple-use-keychain ~/.ssh/id_ed25519_jaarvi`

### Remote Server Tool Requirements

When deploying to a remote Linux server, ensure these tools are installed:

1. **Docker:**
   ```bash
   curl -fsSL https://get.docker.com -o get-docker.sh
   sudo sh get-docker.sh
   sudo usermod -aG docker $USER
   # Logout and login for group changes to take effect
   ```

2. **kubectl:**
   ```bash
   curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
   sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
   ```

3. **kind:**
   ```bash
   curl -Lo ./kind https://kind.sigs.k8s.io/dl/v0.31.0/kind-linux-amd64
   chmod +x ./kind
   sudo mv ./kind /usr/local/bin/kind
   ```

4. **Start Docker daemon:**
   ```bash
   sudo systemctl start docker
   sudo systemctl enable docker
   ```

5. **Verify installation:**
   ```bash
   docker ps
   kubectl version --client
   kind version
   ```

---

## Workflow

### Detection Phase

1. **Parse deployment target**
   - If `$ARGUMENTS` contains `@` → Remote SSH deployment (e.g., `user@192.168.1.73`)
   - If `$ARGUMENTS` is an IP without `@` → Remote SSH with current user
   - If `$ARGUMENTS` is empty → Local deployment

2. **Explore and understand runtime**
   - Confirm runtime/entrypoint and health endpoint:
     - `backend/src/index.ts` (server boot)
     - `backend/src/routes/healthRoutes.ts` and `backend/README.md` (health endpoint)
   - Identify `PORT`, `DB_*`, and `JWT_SECRET` in `backend/.env`.

### Remote Deployment Path (when SSH target is provided)

3. **Prepare remote server**
   - Test SSH connectivity: `ssh -o ConnectTimeout=5 $SSH_TARGET "echo 'Connected'"`
   - Check prerequisites on remote:
     ```bash
     ssh $SSH_TARGET 'which docker kubectl kind || echo "Missing tools"'
     ```
   - If tools are missing, display installation instructions and abort.

4. **Transfer deployment files to remote**
   - Create remote directory: `ssh $SSH_TARGET "mkdir -p ~/jaarvi-deploy"`
   - Transfer files:
     ```bash
     # Transfer backend source and Dockerfile
     rsync -avz --delete \
       --exclude 'node_modules' \
       --exclude 'dist' \
       --exclude '.git' \
       backend/ $SSH_TARGET:~/jaarvi-deploy/backend/
     
     # Transfer k8s templates
     rsync -avz k8s/ $SSH_TARGET:~/jaarvi-deploy/k8s/
     ```

5. **Execute deployment commands via SSH**
   - All commands from step 6 below MUST be prefixed with `ssh $SSH_TARGET "cd ~/jaarvi-deploy && <command>"`
   - Use heredoc for complex commands:
     ```bash
     ssh $SSH_TARGET 'bash -s' <<'EOF'
     cd ~/jaarvi-deploy
     docker build -t jaarvi-backend:local -f backend/Dockerfile backend
     kind load docker-image jaarvi-backend:local --name jaarvi
     EOF
     ```

6. **Verify from remote**
   - Health check: `ssh $SSH_TARGET "curl -s http://localhost:30080/api/health"`
   - Extract IP from SSH target and verify: `curl -s http://$REMOTE_IP:30080/api/health`

### Local Deployment Path (when no SSH target)

3. **Check local prerequisites**
   - Ensure `kubectl` is available and points to the desired cluster context.
   - Ensure `docker` is available (image build).
   - If using `kind` or `k3d`, ensure they are installed (optional but recommended for local image loading).

### Common Deployment Steps (Local or Remote)

7. **Setup Kubernetes cluster**
   
   Run these commands in the target environment (local or via SSH).

   - **(7.1) Check Docker daemon**
     ```bash
     docker ps || (echo "Docker not running. Start Docker and retry." && exit 1)
     ```

   - **(7.2) Ensure kind cluster exists**
     ```bash
     kind get clusters | grep -q jaarvi || kind create cluster --name jaarvi
     kubectl config use-context kind-jaarvi
     kubectl get nodes -o wide
     ```

8. **Build and load backend image**

   - **(8.1) Build backend image**
     ```bash
     docker build -t jaarvi-backend:local -f backend/Dockerfile backend
     ```

   - **(8.2) Load image into kind cluster**
     ```bash
     kind load docker-image jaarvi-backend:local --name jaarvi
     ```

9. **Deploy to Kubernetes**

   - **(9.1) Apply namespace**
     ```bash
     kubectl apply -f <(sed -e "s|__APP_NAME__|jaarvi|g" -e "s|__NAMESPACE__|jaarvi|g" k8s/templates/namespace.yaml)
     ```

   - **(9.2) Create/update Secret from `backend/.env`**
     ```bash
     kubectl -n jaarvi create secret generic jaarvi-env --from-env-file=backend/.env --dry-run=client -o yaml | kubectl apply -f -
     ```

   - **(9.3) Deploy PostgreSQL**
     ```bash
     kubectl apply -f <(sed -e "s|__APP_NAME__|jaarvi|g" -e "s|__NAMESPACE__|jaarvi|g" -e "s|__POSTGRES_STORAGE__|1Gi|g" k8s/templates/postgres.yaml)
     ```

   - **(9.4) Deploy backend (NodePort 30080)**
     - Read `PORT` from `backend/.env` (default `3000`)
     ```bash
     kubectl apply -f <(sed -e "s|__APP_NAME__|jaarvi|g" -e "s|__NAMESPACE__|jaarvi|g" -e "s|__NODE_PORT__|30080|g" -e "s|__BACKEND_IMAGE__|jaarvi-backend:local|g" -e "s|__BACKEND_PORT__|3000|g" k8s/templates/backend.yaml)
     ```

   - **(9.5) Wait for readiness**
     ```bash
     kubectl -n jaarvi rollout status statefulset/postgres --timeout=180s
     kubectl -n jaarvi rollout status deployment/jaarvi-backend --timeout=180s
     ```

10. **Expose service to network (for remote deployments)**

    After deployment, the service runs inside the kind cluster but isn't accessible from the network. Use kubectl port-forward to expose it:

    ```bash
    # Start port-forward in the background (on remote server)
    nohup kubectl -n jaarvi port-forward --address 0.0.0.0 svc/jaarvi-backend 30080:80 > /tmp/port-forward.log 2>&1 &
    
    # Wait for port-forward to establish
    sleep 3
    ```

    ⚠️ **Note**: This port-forward process runs in the background. If the remote server reboots, you'll need to restart it manually.

### Verification

11. **Health check and browser verification**

   **For Remote Deployment:**
   - Extract IP from SSH target (e.g., `192.168.1.73` from `user@192.168.1.73`)
   - Verify from remote server first (inside kind cluster):
     ```bash
     ssh $SSH_TARGET "docker exec jaarvi-control-plane curl -s http://localhost:30080/api/health"
     ```
   - Verify from local machine (via port-forward):
     ```bash
     curl -s http://$REMOTE_IP:30080/api/health
     ```
   - Expected response: `{"success":true,"message":"Hola, soy Jaarvi",...}`
   - Open in browser:
     ```bash
     open "http://$REMOTE_IP:30080/api/health"  # macOS
     xdg-open "http://$REMOTE_IP:30080/api/health"  # Linux
     ```

   **For Local Deployment:**
   - Test via NodePort on localhost:
     ```bash
     kubectl -n jaarvi port-forward svc/jaarvi-backend 8080:80 &
     sleep 2
     curl -s http://localhost:8080/api/health
     ```
   - Expected response: `{"success":true,"message":"Hola, soy Jaarvi",...}`
   - Open in browser:
     ```bash
     open "http://localhost:8080/api/health"  # macOS
     ```

## Notes

- The Kubernetes namespace and cluster name SHOULD match the application name (`jaarvi`)
- The deployment MUST override `DB_HOST` inside Kubernetes to point to the in-cluster PostgreSQL service (`postgres`), even if `backend/.env` uses `0.0.0.0` or `localhost`
- For remote deployments, the `kubectl port-forward` command runs in the background on the remote server. To check if it's running: `ssh jaarvi "ps aux | grep port-forward"`
- To stop the port-forward: `ssh jaarvi "pkill -f 'kubectl.*port-forward.*jaarvi-backend'"`

## Troubleshooting

**SSH Issues:**
- **"Connection refused" or "No route to host"**: SSH server not installed/running on remote server - see "Connection refused" in SSH prerequisites section above for installation steps
- **"Permission denied (publickey,password)"**: See SSH prerequisites section above
- **"ssh-add: Could not open a connection to your authentication agent"**: Run `eval "$(ssh-agent -s)"` then try again
- **Passphrase prompt every time**: Add key to keychain (macOS): `ssh-add --apple-use-keychain ~/.ssh/id_ed25519_jaarvi`

**Network/Port Issues:**
- **"Connection refused" on NodePort**: The `kubectl port-forward` may not be running. Restart it on the remote server.
- **Port-forward not accessible from network**: Ensure `--address 0.0.0.0` is used in the port-forward command

**Docker/Kubernetes Issues:**
- **"Cannot connect to Docker"**: Start Docker daemon on the target machine: `sudo systemctl start docker`
- **"kind: command not found"**: Install kind (see Remote Server Tool Requirements above)
- **"rsync: command not found"**: Install rsync: `sudo apt install rsync` (Debian/Ubuntu) or `sudo yum install rsync` (RHEL/CentOS)
