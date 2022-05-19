plugins {
    id("java-platform")
    id("maven-publish")
}

dependencies {
    // The admiral-platform declares constraints on all components that require alignment
    constraints {
        api(project(Modules.Admiral.resources))
        api(project(Modules.Admiral.Themes.themes))
        api(project(Modules.Admiral.Themes.compose))
        api(project(Modules.Admiral.UiKit.uikit))
        api(project(Modules.Admiral.UiKit.common))
        api(project(Modules.Admiral.UiKit.compose))
    }
}

val artifactIdSuffix: String? by project
val repositoryType: String? by project

publishing(
    artifactId = Publishing.ArtifactIds.platform,
    artifactIdSuffix = artifactIdSuffix,
    repositoryType = repositoryType,
    sourcesJar = null,
    componentName = "javaPlatform"
)