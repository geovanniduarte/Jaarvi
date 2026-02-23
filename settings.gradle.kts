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

// Optional: use a local patched Voyager to fix "State is DESTROYED" lifecycle crash.
// Clone https://github.com/adrielcafe/voyager, checkout 1.0.1, apply patches/voyager-lifecycle-destroyed-fix.patch,
// then uncomment and fix the path below (e.g. "../voyager" if cloned next to Jaarvi).
// if (File(rootDir, "voyager/settings.gradle.kts").exists()) {
//     includeBuild("voyager")
// }
