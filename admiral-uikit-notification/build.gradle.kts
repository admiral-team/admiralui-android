plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.admiral.uikit.notification"
    applyConfig(
        isCoreLibraryDesugaringEnabled = false,
        isComposeEnabled = false,
        isViewBindingEnabled = true
    )
}

dependencies {
    api(platform(project(Modules.Admiral.platform)))
    api(project(Modules.Admiral.UiKit.core))
    api(project(Modules.Admiral.resources))
    api(project(Modules.Admiral.Themes.themes))

    implementation(project(Modules.Admiral.UiKit.Components.imageview))
    implementation(project(Modules.Admiral.UiKit.Components.textview))
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.Google.Android.material)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

val artifactIdSuffix: String? by project
val repositoryType: String? by project

publishing(
    artifactId = Publishing.ArtifactIds.notification,
    artifactIdSuffix = artifactIdSuffix,
    repositoryType = repositoryType,
    sourcesJar = sourcesJar
)