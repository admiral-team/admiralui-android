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
    api(project(Modules.Admiral.Themes.themes))

    implementation(Libs.AndroidX.Compose.Ui.ui)
    implementation(Libs.Kotlin.stdLib)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

val artifactIdSuffix: String? by project

publishing(
    artifactId = Publishing.ArtifactIds.themesCompose,
    artifactIdSuffix = artifactIdSuffix,
    sourcesJar = sourcesJar
)