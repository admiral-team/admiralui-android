plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    applyConfig(
        isCoreLibraryDesugaringEnabled = false,
        isComposeEnabled = false,
        isViewBindingEnabled = true
    )
}

dependencies {
    implementation(project(mapOf("path" to ":admiral-uikit-common")))
    implementation(project(mapOf("path" to ":admiral-themes")))
    implementation(project(mapOf("path" to ":admiral-imageview")))
    implementation(project(mapOf("path" to ":admiral-textview")))
    implementation(project(mapOf("path" to ":admiral-uikit-ext")))
    implementation(project(mapOf("path" to ":admiral-links")))

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.Google.Android.material)
}