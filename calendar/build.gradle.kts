plugins {
    id(Plugins.Library.id)
    id(Plugins.KotlinAndroid.id)
    id(Plugins.KotlinCompose.id)
    id(Plugins.MavenPublish.id)
    id(Plugins.NMCP.id)
}

android {
    namespace = "com.mdgroup.lib.calendar"
    compileSdk = Configuration.targetSdk

    defaultConfig {
        minSdk = Configuration.minSdkSample

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        // For minSdk < 26
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = Configuration.javaVersion
        targetCompatibility = Configuration.javaVersion
    }

    kotlinOptions {
        jvmTarget = Configuration.jvmTarget
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Configuration.kotlinCompilerExtensionVersion
    }
}

dependencies {
    // For minSdk < 26
    coreLibraryDesugaring(Libs.Core.deSugar)

    // Kotlin
    implementation(Kotlin.stdLib)

    // Compose
    implementation(platform(Libs.Compose.platform))
    implementation(Libs.Compose.ui)
    implementation(Libs.Compose.material3)
    implementation(Libs.Compose.foundation)
    implementation(Libs.Compose.foundationAndroid)

    // Only needed for @Immutable annotation.
    implementation(Libs.Compose.runtime)

    implementation(Libs.Compose.uiTooling)
    implementation(Libs.Compose.uiToolingPreview)

    testImplementation(Libs.Core.Test.junit)
}