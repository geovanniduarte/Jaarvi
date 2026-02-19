#!/bin/bash
#
# Script de deployment automatizado para Kubernetes remoto
#
# Uso:
#   ./deploy.sh --ip 192.168.1.100 --app my-app --namespace prod --image my-app:v1
#
# Prerequisitos:
#   - kubectl instalado y configurado
#   - Acceso al cluster remoto configurado en kubeconfig
#

set -euo pipefail

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Funcion de ayuda
show_help() {
    cat << EOF
Uso: $0 [opciones]

Opciones:
  --ip IP            IP del servidor remoto (requerido si no hay context)
  --app NAME         Nombre de la aplicacion (requerido)
  --namespace NS     Namespace de Kubernetes (default: default)
  --image IMAGE      Imagen Docker completa con tag (requerido)
  --port PORT        Puerto del contenedor (default: 3000)
  --replicas N       Numero de replicas (default: 1)
  --nodeport PORT    Puerto NodePort (30000-32767, opcional)
  --manifests DIR    Directorio de manifiestos (default: ./k8s)
  --dry-run          Solo mostrar comandos, no ejecutar
  --context CTX      Contexto de kubectl a usar
  -h, --help         Mostrar esta ayuda

Ejemplos:
  $0 --app my-api --image my-api:v1.0 --namespace production
  $0 --app frontend --image frontend:latest --port 8080 --nodeport 30080
  $0 --manifests ./kubernetes --app backend --image backend:v2

EOF
    exit 0
}

# Valores por defecto
REMOTE_IP=""
APP_NAME=""
NAMESPACE="default"
IMAGE=""
PORT="3000"
REPLICAS="1"
NODE_PORT=""
MANIFESTS_DIR="./k8s"
DRY_RUN=false
CONTEXT=""

# Parsear argumentos
while [[ $# -gt 0 ]]; do
    case $1 in
        --ip)
            REMOTE_IP="$2"
            shift 2
            ;;
        --app)
            APP_NAME="$2"
            shift 2
            ;;
        --namespace)
            NAMESPACE="$2"
            shift 2
            ;;
        --image)
            IMAGE="$2"
            shift 2
            ;;
        --port)
            PORT="$2"
            shift 2
            ;;
        --replicas)
            REPLICAS="$2"
            shift 2
            ;;
        --nodeport)
            NODE_PORT="$2"
            shift 2
            ;;
        --manifests)
            MANIFESTS_DIR="$2"
            shift 2
            ;;
        --dry-run)
            DRY_RUN=true
            shift
            ;;
        --context)
            CONTEXT="$2"
            shift 2
            ;;
        -h|--help)
            show_help
            ;;
        *)
            echo -e "${RED}Error: Argumento desconocido: $1${NC}"
            show_help
            ;;
    esac
done

# Validar argumentos requeridos
if [[ -z "$APP_NAME" ]]; then
    echo -e "${RED}Error: --app es requerido${NC}"
    show_help
fi

if [[ -z "$IMAGE" ]]; then
    echo -e "${RED}Error: --image es requerido${NC}"
    show_help
fi

# Funcion para ejecutar o mostrar comando
run_cmd() {
    if [[ "$DRY_RUN" == true ]]; then
        echo -e "${YELLOW}[DRY-RUN]${NC} $*"
    else
        echo -e "${BLUE}[EXEC]${NC} $*"
        eval "$@"
    fi
}

# Banner
echo -e "${GREEN}"
echo "============================================"
echo "  Kubernetes Deployment Script"
echo "============================================"
echo -e "${NC}"
echo "App:       $APP_NAME"
echo "Image:     $IMAGE"
echo "Namespace: $NAMESPACE"
echo "Port:      $PORT"
echo "Replicas:  $REPLICAS"
[[ -n "$NODE_PORT" ]] && echo "NodePort:  $NODE_PORT"
[[ -n "$CONTEXT" ]] && echo "Context:   $CONTEXT"
echo ""

# Usar contexto si se especifico
if [[ -n "$CONTEXT" ]]; then
    echo -e "${BLUE}Usando contexto: $CONTEXT${NC}"
    run_cmd "kubectl config use-context $CONTEXT"
fi

# Verificar conexion al cluster
echo -e "${BLUE}Verificando conexion al cluster...${NC}"
if ! kubectl cluster-info &>/dev/null; then
    echo -e "${RED}Error: No se puede conectar al cluster${NC}"
    echo "Verifica tu configuracion de kubectl y acceso al cluster"
    exit 1
