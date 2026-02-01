# Frontend Mobile Scaffolding - Jaarvi

## [Original]

> Prompt para crear el scaffolding de la aplicación móvil frontend basado en los estándares definidos en `frontend-standards.mdc` y la arquitectura móvil del proyecto.

---

### Context

Building the mobile frontend scaffolding for Jaarvi using Kotlin Multiplatform (KMP) + Compose Multiplatform (CMP) with shared UI and business logic for Android and iOS.

---

## [Enhanced]

### User Story

**As a** development team member  
**I want** a fully configured mobile frontend scaffolding for Jaarvi  
**So that** I can immediately start developing features with shared UI and logic across Android and iOS

### Description

This task creates the foundational mobile frontend infrastructure for the Jaarvi travel planning application. The scaffolding includes:

- Complete KMP project structure with shared core and UI modules
- Gradle configuration with version catalog
- Platform capability abstractions (maps, notifications, browser, document vault)
- Health check screen to verify backend connectivity
- Dependency injection setup with Koin
- Navigation setup with Voyager
- Design system foundation (theme, colors, typography)

---

## Prerequisites

### Development Environment Requirements

| Tool | Minimum Version | Purpose |
|------|-----------------|---------|
| **Android Studio** | Hedgehog 2023.1.1+ | Android development and KMP support |
| **Xcode** | 15.0+ | iOS development and simulator |
| **JDK** | 17+ | Kotlin compilation |
| **Kotlin Plugin** | 1.9.22+ | Multiplatform support |
| **CocoaPods** | 1.14+ | iOS dependency management (optional) |

### System Requirements

- **macOS** (required for iOS development)
- **RAM**: 16GB recommended
- **Disk**: 20GB free space for Android SDK, Xcode, and simulators

### Verify Installation

```bash
# Check JDK
java -version

# Check Android SDK (set ANDROID_HOME)
echo $ANDROID_HOME

# Check Xcode
xcode-select -p
xcodebuild -version

# Check CocoaPods (optional)
pod --version
```

---

## Technology Stack

| Category | Technology | Version | Purpose |
|----------|------------|---------|---------|
| **Language** | Kotlin | 1.9.22 | Shared and Android code |
| **Language** | Swift | 5.9+ | iOS platform code |
| **UI Framework** | Compose Multiplatform | 1.6.0 | Shared UI |
| **Networking** | Ktor Client | 2.3.7 | HTTP client |
| **Serialization** | Kotlinx Serialization | 1.6.2 | JSON parsing |
| **Database** | SQLDelight | 2.0.1 | Local persistence |
| **Navigation** | Voyager | 1.0.0 | Screen navigation |
| **DI** | Koin | 3.5.3 | Dependency injection |
| **Async** | Coroutines | 1.8.0 | Async operations |

---

## Project Structure

