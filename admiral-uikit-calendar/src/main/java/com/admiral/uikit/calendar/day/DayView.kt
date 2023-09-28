package com.admiral.uikit.calendar.day

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import com.admiral.themes.ColorPaletteEnum
import com.admiral.uikit.calendar.R
import com.admiral.uikit.calendar.databinding.AdmiralViewCalendarDayBinding
import com.admiral.uikit.core.layout.FrameLayout

internal class DayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = AdmiralViewCalendarDayBinding
        .inflate(LayoutInflater.from(context), this)

    private val backgroundDrawable: Drawable? by lazy {
        ContextCompat.getDrawable(context, R.drawable.admiral_bg_rectangle_4dp)
    }

    private val backgroundHighlightedDrawable: Drawable? by lazy {
        ContextCompat.getDrawable(context, R.drawable.admiral_bg_stroke_4dp)
    }

    /**
     * Callback action for clicks
     */
    var onClickAction: ((View) -> Unit)? = null

    /**
     * Day model for the view
     */
    var model: BaseDayModel = BaseDayModel.Unknown
        set(value) {
            field = value
            updateVisualState()
        }

    init {
        binding.clickZone.setOnClickListener { onClickAction?.invoke(it) }
    }

    @Suppress("NestedBlockDepth", "LongMethod", "ComplexMethod")
    private fun updateVisualState() = with(binding) {
        when (val type = model) {
            BaseDayModel.Unknown -> {
                mark.isInvisible = true
                day.isInvisible = true
                clickZone.backgroundColorNormalEnabledPalette = ColorPaletteEnum.BACKGROUND_BASIC
            }

            is BaseDayModel.DayModel -> {
                mark.isInvisible = !type.isMarked
                day.isInvisible = false
                isEnabled = type !is BaseDayModel.DayModel.Disabled
                binding.day.text = type.localDate.dayOfMonth.toString()

                when (type) {
                    is BaseDayModel.DayModel.Normal -> {
                        clickZone.apply {
                            background = backgroundDrawable
                            backgroundColorNormalEnabledPalette = ColorPaletteEnum.BACKGROUND_BASIC
                            backgroundColorPressedPalette =
                                ColorPaletteEnum.BACKGROUND_SELECTED_PRESSED
                        }
                        day.apply {
                            textColorNormalEnabledPalette = ColorPaletteEnum.TEXT_PRIMARY
                            alpha = ENABLED_ALPHA
                        }
                    }

                    is BaseDayModel.DayModel.Disabled -> {
                        clickZone.apply {
                            background = backgroundDrawable
                            backgroundColorNormalEnabledPalette = ColorPaletteEnum.BACKGROUND_BASIC
                            backgroundColorPressedPalette = ColorPaletteEnum.BACKGROUND_BASIC
                        }
                        day.apply {
                            textColorNormalEnabledPalette = ColorPaletteEnum.TEXT_PRIMARY
                            alpha = DISABLED_ALPHA
                        }
                    }

                    is BaseDayModel.DayModel.Current -> {
                        clickZone.apply {
                            background = backgroundHighlightedDrawable
                            backgroundColorNormalEnabledPalette = ColorPaletteEnum.TEXT_PRIMARY
                            backgroundColorPressedPalette =
                                ColorPaletteEnum.BACKGROUND_SELECTED_PRESSED
                        }
                        day.apply {
                            textColorNormalEnabledPalette = ColorPaletteEnum.TEXT_PRIMARY
                            alpha = ENABLED_ALPHA
                        }
                    }

                    is BaseDayModel.DayModel.Highlighted -> {
                        clickZone.apply {
                            background = backgroundDrawable
                            backgroundColorNormalEnabledPalette =
                                ColorPaletteEnum.BACKGROUND_SELECTED
                            backgroundColorPressedPalette =
                                ColorPaletteEnum.BACKGROUND_SELECTED_PRESSED
                        }
                        day.apply {
                            textColorNormalEnabledPalette = ColorPaletteEnum.TEXT_PRIMARY
                            alpha = ENABLED_ALPHA
                        }
                    }

                    is BaseDayModel.DayModel.Selected -> {
                        clickZone.apply {
                            background = backgroundDrawable
                            backgroundColorNormalEnabledPalette =
                                ColorPaletteEnum.BACKGROUND_SELECTED
                            backgroundColorPressedPalette =
                                ColorPaletteEnum.BACKGROUND_SELECTED_PRESSED
                        }
                        day.apply {
                            textColorNormalEnabledPalette = ColorPaletteEnum.TEXT_PRIMARY
                            alpha = ENABLED_ALPHA
                        }
                    }

                    is BaseDayModel.DayModel.SelectedBright -> {
                        clickZone.apply {
                            background = backgroundDrawable
                            backgroundColorNormalEnabledPalette = ColorPaletteEnum.BACKGROUND_ACCENT
                            backgroundColorPressedPalette =
                                ColorPaletteEnum.BACKGROUND_ACCENT_PRESSED
                            isBackgroundTransparent = false
                        }
                        day.apply {
                            textColorNormalEnabledPalette = ColorPaletteEnum.TEXT_STATIC_WHITE
                            alpha = ENABLED_ALPHA
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val DISABLED_ALPHA = 0.6f
        private const val ENABLED_ALPHA = 1f
    }
}