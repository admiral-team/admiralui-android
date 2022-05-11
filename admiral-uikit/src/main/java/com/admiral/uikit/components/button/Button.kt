package com.admiral.uikit.components.button

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isGone
import androidx.core.view.setPadding
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.components.button.ButtonSize
import com.admiral.uikit.common.components.button.ButtonStyle
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.common.util.ComponentsRadius
import com.admiral.uikit.components.spinner.Spinner
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.ext.ripple
import com.admiral.uikit.ext.roundedColoredRectangle
import com.admiral.uikit.ext.roundedColoredStroke
import com.admiral.uikit.ext.roundedRectangle

/**
 * Replacement of [Button].
 */
class Button @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

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
            actionTextView.text = value
        }

    /**
     * Additional text of button.
     * Default gravity is start.
     */
    var additionalText: String? = null
        set(value) {
            field = value
            additionalTextView.text = value
            additionalTextView.isGone = value.isNullOrEmpty() || buttonSize == ButtonSize.Small
        }

    /**
     * Color state for text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var drawableTintColor: ColorState? = null
        set(value) {
            field = value
            invalidateDrawableColors()
        }

    /**
     * In case Drawable is null, the icon will be hidden.
     */
    var drawableEnd: Drawable? = null
        set(value) {
            field = value
            invalidateDrawableColors()
        }

    /**
     * In case Drawable is null, the icon will be hidden.
     */
    var drawableStart: Drawable? = null
        set(value) {
            field = value
            invalidateDrawableColors()
        }

    /**
     * Boolean to hide/show loader. When the loader is shown, all the texts are invisible
     */
    var isLoading: Boolean = false
        set(value) {
            field = value
            invalidateLoading()
        }

    private val actionTextView: TextView by lazy { findViewById(R.id.actionTextView) }
    private val additionalTextView: TextView by lazy { findViewById(R.id.additionalTextView) }
    private val spinner: Spinner by lazy { findViewById(R.id.admiralButtonSpinner) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_button, this)

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

            actionTextView.isAllCaps = it.getBoolean(R.styleable.Button_android_textAllCaps, false)
            additionalTextView.isAllCaps = it.getBoolean(R.styleable.Button_android_textAllCaps, false)

            val drawableStartId = it.getResourceId(R.styleable.Button_android_drawableStart, 0)
            val drawableEndId = it.getResourceId(R.styleable.Button_android_drawableEnd, 0)

            if (drawableStartId != 0) drawableStart = drawable(drawableStartId)
            if (drawableEndId != 0) drawableEnd = drawable(drawableEndId)

            isLoading = it.getBoolean(R.styleable.Button_admiralIsLoading, false)

            radius =
                ComponentsRadius.from(it.getInt(R.styleable.Button_admiralRadius, ComponentsRadius.RADIUS_8.ordinal))
        }

        invalidateAppearance()
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
        actionTextView.isEnabled = enabled
        additionalTextView.isEnabled = enabled

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
        setPadding(pixels(buttonSize.padding))
        minWidth = pixels(buttonSize.defaultWidth)
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
        val mask = roundedRectangle(radius)
        val colorState = ColorState(
            normalEnabled = backgroundColor?.normalEnabled ?: ThemeManager.theme.palette.backgroundAccent,
            normalDisabled = backgroundColor?.normalDisabled ?: ThemeManager.theme.palette.backgroundAccent.withAlpha(),
            pressed = backgroundColor?.pressed ?: ThemeManager.theme.palette.backgroundAccent
        )

        background = when (buttonStyle) {
            ButtonStyle.Primary -> {
                val content = roundedColoredRectangle(radius, colorState)

                ripple(color, content, mask)
            }
            ButtonStyle.Secondary -> {
                val content = roundedColoredStroke(radius, colorState)

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
                normalEnabled = textColor?.normalEnabled ?: ThemeManager.theme.palette.textStaticWhite,
                normalDisabled = textColor?.normalDisabled ?: ThemeManager.theme.palette.textStaticWhite.withAlpha(),
                pressed = textColor?.pressed ?: ThemeManager.theme.palette.textStaticWhite
            )
            ButtonStyle.Secondary, ButtonStyle.Ghost -> ColorState(
                normalEnabled = textColor?.normalEnabled ?: ThemeManager.theme.palette.textAccent,
                normalDisabled = textColor?.normalDisabled ?: ThemeManager.theme.palette.textAccent.withAlpha(),
                pressed = textColor?.pressed ?: ThemeManager.theme.palette.textAccentPressed
            )
        }

        additionalTextView.textColor = colorState
        actionTextView.textColor = colorState
    }

    private fun invalidateDrawableColors() {
        val colorState = when (buttonStyle) {
            ButtonStyle.Primary -> ColorState(
                normalEnabled = drawableTintColor?.normalEnabled ?: ThemeManager.theme.palette.elementStaticWhite,
                normalDisabled = drawableTintColor?.normalDisabled
                    ?: ThemeManager.theme.palette.elementStaticWhite.withAlpha(),
                pressed = drawableTintColor?.pressed ?: ThemeManager.theme.palette.elementStaticWhite
            )
            ButtonStyle.Secondary, ButtonStyle.Ghost -> ColorState(
                normalEnabled = drawableTintColor?.normalEnabled ?: ThemeManager.theme.palette.elementAccent,
                normalDisabled = drawableTintColor?.normalDisabled ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                pressed = drawableTintColor?.pressed ?: ThemeManager.theme.palette.elementAccent
            )
        }

        actionTextView.apply {
            compoundDrawablesColorState = colorState
            setCompoundDrawablesWithIntrinsicBounds(
                drawableStart,
                null,
                drawableEnd,
                null
            )
        }
    }

    private fun invalidateSpinnerStyle() {
        when (buttonStyle) {
            ButtonStyle.Primary -> spinner.isContrast = true
            ButtonStyle.Secondary, ButtonStyle.Ghost -> spinner.isContrast = false
        }
    }

    private fun invalidateLoading() {
        if (isLoading) {
            spinner.animate().alpha(1f)

            actionTextView.animate().alpha(0f)
            additionalTextView.animate().alpha(0f)
        } else {
            spinner.animate().alpha(0f)

            actionTextView.animate().alpha(1f)

            if (!additionalText.isNullOrEmpty() && buttonSize != ButtonSize.Small) {
                additionalTextView.animate().alpha(1f)
            }
        }
    }

    private companion object {
        private const val RIPPLE_ALPHA = 0.2f
    }
}