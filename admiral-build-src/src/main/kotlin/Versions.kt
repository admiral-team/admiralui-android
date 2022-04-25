@file:Suppress("MayBeConstant", "unused")

object Versions {
    val timber = "4.7.1"
    val viewBinding = "1.5.3"
    val colorPickerView = "2.2.4"
    val expandableLayout = "1.0.7"
    val junit = "4.13.2"
    val mockito = "3.0.0"

    object Sdk {
        val compileSdk = 31
        val targetSdk = 31
        val minSdk = 21
    }

    object Kotlin {
        val stdLib = "1.6.10"
        val coroutines = "1.5.2"
    }

    object Google {
        val gson = "2.8.6"
        object Android {
            val material = "1.4.0"
        }
    }

    object AndroidTools {
        val desugarJdkLibs = "1.1.5"
        val lint = "30.1.0-rc01"
    }

    object AndroidX {
        val appcompat = "1.4.0"
        val coreKtx = "1.7.0"
        val constraintLayout = "2.1.3"
        val lifecycle = "2.4.1"
        val recyclerView = "1.2.1"
        val fragment = "1.4.0"
        val activityCompose = "1.4.0"
        val compose = "1.2.0-alpha01"
        val test = "1.4.0"
        val espresso = "3.4.0"
    }
}