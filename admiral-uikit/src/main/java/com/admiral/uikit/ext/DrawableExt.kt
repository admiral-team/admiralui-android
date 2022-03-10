package com.admiral.uikit.ext

import android.content.res.ColorStateList
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat

internal fun Drawable.colored(@ColorInt colorInt: Int): Drawable {
    return mutate().also { DrawableCompat.setTint(it, colorInt) }
}

internal fun Drawable.colored(colorStateList: ColorStateList): Drawable {
    return mutate().also { DrawableCompat.setTintList(it, colorStateList) }
}

internal val Rect.namedString: String
    get() = "[L: $left, T: $top][R: $right, B: $bottom]"