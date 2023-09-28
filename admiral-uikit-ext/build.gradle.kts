plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    applyConfig(
        isCoreLibraryDesugaringEnabled = false,
        isComposeEnabled = false,
        isViewBindingEnabled = false
    )
}

dependencies {
    api(project(Modules.Admiral.UiKit.common))
    api(project(Modules.Admiral.Themes.themes))

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.Kotlin.coroutines)
    implementation(Libs.Google.Android.material)
    implementation(Libs.Kotlin.stdLib)
}