plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    applyConfig(
        isCoreLibraryDesugaringEnabled = true,
        isComposeEnabled = false,
        isViewBindingEnabled = true
    )
}

dependencies {
    api(project(Modules.Admiral.UiKit.core))
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.recyclerView)
    implementation(Libs.Kotlin.coroutines)
    implementation(Libs.AndroidX.Lifecycle.viewModelKtx)
    implementation(Libs.AndroidX.constraintLayout)
    implementation(project(Modules.Admiral.UiKit.Components.imageview))
    implementation(project(Modules.Admiral.UiKit.Components.textview))
}