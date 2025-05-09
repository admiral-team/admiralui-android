plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    applyConfig(
        isCoreLibraryDesugaringEnabled = false,
        isComposeEnabled = false,
        isViewBindingEnabled = true
    )
}

dependencies {
    api(platform(project(Modules.Admiral.platform)))
    api(project(Modules.Admiral.UiKit.core))

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

val artifactIdSuffix: String? by project
val repositoryType: String? by project

publishing(
    artifactId = Publishing.ArtifactIds.imageview,
    artifactIdSuffix = artifactIdSuffix,
    repositoryType = repositoryType,
    sourcesJar = sourcesJar
)