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
    implementation(project(Modules.Admiral.UiKit.common))
    implementation(project(Modules.Admiral.Themes.themes))

    implementation(Libs.Kotlin.coroutines)
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.constraintLayout)
    implementation(Libs.AndroidX.recyclerView)
    implementation(Libs.Google.Android.material)
    implementation(Libs.AndroidX.Lifecycle.viewModelKtx)
}