```
Jaarvi/
├── shared/                     # KMP: Shared core (domain/data/platform abstractions)
│   ├── src/
│   │   ├── commonMain/
│   │   │   └── kotlin/
│   │   │       ├── domain/
│   │   │       │   ├── models/          # Domain entities
│   │   │       │   ├── repositories/    # Repository interfaces
│   │   │       │   └── usecases/        # Use cases
│   │   │       ├── data/
│   │   │       │   ├── repositories/    # Repository implementations
│   │   │       │   ├── datasources/     # Local & remote data sources
│   │   │       │   └── mappers/         # DTO <-> Domain mappers
│   │   │       ├── network/
│   │   │       │   ├── api/             # Ktor API client
│   │   │       │   ├── dto/             # Data Transfer Objects
│   │   │       │   └── HttpClientFactory.kt
│   │   │       ├── database/
│   │   │       │   └── DatabaseDriverFactory.kt  # expect/actual
│   │   │       ├── capabilities/
│   │   │       │   ├── MapCapability.kt          # External/embedded maps
│   │   │       │   ├── NotificationCapability.kt # Push notifications
│   │   │       │   ├── BrowserCapability.kt      # In-app browser
│   │   │       │   └── DocumentVaultCapability.kt # Secure storage
│   │   │       └── di/
│   │   │           └── CommonModule.kt  # Koin DI module
│   │   ├── commonTest/
│   │   │   └── kotlin/                  # Shared tests
│   │   ├── androidMain/
│   │   │   └── kotlin/
│   │   │       └── database/
│   │   │           └── DatabaseDriverFactory.android.kt
│   │   └── iosMain/
│   │       └── kotlin/
│   │           └── database/
│   │               └── DatabaseDriverFactory.ios.kt
│   └── build.gradle.kts
│
├── shared-ui/                  # CMP: Shared UI (screens/components/state holders)
│   ├── src/
│   │   ├── commonMain/
│   │   │   └── kotlin/
│   │   │       ├── screens/
│   │   │       │   ├── splash/
│   │   │       │   │   ├── SplashScreen.kt
│   │   │       │   │   └── SplashPresenter.kt
│   │   │       │   └── health/
│   │   │       │       ├── HealthScreen.kt
│   │   │       │       └── HealthPresenter.kt
│   │   │       ├── components/
│   │   │       │   ├── buttons/
│   │   │       │   ├── cards/
│   │   │       │   └── inputs/
│   │   │       ├── theme/
│   │   │       │   ├── JaarviTheme.kt
│   │   │       │   ├── Colors.kt
│   │   │       │   ├── Typography.kt
│   │   │       │   └── Spacing.kt
│   │   │       ├── navigation/
│   │   │       │   └── AppNavigation.kt
│   │   │       └── di/
│   │   │           └── UiModule.kt
│   │   ├── commonTest/
│   │   │   └── kotlin/
│   │   ├── androidMain/
│   │   │   └── kotlin/
│   │   └── iosMain/
│   │       └── kotlin/
│   └── build.gradle.kts
│
├── androidApp/                 # Android: Entry point + platform capabilities
│   ├── src/
│   │   └── main/
│   │       ├── kotlin/
│   │       │   ├── com/jaarvi/android/
│   │       │   │   ├── MainActivity.kt
│   │       │   │   ├── JaarviApplication.kt
│   │       │   │   └── capabilities/
│   │       │   │       ├── AndroidMapCapability.kt
│   │       │   │       ├── AndroidNotificationCapability.kt
│   │       │   │       ├── AndroidBrowserCapability.kt
│   │       │   │       └── AndroidDocumentVaultCapability.kt
│   │       ├── res/
│   │       │   ├── values/
│   │       │   │   ├── strings.xml
│   │       │   │   ├── colors.xml
│   │       │   │   └── themes.xml
│   │       │   ├── drawable/
│   │       │   └── mipmap-*/
│   │       └── AndroidManifest.xml
│   └── build.gradle.kts
│
├── iosApp/                     # iOS: Entry point + platform capabilities
│   ├── Jaarvi/
│   │   ├── capabilities/
│   │   │   ├── IOSMapCapability.swift
│   │   │   ├── IOSNotificationCapability.swift
│   │   │   ├── IOSBrowserCapability.swift
│   │   │   └── IOSDocumentVaultCapability.swift
│   │   ├── ContentView.swift
│   │   ├── JaarviApp.swift
│   │   ├── Assets.xcassets/
│   │   └── Info.plist
│   ├── Jaarvi.xcodeproj/
│   └── Podfile (if using CocoaPods)
│
├── gradle/
│   ├── wrapper/
│   │   ├── gradle-wrapper.jar
│   │   └── gradle-wrapper.properties
│   └── libs.versions.toml      # Version catalog
├── build.gradle.kts            # Root build file
├── settings.gradle.kts         # Module settings
├── gradle.properties           # Gradle properties
├── local.properties            # Local SDK paths (gitignored)
├── .gitignore                  # Git ignore rules
└── README.md                   # Project documentation
```

---

## Files to Create (Complete List)

### Total File Count: 55 files

### Gradle Configuration (5 files)

| File | Purpose |
|------|---------|
| `settings.gradle.kts` | Module includes and plugin management |
| `build.gradle.kts` (root) | Root project configuration |
| `gradle/libs.versions.toml` | Centralized version catalog |
| `gradle.properties` | Gradle and Kotlin settings |
| `.gitignore` | Git ignore rules |

### Shared Module - 18 files (`shared/`)

