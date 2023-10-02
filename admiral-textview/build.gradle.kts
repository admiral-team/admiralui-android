plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    applyConfig(
        isCoreLibraryDesugaringEnabled = true,
        isComposeEnabled = false,
        isViewBindingEnabled = false
    )
}

dependencies {
    api(project(Modules.Admiral.UiKit.core))

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
}