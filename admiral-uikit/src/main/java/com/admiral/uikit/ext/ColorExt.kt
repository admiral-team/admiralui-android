package com.admiral.uikit.ext

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import kotlin.math.roundToInt

@Deprecated("Use com.admiral.uikit.core.ext.withAlpha instead.")
@ColorInt
fun Int.withAlpha(alpha: Float = 0.6f): Int {
    val alpha = (Color.alpha(this) * alpha).roundToInt()
    val red: Int = Color.red(this)
    val green: Int = Color.green(this)
    val blue: Int = Color.blue(this)
    return Color.argb(alpha, red, green, blue)
}

/**
 * Determines if this color is dark.
 * @param threshold - min darkness value; the higher the value, the darker the color;
 * float value between 0.0 and 1.0.
 */
fun Int.isColorDark(@FloatRange(from = 0.0, to = 1.0) threshold: Float = 0.9f): Boolean {
    val darkness = 1 - (Color.red(this) * RED_WEIGHT + Color.green(this)
            * GREEN_WEIGHT + Color.blue(this) * BLUE_WEIGHT) / COLOR_MAX_VALUE
    return darkness <= threshold
}

private const val RED_WEIGHT = 0.299
private const val GREEN_WEIGHT = 0.587
private const val BLUE_WEIGHT = 0.114
private const val COLOR_MAX_VALUE = 255