@file:Suppress("MayBeConstant", "unused")

object Modules {
    val demo = ":demo"

    object Admiral {
        val platform = ":admiral-platform"
        val lint = ":admiral-lint"
        val resources = ":admiral-resources"

        object Themes {
            val themes = ":admiral-themes"
            val compose = ":admiral-themes-compose"
        }

        object UiKit {
            val uikit = ":admiral-uikit"
            val common = ":admiral-uikit-common"
            val compose = ":admiral-uikit-compose"
            val core = ":admiral-uikit-core"

            object Components {
                val imageview = ":admiral-imageview"
                val textview = ":admiral-textview"
                val notification = ":admiral-notification"
            }
        }
    }
}