MOBILE

```mermaid
flowchart TB
  User[Usuario]

  subgraph Apps[Apps]
    AndroidApp["Android App (Kotlin)"]
    IosApp["iOS App (Kotlin)"]
  end

  subgraph SharedUI["Shared UI (CMP)"]
    Screens["Pantallas y UI compartida (Compose)"]
    UIState["UI State (StateFlow) + Presenters"]
  end

  subgraph SharedCore["Shared Core (KMP)"]
    Domain["Dominio: itinerario, playbooks, Plan B"]
    Data["Datos: repositorios"]
    Net["Networking: Ktor + Kotlinx Serialization"]
    Db["Persistencia: SQLDelight"]
  end

  subgraph Platform["Capacidades por plataforma"]
    MapCap["MapCapability (interfaz)"]
    MapV1["v1: navegación externa (deep links)"]
    MapV2["v2: mapas embebidos (Android/iOS nativo)"]
    NotifCap["NotificationCapability (interfaz)"]
    BrowserCap["BrowserCapability (interfaz)"]
    DocsCap["DocumentVault (interfaz)"]
  end

  subgraph Services["Servicios externos"]
    BackendAPI["Backend API (REST/JSON)"]
    Affiliate["Partners afiliados"]
  end

  User --> AndroidApp
  User --> IosApp

  AndroidApp --> Screens
  IosApp --> Screens

  Screens --> UIState
  UIState --> Domain
  Domain --> Data
  Data --> Net
  Data --> Db

  Screens --> MapCap
  MapCap --> MapV1
  MapCap --> MapV2

  Screens --> NotifCap
  Screens --> BrowserCap
  Screens --> DocsCap

  Net --> BackendAPI
  BrowserCap --> Affiliate
```

BACKEND