| File | Purpose |
|------|---------|
| `shared/build.gradle.kts` | KMP module configuration |
| `shared/src/commonMain/kotlin/com/jaarvi/shared/domain/models/HealthStatus.kt` | Health domain model |
| `shared/src/commonMain/kotlin/com/jaarvi/shared/domain/repositories/HealthRepository.kt` | Repository interface |
| `shared/src/commonMain/kotlin/com/jaarvi/shared/data/repositories/HealthRepositoryImpl.kt` | Repository implementation |
| `shared/src/commonMain/kotlin/com/jaarvi/shared/data/datasources/RemoteDataSource.kt` | Remote data source |
| `shared/src/commonMain/kotlin/com/jaarvi/shared/data/mappers/HealthMapper.kt` | DTO to domain mapper |
| `shared/src/commonMain/kotlin/com/jaarvi/shared/network/api/JaarviApiClient.kt` | Ktor API client |
| `shared/src/commonMain/kotlin/com/jaarvi/shared/network/dto/HealthResponseDto.kt` | API response DTO |
| `shared/src/commonMain/kotlin/com/jaarvi/shared/network/HttpClientFactory.kt` | Ktor client factory |
| `shared/src/commonMain/kotlin/com/jaarvi/shared/network/ApiConfig.kt` | API configuration (base URL) |
| `shared/src/commonMain/kotlin/com/jaarvi/shared/capabilities/MapCapability.kt` | Map abstraction |
| `shared/src/commonMain/kotlin/com/jaarvi/shared/capabilities/NotificationCapability.kt` | Notification abstraction |
| `shared/src/commonMain/kotlin/com/jaarvi/shared/capabilities/BrowserCapability.kt` | Browser abstraction |
| `shared/src/commonMain/kotlin/com/jaarvi/shared/capabilities/DocumentVaultCapability.kt` | Document storage abstraction |
| `shared/src/commonMain/kotlin/com/jaarvi/shared/di/CommonModule.kt` | Koin DI configuration |
| `shared/src/androidMain/kotlin/com/jaarvi/shared/network/HttpClientFactory.android.kt` | Android Ktor engine |
| `shared/src/iosMain/kotlin/com/jaarvi/shared/network/HttpClientFactory.ios.kt` | iOS Ktor engine |
| `shared/src/commonTest/kotlin/com/jaarvi/shared/data/HealthRepositoryTest.kt` | Repository tests |

### Shared UI Module - 16 files (`shared-ui/`)

| File | Purpose |
|------|---------|
| `shared-ui/build.gradle.kts` | CMP module configuration |
| `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/screens/health/HealthScreen.kt` | Health check screen |
| `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/screens/health/HealthPresenter.kt` | Health screen state holder |
| `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/screens/health/HealthUiState.kt` | UI state data class |
| `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/screens/health/HealthUiEvent.kt` | UI events sealed interface |
| `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/screens/splash/SplashScreen.kt` | Splash screen |
| `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/components/buttons/JaarviButton.kt` | Primary button component |
| `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/components/buttons/JaarviTextButton.kt` | Text button component |
| `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/components/cards/StatusCard.kt` | Status display card |
| `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/components/loading/LoadingIndicator.kt` | Loading spinner |
| `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/theme/JaarviTheme.kt` | App theme |
| `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/theme/Colors.kt` | Color definitions |
| `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/theme/Typography.kt` | Typography definitions |
| `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/theme/Spacing.kt` | Spacing constants |
| `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/navigation/AppNavigation.kt` | Voyager navigation |
| `shared-ui/src/commonMain/kotlin/com/jaarvi/ui/di/UiModule.kt` | UI Koin module |
| `shared-ui/src/commonTest/kotlin/com/jaarvi/ui/screens/health/HealthPresenterTest.kt` | Presenter tests |

### Android App - 10 files (`androidApp/`)

| File | Purpose |
|------|---------|
| `androidApp/build.gradle.kts` | Android app configuration |
| `androidApp/src/main/kotlin/com/jaarvi/android/MainActivity.kt` | Main activity |
| `androidApp/src/main/kotlin/com/jaarvi/android/JaarviApplication.kt` | Application class |
| `androidApp/src/main/kotlin/com/jaarvi/android/di/AndroidModule.kt` | Android Koin module |
| `androidApp/src/main/kotlin/com/jaarvi/android/capabilities/AndroidMapCapability.kt` | Android map impl |
| `androidApp/src/main/kotlin/com/jaarvi/android/capabilities/AndroidBrowserCapability.kt` | Android browser impl |
| `androidApp/src/main/AndroidManifest.xml` | Android manifest |
| `androidApp/src/main/res/values/strings.xml` | String resources |
| `androidApp/src/main/res/values/colors.xml` | Color resources |
| `androidApp/src/main/res/values/themes.xml` | Theme resources |

### iOS App - 6 files (`iosApp/`)

