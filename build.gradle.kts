// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    // NB: use such way only for 'old' plugins
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.karumi:shot:5.14.1")
        classpath("app.cash.paparazzi:paparazzi-gradle-plugin:1.3.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    val androidGradlePluginVersion = "8.0.2"
    val kotlinVersion = "1.7.0"

    id("admiral-gradle-plugin")
    id("com.android.application") version androidGradlePluginVersion apply false
    id("com.android.library") version androidGradlePluginVersion apply false
    id("org.jetbrains.kotlin.android") version kotlinVersion apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version kotlinVersion apply false
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
}

allprojects {
    val versionJsonFile = rootProject.file("version.json")

    group = Publishing.groupId
    version = AppVersion.fromJsonFile(versionJsonFile).external

    project.tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        description = "Runs over whole code base."
        config.from(files("$rootDir/config/detekt/detekt.yml"))
        parallel = true
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        setSource(files(projectDir))
        include("**/*.kt")
        include("**/*.kts")
        exclude("**/admiral-uikit-compose/**")
        exclude("**/admiral-build-src/**")
        exclude("**/androidTest/**")
        exclude("**/resources/**")
        exclude("**/build/**")
        exclude(".gradle/**")
    }
}

tasks.register("cleanArtifacts", Delete::class) {
    delete("${rootProject.projectDir}${File.separator}artifacts")
}

tasks.register("cleanScreenshotsTempFolder", Delete::class) {
    val demoPath = "${rootProject.projectDir}${File.separator}demo${File.separator}"
    val screenshotsTempFolder =
        "screenshots${File.separator}debug${File.separator}screenshots-default"
    delete("$demoPath$screenshotsTempFolder")
}