package com.admiral.themes.compose

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.admiral.resources.R

data class Typography(
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
fun defaultTypography(): Typography {
    val fontFamily = FontFamily(
        Font(R.font.roboto_400, FontWeight.W400),
        Font(R.font.roboto_500, FontWeight.W500),
        Font(R.font.roboto_700, FontWeight.W700)
    )

    return Typography(
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