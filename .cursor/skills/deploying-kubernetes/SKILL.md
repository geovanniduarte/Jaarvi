---
name: deploying-kubernetes
description: Experto en desplegar aplicaciones en clusters de Kubernetes en computadores remotos de red local. Usar cuando el usuario menciona desplegar en Kubernetes, k8s, cluster remoto, deploy to cluster, configurar nodo, o deployar aplicacion.
---

# Desplegando en Kubernetes Remoto

Skill especializado en desplegar aplicaciones en clusters de Kubernetes ubicados en computadores remotos de la red local.

## Cuando usar esta habilidad

- El usuario menciona "desplegar en Kubernetes", "deploy to k8s"
- Se solicita "configurar cluster remoto", "conectar a nodo"
- Se pide "deployar aplicación en servidor local"
- Referencias a manifiestos, pods, deployments, services en servidor remoto
- Configurar kubectl para apuntar a cluster remoto

## Flujo de trabajo

### Lista de verificacion

```markdown
- [ ] 1. Obtener IP del servidor remoto y verificar conectividad
- [ ] 2. Configurar kubeconfig para cluster remoto
- [ ] 3. Verificar acceso al cluster (kubectl get nodes)
- [ ] 4. Preparar manifiestos de Kubernetes
- [ ] 5. Construir y subir imagen Docker (si aplica)
- [ ] 6. Aplicar manifiestos al cluster
- [ ] 7. Verificar estado del deployment
- [ ] 8. Configurar acceso externo (LoadBalancer/NodePort/Ingress)
```

### Patron de ejecucion

1. **Planificar**: Identificar aplicación, dependencias y recursos necesarios en el cluster
2. **Validar**: Verificar conectividad SSH, acceso kubectl y disponibilidad de recursos
3. **Ejecutar**: Aplicar manifiestos y monitorear deployment

## Instrucciones

### Prerequisitos en la maquina local

```bash
# Verificar kubectl instalado
kubectl version --client

# Verificar conectividad al servidor remoto
ping -c 3 {{REMOTE_IP}}

# Verificar acceso SSH (si es necesario)
ssh {{USER}}@{{REMOTE_IP}} "echo 'Conexion exitosa'"
```

### Configurar kubeconfig para cluster remoto

Existen varias formas de conectar a un cluster remoto:

#### Opcion 1: Copiar kubeconfig del servidor remoto

```bash
# Copiar el archivo de configuracion del cluster remoto
scp {{USER}}@{{REMOTE_IP}}:~/.kube/config ~/.kube/config-remote

# Usar el config remoto
export KUBECONFIG=~/.kube/config-remote

# O combinar con configs existentes
export KUBECONFIG=~/.kube/config:~/.kube/config-remote
```

#### Opcion 2: Configurar contexto manualmente

```bash
# Agregar cluster
kubectl config set-cluster remote-cluster \
  --server=https://{{REMOTE_IP}}:6443 \
  --certificate-authority=/path/to/ca.crt

# Agregar credenciales
kubectl config set-credentials remote-admin \
  --client-certificate=/path/to/admin.crt \
  --client-key=/path/to/admin.key

# Crear contexto
kubectl config set-context remote-context \
  --cluster=remote-cluster \
  --user=remote-admin \
  --namespace=default

# Usar el contexto
kubectl config use-context remote-context
```

#### Opcion 3: K3s con token (cluster ligero)

```bash
# Obtener token del servidor K3s
ssh {{USER}}@{{REMOTE_IP}} "sudo cat /var/lib/rancher/k3s/server/node-token"

# Configurar kubectl para K3s
kubectl config set-cluster k3s-remote \
  --server=https://{{REMOTE_IP}}:6443 \
  --insecure-skip-tls-verify=true

kubectl config set-credentials k3s-admin \
  --token={{TOKEN}}

kubectl config set-context k3s-context \
  --cluster=k3s-remote \
  --user=k3s-admin

kubectl config use-context k3s-context
```

### Verificar conexion al cluster

```bash
# Listar nodos
kubectl get nodes

# Ver informacion del cluster
kubectl cluster-info

# Ver namespaces disponibles
kubectl get namespaces
```

### Estructura de manifiestos recomendada

```
k8s/
├── namespace.yaml       # Namespace de la aplicacion
├── configmap.yaml       # Configuraciones
├── secrets.yaml         # Secretos (encriptados)
├── deployment.yaml      # Deployment principal
├── service.yaml         # Service para exponer
├── ingress.yaml         # Ingress (opcional)
└── kustomization.yaml   # Para usar kustomize
```

### Manifiesto de Deployment basico

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{APP_NAME}}
  namespace: {{NAMESPACE}}
  labels:
    app: {{APP_NAME}}
spec:
  replicas: {{REPLICAS}}
  selector:
    matchLabels:
      app: {{APP_NAME}}
  template:
    metadata:
      labels:
        app: {{APP_NAME}}
    spec:
      containers:
      - name: {{APP_NAME}}
        image: {{IMAGE}}:{{TAG}}
        ports:
        - containerPort: {{PORT}}
        resources:
          requests:
            memory: "128Mi"
            cpu: "100m"
          limits:
            memory: "256Mi"
            cpu: "500m"
        env:
        - name: NODE_ENV
          value: "production"
        livenessProbe:
          httpGet:
            path: /health
            port: {{PORT}}
          initialDelaySeconds: 30
          periodSeconds: 10
        readinessProbe:
          httpGet:
            path: /ready
            port: {{PORT}}
          initialDelaySeconds: 5
          periodSeconds: 5
