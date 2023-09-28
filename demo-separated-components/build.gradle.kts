plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.demo"

    defaultConfig {
        applicationId = "com.example.demo"

        compileSdk = Versions.Sdk.compileSdk
        minSdk = Versions.Sdk.minSdk
        targetSdk = Versions.Sdk.targetSdk

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Libs.Kotlin.coroutines)
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.constraintLayout)
    implementation(Libs.AndroidX.recyclerView)
    implementation(Libs.AndroidX.Lifecycle.viewModelKtx)
    implementation(Libs.Google.Android.material)
    implementation(Libs.Kotlin.stdLib)

    implementation(project(Modules.Admiral.UiKit.Components.notification))
}