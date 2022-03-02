package com.admiral.uikit.view.calendar.model

import androidx.annotation.Px

/**
 * Class for describing width and height dimensions in pixels.
 * Basically [android.util.Size], but allows this library to keep minSdk < 21.
 */
data class Size(@Px val width: Int, @Px val height: Int)

internal const val NO_INDEX = -1