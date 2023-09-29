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
    implementation(project(mapOf("path" to ":admiral-uikit-ext")))

    api(project(Modules.Admiral.UiKit.common))
    api(project(Modules.Admiral.Themes.themes))
    api(project(Modules.Admiral.Components.textview))

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
}