| File | Purpose |
|------|---------|
| `iosApp/Jaarvi/JaarviApp.swift` | iOS app entry point |
| `iosApp/Jaarvi/ContentView.swift` | Root SwiftUI view |
| `iosApp/Jaarvi/IOSKoinHelper.swift` | Koin integration helper |
| `iosApp/Jaarvi/capabilities/IOSMapCapability.swift` | iOS map implementation |
| `iosApp/Jaarvi/capabilities/IOSBrowserCapability.swift` | iOS browser implementation |
| `iosApp/Jaarvi/Info.plist` | iOS app configuration |

---

## Configuration Files Content

### `gradle.properties`

```properties
# Gradle
org.gradle.jvmargs=-Xmx4096M -Dfile.encoding=UTF-8 -Dkotlin.daemon.jvm.options\="-Xmx4096M"
org.gradle.parallel=true
org.gradle.caching=true

# Kotlin
kotlin.code.style=official
kotlin.native.binary.memoryModel=experimental
kotlin.mpp.androidSourceSetLayoutVersion=2

# Android
android.useAndroidX=true
android.nonTransitiveRClass=true

# Compose
org.jetbrains.compose.experimental.uikit.enabled=true
```

### `settings.gradle.kts`

```kotlin
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Jaarvi"

include(":shared")
include(":shared-ui")
include(":androidApp")
```

### `.gitignore`

```gitignore
# Gradle
.gradle/
build/
!gradle/wrapper/gradle-wrapper.jar

# IDE
.idea/
*.iml

# Kotlin
*.class

# Android
local.properties
*.apk
*.aab

# iOS
iosApp/Pods/
iosApp/*.xcworkspace
iosApp/DerivedData/
*.xcuserstate

# Environment
.env
.env.local

# macOS
.DS_Store
```

---

## Environment Configuration

### Android (`androidApp/build.gradle.kts`)

```kotlin
android {
    // ...
    buildTypes {
        debug {
            buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:3000/api\"")
        }
        release {
            buildConfigField("String", "API_BASE_URL", "\"https://api.jaarvi.app\"")
        }
    }
}
```

### iOS (`Info.plist` additions)

```xml
<key>NSAppTransportSecurity</key>
<dict>
    <key>NSAllowsLocalNetworking</key>
    <true/>
</dict>
```

### Shared Network Config

```kotlin
// shared/src/commonMain/kotlin/.../network/ApiConfig.kt
expect object ApiConfig {
    val baseUrl: String
}

// shared/src/androidMain/kotlin/.../network/ApiConfig.android.kt
actual object ApiConfig {
    actual val baseUrl: String = BuildConfig.API_BASE_URL
}

// shared/src/iosMain/kotlin/.../network/ApiConfig.ios.kt
actual object ApiConfig {
    actual val baseUrl: String = "http://localhost:3000/api"
}
```

---

## Dependencies (Version Catalog)

