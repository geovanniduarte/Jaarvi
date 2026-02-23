plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    
    // iOS: enable with -Pjaarvi.includeIos=true (requires Xcode)
    if (project.findProperty("jaarvi.includeIos") == "true") {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach {
            it.binaries.framework {
                baseName = "shared-ui"
                isStatic = true
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Compose Multiplatform
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            
            // Voyager Navigation
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenModel)
            implementation(libs.voyager.koin)
            implementation(libs.voyager.transitions)
            
            // Koin DI
            implementation(libs.koin.core)
            
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
            
            // DateTime
            implementation(libs.kotlinx.datetime)

            // Coil: disabled in commonMain while project uses Kotlin 2.0.x (Coil is built with 2.2.0, causes metadata mismatch).
            // Re-enable after upgrading to Kotlin 2.2.x, or add Coil only in androidMain via expect/actual.
            // implementation(libs.coil.compose)

            // Landscapist — KMP standalone image loading (no Coil/Glide required)
            implementation(libs.landscapist.image)
            implementation(libs.landscapist.placeholder)
            implementation(libs.landscapist.animation)

            // Shared module
            implementation(project(":shared"))
        }
        
        androidMain.dependencies {
            implementation(compose.uiTooling)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.turbine)
        }
    }
}

android {
    namespace = "com.jaarvi.shared.ui"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
