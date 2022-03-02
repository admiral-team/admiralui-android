package com.admiral.uikit.components.actionbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.admiral.themes.ColorPaletteEnum
import com.admiral.uikit.R
import com.admiral.uikit.databinding.AdmiralActionBarSecondaryItemBinding
import com.admiral.uikit.ext.getIntOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.layout.ConstraintLayout

class ActionBarSecondaryItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = AdmiralActionBarSecondaryItemBinding
        .inflate(LayoutInflater.from(context), this)

    /**
     * Icon drawable
     */
    var icon: Drawable? = null
        set(value) {
            field = value
            binding.icon.apply {
                isVisible = value != null
                setImageDrawable(value)
            }
        }

    /**
     * Description text
     */
    var description: String? = null
        set(value) {
            field = value
            binding.description.apply {
                isVisible = value != null
                text = value
            }
        }

    /**
     * Color of description text for the normal enabled state from palette.
     */
    var descriptionColorNormalEnabledPalette: ColorPaletteEnum? = null
        set(value) {
            field = value
            binding.description.textColorNormalEnabledPalette = value
        }

    /**
     * Color of description text for the pressed state from palette.
     */
    var descriptionColorPressedPalette: ColorPaletteEnum? = null
        set(value) {
            field = value
            binding.description.textColorPressedPalette = value
        }

    init {
        val width = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            WIDTH,
            resources.displayMetrics
        ).toInt()

        minWidth = width
        maxWidth = width
        isClickable = true
        isFocusable = true

        parseAttrs(attrs, R.styleable.ActionBarSecondaryItem).use { typedArray ->
            description = typedArray.getString(R.styleable.ActionBarSecondaryItem_admiralText)
            icon = typedArray.getDrawable(R.styleable.ActionBarSecondaryItem_admiralIcon)
            backgroundColorNormalEnabledPalette = ColorPaletteEnum.from(
                typedArray.getIntOrNull(R.styleable.ActionBarSecondaryItem_admiralBackgroundColorNormalEnabledPalette)
            )
            backgroundColorPressedPalette = ColorPaletteEnum.from(
                typedArray.getIntOrNull(R.styleable.ActionBarSecondaryItem_admiralBackgroundColorPressedPalette)
            )
            descriptionColorNormalEnabledPalette = ColorPaletteEnum.from(
                typedArray.getIntOrNull(R.styleable.ActionBarSecondaryItem_admiralTextColorNormalEnabledPalette)
            )
            descriptionColorPressedPalette = ColorPaletteEnum.from(
                typedArray.getIntOrNull(R.styleable.ActionBarSecondaryItem_admiralTextColorPressedPalette)
            )
        }
    }

    private companion object {
        const val WIDTH = 90f
    }
}