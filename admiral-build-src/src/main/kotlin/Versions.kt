@file:Suppress("MayBeConstant", "unused")

object Versions {
    val timber = "4.7.1"
    val viewBinding = "1.5.6"
    val colorPickerView = "2.2.4"
    val expandableLayout = "1.0.7"
    val junit = "4.13.2"
    val mockito = "3.0.0"
    val inputMask = "6.1.0"
    val pdfViewer = "3.2.0-beta.1"

    object Sdk {
        val compileSdk = 33
        val targetSdk = 33
        val minSdk = 21
    }

    object Kotlin {
        val stdLib = "1.6.10"
        val coroutines = "1.5.2"
        val coroutinesTest = "1.6.1"
    }

    object Google {
        val gson = "2.8.6"
        object Android {
            val material = "1.4.0"
            val mlKitTextRecognition = "18.0.0"
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
        val constraintLayoutCompose = "1.0.1"
        val lifecycle = "2.4.1"
        val recyclerView = "1.2.1"
        val fragment = "1.4.1"
        val activityCompose = "1.4.0"
        val compose = "1.2.0"
        val composeExtension = "1.4.7"
        val composeNavigation = "2.5.3"
        val preference = "1.1.1"
        val camerax = "1.2.0-alpha01"
        val test = "1.4.0"
        val junit = "1.1.3"
        val espresso = "3.4.0"
    }
}