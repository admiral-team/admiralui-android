@file:Suppress("MayBeConstant", "unused")

object Publishing {
    val groupId = "admiralui-android"

    object ArtifactIds {
        val platform = "admiral-platform"
        val themes = "admiral-themes"
        val themesCompose = "admiral-themes-compose"
        val resources = "admiral-resources"
        val uiKitCommon = "admiral-uikit-common"
        val uiKit = "admiral-uikit"
        val uiKitCompose = "admiral-uikit-compose"
    }

    object POM {
        val url = "https://github.com/admiral-team/admiralui-android"
        val name = "Admiral UIKit"
        val description = "Библиотека визуальных компонентов"
    }

    object GithubRepository {
        val name = "GitHubPackages"
        val url = "https://maven.pkg.github.com/admiral-team/admiralui-android"
    }

    object NexusRepository {
        val name = "pfom-maven-lib"
        // NB: url is a secret on GitHub
    }
}

