package com.admiral.themes

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONObject

const val THEME_LIGHT = "Light"
const val THEME_DARK = "Dark"
const val THEME_DARK_SME = "Dark SME"
const val THEME_LIGHT_SME = "Light SME"

@Parcelize
open class Theme(
    var name: String,
    var palette: ColorPalette,
    var typography: Typography,
    var isTypographyFixed: Boolean
) : Parcelable

fun Theme.toJson(): JSONObject {
    return JSONObject().apply {
        put("name", name)
        put("palette", palette.toJSON())
        put("typography", typography.toJSON())
        put("typographyIsFixed", isTypographyFixed)
    }
}

fun JSONObject.toTheme(): Theme {
    return Theme(
        name = getString("name"),
        palette = getJSONObject("palette").toPalette(),
        typography = getJSONObject("typography").toTypography(),
        isTypographyFixed = getBoolean("typographyIsFixed")
    )
}

fun Theme.copy(
    name: String = this.name,
    palette: ColorPalette = this.palette,
    typography: Typography = this.typography,
    isTypographyFixed: Boolean = this.isTypographyFixed
): Theme {
    return Theme(name, palette, typography, isTypographyFixed)
}