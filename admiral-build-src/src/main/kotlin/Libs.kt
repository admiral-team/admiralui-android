@file:Suppress("MayBeConstant", "unused")

object Libs {
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    val colorPickerView = "com.github.skydoves:colorpickerview:${Versions.colorPickerView}"
    val expandableLayout = "com.github.skydoves:expandablelayout:${Versions.expandableLayout}"
    val junit = "junit:junit:${Versions.junit}"
    val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    val viewBinding =
        "com.github.kirich1409:viewbindingpropertydelegate-noreflection:${Versions.viewBinding}"

    object Kotlin {
        val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.Kotlin.stdLib}"
        val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutines}"
    }

    object Google {
        val gson = "com.google.code.gson:gson:${Versions.Google.gson}"

        object Android {
            val material =
                "com.google.android.material:material:${Versions.Google.Android.material}"
        }
    }

    object AndroidTools {
        val desugarJdkLibs =
            "com.android.tools:desugar_jdk_libs:${Versions.AndroidTools.desugarJdkLibs}"

        object Lint {
            val api = "com.android.tools.lint:lint-api:${Versions.AndroidTools.lint}"
            val checks = "com.android.tools.lint:lint-checks:${Versions.AndroidTools.lint}"
        }
    }

    object AndroidX {
        val coreKtx = "androidx.core:core-ktx:${Versions.AndroidX.coreKtx}"
        val appcompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appcompat}"
        val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.AndroidX.fragment}"
        val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraintLayout}"
        val recyclerView = "androidx.recyclerview:recyclerview:${Versions.AndroidX.recyclerView}"

        object Activity {
            val compose = "androidx.activity:activity-compose:${Versions.AndroidX.activityCompose}"
        }

        object Lifecycle {
            val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.lifecycle}"
            val lifecycleLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycle}"
            val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.lifecycle}"
        }

        object Compose {
            object Ui {
                val ui = "androidx.compose.ui:ui:${Versions.AndroidX.compose}"
                val tooling = "androidx.compose.ui:ui-tooling:${Versions.AndroidX.compose}"
            }

            val foundation = "androidx.compose.foundation:foundation:${Versions.AndroidX.compose}"
            val material = "androidx.compose.material:material:${Versions.AndroidX.compose}"
        }

        object Test {
            val runner = "androidx.test:runner:${Versions.AndroidX.test}"
            val rules = "androidx.test:rules:${Versions.AndroidX.test}"

            object Espresso {
                val core = "androidx.test.espresso:espresso-core:${Versions.AndroidX.espresso}"
                val contrib =
                    "androidx.test.espresso:espresso-contrib:${Versions.AndroidX.espresso}"
                val intents =
                    "androidx.test.espresso:espresso-intents:${Versions.AndroidX.espresso}"
            }
        }
    }
}