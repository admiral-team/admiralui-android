import java.util.Properties
import java.io.FileReader

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("shot")
}

android {
    defaultConfig {
        applicationId = APPLICATION_ID
        testInstrumentationRunner = ANDROID_TEST_INSTRUMENTATION_RUNNER

        compileSdk = Versions.Sdk.compileSdk
        minSdk = Versions.Sdk.minSdk
        targetSdk = Versions.Sdk.targetSdk

        val versionJsonFile = rootProject.file("version.json")

        versionName = AppVersion.fromJsonFile(versionJsonFile).name
        versionCode = (System.currentTimeMillis() / MILLS_IN_SECOND).toInt()
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.AndroidX.compose
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    signingConfigs {
        getByName("debug") {
            val keystorePropertiesFile = rootProject.file(Keystore.Files.Debug.properties)
            if (keystorePropertiesFile.exists().not()) {
                println("File ${Keystore.Files.Debug.properties} doesn't exist")
            }

            val keystoreFile = rootProject.file(Keystore.Files.Debug.keystore)
            if (keystoreFile.exists().not()) {
                println("File ${Keystore.Files.Debug.keystore} doesn't exist")
            }

            if (keystorePropertiesFile.exists() && keystoreFile.exists()) {
                val keystoreProperties = Properties()
                FileReader(keystorePropertiesFile).use { reader -> keystoreProperties.load(reader) }

                storeFile = keystoreFile
                storePassword = keystoreProperties.getProperty(Keystore.Properties.storePassword)
                keyAlias = keystoreProperties.getProperty(Keystore.Properties.keyAlias)
                keyPassword = keystoreProperties.getProperty(Keystore.Properties.keyPassword)
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    file("proguard-rules.pro")
                )
            )
        }
    }

    packagingOptions {
        // Multiple dependency bring these files in. Exclude them to enable
        // our test APK to build (has no effect on our AARs)
        // More info: https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-debug#debug-agent-and-android
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(project(Modules.network))
    implementation(project(Modules.Admiral.UiKit.uikit))
    implementation(project(Modules.Admiral.UiKit.compose))

    coreLibraryDesugaring(Libs.AndroidTools.desugarJdkLibs)
    implementation(Libs.Kotlin.stdLib)

    implementation(Libs.Google.gson)
    implementation(Libs.Google.Android.material)

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.fragmentKtx)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.constraintLayout)
    implementation(Libs.AndroidX.swipeRefreshLayout)
    implementation(Libs.AndroidX.Lifecycle.lifecycleRuntimeKtx)
    implementation(Libs.AndroidX.Activity.compose)
    implementation(Libs.AndroidX.Compose.foundation)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Compose.Ui.ui)
    implementation(Libs.AndroidX.Compose.Ui.tooling)

    implementation(Libs.timber)
    implementation(Libs.viewBinding)
    implementation(Libs.colorPickerView)
    implementation(Libs.expandableLayout)

    implementation(Libs.Retrofit.retrofit2)

    testImplementation(Libs.junit)
    androidTestImplementation(Libs.mockito)

    androidTestImplementation(Libs.AndroidX.Test.runner)
    androidTestImplementation(Libs.AndroidX.Test.rules)
    androidTestImplementation(Libs.AndroidX.Test.Espresso.core)
    androidTestImplementation(Libs.AndroidX.Test.Espresso.contrib)
    androidTestImplementation(Libs.AndroidX.Test.Espresso.intents)
}