fi
echo -e "${GREEN}Conexion al cluster verificada${NC}"

# Crear namespace si no existe
echo -e "${BLUE}Verificando namespace: $NAMESPACE${NC}"
if ! kubectl get namespace "$NAMESPACE" &>/dev/null; then
    echo -e "${YELLOW}Namespace no existe, creando...${NC}"
    run_cmd "kubectl create namespace $NAMESPACE"
else
    echo -e "${GREEN}Namespace existe${NC}"
fi

# Verificar si existen manifiestos personalizados
if [[ -d "$MANIFESTS_DIR" ]]; then
    echo -e "${BLUE}Aplicando manifiestos desde $MANIFESTS_DIR...${NC}"
    run_cmd "kubectl apply -f $MANIFESTS_DIR -n $NAMESPACE"
else
    # Generar y aplicar manifiestos basicos
    echo -e "${YELLOW}No se encontro directorio de manifiestos, generando basicos...${NC}"
    
    # Deployment
    DEPLOYMENT_YAML=$(cat << EOF
apiVersion: apps/v1
kind: Deployment
metadata:
  name: $APP_NAME
  namespace: $NAMESPACE
  labels:
    app: $APP_NAME
spec:
  replicas: $REPLICAS
  selector:
    matchLabels:
      app: $APP_NAME
  template:
    metadata:
      labels:
        app: $APP_NAME
    spec:
      containers:
      - name: $APP_NAME
        image: $IMAGE
        ports:
        - containerPort: $PORT
        resources:
          requests:
            memory: "128Mi"
            cpu: "100m"
          limits:
            memory: "256Mi"
            cpu: "500m"
EOF
)
    
    echo -e "${BLUE}Aplicando Deployment...${NC}"
    if [[ "$DRY_RUN" == true ]]; then
        echo -e "${YELLOW}[DRY-RUN] Deployment YAML:${NC}"
        echo "$DEPLOYMENT_YAML"
    else
        echo "$DEPLOYMENT_YAML" | kubectl apply -f -
    fi
    
    # Service
    SERVICE_TYPE="ClusterIP"
    SERVICE_YAML="apiVersion: v1
kind: Service
metadata:
  name: $APP_NAME-service
  namespace: $NAMESPACE
spec:
  selector:
    app: $APP_NAME
  ports:
  - port: 80
    targetPort: $PORT"
    
    if [[ -n "$NODE_PORT" ]]; then
        SERVICE_TYPE="NodePort"
        SERVICE_YAML="apiVersion: v1
kind: Service
metadata:
  name: $APP_NAME-service
  namespace: $NAMESPACE
spec:
  type: NodePort
  selector:
    app: $APP_NAME
  ports:
  - port: 80
    targetPort: $PORT
    nodePort: $NODE_PORT"
    fi
    
    echo -e "${BLUE}Aplicando Service ($SERVICE_TYPE)...${NC}"
    if [[ "$DRY_RUN" == true ]]; then
        echo -e "${YELLOW}[DRY-RUN] Service YAML:${NC}"
        echo "$SERVICE_YAML"
    else
        echo "$SERVICE_YAML" | kubectl apply -f -
    fi
fi

# Esperar a que el deployment este listo
if [[ "$DRY_RUN" == false ]]; then
    echo -e "${BLUE}Esperando a que el deployment este listo...${NC}"
    kubectl rollout status deployment/"$APP_NAME" -n "$NAMESPACE" --timeout=120s
fi

# Mostrar estado final
echo ""
echo -e "${GREEN}============================================${NC}"
echo -e "${GREEN}  Deployment completado${NC}"
echo -e "${GREEN}============================================${NC}"
echo ""

if [[ "$DRY_RUN" == false ]]; then
    echo -e "${BLUE}Pods:${NC}"
    kubectl get pods -n "$NAMESPACE" -l app="$APP_NAME"
    echo ""
    
    echo -e "${BLUE}Services:${NC}"
    kubectl get svc -n "$NAMESPACE" -l app="$APP_NAME"
    echo ""
    
    if [[ -n "$NODE_PORT" ]]; then
        NODE_IP=$(kubectl get nodes -o jsonpath='{.items[0].status.addresses[?(@.type=="InternalIP")].address}')
        echo -e "${GREEN}Acceso: http://$NODE_IP:$NODE_PORT${NC}"
    fi
fi

echo ""
echo -e "${GREEN}Done!${NC}"
