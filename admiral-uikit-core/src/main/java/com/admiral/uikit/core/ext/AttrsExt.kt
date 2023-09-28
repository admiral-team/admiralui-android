package com.admiral.uikit.core.ext

import android.content.Context
import android.content.res.TypedArray
import android.util.TypedValue
import androidx.annotation.StyleableRes
import com.admiral.themes.ThemeManager

fun TypedArray.getColorOrNull(@StyleableRes index: Int): Int? {
    return if (this.hasValue(index)) {
        val result = this.getColor(index, 0)
        if (result == 0) {
            null
        } else {
            result
        }
    } else {
        null
    }
}

fun TypedArray.getIntOrNull(@StyleableRes index: Int): Int? {
    return if (this.hasValue(index)) {
        val result = this.getInt(index, 0)
        if (result == 0) {
            null
        } else {
            result
        }
    } else {
        null
    }
}

fun Number.dpToPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}

fun Number.spToPx(context: Context): Int {
    return if (ThemeManager.theme.isTypographyFixed) {
        this.dpToPx(context)
    } else {
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }
}