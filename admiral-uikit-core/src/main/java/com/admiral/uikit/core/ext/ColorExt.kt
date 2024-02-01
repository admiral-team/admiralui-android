package com.admiral.uikit.core.ext

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import kotlin.math.roundToInt

@ColorInt
fun Int.withAlpha(alphaValue: Float = 0.6f): Int {
    val alpha = (Color.alpha(this) * alphaValue).roundToInt()
    val red: Int = Color.red(this)
    val green: Int = Color.green(this)
    val blue: Int = Color.blue(this)
    return Color.argb(alpha, red, green, blue)
}
