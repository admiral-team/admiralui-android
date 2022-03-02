@file:Suppress("MayBeConstant", "unused")

object Keystore {
    object Properties {
        val storePassword = "STORE_PASSWORD"
        val keyAlias = "KEY_ALIAS"
        val keyPassword = "KEY_PASSWORD"
    }

    object Files {
        object Debug {
            val keystore = "debug.keystore"
            val properties = "keystore_debug.properties"
        }
    }
}