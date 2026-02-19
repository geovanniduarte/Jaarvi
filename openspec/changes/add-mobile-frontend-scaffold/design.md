# Design: Mobile Frontend Scaffolding

## Context

Jaarvi is a mobile-first travel assistant targeting Android and iOS from day one. The product requires:
- Fast time-to-market with cross-platform code sharing
- Platform-specific capabilities (maps, notifications, browser, secure storage) with migration path from external to embedded implementations
- Testable architecture that supports TDD workflow
- Offline-friendly foundation (future requirement, prepared now)

**Constraints:**
- Solo development initially (no team coordination overhead)
- MVP prioritizes manual itinerary creation (no AI generation yet)
- Must support both platforms with single codebase where possible
- Development environment: macOS required for iOS builds

**Stakeholders:**
- Mobile developers (initial solo developer)
- Backend API (health endpoint consumer)
- Future feature developers who will build on this foundation

## Goals / Non-Goals

### Goals
1. Enable Android and iOS development from single codebase with maximum code sharing
2. Establish clear separation between shared logic, shared UI, and platform-specific implementations
3. Provide migration path for capabilities (external → embedded) without breaking abstractions
4. Support TDD with comprehensive shared testing infrastructure
5. Demonstrate full stack integration with backend health check
6. Enable immediate feature development post-scaffold

### Non-Goals
1. Embedded maps implementation (V1 uses external navigation only)
2. Complete design system (only foundation: colors, typography, spacing)
3. Offline persistence layer (SQLDelight configured but not used in scaffold)
4. Production-ready error handling and logging (basic only)
5. CI/CD pipelines (manual builds for MVP)

## Decisions

### Decision 1: Kotlin Multiplatform + Compose Multiplatform

**Chosen**: KMP for shared core + CMP for shared UI

**Why**:
- Maximum code sharing (80-90% shared business logic + UI)
- Native performance on both platforms
- Access to platform APIs when needed
- Growing ecosystem with JetBrains support
- Compose Multiplatform now stable for production (1.6.0+)

**Alternatives Considered**:
- **React Native**: Lower learning curve but requires JavaScript ecosystem, less native integration, performance concerns for complex UI
- **Flutter**: Good cross-platform but Dart language adds complexity, less flexible for platform-specific code
- **Native per platform**: Maximum control but 2x development time, no code sharing

### Decision 2: Voyager for Navigation

**Chosen**: Voyager Navigator with ScreenModel integration

**Why**:
- Built specifically for Compose Multiplatform
- Type-safe navigation
- Tab support for future bottom navigation
- Transitions and animations included
- ScreenModel pattern aligns with our state management

**Alternatives Considered**:
- **Precompose**: Alternative CMP navigation library, less mature ecosystem
- **Custom navigation**: More control but reinventing the wheel, maintenance burden

### Decision 3: Koin for Dependency Injection

**Chosen**: Koin for DI across shared and platform modules

**Why**:
- Kotlin-first, lightweight, no code generation
- Multiplatform support (unlike Hilt/Dagger which are Android-only)
- Simple API, low learning curve
- Good integration with Voyager (voyager-koin module)

**Alternatives Considered**:
- **Kodein**: Similar to Koin, less popular, smaller ecosystem
- **Manual DI**: Simple for MVP but doesn't scale, no testing convenience

### Decision 4: Capability Pattern for Platform Abstractions

**Chosen**: Interface-based capability pattern with expect/actual platform implementations

**Why**:
- Clean migration path: V1 external (deep links) → V2 embedded (map SDKs) by swapping implementations
- Testable: mock capabilities in shared tests
- Clear platform boundaries: all OS integration isolated
- Future-proof: can add new capabilities without changing architecture

**Example (MapCapability)**:
```kotlin
// shared/commonMain - Interface
interface MapCapability {
    suspend fun openExternalNavigation(lat: Double, lng: Double, label: String?): Result<Unit>
    fun isNavigationAvailable(): Boolean
}

// androidApp - Android Implementation
class AndroidMapCapability(context: Context) : MapCapability {
    override suspend fun openExternalNavigation(...) = runCatching {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:$lat,$lng"))
        context.startActivity(intent)
    }
}

// iosApp - iOS Implementation (Swift)
class IOSMapCapability: MapCapability {
    func openExternalNavigation(...) {
        let mapItem = MKMapItem(...)
        mapItem.openInMaps(...)
    }
}
```

**Capabilities Defined**:
1. `MapCapability` - V1: External navigation (deep links) → V2: Embedded maps
2. `NotificationCapability` - Push notifications for time-based alerts
3. `BrowserCapability` - In-app browser for affiliate links (monetization)
4. `DocumentVaultCapability` - Secure storage for tickets, permits, documents

