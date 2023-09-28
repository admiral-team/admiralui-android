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
    implementation(project(mapOf("path" to ":admiral-themes")))
    implementation(project(mapOf("path" to ":admiral-uikit-common")))
    implementation(project(mapOf("path" to ":admiral-uikit-ext")))

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
}