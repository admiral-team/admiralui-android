package com.admiral.uikit.ext

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.admiral.themes.ThemeManager
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.common.util.ComponentsRadius

@ColorInt
internal fun Context.color(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

internal fun Context.pixels(@DimenRes dimenRes: Int): Int {
    return if (dimenRes != 0) {
        resources.getDimensionPixelSize(dimenRes)
    } else {
        0
    }
}

internal fun Context.drawable(@DrawableRes drawableRes: Int): Drawable? =
    ContextCompat.getDrawable(this, drawableRes)

internal fun Context.coloredDrawable(
    @DrawableRes drawableResId: Int,
    @ColorRes colorResId: Int
): Drawable? {

    return coloredDrawable(drawable(drawableResId), colorResId)
}

internal fun Context.coloredDrawable(drawable: Drawable?, @ColorRes colorResId: Int): Drawable? {
    @ColorInt val colorInt = color(colorResId)
    return drawable?.colored(colorInt)
}

internal fun Context.coloredDrawable(
    @DrawableRes drawableResId: Int,
    colorStateList: ColorStateList
): Drawable? {
    return coloredDrawable(drawable(drawableResId), colorStateList)
}

internal fun Context.coloredDrawable(
    drawable: Drawable?,
    colorStateList: ColorStateList
): Drawable? {
    return drawable?.colored(colorStateList)
}

const val RADIUS_ARRAY_SIZE = 8

internal fun Context.createRoundedRectangleDrawable(radius: ComponentsRadius): Drawable {
    val outerRadii = FloatArray(RADIUS_ARRAY_SIZE).apply {
        fill(getRadiusFloat(radius))
    }

    val shape = RoundRectShape(outerRadii, null, null)
    return ShapeDrawable(shape)
}

internal fun Context.createRoundedRectangleDrawable(
    topLeft: ComponentsRadius,
    topRight: ComponentsRadius,
    bottomLeft: ComponentsRadius,
    bottomRight: ComponentsRadius
): Drawable {
    val outerRadii =
        floatArrayOf(
            getRadiusFloat(topLeft),
            getRadiusFloat(topLeft),
            getRadiusFloat(topRight),
            getRadiusFloat(topRight),
            getRadiusFloat(bottomRight),
            getRadiusFloat(bottomRight),
            getRadiusFloat(bottomLeft),
            getRadiusFloat(bottomLeft)
        )

    val shape = RoundRectShape(outerRadii, null, null)
    return ShapeDrawable(shape)
}

internal fun Context.createRoundedColoredRectangleDrawable(
    radius: ComponentsRadius,
    colorState: ColorStateList
): Drawable {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    shape.cornerRadius = getRadiusFloat(radius)

    shape.color = colorState

    return shape
}

internal fun Context.createRoundedColoredRectangleDrawable(
    radius: ComponentsRadius,
    colorState: ColorState
): Drawable {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    shape.cornerRadius = getRadiusFloat(radius)

    shape.color = colorStateList(
        enabled = colorState.normalEnabled ?: ThemeManager.theme.palette.backgroundAccent,
        disabled = colorState.normalDisabled
            ?: ThemeManager.theme.palette.backgroundAccent.withAlpha(),
        pressed = colorState.pressed ?: ThemeManager.theme.palette.backgroundAccent
    )

    return shape
}

internal fun Context.createRoundedColoredStrokeDrawable(
    radius: ComponentsRadius,
    colorState: ColorStateList
): Drawable {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    shape.cornerRadius = getRadiusFloat(radius)

    shape.setStroke(
        2.dpToPx(this), colorState
    )

    return shape
}

internal fun Context.createRoundedColoredStrokeDrawable(
    radius: ComponentsRadius,
    colorState: ColorState
): Drawable {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    shape.cornerRadius = getRadiusFloat(radius)

    shape.setStroke(
        2.dpToPx(this), colorStateList(
            enabled = colorState.normalEnabled ?: ThemeManager.theme.palette.backgroundAccent,
            disabled = colorState.normalDisabled
                ?: ThemeManager.theme.palette.backgroundAccent.withAlpha(),
            pressed = colorState.pressed ?: ThemeManager.theme.palette.backgroundAccent
        )
    )

    return shape
}

private fun Context.getRadiusFloat(radius: ComponentsRadius): Float {
    return when (radius) {
        ComponentsRadius.NONE -> 0.dpToPx(this).toFloat()
        ComponentsRadius.RADIUS_4 -> 4.dpToPx(this).toFloat()
        ComponentsRadius.RADIUS_8 -> 8.dpToPx(this).toFloat()
        ComponentsRadius.RADIUS_12 -> 12.dpToPx(this).toFloat()
        ComponentsRadius.RADIUS_16 -> 16.dpToPx(this).toFloat()
        ComponentsRadius.RADIUS_20 -> 20.dpToPx(this).toFloat()
    }
}

internal fun Context.colorStateList(
    @ColorInt enabled: Int,
    @ColorInt disabled: Int,
    @ColorInt pressed: Int
): ColorStateList {

    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            pressed,
            disabled,
            enabled
        )
    )
}
