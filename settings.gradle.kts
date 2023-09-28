pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url = java.net.URI("https://jitpack.io") }
    }
}

rootProject.name = "Admiral UI Android"

includeBuild("admiral-build-src")

include(
    ":admiral-lint",
    ":admiral-platform",
    ":admiral-resources",
    ":admiral-themes",
    ":admiral-themes-compose",
    ":admiral-uikit",
    ":admiral-uikit-compose",
    ":admiral-uikit-common",
    ":demo"
)
include(":admiral-uikit-imageview")
include(":admiral-uikit-core")
include(":demo-separated-components")
include(":admiral-uikit-textview")
include(":admiral-uikit-notification")