### Decision 5: State Management Pattern

**Chosen**: Presenter/ScreenModel pattern with StateFlow

**Why**:
- Avoid `androidx.lifecycle.ViewModel` (Android-only, breaks iOS compilation)
- Voyager's ScreenModel provides lifecycle management
- StateFlow is multiplatform-friendly (unlike LiveData)
- Aligns with TDD: Presenter is pure Kotlin, easy to test

**Pattern**:
```kotlin
// UiState (immutable data class)
data class HealthUiState(
    val isLoading: Boolean = true,
    val message: String? = null,
    val error: String? = null
)

// UiEvent (user intents)
sealed interface HealthUiEvent {
    data object CheckHealth : HealthUiEvent
    data object Retry : HealthUiEvent
}

// Presenter (state holder)
class HealthPresenter(
    private val repository: HealthRepository
) : ScreenModel {
    private val _state = MutableStateFlow(HealthUiState())
    val state: StateFlow<HealthUiState> = _state.asStateFlow()
    
    fun onEvent(event: HealthUiEvent) { /* handle events */ }
}
```

### Decision 6: Gradle Version Catalog

**Chosen**: `gradle/libs.versions.toml` for centralized dependency management

**Why**:
- Single source of truth for versions
- Type-safe accessors in build files
- Easier updates and dependency audits
- Gradle best practice for multimodule projects

**Alternatives Considered**:
- **buildSrc**: More powerful but slower builds, overkill for current scale
- **Inline versions**: Harder to maintain consistency across modules

### Decision 7: Testing Strategy

**Chosen**: Focus on shared tests (commonTest) with Turbine for StateFlow testing

**Why**:
- Business logic in shared module → test once, works everywhere
- Turbine provides clean API for testing StateFlow emissions
- Platform UI tests minimal (smoke tests only) to avoid duplication
- Aligns with TDD workflow: write failing test first, implement, verify

**Test Pyramid**:
- **Shared tests (majority)**: Domain logic, repository contracts, presenter state transitions
- **Android tests (minimal)**: Compose UI rendering smoke tests
- **iOS tests (minimal)**: XCTest smoke tests for app startup

## Risks / Trade-offs

### Risk 1: Compose Multiplatform iOS Maturity
**Risk**: CMP for iOS is newer than Android, potential bugs or missing features

**Mitigation**:
- Use stable 1.6.0+ version with production-ready APIs
- Keep UI simple initially (standard Material components)
- Monitor JetBrains issue tracker for known issues
- Platform escape hatch: SwiftUI wrapper if needed (capability pattern allows this)

### Risk 2: Learning Curve for iOS Platform Code
**Risk**: Swift implementation of capabilities requires iOS knowledge

**Mitigation**:
- Capabilities are isolated, small surface area (4 interfaces total)
- External navigation is simple (MapKit deep links)
- Documentation and examples provided in scaffold
- V1 uses minimal iOS APIs (push notifications and browser are optional features)

### Risk 3: Gradle Build Complexity
**Risk**: KMP + Android + iOS configuration can be error-prone

**Mitigation**:
- Follow official Kotlin Multiplatform wizard structure
- Document exact Gradle versions and configuration
- Provide troubleshooting guide for common issues (framework linking, etc.)
- Test on clean environment before delivery

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                       Mobile Apps                           │
├──────────────────┬──────────────────────────────────────────┤
│   androidApp/    │              iosApp/                     │
│  ┌────────────┐  │           ┌────────────┐                 │
│  │MainActivity│  │           │JaarviApp   │                 │
│  │(Compose)   │  │           │(SwiftUI)   │                 │
│  └─────┬──────┘  │           └─────┬──────┘                 │
│        │         │                 │                        │
│  ┌─────▼──────┐  │           ┌─────▼──────┐                 │
│  │Android     │  │           │iOS         │                 │
│  │Capabilities│  │           │Capabilities│                 │
│  │(Maps,etc)  │  │           │(Swift)     │                 │
│  └────────────┘  │           └────────────┘                 │
└──────────────────┴──────────────────────────────────────────┘
                           │
                ┌──────────▼───────────┐
                │   shared-ui/         │ (CMP)
                │ ┌──────────────────┐ │
                │ │ Screens          │ │
                │ │ HealthScreen     │ │
                │ │ SplashScreen     │ │
                │ └────────┬─────────┘ │
                │          │           │
                │ ┌────────▼─────────┐ │
                │ │ Presenters       │ │
                │ │ (StateFlow)      │ │
                │ └────────┬─────────┘ │
                │          │           │
                │ ┌────────▼─────────┐ │
                │ │ Theme/Components │ │
                │ │ JaarviTheme      │ │
                │ └──────────────────┘ │
                └──────────┬───────────┘
                           │
                ┌──────────▼───────────┐
                │   shared/            │ (KMP)
                │ ┌──────────────────┐ │
                │ │ Domain           │ │
                │ │ Models/UseCases  │ │
                │ └────────┬─────────┘ │
                │          │           │
                │ ┌────────▼─────────┐ │
                │ │ Data             │ │
                │ │ Repositories     │ │
                │ └────────┬─────────┘ │
                │          │           │
                │ ┌────────▼─────────┐ │
                │ │ Network (Ktor)   │ │
                │ │ expect/actual    │ │
                │ └────────┬─────────┘ │
                │          │           │
                │ ┌────────▼─────────┐ │
                │ │ Capabilities     │ │
                │ │ (Interfaces)     │ │
                │ └──────────────────┘ │
                └──────────┬───────────┘
                           │
                    ┌──────▼────────┐
                    │ Backend API   │
                    │ /api/health   │
                    └───────────────┘
