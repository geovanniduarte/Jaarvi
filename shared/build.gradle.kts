import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
}

/** API base URL: `local.properties` → `gradle.properties` → emulator default. */
private fun Project.resolveApiBaseUrl(): String {
    val fromLocal = rootProject.file("local.properties")
        .takeIf { it.exists() }
        ?.inputStream()
        ?.use { stream ->
            Properties().apply { load(stream) }.getProperty("jaarvi.apiBaseUrl")?.trim()
        }
        ?.takeIf { it.isNotEmpty() }
    val fromGradle = findProperty("jaarvi.apiBaseUrl") as String?
    return fromLocal ?: fromGradle ?: "http://10.0.2.2:30080/api"
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
                baseName = "shared"
                isStatic = true
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
            
            // Serialization
            implementation(libs.kotlinx.serialization.json)
            
            // Ktor Client
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            
            // Koin DI
            implementation(libs.koin.core)

            // DateTime
            implementation(libs.kotlinx.datetime)
        }
        
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        if (project.findProperty("jaarvi.includeIos") == "true") {
            iosMain.dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

android {
    namespace = "com.jaarvi.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        // Override in local.properties (gitignored), e.g. jaarvi.apiBaseUrl=http://192.168.x.x:30080/api
        buildConfigField("String", "API_BASE_URL", "\"${project.resolveApiBaseUrl()}\"")
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
