@file:Suppress("MayBeConstant", "unused")

import java.lang.IllegalStateException

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

    sealed class Repository(
        val type: String,
        val name: String
    ) {
        data class Github(
            val url: String = "https://maven.pkg.github.com/admiral-team/admiralui-android"
        ) : Repository(
            type = GITHUB_REPO_TYPE,
            name = "GitHubPackages"
        )

        object Nexus : Repository(
            type = NEXUS_REPO_TYPE,
            name = "pfom-maven-lib"
            // NB: url is a secret on GitHub
        )

        companion object {
            fun String.toRepository(): Repository {
                return when {
                    this == GITHUB_REPO_TYPE -> Github()
                    this == NEXUS_REPO_TYPE -> Nexus
                    else -> throw IllegalStateException("Unknown repository type: $this")
                }
            }

            private const val GITHUB_REPO_TYPE = "Github"
            private const val NEXUS_REPO_TYPE = "Nexus"
        }
    }
}