```

## Module Responsibilities

| Module | Responsibility | Platform | Languages |
|--------|---------------|----------|-----------|
| `shared/` | Domain, data, network, capability interfaces | Android + iOS | Kotlin |
| `shared-ui/` | Screens, presenters, components, theme | Android + iOS | Kotlin (Compose) |
| `androidApp/` | Android entry point, capability implementations | Android | Kotlin |
| `iosApp/` | iOS entry point, capability implementations | iOS | Swift |

## Migration Plan

### Phase 1: Scaffolding (This Change)
1. Create all modules and Gradle configuration
2. Implement capability interfaces and V1 external implementations
3. Build health check screen to demonstrate full stack
4. Verify both platforms launch and connect to backend

### Phase 2: Feature Development (Post-Scaffold)
1. Implement manual itinerary creation screens
2. Add Today Mode execution flow
3. Integrate document vault
4. Add monetization (browser capability for affiliates)

### Phase 3: V2 Migrations (Future)
1. Migrate MapCapability from external to embedded (Google Maps SDK Android, MapKit iOS)
2. Add advanced notifications
3. Enhance design system with custom components

### Rollback Plan
If scaffolding is incomplete or broken:
1. Delete mobile modules and configuration
2. Revert to backend-only state
3. Re-evaluate mobile strategy (unlikely - scaffolding is low-risk)

## Environment Configuration

### Development Environment Requirements
- **macOS**: Required for iOS development (Xcode)
- **Android Studio**: Hedgehog 2023.1.1+ with KMP plugin
- **Xcode**: 15.0+ for iOS builds
- **JDK**: 17+
- **CocoaPods**: 1.14+ (optional, for future iOS dependencies)

### API Configuration
- **Android Debug**: `http://10.0.2.2:3000/api` (emulator localhost alias)
- **iOS Debug**: `http://localhost:3000/api` (simulator shares host network)
- **Release**: `https://api.jaarvi.app` (future production endpoint)

### Build Variants
- **Debug**: Points to local backend, enables logging, no obfuscation
- **Release**: Production API, ProGuard/R8 enabled, optimized builds

## Open Questions

1. **Design system branding**: Do we have color palette, logo, typography guidelines ready? (Can use placeholder Material theme if not)
2. **iOS code signing**: Do we need Apple Developer account now or later? (Simulator works without, but device testing requires it)
3. **Backend health endpoint format**: Confirm JSON structure for `/api/health` response (assumed: `{ message, timestamp, status }`)
4. **Notification permissions timing**: When should app request notification permission? (Suggest: first time user creates activity with time alert)

## Success Metrics

**Definition of Done**:
- [ ] Android app runs on emulator (API 24+) and displays HealthScreen
- [ ] iOS app runs on simulator (iOS 15+) and displays HealthScreen
- [ ] Health check successfully calls backend `/api/health` and displays response
- [ ] All 4 capability interfaces are defined with V1 implementations
- [ ] Shared tests pass: `./gradlew :shared:allTests` (minimum 9 tests)
- [ ] Design system foundation (colors, typography, spacing) applied to HealthScreen
- [ ] Gradle build completes: `./gradlew build` without errors
- [ ] Documentation includes setup instructions and troubleshooting guide

**Quality Gates**:
- No Android-only imports in `shared/` or `shared-ui/`
- StateFlow used (not LiveData)
- All public APIs have KDoc comments
- Tests follow AAA pattern (Arrange, Act, Assert)