```

### Manifiesto de Service

```yaml
apiVersion: v1
kind: Service
metadata:
  name: {{APP_NAME}}-service
  namespace: {{NAMESPACE}}
spec:
  type: NodePort  # O LoadBalancer si hay MetalLB
  selector:
    app: {{APP_NAME}}
  ports:
  - port: 80
    targetPort: {{PORT}}
    nodePort: {{NODE_PORT}}  # Puerto 30000-32767
```

### Comandos de deployment

```bash
# Crear namespace
kubectl create namespace {{NAMESPACE}}

# Aplicar todos los manifiestos
kubectl apply -f k8s/ -n {{NAMESPACE}}

# O aplicar uno por uno
kubectl apply -f k8s/deployment.yaml -n {{NAMESPACE}}
kubectl apply -f k8s/service.yaml -n {{NAMESPACE}}

# Con kustomize
kubectl apply -k k8s/
```

### Verificar estado del deployment

```bash
# Ver estado de pods
kubectl get pods -n {{NAMESPACE}} -w

# Ver logs del pod
kubectl logs -f deployment/{{APP_NAME}} -n {{NAMESPACE}}

# Describir pod (para debug)
kubectl describe pod -l app={{APP_NAME}} -n {{NAMESPACE}}

# Ver eventos
kubectl get events -n {{NAMESPACE}} --sort-by='.lastTimestamp'

# Ver estado del service
kubectl get svc -n {{NAMESPACE}}
```

### Acceso a la aplicacion

```bash
# Con NodePort - acceder via IP del nodo
curl http://{{REMOTE_IP}}:{{NODE_PORT}}

# Port-forward para testing local
kubectl port-forward svc/{{APP_NAME}}-service 8080:80 -n {{NAMESPACE}}

# Luego acceder en localhost:8080
```

### Rollback y actualizaciones

```bash
# Actualizar imagen
kubectl set image deployment/{{APP_NAME}} {{APP_NAME}}={{NEW_IMAGE}} -n {{NAMESPACE}}

# Ver historial de rollouts
kubectl rollout history deployment/{{APP_NAME}} -n {{NAMESPACE}}

# Rollback a version anterior
kubectl rollout undo deployment/{{APP_NAME}} -n {{NAMESPACE}}

# Rollback a revision especifica
kubectl rollout undo deployment/{{APP_NAME}} --to-revision=2 -n {{NAMESPACE}}
```

### Escalar aplicacion

```bash
# Escalar manualmente
kubectl scale deployment/{{APP_NAME}} --replicas=3 -n {{NAMESPACE}}

# Ver HPA (si existe)
kubectl get hpa -n {{NAMESPACE}}
```

## Configuracion de registro privado

Si usas un registro Docker privado:

```bash
# Crear secret para registry
kubectl create secret docker-registry regcred \
  --docker-server={{REGISTRY_URL}} \
  --docker-username={{USERNAME}} \
  --docker-password={{PASSWORD}} \
  -n {{NAMESPACE}}
```

Agregar al deployment:
```yaml
spec:
  template:
    spec:
      imagePullSecrets:
      - name: regcred
```

## Manejo de errores

| Error | Causa | Solucion |
|-------|-------|----------|
| `Unable to connect to server` | Cluster no accesible | Verificar IP, puerto 6443, y firewall |
| `certificate signed by unknown authority` | Certificado no confiable | Usar `--insecure-skip-tls-verify` o instalar CA |
| `ImagePullBackOff` | No puede descargar imagen | Verificar nombre de imagen y credenciales de registry |
| `CrashLoopBackOff` | Aplicacion falla al iniciar | Revisar logs con `kubectl logs` |
| `Pending` prolongado | Sin recursos disponibles | Verificar recursos del nodo y requests/limits |
| `connection refused` en NodePort | Firewall o servicio caido | Verificar firewall y estado del service |
| `Unauthorized` | Token expirado o invalido | Regenerar token o kubeconfig |

## Troubleshooting avanzado

```bash
# Entrar al pod para debug
kubectl exec -it {{POD_NAME}} -n {{NAMESPACE}} -- /bin/sh

# Ver uso de recursos
kubectl top pods -n {{NAMESPACE}}
kubectl top nodes

# Verificar endpoints del service
kubectl get endpoints {{APP_NAME}}-service -n {{NAMESPACE}}

# Ver todos los recursos del namespace
kubectl get all -n {{NAMESPACE}}

# Eliminar deployment completo
kubectl delete -f k8s/ -n {{NAMESPACE}}
```

## Variables a reemplazar

| Variable | Descripcion | Ejemplo |
|----------|-------------|---------|
| `{{REMOTE_IP}}` | IP del servidor remoto | `192.168.1.100` |
| `{{USER}}` | Usuario SSH | `ubuntu` |
| `{{APP_NAME}}` | Nombre de la aplicacion | `my-api` |
| `{{NAMESPACE}}` | Namespace de Kubernetes | `production` |
| `{{IMAGE}}` | Nombre de imagen Docker | `my-registry/my-api` |
| `{{TAG}}` | Tag de la imagen | `v1.0.0` |
| `{{PORT}}` | Puerto del contenedor | `3000` |
| `{{NODE_PORT}}` | Puerto NodePort (30000-32767) | `30080` |
| `{{REPLICAS}}` | Numero de replicas | `2` |
| `{{TOKEN}}` | Token de autenticacion | `K10...` |

## Recursos

- [Plantilla de Deployment](resources/deployment-template.yaml)
- [Plantilla de Service](resources/service-template.yaml)
- [Script de deploy automatizado](scripts/deploy.sh)
