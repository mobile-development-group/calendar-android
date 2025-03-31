import org.gradle.api.JavaVersion

object Configuration {
    const val minSdkSample = 24
    const val targetSdk = 35
    const val compileSdk = 35

    // See compose/kotlin version mapping
    // https://developer.android.com/jetpack/androidx/releases/compose-kotlin
    const val kotlinCompilerExtensionVersion = "1.5.11"

    val javaVersion = JavaVersion.VERSION_17
    val jvmTarget = "17"
}

sealed class Plugins(val id: String, val version: String) {
    object Application : Plugins(id = "com.android.application", version = "8.8.0")

    object KotlinAndroid : Plugins(id = "org.jetbrains.kotlin.android", version = "2.1.0")

    object KotlinCompose : Plugins(id = "org.jetbrains.kotlin.plugin.compose", version = "2.1.0")

    object Library : Plugins(id = "com.android.library", version = "8.1.1")
    
    object MavenPublish : Plugins(id = "com.vanniktech.maven.publish", version = "0.28.0")

    object NMCP : Plugins(id = "com.gradleup.nmcp", version = "0.0.7")
}

object Kotlin {
    private const val version = "2.1.0"
    const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
}

object Libs {
    object Core {
        const val deSugar = "com.android.tools:desugar_jdk_libs:2.1.5"

        object Test {
            const val junit = "junit:junit:4.13.2"
        }
    }

    object Androidx {
        private const val coreKtkVersion = "1.12.0"

        const val coreKtx = "androidx.core:core-ktx:$coreKtkVersion"
        const val appcompat = "androidx.appcompat:appcompat:1.6.1"

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.8.2"

        }

        object Lifecycle {
            const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.7.0"
        }
    }

    object Material {
        const val material = "com.google.android.material:material:1.12.0"
    }

    object Compose {
        const val platform = "androidx.compose:compose-bom:2025.03.00"

        const val ui = "androidx.compose.ui:ui"
        const val uiGraphics = "androidx.compose.ui:ui-graphics"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
        const val material3 = "androidx.compose.material3:material3"
        const val foundation = "androidx.compose.foundation:foundation"
        const val foundationAndroid = "androidx.compose.foundation:foundation-android"
        const val runtime = "androidx.compose.runtime:runtime"
        const val animation = "androidx.compose.animation:animation"

        const val uiTooling = "androidx.compose.ui:ui-tooling"
        const val uiTestManifest = "androidx.compose.ui:ui-test-manifest"
        const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4"
    }
}
