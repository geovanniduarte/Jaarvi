# Jaarvi Mobile Frontend

Cross-platform mobile application for Jaarvi travel assistant, built with Kotlin Multiplatform (KMP) and Compose Multiplatform (CMP).

## 📋 Table of Contents

- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Building](#building)
- [Testing](#testing)
- [Platform Capabilities](#platform-capabilities)
- [Troubleshooting](#troubleshooting)

## 🏗 Architecture

Jaarvi uses a multi-module architecture with maximum code sharing:

```
┌─────────────────────────────────────────────────────────────┐
│                       Mobile Apps                           │
├──────────────────┬──────────────────────────────────────────┤
│   androidApp/    │              iosApp/                     │
│   Platform-      │           Platform-                      │
│   specific code  │           specific code                  │
└──────────────────┴──────────────────────────────────────────┘
                           │
                ┌──────────▼───────────┐
                │   shared-ui/         │ (Compose Multiplatform)
                │ - Screens            │
                │ - Presenters         │
                │ - UI Components      │
                │ - Theme              │
                └──────────┬───────────┘
                           │
                ┌──────────▼───────────┐
                │   shared/            │ (Kotlin Multiplatform)
                │ - Domain Models      │
                │ - Repositories       │
                │ - Network Layer      │
                │ - Capabilities       │
                └──────────────────────┘
```

### Modules

| Module | Description | Platform | Language |
|--------|-------------|----------|----------|
| `shared/` | Core business logic, domain models, data layer | Android + iOS | Kotlin |
| `shared-ui/` | Compose UI screens, presenters, components | Android + iOS | Kotlin (Compose) |
| `androidApp/` | Android entry point, platform capabilities | Android | Kotlin |
| `iosApp/` | iOS entry point, platform capabilities | iOS | Swift |

## 🔧 Prerequisites

### Required

- **macOS**: Required for iOS development (Xcode)
- **JDK**: 17 or higher
- **Android Studio**: Hedgehog 2023.1.1+ with Kotlin Multiplatform plugin
- **Xcode**: 15.0+ for iOS builds

### Optional

- **CocoaPods**: 1.14+ (for future iOS dependencies)

### Backend

The mobile app requires the Jaarvi backend to be running:

```bash
cd backend
npm install
npm run dev
```

Backend should be available at:
- Android emulator: `http://10.0.2.2:3000/api`
- iOS simulator: `http://localhost:3000/api`

## 📁 Project Structure

```
Jaarvi/
├── shared/                          # Shared Kotlin Multiplatform module
│   ├── src/
│   │   ├── commonMain/kotlin/       # Platform-agnostic code
│   │   │   ├── capabilities/        # Platform capability interfaces
│   │   │   ├── data/               # Repositories, data sources, mappers
│   │   │   ├── domain/             # Domain models, repository interfaces
│   │   │   ├── network/            # Ktor API client, DTOs
│   │   │   └── di/                 # Koin DI modules
│   │   ├── androidMain/kotlin/      # Android-specific implementations
│   │   ├── iosMain/kotlin/          # iOS-specific implementations
│   │   └── commonTest/kotlin/       # Shared unit tests
│   └── build.gradle.kts
│
├── shared-ui/                       # Shared Compose UI module
│   ├── src/
│   │   ├── commonMain/kotlin/       # Shared Compose UI
│   │   │   ├── screens/            # Screen composables & presenters
│   │   │   ├── components/         # Reusable UI components
│   │   │   ├── theme/              # Colors, typography, spacing
│   │   │   ├── navigation/         # Voyager navigation setup
│   │   │   └── di/                 # UI Koin module
│   │   ├── iosMain/kotlin/          # iOS UI helpers
│   │   └── commonTest/kotlin/       # UI/Presenter tests
│   └── build.gradle.kts
│
├── androidApp/                      # Android application
│   ├── src/main/
│   │   ├── kotlin/
│   │   │   ├── capabilities/       # Android capability implementations
│   │   │   └── di/                 # Android Koin module
│   │   ├── AndroidManifest.xml
│   │   └── res/                    # Android resources
│   └── build.gradle.kts
│
├── iosApp/                          # iOS application
│   └── iosApp/
│       ├── JaarviApp.swift         # App entry point
│       ├── ContentView.swift       # Compose wrapper
│       ├── IOSKoinHelper.swift     # Koin initialization bridge
│       ├── Capabilities/           # iOS capability implementations
│       └── Info.plist
│
├── gradle/
│   └── libs.versions.toml          # Version catalog
├── settings.gradle.kts
├── build.gradle.kts
└── gradle.properties
```

## 🚀 Getting Started

### 1. Clone and Open Project

```bash
git clone <repository-url>
cd Jaarvi
```

Open the project in Android Studio with the Kotlin Multiplatform plugin installed.

### 2. Sync Gradle

```bash
./gradlew build
```

This will download dependencies and build all modules.

### 3. Run on Android

**Option 1: Android Studio**
1. Select `androidApp` run configuration
2. Choose an emulator (API 24+) or physical device
3. Click Run

**Option 2: Command Line**
```bash
./gradlew :androidApp:installDebug
adb shell am start -n com.jaarvi.android/.MainActivity
```

### 4. Run on iOS

**Option 1: Xcode**
1. Open `iosApp/Jaarvi.xcodeproj` in Xcode
2. Select a simulator (iOS 15.0+)
3. Click Run (⌘R)

**Option 2: Command Line**
```bash
cd iosApp
xcodebuild -scheme Jaarvi -sdk iphonesimulator -configuration Debug
```

## 🔨 Building

### Build All Modules

```bash
./gradlew build
```

### Build Specific Modules

```bash
./gradlew :shared:build          # Build shared module
./gradlew :shared-ui:build       # Build shared UI
./gradlew :androidApp:build      # Build Android app
```

### Generate Android APK

```bash
./gradlew :androidApp:assembleDebug     # Debug APK
./gradlew :androidApp:assembleRelease   # Release APK
```

APKs will be in `androidApp/build/outputs/apk/`

### Build iOS Framework

```bash
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

Framework will be in `shared/build/bin/iosSimulatorArm64/debugFramework/`

## 🧪 Testing

### Run All Tests

```bash
./gradlew allTests
```

### Run Shared Module Tests

```bash
./gradlew :shared:allTests
```

### Run Shared UI Tests

```bash
./gradlew :shared-ui:allTests
```

### Test Coverage

The test suite includes:
- **Repository tests**: 3 tests (success, failure, mapping)
- **Presenter tests**: 6 tests (state transitions, events, immutability)

All tests use:
- `kotlin-test` for assertions
- `kotlinx-coroutines-test` for coroutine testing
- `Turbine` for StateFlow testing

## 🔌 Platform Capabilities

Jaarvi uses a capability pattern for platform-specific features:

### MapCapability
- **V1 (Current)**: External navigation via deep links
  - Android: Opens Google Maps with `geo:` URI
  - iOS: Opens Apple Maps with MKMapItem
- **V2 (Future)**: Embedded maps with Google Maps SDK / MapKit

### BrowserCapability
- **V1 (Current)**: In-app browser for affiliate links
  - Android: Chrome Custom Tabs
  - iOS: SFSafariViewController

### NotificationCapability
- **V1 (Current)**: Basic notification channel setup
  - Android: NotificationChannel for Android 8+
  - iOS: UNUserNotificationCenter registration
- **V2 (Future)**: Scheduling, push notifications, rich content

### DocumentVaultCapability
- **V1 (Current)**: Placeholder (not implemented)
- **V2 (Future)**: Secure local storage for tickets, permits, documents

## 🛠 Troubleshooting

### Common Issues

#### 1. Gradle Build Fails

**Problem**: `Could not resolve all dependencies`

**Solution**:
```bash
./gradlew clean
rm -rf ~/.gradle/caches
./gradlew build --refresh-dependencies
```

#### 2. iOS Build Fails: Framework Not Found

**Problem**: `ld: framework not found shared`

**Solution**:
1. Build the shared framework first:
   ```bash
   ./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
   ```
2. In Xcode, verify framework search paths include:
   ```
   $(SRCROOT)/../shared/build/bin/iosSimulatorArm64/debugFramework
   ```

#### 3. Android Emulator Can't Reach Backend

**Problem**: Network error when calling `http://localhost:3000/api`

**Solution**: Use emulator's localhost alias:
```kotlin
// In ApiConfig.android.kt
const val API_BASE_URL = "http://10.0.2.2:3000/api"
```

#### 4. iOS Simulator Can't Reach Backend

**Problem**: Network error when calling local backend

**Solution**: Ensure `Info.plist` includes:
```xml
<key>NSAllowsLocalNetworking</key>
<true/>
```

#### 5. Tests Fail with Timeout

**Problem**: StateFlow tests timeout

**Solution**: Ensure `TestDispatcher` is used:
```kotlin
@OptIn(ExperimentalCoroutinesApi::class)
class MyTest {
    private val testDispatcher = StandardTestDispatcher()
    
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }
    
    @Test
    fun myTest() = runTest {
        // Test code
        testDispatcher.scheduler.advanceUntilIdle()
    }
}
```

#### 6. Compose Preview Not Working

**Problem**: `@Preview` annotations don't work in multiplatform modules

**Solution**: Previews are not supported in `commonMain`. Use platform-specific preview modules or test on actual devices/emulators.

### Gradle Version Issues

If you encounter Gradle version issues:

```bash
./gradlew wrapper --gradle-version=8.5 --distribution-type=bin
```

### Clearing All Build Artifacts

```bash
./gradlew clean
rm -rf build/
rm -rf shared/build/
rm -rf shared-ui/build/
rm -rf androidApp/build/
rm -rf .gradle/
```

## 📚 Additional Resources

- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Voyager Navigation](https://voyager.adriel.cafe/)
- [Koin Dependency Injection](https://insert-koin.io/)
- [Ktor Client](https://ktor.io/docs/client.html)

## 📝 Development Workflow

### Adding a New Screen

1. Create presenter in `shared-ui/src/commonMain/.../screens/<name>/`
   - `<Name>UiState.kt` (immutable data class)
   - `<Name>UiEvent.kt` (sealed interface)
   - `<Name>Presenter.kt` (extends ScreenModel)
   - `<Name>Screen.kt` (composable implementing Screen)

2. Register presenter in `shared-ui/src/commonMain/.../di/UiModule.kt`:
   ```kotlin
   factory { MyPresenter(repository = get()) }
   ```

3. Add navigation in existing screen:
   ```kotlin
   val navigator = LocalNavigator.currentOrThrow
   navigator.push(MyScreen())
   ```

### Adding a New Capability

1. Define interface in `shared/src/commonMain/.../capabilities/`:
   ```kotlin
   interface MyCapability {
       suspend fun doSomething(): Result<Unit>
   }
   ```

2. Implement for Android in `androidApp/.../capabilities/`:
   ```kotlin
   class AndroidMyCapability(context: Context) : MyCapability {
       override suspend fun doSomething() = runCatching { /* ... */ }
   }
   ```

3. Implement for iOS in `iosApp/.../Capabilities/`:
   ```swift
   class IOSMyCapability: MyCapability {
       func doSomething() async throws { /* ... */ }
   }
   ```

4. Register in platform DI modules

## 🎯 Next Steps

After this scaffold, you can:

1. **Build features**: Itinerary creation, Today Mode, document vault
2. **Enhance design system**: Custom components, animations, dark mode
3. **Add offline support**: SQLDelight for local persistence
4. **Implement V2 capabilities**: Embedded maps, advanced notifications
5. **Set up CI/CD**: GitHub Actions for automated builds and tests

---

**Happy Coding! 🚀**