```mermaid
graph TB
    subgraph "Mobile Layer"
        Mobile["Mobile Apps<br/>(Android/iOS KMP)"]
    end

    subgraph "API Gateway"
        Gateway["AWS API Gateway<br/>REST + WebSocket"]
        Auth["AWS Cognito<br/>Authentication"]
    end

    subgraph "Presentation Layer"
        Routes["Express Routes<br/>API Endpoints"]
        Controllers["Controllers<br/>Request Handlers"]
        Middleware["Middleware<br/>Auth, Validation, CORS, Errors"]
    end

    subgraph "Application Layer"
        TripService["TripService<br/>Trip Management"]
        ItineraryService["ItineraryService<br/>Planning Orchestration"]
        ActivityService["ActivityService<br/>Activity Operations"]
        DocumentService["DocumentService<br/>Document Management"]
        PlaybookService["PlaybookService<br/>Guidance Logic"]
        AIOrchestrator["AIOrchestrator<br/>AI Service Coordination"]
        Validator["Validator<br/>Input Validation"]
    end

    subgraph "Domain Layer"
        subgraph "Aggregates"
            TripAggregate["Trip Aggregate<br/>Trip + Destinations<br/>+ Itineraries"]
            UserAggregate["User Aggregate<br/>User + Preferences<br/>+ Profile"]
        end
        
        subgraph "Entities"
            Itinerary["Itinerary"]
            DayPlan["DayPlan"]
            Activity["Activity"]
            Document["Document"]
        end
        
        subgraph "Value Objects"
            Preferences["TravelPreferences"]
            Location["Location"]
            TimeBlock["TimeBlock"]
            CostEstimate["CostEstimate"]
        end
        
        subgraph "Domain Services"
            ItineraryGenerator["ItineraryGenerator"]
            PlanBEngine["PlanBEngine"]
            TimeCalculator["TimeCalculator"]
            BudgetCalculator["BudgetCalculator"]
        end
        
        subgraph "Repository Interfaces"
            ITripRepo["ITripRepository"]
            IUserRepo["IUserRepository"]
            IDocRepo["IDocumentRepository"]
        end
    end

    subgraph "Infrastructure Layer"
        subgraph "Repository Implementations"
            TripRepo["TripRepository<br/>(Prisma)"]
            UserRepo["UserRepository<br/>(Prisma)"]
            DocumentRepo["DocumentRepository<br/>(Prisma)"]
        end
        
        subgraph "External Services"
            Prisma["Prisma Client<br/>ORM"]
            AIService["AI Service Client<br/>(Amazon Bedrock)"]
            S3Client["S3 Client<br/>Document Storage"]
            SQSClient["SQS Client<br/>Async Jobs"]
            SNSClient["SNS Client<br/>Notifications"]
        end
        
        subgraph "Infrastructure Utilities"
            Logger["Logger<br/>CloudWatch"]
            EventBus["Event Bus<br/>Domain Events"]
        end
    end

    subgraph "Data Layer"
        Postgres[("PostgreSQL<br/>RDS Multi-AZ")]
        S3[("S3 Buckets<br/>Documents")]
        Cache[("DynamoDB<br/>Session Cache")]
    end

    subgraph "AI & Async Processing"
        Bedrock["Amazon Bedrock<br/>LLM Service"]
        Lambda["Lambda Functions<br/>Background Jobs"]
        SQS["SQS Queue<br/>Job Queue"]
    end

    %% Connections
    Mobile --> Gateway
    Gateway --> Auth
    Gateway --> Routes
    
    Routes --> Middleware
    Middleware --> Controllers
    
    Controllers --> TripService
    Controllers --> ItineraryService
    Controllers --> ActivityService
    Controllers --> DocumentService
    Controllers --> PlaybookService
    
    TripService --> Validator
    ItineraryService --> Validator
    ItineraryService --> AIOrchestrator
    PlaybookService --> AIOrchestrator
    
    TripService --> TripAggregate
    ItineraryService --> TripAggregate
    ActivityService --> Itinerary
    DocumentService --> Document
    
    TripAggregate --> ItineraryGenerator
    TripAggregate --> PlanBEngine
    Itinerary --> TimeCalculator
    Activity --> BudgetCalculator
    
    ItineraryGenerator --> ITripRepo
    TripService --> ITripRepo
    ItineraryService --> ITripRepo
    DocumentService --> IDocRepo
    
    ITripRepo -.implements.-> TripRepo
    IUserRepo -.implements.-> UserRepo
    IDocRepo -.implements.-> DocumentRepo
    
    TripRepo --> Prisma
    UserRepo --> Prisma
    DocumentRepo --> Prisma
    
    AIOrchestrator --> AIService
    AIOrchestrator --> SQSClient
    DocumentRepo --> S3Client
    
    Prisma --> Postgres
    S3Client --> S3
    UserRepo --> Cache
    
    AIService --> Bedrock
    SQSClient --> SQS
    SQS --> Lambda
    Lambda --> AIService
    
    TripService --> Logger
    Controllers --> EventBus
    
    style TripAggregate fill:#E8F4F8
    style UserAggregate fill:#E8F4F8
    style Prisma fill:#3B48CC,color:#fff
    style Postgres fill:#3B48CC,color:#fff
    style S3 fill:#569A31,color:#fff
    style Bedrock fill:#6B5EFF,color:#fff
```

INFRASTRUCTURE