```toml
[versions]
kotlin = "1.9.22"
compose-multiplatform = "1.6.0"
ktor = "2.3.7"
kotlinx-serialization = "1.6.2"
kotlinx-coroutines = "1.8.0"
sqldelight = "2.0.1"
voyager = "1.0.0"
koin = "3.5.3"
android-gradle-plugin = "8.2.0"
android-compileSdk = "34"
android-minSdk = "24"
android-targetSdk = "34"

[libraries]
# Kotlin
kotlinx-coroutines-core = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version.ref = "kotlinx-coroutines" }
kotlinx-serialization-json = { group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-json", version.ref = "kotlinx-serialization" }

# Ktor
ktor-client-core = { group = "io.ktor", name = "ktor-client-core", version.ref = "ktor" }
ktor-client-content-negotiation = { group = "io.ktor", name = "ktor-client-content-negotiation", version.ref = "ktor" }
ktor-serialization-kotlinx-json = { group = "io.ktor", name = "ktor-serialization-kotlinx-json", version.ref = "ktor" }
ktor-client-okhttp = { group = "io.ktor", name = "ktor-client-okhttp", version.ref = "ktor" }
ktor-client-darwin = { group = "io.ktor", name = "ktor-client-darwin", version.ref = "ktor" }
ktor-client-logging = { group = "io.ktor", name = "ktor-client-logging", version.ref = "ktor" }

# SQLDelight
sqldelight-runtime = { group = "app.cash.sqldelight", name = "runtime", version.ref = "sqldelight" }
sqldelight-coroutines = { group = "app.cash.sqldelight", name = "coroutines-extensions", version.ref = "sqldelight" }
sqldelight-driver-android = { group = "app.cash.sqldelight", name = "android-driver", version.ref = "sqldelight" }
sqldelight-driver-native = { group = "app.cash.sqldelight", name = "native-driver", version.ref = "sqldelight" }

# Voyager Navigation
voyager-navigator = { group = "cafe.adriel.voyager", name = "voyager-navigator", version.ref = "voyager" }
voyager-screenModel = { group = "cafe.adriel.voyager", name = "voyager-screenmodel", version.ref = "voyager" }
voyager-koin = { group = "cafe.adriel.voyager", name = "voyager-koin", version.ref = "voyager" }
voyager-transitions = { group = "cafe.adriel.voyager", name = "voyager-transitions", version.ref = "voyager" }

# Koin DI
koin-core = { group = "io.insert-koin", name = "koin-core", version.ref = "koin" }
koin-android = { group = "io.insert-koin", name = "koin-android", version.ref = "koin" }

# Testing
kotlin-test = { group = "org.jetbrains.kotlin", name = "kotlin-test", version.ref = "kotlin" }
kotlinx-coroutines-test = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-test", version.ref = "kotlinx-coroutines" }
turbine = { group = "app.cash.turbine", name = "turbine", version = "1.0.0" }

[plugins]
kotlin-multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
compose-multiplatform = { id = "org.jetbrains.compose", version.ref = "compose-multiplatform" }
android-application = { id = "com.android.application", version.ref = "android-gradle-plugin" }
android-library = { id = "com.android.library", version.ref = "android-gradle-plugin" }
sqldelight = { id = "app.cash.sqldelight", version.ref = "sqldelight" }
```

---

## State Management Pattern

### UiState (Immutable data class)

```kotlin
data class HealthUiState(
    val isLoading: Boolean = true,
    val message: String? = null,
    val error: String? = null,
    val isConnected: Boolean = false,
    val timestamp: String? = null
)
```

### UiEvent (User intents)

```kotlin
sealed interface HealthUiEvent {
    data object CheckHealth : HealthUiEvent
    data object Retry : HealthUiEvent
}
```

### Presenter (StateFlow + event handling)

```kotlin
class HealthPresenter(
    private val healthRepository: HealthRepository
) : ScreenModel {
    private val _state = MutableStateFlow(HealthUiState())
    val state: StateFlow<HealthUiState> = _state.asStateFlow()
    
    init {
        checkHealth()
    }
    
    fun onEvent(event: HealthUiEvent) {
        when (event) {
            is HealthUiEvent.CheckHealth -> checkHealth()
            is HealthUiEvent.Retry -> checkHealth()
        }
    }
    
    private fun checkHealth() {
        screenModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            healthRepository.getHealth()
                .onSuccess { health ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = health.message,
                            isConnected = true,
                            timestamp = health.timestamp
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message,
                            isConnected = false
                        )
                    }
                }
        }
    }
}
```

---

## Capability Pattern (Platform Abstraction)

### Interface Definition (commonMain)

```kotlin
// shared/src/commonMain/kotlin/capabilities/MapCapability.kt
interface MapCapability {
    /**
     * Opens external navigation app with the given coordinates
     * V1: Deep links to Google Maps (Android) or Apple Maps (iOS)
     * V2: Will support embedded maps
     */
    suspend fun openExternalNavigation(
        latitude: Double,
        longitude: Double,
        label: String? = null
    ): Result<Unit>
    
    /**
     * Checks if navigation app is available
     */
    fun isNavigationAvailable(): Boolean
}
```

### Android Implementation

```kotlin
// androidApp/.../capabilities/AndroidMapCapability.kt
class AndroidMapCapability(
    private val context: Context
) : MapCapability {
    override suspend fun openExternalNavigation(
        latitude: Double,
        longitude: Double,
        label: String?
    ): Result<Unit> = runCatching {
        val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($label)")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
    
    override fun isNavigationAvailable(): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0"))
        return intent.resolveActivity(context.packageManager) != null
    }
}
```

### iOS Implementation

