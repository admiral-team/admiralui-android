import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("maven-publish")
}

android {
    applyConfig(
        isCoreLibraryDesugaringEnabled = true,
        isComposeEnabled = false,
        isViewBindingEnabled = true
    )
}

dependencies {
    api(platform(project(Modules.Admiral.platform)))
    api(project(Modules.Admiral.UiKit.core))

    lintPublish(project(Modules.Admiral.lint))

    implementation(Libs.Kotlin.coroutines)
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.constraintLayout)
    implementation(Libs.AndroidX.recyclerView)
    implementation(Libs.AndroidX.Lifecycle.viewModelKtx)
    implementation(Libs.Google.Android.material)
    coreLibraryDesugaring(Libs.AndroidTools.desugarJdkLibs)
    implementation(Libs.Kotlin.stdLib)
}

tasks.register("generateSources", Jar::class) {
    archiveFileName.set("sources.jar")
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

tasks.register("addSourcesToAar", Jar::class) {
    archiveFileName.set("admiral-uikit-debug-with-sources.aar")
    destinationDirectory.set(layout.buildDirectory.dir("../../artifacts"))

    from(zipTree("build/outputs/aar/admiral-uikit-debug.aar"))
    from(fileTree("build/libs").include("sources.jar"))
}

afterEvaluate {
    project.tasks.preBuild.dependsOn("generateSources")
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

val artifactIdSuffix: String? by project
val repositoryType: String? by project

publishing(
    artifactId = Publishing.ArtifactIds.uiKit,
    artifactIdSuffix = artifactIdSuffix,
    repositoryType = repositoryType,
    sourcesJar = sourcesJar
)