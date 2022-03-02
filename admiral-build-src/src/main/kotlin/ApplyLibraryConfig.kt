@file:Suppress("UnstableApiUsage", "unused")

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion

fun LibraryExtension.applyConfig(
    isCoreLibraryDesugaringEnabled: Boolean,
    isComposeEnabled: Boolean,
    isViewBindingEnabled: Boolean
) = defaultConfig {
    compileSdk = Versions.Sdk.compileSdk
    minSdk = Versions.Sdk.minSdk
    targetSdk = Versions.Sdk.targetSdk

    buildFeatures {
        compose = isComposeEnabled
        viewBinding = isViewBindingEnabled
    }

    if (isComposeEnabled) {
        composeOptions {
            kotlinCompilerExtensionVersion = Versions.AndroidX.compose
        }
    }

    compileOptions {
        // Flag to enable support for the new language APIs
        this.isCoreLibraryDesugaringEnabled = isCoreLibraryDesugaringEnabled

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    publishing {
        // Publishes "release" build variant with "release" component created by
        // Android Gradle plugin
        singleVariant("release")
    }
}