@file:Suppress("MayBeConstant", "unused")

import Publishing.Repository.Companion.toRepository
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get

fun Project.publishing(
    artifactId: String,
    artifactIdSuffix: String?,
    repositoryType: String?,
    sourcesJar: Any?,
    componentName: String = "release"
) = afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("mavenSources") {
                val versionJsonFile = rootProject.file("version.json")

                this.groupId = Publishing.groupId
                this.artifactId = createArtifactId(artifactId, artifactIdSuffix)
                this.version = AppVersion.fromJsonFile(versionJsonFile).external

                from(components[componentName])

                // NB: this is what makes the source code jar available in the published package
                sourcesJar?.let { artifact(it) }

                pom {
                    name.set(Publishing.POM.name)
                    description.set(Publishing.POM.description)

                    scm {
                        url.set(Publishing.POM.url)
                    }
                }
            }
        }

        repositories {
            when (val repository = repositoryType?.toRepository()) {
                is Publishing.Repository.Local -> {
                    maven {
                        name = "libs"
                        url = uri(layout.buildDirectory.dir("../../libs"))
                    }
                }

                is Publishing.Repository.Github -> {
                    maven {
                        name = repository.name
                        url = uri(repository.url)
                        credentials {
                            username = System.getenv("CI_GITHUB_USERNAME")
                            password = System.getenv("CI_GITHUB_TOKEN")
                        }
                    }
                }

                Publishing.Repository.Nexus -> {
                    val NEXUS_LOGIN: String =
                        gradleLocalProperties(rootDir).getProperty("NEXUS_LOGIN")
                    val NEXUS_PASSWORD: String =
                        gradleLocalProperties(rootDir).getProperty("NEXUS_PASSWORD")
                    val NEXUS_URL: String = gradleLocalProperties(rootDir).getProperty("NEXUS_URL")
                    maven {
                        name = repository.name
                        url = uri(NEXUS_URL)
                        credentials {
                            username = NEXUS_LOGIN
                            password = NEXUS_PASSWORD
                        }
                    }
                }

                else -> {}
            }
        }
    }
}

private fun createArtifactId(
    artifactId: String,
    artifactIdSuffix: String?
): String {
    return "$artifactId${artifactIdSuffix ?: ""}"
        .replace("/", "-")
        .replace("\\", "-")
}

val Project.publishing: PublishingExtension
    get() = (this as org.gradle.api.plugins.ExtensionAware)
        .extensions.getByName("publishing") as PublishingExtension

fun Project.publishing(configure: Action<PublishingExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware)
        .extensions.configure("publishing", configure)