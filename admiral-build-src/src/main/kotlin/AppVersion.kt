@file:Suppress("unused")

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileReader

@Serializable
data class AppVersion(
    @SerialName("internal_version")
    val internal: String,

    @SerialName("external_version")
    val external: String
) {

    val name: String
        get() = "$external ($internal)"

    companion object {
        fun fromJsonFile(jsonFile: File): AppVersion {
            val jsonString = FileReader(jsonFile).readText()

            return Json.decodeFromString(
                serializer(),
                jsonString
            )
        }
    }
}