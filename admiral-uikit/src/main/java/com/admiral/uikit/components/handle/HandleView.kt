package com.admiral.uikit.components.handle

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.admiral.themes.ColorPaletteEnum
import com.admiral.uikit.R
import com.admiral.uikit.components.imageview.ImageView

/**
 * Handle view can be added in a BottomSheet layout when needed
 */
class HandleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ImageView(context, attrs, defStyleAttr) {

    init {
        setImageDrawable(ContextCompat.getDrawable(context, R.drawable.admiral_bg_handle))
        imageColorNormalEnabledPalette = ColorPaletteEnum.ELEMENT_ADDITIONAL
    }
}