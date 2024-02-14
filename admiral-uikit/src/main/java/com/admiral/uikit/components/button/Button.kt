package com.admiral.uikit.components.button

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isGone
import androidx.core.view.setPadding
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.core.components.button.ButtonSize
import com.admiral.uikit.core.components.button.ButtonStyle
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.core.util.ComponentsRadius
import com.admiral.uikit.databinding.AdmiralViewButtonBinding
import com.admiral.uikit.ext.createRoundedColoredRectangleDrawable
import com.admiral.uikit.ext.createRoundedColoredStrokeDrawable
import com.admiral.uikit.ext.createRoundedRectangleDrawable
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.ext.ripple

/**
 * Replacement of [Button].
 */
class Button @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    private val binding = AdmiralViewButtonBinding
        .inflate(LayoutInflater.from(context), this)

    /**
     * Color state for background.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var backgroundColor: ColorState? = null
        set(value) {
            field = value
            invalidateBackground()
        }

    /**
     * Sets the radius to the view. By default the radius is 0. You can use admiralRadius attribute from xml.
     */
    var radius: ComponentsRadius = ComponentsRadius.RADIUS_8
        set(value) {
            field = value
            invalidateBackground()
        }

    /**
     * Color state for text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var textColor: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    /**
     * Default size is [ButtonSize.Big]
     */
    var buttonSize: ButtonSize = ButtonSize.Big
        set(value) {
            field = value
            invalidateSize()
        }

    /**
     * Default style is [ButtonStyle.Primary]
     */
    var buttonStyle: ButtonStyle = ButtonStyle.Primary
        set(value) {
            field = value
            invalidateBackground()
            invalidateTextColors()
            invalidateSpinnerStyle()
        }

    /**
     * Main text of button.
     * Default gravity is center.
     * In case [additionalText] is not null and not empty, the gravity is end.
     */
    var text: String? = null
        set(value) {
            field = value
            binding.actionTextView.text = value
        }

    /**
     * Additional text of button.
     * Default gravity is start.
     */
    var additionalText: String? = null
        set(value) {
            field = value
            binding.additionalTextView.text = value
            binding.additionalTextView.isGone =
                value.isNullOrEmpty() || buttonSize == ButtonSize.Small
        }

    /**
     * Color state for text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var drawableTintColor: ColorState? = null
        set(value) {
            field = value
            invalidateDrawable()
        }

    /**
     * In case Drawable is null, the icon will be hidden.
     */
    var drawableEnd: Drawable? = null
        set(value) {
            field = value
            invalidateDrawable()
        }

    /**
     * In case Drawable is null, the icon will be hidden.
     */
    var drawableStart: Drawable? = null
        set(value) {
            field = value
            invalidateDrawable()
        }

    var drawablePadding: Float = 0f
        set(value) {
            field = value
            invalidateDrawable()
        }

    /**
     * Boolean to hide/show loader. When the loader is shown, all the texts are invisible
     */
    var isLoading: Boolean = false
        set(value) {
            field = value
            invalidateLoading()
        }

    init {
        parseAttrs(attrs, R.styleable.Button).use {
            parseBackgroundColors(it)
            parseTextColors(it)
            parseStyle(it)
            parseSize(it)
            parseDrawableColors(it)

            isFocusable = true
            isClickable = true
            isEnabled = it.getBoolean(R.styleable.Button_enabled, true)
            text = it.getString(R.styleable.Button_android_text)
            additionalText = it.getString(R.styleable.Button_admiralTextAdditional)

            binding.actionTextView.isAllCaps =
                it.getBoolean(R.styleable.Button_android_textAllCaps, false)
            binding.additionalTextView.isAllCaps =
                it.getBoolean(R.styleable.Button_android_textAllCaps, false)
            drawablePadding = it.getDimension(R.styleable.Button_android_drawablePadding, 0f)

            val drawableStartId = it.getResourceId(R.styleable.Button_android_drawableStart, 0)
            val drawableEndId = it.getResourceId(R.styleable.Button_android_drawableEnd, 0)

            if (drawableStartId != 0) drawableStart = drawable(drawableStartId)
            if (drawableEndId != 0) drawableEnd = drawable(drawableEndId)

            isLoading = it.getBoolean(R.styleable.Button_admiralIsLoading, false)

            radius =
                ComponentsRadius.from(
                    it.getInt(
                        R.styleable.Button_admiralRadius,
                        ComponentsRadius.RADIUS_8.ordinal
                    )
                )
        }
    }

    /**
     * Subscribe for theme change.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.subscribe(this)
    }

    /**
     * Unsubscribe for theme change.
     */
    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
    }

    override fun setEnabled(enabled: Boolean) {
        binding.actionTextView.isEnabled = enabled
        binding.additionalTextView.isEnabled = enabled

        super.setEnabled(enabled)
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateAppearance()
    }

    private fun invalidateAppearance() {
        invalidateBackground()
        invalidateTextColors()
        invalidateSpinnerStyle()
    }

    private fun invalidateSize() {
        this.setPadding(pixels(buttonSize.padding))
        this.minWidth = pixels(buttonSize.defaultWidth)
    }

    private fun parseSize(a: TypedArray) {
        val styleIndex = a.getInt(R.styleable.Button_admiralButtonSize, 0)

        buttonSize = when (styleIndex) {
            0 -> ButtonSize.Big
            1 -> ButtonSize.Medium
            2 -> ButtonSize.Small
            else -> ButtonSize.Big
        }
    }

    private fun parseStyle(a: TypedArray) {
        val styleIndex = a.getInt(R.styleable.Button_admiralButtonStyle, 0)

        buttonStyle = when (styleIndex) {
            0 -> ButtonStyle.Primary
            1 -> ButtonStyle.Secondary
            2 -> ButtonStyle.Ghost
            else -> ButtonStyle.Primary
        }
    }

    private fun parseBackgroundColors(a: TypedArray) {
        backgroundColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.Button_admiralBackgroundColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.Button_admiralBackgroundColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.Button_admiralBackgroundColorNormalDisabled,
            )
        )
    }

    private fun parseTextColors(a: TypedArray) {
        textColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.Button_admiralTextColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.Button_admiralTextColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.Button_admiralTextColorNormalDisabled,
            )
        )
    }

    private fun parseDrawableColors(a: TypedArray) {
        drawableTintColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.Button_admiralIconTintColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.Button_admiralIconTintColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.Button_admiralIconTintColorNormalDisabled,
            )
        )
    }

    private fun invalidateBackground() {
        val color = backgroundColor?.pressed ?: ThemeManager.theme.palette.backgroundAccentPressed
        val mask = createRoundedRectangleDrawable(radius)
        val colorState = ColorState(
            normalEnabled = backgroundColor?.normalEnabled
                ?: ThemeManager.theme.palette.backgroundAccent,
            normalDisabled = backgroundColor?.normalDisabled
                ?: ThemeManager.theme.palette.backgroundAccent.withAlpha(),
            pressed = backgroundColor?.pressed ?: ThemeManager.theme.palette.backgroundAccent
        )

        background = when (buttonStyle) {
            ButtonStyle.Primary -> {
                val content = createRoundedColoredRectangleDrawable(radius, colorState)

                ripple(color, content, mask)
            }

            ButtonStyle.Secondary -> {
                val content = createRoundedColoredStrokeDrawable(radius, colorState)

                ripple(color.withAlpha(RIPPLE_ALPHA), content, mask)
            }

            ButtonStyle.Ghost -> {
                ripple(color.withAlpha(RIPPLE_ALPHA), null, mask)
            }
        }
    }

    private fun invalidateTextColors() {
        val colorState = when (buttonStyle) {
            ButtonStyle.Primary -> ColorState(
                normalEnabled = textColor?.normalEnabled
                    ?: ThemeManager.theme.palette.textStaticWhite,
                normalDisabled = textColor?.normalDisabled
                    ?: ThemeManager.theme.palette.textStaticWhite.withAlpha(),
                pressed = textColor?.pressed ?: ThemeManager.theme.palette.textStaticWhite
            )

            ButtonStyle.Secondary, ButtonStyle.Ghost -> ColorState(
                normalEnabled = textColor?.normalEnabled ?: ThemeManager.theme.palette.textAccent,
                normalDisabled = textColor?.normalDisabled
                    ?: ThemeManager.theme.palette.textAccent.withAlpha(),
                pressed = textColor?.pressed ?: ThemeManager.theme.palette.textAccentPressed
            )
        }

        binding.additionalTextView.textColor = colorState
        binding.actionTextView.textColor = colorState
    }

    private fun invalidateDrawable() {
        val colorState = when (buttonStyle) {
            ButtonStyle.Primary -> ColorState(
                normalEnabled = drawableTintColor?.normalEnabled
                    ?: ThemeManager.theme.palette.elementStaticWhite,
                normalDisabled = drawableTintColor?.normalDisabled
                    ?: ThemeManager.theme.palette.elementStaticWhite.withAlpha(),
                pressed = drawableTintColor?.pressed
                    ?: ThemeManager.theme.palette.elementStaticWhite
            )

            ButtonStyle.Secondary, ButtonStyle.Ghost -> ColorState(
                normalEnabled = drawableTintColor?.normalEnabled
                    ?: ThemeManager.theme.palette.elementAccent,
                normalDisabled = drawableTintColor?.normalDisabled
                    ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                pressed = drawableTintColor?.pressed ?: ThemeManager.theme.palette.elementAccent
            )
        }

        binding.actionTextView.apply {
            compoundDrawablesColorState = colorState
            setCompoundDrawablesWithIntrinsicBounds(
                drawableStart,
                null,
                drawableEnd,
                null
            )
            compoundDrawablePadding = drawablePadding.toInt()
        }
    }

    private fun invalidateSpinnerStyle() {
        when (buttonStyle) {
            ButtonStyle.Primary -> binding.admiralButtonSpinner.isContrast = true
            ButtonStyle.Secondary, ButtonStyle.Ghost -> binding.admiralButtonSpinner.isContrast =
                false
        }
    }

    private fun invalidateLoading() {
        with(binding) {
            if (isLoading) {
                admiralButtonSpinner.animate().alpha(1f).withEndAction {
                    admiralButtonSpinner.visibility = View.VISIBLE
                }
                actionTextView.animate().alpha(0f)
                additionalTextView.animate().alpha(0f)
            } else {
                admiralButtonSpinner.animate().alpha(0f).withEndAction {
                    admiralButtonSpinner.visibility = View.INVISIBLE
                }
                if (!additionalText.isNullOrEmpty() && buttonSize != ButtonSize.Small) {
                    additionalTextView.animate().alpha(1f)
                }
                actionTextView.animate().alpha(1f)
            }
        }
    }

    private companion object {
        private const val RIPPLE_ALPHA = 0.2f
    }
}