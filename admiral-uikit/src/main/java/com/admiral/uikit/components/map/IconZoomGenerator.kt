package com.admiral.uikit.components.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.LayerDrawable
import androidx.core.graphics.drawable.toBitmap
import com.admiral.themes.ThemeManager
import com.admiral.uikit.R
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.drawable
import com.admiral.resources.R as res

/**
 * IconGenerator generates icons that contain zoom and location icons.
 */
class IconZoomGenerator(private val context: Context) {

    /**
     * Creates Zoom-In bitmap.
     *
     * @param size - width and height of the Icon in dp.
     * @param iconColor - color of the plus icon.
     * @param backgroundColor - background color of the plus icon.
     * @return Bitmap icon.
     */
    fun makePlusIcon(size: Int = 40, iconColor: Int? = null, backgroundColor: Int? = null): Bitmap {
        return createIconDrawable(
            res.drawable.admiral_ic_plus_outline,
            iconColor,
            backgroundColor,
            size
        )
    }

    /**
     * Creates Zoom-Out bitmap.
     *
     * @param size - width and height of the Icon in dp.
     * @param iconColor - color of the minus icon.
     * @param backgroundColor - background color of the minus icon.
     * @return Bitmap icon.
     */
    fun makeMinusIcon(
        size: Int = 40,
        iconColor: Int? = null,
        backgroundColor: Int? = null
    ): Bitmap {
        return createIconDrawable(
            res.drawable.admiral_ic_minus_outline,
            iconColor,
            backgroundColor,
            size
        )
    }

    /**
     * Creates Location icon bitmap.
     *
     * @param size - width and height of the Icon in dp.
     * @param iconColor - color of the location icon.
     * @param backgroundColor - background color of the location icon.
     * @return Bitmap icon.
     */
    fun makeLocationIcon(
        size: Int = 40,
        iconColor: Int? = null,
        backgroundColor: Int? = null
    ): Bitmap {
        return createIconDrawable(
            res.drawable.admiral_ic_gps_solid,
            iconColor,
            backgroundColor,
            size
        )
    }

    private fun createIconDrawable(
        iconId: Int,
        iconColor: Int?,
        backgroundColor: Int?,
        size: Int
    ): Bitmap {
        val drawablePlus =
            context.drawable(iconId)
                ?.colored(iconColor ?: ThemeManager.theme.palette.elementSecondary)
        val drawableBackground = context.drawable(R.drawable.admiral_bg_circle_mask)?.colored(
            backgroundColor ?: ThemeManager.theme.palette.backgroundBasic
        )

        val finalDrawable = LayerDrawable(arrayOf(drawableBackground, drawablePlus))
        finalDrawable.setLayerInset(0, 0, 0, 0, 0)
        finalDrawable.setLayerInset(
            1,
            DRAWABLE_INNER_INSET,
            DRAWABLE_INNER_INSET,
            DRAWABLE_INNER_INSET,
            DRAWABLE_INNER_INSET
        )

        val bitmapPlus = finalDrawable.toBitmap(size.dpToPx(context), size.dpToPx(context))
        return addShadow(
            bitmapPlus,
            bitmapPlus.height,
            bitmapPlus.width,
            SHADOW_SIZE,
            0f,
            SHADOW_DY
        )
    }

    private companion object {
        const val SHADOW_SIZE = 8f
        const val SHADOW_DY = 3f
        const val DRAWABLE_INNER_INSET = 30
    }
}