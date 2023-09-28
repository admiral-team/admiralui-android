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
    api(project(Modules.Admiral.UiKit.common))
    api(project(Modules.Admiral.Themes.themes))
    implementation(project(mapOf("path" to ":admiral-uikit-ext")))

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.Kotlin.stdLib)
}