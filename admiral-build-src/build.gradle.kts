plugins {
    `kotlin-dsl`

    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

gradlePlugin {
    plugins.register("admiral-gradle-plugin") {
        id = "admiral-gradle-plugin"
        implementationClass = "AdmiralGradlePlugin"
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.0-rc01")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
}