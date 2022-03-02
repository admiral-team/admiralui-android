plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    applyConfig(
        isCoreLibraryDesugaringEnabled = false,
        isComposeEnabled = false,
        isViewBindingEnabled = false
    )
}

dependencies {
    api(platform(project(Modules.Admiral.platform)))

    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.Google.Android.material)
}

val artifactIdSuffix: String? by project

publishing(
    artifactId = Publishing.ArtifactIds.resources,
    artifactIdSuffix = artifactIdSuffix,
    sourcesJar = null
)