plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(project(":shared"))
            implementation(project(":shared-ui"))
            
            // Compose Multiplatform
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            
            // Voyager Navigation
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.koin)
            
            // Koin DI
            implementation(libs.koin.core)
            implementation(libs.koin.android)
            
            // Android
            implementation("androidx.activity:activity-compose:1.8.2")
            implementation("androidx.browser:browser:1.7.0")
        }
    }
}

// Landscapist and Compose 1.9.x pull lifecycle 2.9.x, which breaks Voyager 1.0.0.
// Force the entire lifecycle BOM to 2.8.7, the last version Voyager is compatible with.
configurations.all {
    resolutionStrategy {
        val lifecycleVersion = "2.8.7"
        force("androidx.lifecycle:lifecycle-common:$lifecycleVersion")
        force("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")
        force("androidx.lifecycle:lifecycle-runtime:$lifecycleVersion")
        force("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
        force("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")
        force("androidx.lifecycle:lifecycle-viewmodel:$lifecycleVersion")
        force("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
        force("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
        force("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")
        force("androidx.lifecycle:lifecycle-livedata-core:$lifecycleVersion")
        force("androidx.lifecycle:lifecycle-process:$lifecycleVersion")
    }
}

android {
    namespace = "com.jaarvi.android"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    
    sourceSets["main"].manifest.srcFile("src/main/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/main/res")
    
    defaultConfig {
        applicationId = "com.jaarvi.android"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"
        
        buildConfigField("String", "API_BASE_URL", "\"http://192.168.80.86:30080/api\"")
    }
    
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_BASE_URL", "\"https://api.jaarvi.app\"")
        }
        debug {
            buildConfigField("String", "API_BASE_URL", "\"http://192.168.1.83:3000/api\"")
        }
    }
    
    buildFeatures {
        compose = true
        buildConfig = true
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
