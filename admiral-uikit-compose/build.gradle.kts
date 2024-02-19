plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    applyConfig(
        isCoreLibraryDesugaringEnabled = false,
        isComposeEnabled = true,
        isViewBindingEnabled = false
    )
}

dependencies {
    api(platform(project(Modules.Admiral.platform)))
    api(project(Modules.Admiral.Themes.compose))
    api(project(Modules.Admiral.UiKit.core))

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.Google.Android.material)

    implementation(Libs.AndroidX.Compose.Ui.ui)
    debugImplementation(Libs.AndroidX.Compose.Ui.tooling)
    implementation(Libs.AndroidX.Compose.Ui.preview)
    implementation(Libs.AndroidX.Compose.constraintlayout)
    implementation(Libs.AndroidX.Compose.foundation)
    implementation(Libs.AndroidX.Compose.material)

    coreLibraryDesugaring(Libs.AndroidTools.desugarJdkLibs)
    implementation(Libs.Kotlin.stdLib)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

val artifactIdSuffix: String? by project
val repositoryType: String? by project

publishing(
    artifactId = Publishing.ArtifactIds.uiKitCompose,
    artifactIdSuffix = artifactIdSuffix,
    repositoryType = repositoryType,
    sourcesJar = sourcesJar
)