```plantuml
@startuml Jaarvi AWS Infrastructure
!define RECTANGLE_COLOR #FF9900
!define VPC_COLOR #E8F4F8
!define DATABASE_COLOR #3B48CC
!define STORAGE_COLOR #569A31
!define COMPUTE_COLOR #FF9900

skinparam rectangle {
  BackgroundColor<<mobile>> #3498DB
  BackgroundColor<<cdn>> #FF9900
  BackgroundColor<<gateway>> #FF9900
  BackgroundColor<<auth>> #DD344C
  BackgroundColor<<compute>> #FF9900
  BackgroundColor<<database>> #3B48CC
  BackgroundColor<<storage>> #569A31
  BackgroundColor<<ai>> #6B5EFF
  BackgroundColor<<messaging>> #E7157B
  BackgroundColor<<monitoring>> #FF9900
  BorderColor Black
  FontColor White
}

actor "User" as user

' Mobile Layer
package "Mobile Apps" <<mobile>> {
  component "Android App\n(Kotlin/Compose)" as android
  component "iOS App\n(Kotlin/Compose)" as ios
}

' Edge Layer
component "CloudFront\nCDN" <<cdn>> as cdn
component "API Gateway\nREST/WebSocket" <<gateway>> as apigw
component "Cognito\nAuthentication" <<auth>> as cognito

' Application Layer
package "VPC - Application Tier" <<compute>> as vpc_app {
  component "Application\nLoad Balancer" <<gateway>> as alb
  
  package "ECS Cluster" <<compute>> {
    component "Backend API\nNode.js/TypeScript\nPrisma ORM" <<compute>> as backend
    component "AI Service\nItinerary Generation\nPlaybooks Engine" <<ai>> as ai_service
  }
}

' Data Layer
package "VPC - Data Tier" <<database>> as vpc_data {
  database "RDS PostgreSQL\nPrimary (Multi-AZ)" <<database>> as postgres
  database "RDS PostgreSQL\nRead Replica" <<database>> as postgres_read
  database "DynamoDB\nSession Cache" <<database>> as dynamodb
}

' Storage Layer
storage "S3 Documents\nTravel Docs\nTickets & Permits" <<storage>> as s3_docs
storage "S3 Static Assets\nImages & Maps\nPlaybooks" <<storage>> as s3_static

' AI & Processing Services
component "Amazon Bedrock\nLLM Service" <<ai>> as bedrock
component "Lambda Functions\nEvent Processing" <<compute>> as lambda

' Messaging & Notifications
queue "SQS\nAsync Job Queue" <<messaging>> as sqs
component "SNS\nPush Notifications" <<messaging>> as sns

' Monitoring
component "CloudWatch\nLogs & Metrics\nAlerts" <<monitoring>> as cloudwatch

' CI/CD
cloud "CI/CD Pipeline" {
  component "GitHub Actions" <<compute>> as github
  component "App Distribution\nGoogle Play\nApp Store" <<mobile>> as distribution
}

' Connections
user -down-> android
user -down-> ios

android -down-> cdn
ios -down-> cdn
android -down-> apigw : Direct API
ios -down-> apigw : Direct API

cdn -down-> apigw
apigw -right-> cognito : Auth
apigw -down-> alb

alb -down-> backend
alb -down-> ai_service

backend -down-> postgres : Write
backend -down-> postgres_read : Read
backend -down-> dynamodb : Cache
backend -right-> s3_docs : Documents
backend -right-> s3_static : Assets

ai_service -down-> postgres
ai_service -right-> bedrock : Generate
ai_service -right-> s3_static : Playbooks

backend -down-> sqs : Queue Jobs
sqs -right-> lambda
lambda -up-> ai_service : Trigger

backend -down-> sns : Notifications
ai_service -down-> sns
sns -up-> android : Push
sns -up-> ios : Push

backend -right-> cloudwatch : Logs
ai_service -right-> cloudwatch : Logs
lambda -right-> cloudwatch : Logs

github -down-> backend : Deploy
github -down-> ai_service : Deploy
github -down-> distribution
distribution -up-> android : Release
distribution -up-> ios : Release

note right of backend
  **Backend API**
  - REST API with Prisma ORM
  - Trip CRUD operations
  - Document management
  - User preferences
  - Authentication flows
end note

note right of ai_service
  **AI Service**
  - Itinerary generation
  - Playbooks execution
  - Plan B alternatives
  - Local advice engine
  - Context-aware recommendations
end note

note bottom of postgres
  **PostgreSQL Database**
  - Users, Trips, Itineraries
  - Activities, Documents
  - AI Generation metadata
  - Multi-AZ for High Availability
  - Automated backups
end note

note bottom of s3_docs
  **Secure Document Storage**
  - Encrypted at rest (AES-256)
  - Lifecycle policies
  - Versioning enabled
  - Access control with IAM
end note

@enduml

