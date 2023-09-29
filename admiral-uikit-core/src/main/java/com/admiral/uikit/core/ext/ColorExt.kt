package com.admiral.uikit.core.ext

import android.graphics.Color
import androidx.annotation.ColorInt
import kotlin.math.roundToInt

@Deprecated("Use com.admiral.uikit.common.ext.withAlpha instead.")
@ColorInt
fun Int.withAlpha(alpha: Float = 0.6f): Int {
    val alpha = (Color.alpha(this) * alpha).roundToInt()
    val red: Int = Color.red(this)
    val green: Int = Color.green(this)
    val blue: Int = Color.blue(this)
    return Color.argb(alpha, red, green, blue)
}