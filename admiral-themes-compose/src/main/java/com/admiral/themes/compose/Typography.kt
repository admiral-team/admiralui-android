package com.admiral.themes.compose

import androidx.compose.material.Typography
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal val LocalAdmiralTypographies = compositionLocalOf<AdmiralTypography> {
    error("No AdmiralTypography provided")
}

data class AdmiralTypography(
    val body1: TextStyle,
    val body2: TextStyle,
    val caption1: TextStyle,
    val caption2: TextStyle,
    val headline: TextStyle,
    val largetitle1: TextStyle,
    val largetitle2: TextStyle,
    val overline: TextStyle,
    val subhead1: TextStyle,
    val subhead2: TextStyle,
    val subhead3: TextStyle,
    val subtitle1: TextStyle,
    val subtitle2: TextStyle,
    val subtitle3: TextStyle,
    val title1: TextStyle,
    val title2: TextStyle,
    val pinNum: TextStyle
)

@Suppress("MagicNumber", "LongMethod")
fun defaultAdmiralTypography(): AdmiralTypography {
    val fontFamily = FontFamily(
        Font(R.font.roboto_400, FontWeight.W400),
        Font(R.font.roboto_500, FontWeight.W500),
        Font(R.font.roboto_700, FontWeight.W700)
    )

    return AdmiralTypography(
        body1 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            letterSpacing = 0.05.sp
        ),
        body2 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W400,
            fontSize = 16.sp,
            letterSpacing = 0.025.sp
        ),
        caption1 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 12.sp,
            letterSpacing = 0.04000000059604645.sp
        ),
        caption2 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 10.sp,
            letterSpacing = 0.04000000059604645.sp
        ),
        headline = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            letterSpacing = 0.0.sp
        ),
        largetitle1 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W700,
            fontSize = 32.sp,
            letterSpacing = 0.0.sp
        ),
        largetitle2 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W700,
            fontSize = 28.sp,
            letterSpacing = 0.0.sp
        ),
        overline = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 10.sp,
            letterSpacing = 0.05.sp
        ),
        subhead1 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W700,
            fontSize = 14.sp,
            letterSpacing = 0.0.sp
        ),
        subhead2 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            letterSpacing = 0.05.sp
        ),
        subhead3 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            letterSpacing = 0.0.sp
        ),
        subtitle1 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W700,
            fontSize = 18.sp,
            letterSpacing = 0.0.sp
        ),
        subtitle2 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp,
            letterSpacing = 0.0.sp
        ),
        subtitle3 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W400,
            fontSize = 18.sp,
            letterSpacing = 0.0.sp
        ),
        title1 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W700,
            fontSize = 22.sp,
            letterSpacing = 0.0.sp
        ),
        title2 = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 22.sp,
            letterSpacing = 0.05.sp
        ),
        pinNum = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.W400,
            fontSize = 36.sp,
            letterSpacing = 0.0.sp
        )
    )
}

val debugTypography = Typography(
    h1 = defaultAdmiralTypography().body1,
    h2 = defaultAdmiralTypography().body1,
    h3 = defaultAdmiralTypography().body1,
    h4 = defaultAdmiralTypography().body1,
    h5 = defaultAdmiralTypography().body1,
    h6 = defaultAdmiralTypography().body1,
    subtitle1 = defaultAdmiralTypography().body1,
    subtitle2 = defaultAdmiralTypography().body1,
    body1 = defaultAdmiralTypography().body1,
    body2 = defaultAdmiralTypography().body1,
    button = defaultAdmiralTypography().body1,
    caption = defaultAdmiralTypography().body1,
    overline = defaultAdmiralTypography().body1,
)