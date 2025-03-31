plugins {
    id(Plugins.Application.id)
    id(Plugins.KotlinAndroid.id)
    id(Plugins.KotlinCompose.id)
}

android {
    namespace = "com.mdgroup.sample"
    compileSdk = Configuration.compileSdk

    defaultConfig {
        applicationId = "com.mdgroup.sample"
        minSdk = Configuration.minSdkSample
        targetSdk = Configuration.targetSdk
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":calendar"))

    // For minSdk < 26
    coreLibraryDesugaring(Libs.Core.deSugar)

    // Kotlin
    implementation(Kotlin.stdLib)

    // Android core
    api(Libs.Androidx.coreKtx)
    api(Libs.Androidx.Lifecycle.lifecycleRuntimeKtx)
    api(Libs.Androidx.Activity.activityCompose)
    api(Libs.Androidx.appcompat)

    // Material
    implementation(Libs.Material.material)

    // Compose
    implementation(platform(Libs.Compose.platform))
    implementation(Libs.Compose.ui)
    implementation(Libs.Compose.uiGraphics)
    implementation(Libs.Compose.material3)
    implementation(Libs.Compose.animation)
    implementation(Libs.Compose.foundation)

    implementation(Libs.Compose.uiTooling)
    implementation(Libs.Compose.uiToolingPreview)

    testImplementation(Libs.Core.Test.junit)

    debugImplementation(Libs.Compose.uiTestManifest)
    androidTestImplementation(Libs.Compose.uiTestJunit4)
}