```swift
// iosApp/Jaarvi/capabilities/IOSMapCapability.swift
import MapKit
import shared

class IOSMapCapability: MapCapability {
    func openExternalNavigation(
        latitude: Double,
        longitude: Double,
        label: String?
    ) async throws {
        let coordinate = CLLocationCoordinate2D(
            latitude: latitude,
            longitude: longitude
        )
        let placemark = MKPlacemark(coordinate: coordinate)
        let mapItem = MKMapItem(placemark: placemark)
        mapItem.name = label
        mapItem.openInMaps(launchOptions: [
            MKLaunchOptionsDirectionsModeKey: MKLaunchOptionsDirectionsModeDriving
        ])
    }
    
    func isNavigationAvailable() -> Bool {
        return true // Apple Maps always available on iOS
    }
}
```

---

## Acceptance Criteria

### AC1: Gradle Configuration
- [ ] `settings.gradle.kts` includes all modules (shared, shared-ui, androidApp)
- [ ] Version catalog (`libs.versions.toml`) has all dependencies
- [ ] `./gradlew build` completes without errors
- [ ] Gradle wrapper is version 8.5+

### AC2: Shared Module
- [ ] `shared` module compiles for both Android and iOS targets
- [ ] No Android-only imports in `commonMain`
- [ ] All 4 capability interfaces are defined
- [ ] Ktor client is configured with JSON serialization and logging
- [ ] Koin module exports all dependencies
- [ ] Package structure follows `com.jaarvi.shared.*`

### AC3: Shared UI Module
- [ ] `shared-ui` module compiles for both targets
- [ ] HealthScreen displays loading, success, and error states
- [ ] Theme (colors, typography, spacing) is defined with Jaarvi branding
- [ ] Voyager navigation is configured with transitions
- [ ] No `androidx.lifecycle.ViewModel` usage
- [ ] At least 2 reusable components (JaarviButton, StatusCard)

### AC4: Android App
- [ ] App launches on Android emulator (API 24+)
- [ ] HealthScreen is displayed as initial screen
- [ ] API call to `/api/health` works (with backend running)
- [ ] Capability implementations are registered with Koin
- [ ] App icon and splash screen are configured

### AC5: iOS App
- [ ] App builds with Xcode 15+
- [ ] App launches on iOS 15+ simulator
- [ ] HealthScreen is displayed via Compose Multiplatform
- [ ] Shared module is properly linked
- [ ] Capability implementations are available

### AC6: Testing
- [ ] `./gradlew :shared:allTests` passes
- [ ] `./gradlew :shared-ui:allTests` passes
- [ ] HealthPresenter tests cover all state transitions
- [ ] HealthRepository tests cover success and failure cases
- [ ] Turbine is used for StateFlow testing

---

## Unit Tests Required

### HealthPresenterTest (6 tests)

```kotlin
class HealthPresenterTest {
    private val mockRepository = mockk<HealthRepository>()
    private lateinit var presenter: HealthPresenter
    
    @BeforeTest
    fun setup() {
        presenter = HealthPresenter(mockRepository)
    }
    
    @Test
    fun `should emit loading state initially`() = runTest {
        presenter.state.test {
            val initial = awaitItem()
            assertTrue(initial.isLoading)
        }
    }
    
    @Test
    fun `should emit success state when health check succeeds`()
    
    @Test
    fun `should emit error state when health check fails`()
    
    @Test
    fun `should retry health check on Retry event`()
    
    @Test
    fun `should set isConnected true on success`()
    
    @Test
    fun `should set isConnected false on failure`()
}
```

### HealthRepositoryTest (3 tests)

```kotlin
class HealthRepositoryTest {
    @Test
    fun `should return HealthStatus when API returns success`()
    
    @Test
    fun `should return failure when API throws exception`()
    
    @Test
    fun `should map DTO to domain model correctly`()
}
```

---

## Non-Functional Requirements

### Performance
- [ ] Ktor client uses appropriate engine per platform (OkHttp for Android, Darwin for iOS)
- [ ] StateFlow is used (not LiveData) for reactive state
- [ ] Coroutines handle all async operations with proper dispatchers
- [ ] Images and assets are optimized for mobile

### Code Quality
- [ ] No `Any` or untyped collections
- [ ] All public functions have KDoc comments
- [ ] Consistent naming conventions (PascalCase classes, camelCase functions)
- [ ] No hardcoded strings in UI (use resources)

### Platform Compatibility
- [ ] Android minSdk = 24 (Android 7.0)
- [ ] Android targetSdk = 34 (Android 14)
- [ ] iOS deployment target = 15.0
- [ ] No platform-specific code in commonMain

