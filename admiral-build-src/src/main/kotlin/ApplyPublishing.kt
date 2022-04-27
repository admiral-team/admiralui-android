@file:Suppress("MayBeConstant", "unused")

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get

fun Project.publishing(
    artifactId: String,
    artifactIdSuffix: String?,
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
            maven {
                name = Publishing.GithubRepository.name
                url = uri(Publishing.GithubRepository.url)
                credentials {
                    username = System.getenv("CI_GITHUB_USERNAME")
                    password = System.getenv("CI_GITHUB_TOKEN")
                }
            }
            maven {
                name = Publishing.NexusRepository.name
                url = uri(System.getenv("NEXUS_URL") ?: "")
                credentials {
                    username = System.getenv("NEXUS_USERNAME")
                    password = System.getenv("NEXUS_PASSWORD")
                }
            }
        }
    }
}

private fun createArtifactId(
    artifactId: String,
    artifactIdSuffix: String?
) : String {
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