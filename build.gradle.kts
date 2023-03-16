import java.net.URL
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.cert.X509Certificate
import java.security.SecureRandom


// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    // NB: use such way only for 'old' plugins
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.karumi:shot:5.14.1")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    val androidGradlePluginVersion = "7.1.0-rc01"
    val kotlinVersion = "1.6.10"

    id("admiral-gradle-plugin")
    id("java")
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

fun copyFilesFromSubproject(subproject: Project) {
    if (subproject.plugins.hasPlugin("com.android.library")) {
        val fileContent = "This is the content of the file."
        val outputFile = File("${subproject.buildDir}/myFile.txt")
        outputFile.writeText(fileContent)

        val sourceDirectory = File("${subproject.buildDir}/outputs/aar")
        val destinationDirectory = File("${subproject.parent?.projectDir}/libs")

        val regex = Regex(".*\\-release.aar") // regex to match all files with .txt extension

        sourceDirectory.listFiles()?.filter { file -> regex.matches(file.name) }?.forEach { file ->
            val newFileName = file.name.replace("-release", "")
            val destinationFile = File(destinationDirectory, newFileName)
            file.copyTo(destinationFile, overwrite = true)
        }
    }
}

tasks.register("createFile") {
    doLast {
        subprojects.forEach { proj ->
            copyFilesFromSubproject(proj)
        }
    }
}

tasks.named("assemble") {
    finalizedBy("createFile")
}

tasks.configureEach {
    doFirst {
        System.setProperty("javax.net.ssl.trustStoreType", "none")
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,TLSv1,SSLv3")
//        // Create a trust manager that does not validate certificate chains
//        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
//            override fun getAcceptedIssuers(): Array<X509Certificate>? {
//                return null
//            }
//            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {
//                // Do nothing
//            }
//            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {
//                // Do nothing
//            }
//        })
//
//        // Install the trust manager
//        val sc = SSLContext.getInstance("SSL")
//        sc.init(null, trustAllCerts, SecureRandom())
//        HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
//
//        val allHostsValid = HostnameVerifier { _, _ -> true }
//        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid)
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