### Security
- [ ] API base URL is configurable per build type
- [ ] No secrets in source code
- [ ] HTTPS enforced in release builds
- [ ] Local networking allowed only in debug builds

---

## Implementation Steps

### Phase 1: Gradle Setup (1-2 hours)
1. Create `settings.gradle.kts` with all modules
2. Create `gradle/libs.versions.toml` version catalog
3. Create root `build.gradle.kts`
4. Configure `gradle.properties`
5. Create `.gitignore`
6. Verify: `./gradlew tasks` works

### Phase 2: Shared Module (2-3 hours)
1. Create `shared/build.gradle.kts` with KMP targets
2. Define capability interfaces in `commonMain`
3. Implement Ktor HTTP client with expect/actual
4. Create health domain model and repository
5. Configure Koin DI module
6. Verify: `./gradlew :shared:build` passes

### Phase 3: Shared UI Module (2-3 hours)
1. Create `shared-ui/build.gradle.kts` with CMP
2. Define theme (colors, typography, spacing)
3. Create reusable components (JaarviButton, StatusCard)
4. Create HealthScreen and HealthPresenter
5. Configure Voyager navigation
6. Verify: `./gradlew :shared-ui:build` passes

### Phase 4: Android App (1-2 hours)
1. Create `androidApp/build.gradle.kts`
2. Implement JaarviApplication with Koin init
3. Implement MainActivity with Compose
4. Implement Android capabilities
5. Configure resources (strings, colors, themes)
6. Set up AndroidManifest
7. Verify: Run on emulator

### Phase 5: iOS App (1-2 hours)
1. Create Xcode project structure
2. Implement JaarviApp.swift entry point
3. Implement ContentView.swift with shared UI
4. Implement iOS capabilities in Swift
5. Configure Info.plist
6. Verify: Run on simulator

### Phase 6: Testing (1 hour)
1. Write HealthPresenter tests with Turbine
2. Write HealthRepository tests
3. Configure test dependencies
4. Verify: `./gradlew allTests` passes

### Phase 7: Documentation (30 min)
1. Create README.md with setup instructions
2. Document build commands
3. Document environment configuration
4. Add troubleshooting section

---

## Troubleshooting Guide

### Common Issues

| Issue | Solution |
|-------|----------|
| `Unresolved reference: compose` | Add Compose Multiplatform plugin to root build.gradle.kts |
| iOS build fails with framework error | Run `./gradlew :shared:linkDebugFrameworkIosSimulatorArm64` |
| Ktor timeout on Android emulator | Use `10.0.2.2` instead of `localhost` for API URL |
| Koin not found in iOS | Create `IOSKoinHelper.swift` to initialize Koin from Swift |
| Gradle sync fails | Invalidate caches and restart Android Studio |

### iOS Koin Integration

```swift
// iosApp/Jaarvi/IOSKoinHelper.swift
import shared

@MainActor
class IOSKoinHelper {
    static let shared = IOSKoinHelper()
    
    func startKoin() {
        // Initialize Koin for iOS
        let koinApp = CommonModuleKt.doInitKoin()
    }
}
```

---

## Definition of Done

- [ ] All 55 files are created
- [ ] All Gradle modules compile successfully
- [ ] Android app runs on emulator and shows HealthScreen
- [ ] iOS app runs on simulator and shows HealthScreen
- [ ] Health endpoint call works from both platforms (with backend running)
- [ ] All commonTest tests pass (minimum 9 tests)
- [ ] No Android-only imports in shared modules
- [ ] Theme is applied consistently across platforms
- [ ] README.md documents setup and running instructions
- [ ] `.gitignore` excludes all generated files
- [ ] Code follows Kotlin coding conventions

---

## Verification Commands

```bash
# Build all modules
./gradlew build

# Run shared tests
./gradlew :shared:allTests
./gradlew :shared-ui:allTests

# Build Android app
./gradlew :androidApp:assembleDebug

# Build iOS framework
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

# Run Android app on emulator
./gradlew :androidApp:installDebug

# Clean build
./gradlew clean
```

---

## References

- `ai-specs/specs/frontend-standards.mdc` - Frontend development standards
- `readme.md` Section 2.1.1 - Mobile Apps Architecture
- `readme.md` Section 2.3 - Project Structure
- [Compose Multiplatform Documentation](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [Voyager Navigation](https://voyager.adriel.cafe/)
- [Koin Documentation](https://insert-koin.io/)