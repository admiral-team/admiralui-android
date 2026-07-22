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

/**
 * Lightens the color by mixing it with white (no transparency).
 *
 * @param factor how much white to mix in; float value between 0.0 and 1.0.
 */
@ColorInt
fun Int.lighten(@FloatRange(from = 0.0, to = 1.0) factor: Float = 0.4f): Int {
    val red = Color.red(this)
    val green = Color.green(this)
    val blue = Color.blue(this)

    val newRed = (red + (COLOR_MAX_VALUE - red) * factor).roundToInt()
    val newGreen = (green + (COLOR_MAX_VALUE - green) * factor).roundToInt()
    val newBlue = (blue + (COLOR_MAX_VALUE - blue) * factor).roundToInt()

    return Color.argb(COLOR_MAX_VALUE, newRed, newGreen, newBlue)
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