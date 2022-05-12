package com.admiral.uikit.components.chat

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.button.Button
import com.admiral.uikit.components.cell.unit.IconCellUnit
import com.admiral.uikit.components.textfield.TextFieldSearch
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import kotlinx.coroutines.flow.StateFlow

class Input @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Color of background drawable.
     * In case color is null, the selected color theme will be used.
     */
    var backgroundColors: ColorState? = null
        set(value) {
            field = value
            invalidateBackgroundColors()
        }

    /**
     * Icon  shown at the beginning of the Input.
     */
    var iconStart: Drawable? = null
        set(value) {
            field = value
            iconStartCellUnit.icon = value
            iconStartCellUnit.isVisible = field != null
        }

    /**
     * Color state for icon' tint.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var iconTintColors: ColorState? = null
        set(value) {
            field = value
            invalidateDrawableIconTintColors()
        }

    var onButtonClickListener: OnClickListener? = null
        set(value) {
            with(button) { setOnClickListener(value) }
        }

    var onIconClickListener: OnClickListener? = null
        set(value) {
            with(iconStartCellUnit) { setOnClickListener(value) }
        }

    var text: String = ""
        set(value) {
            field = value
            Handler(Looper.getMainLooper()).post {
                textFieldSearch.editText?.setText(value)
            }
        }
        get() {
            if (textFieldSearch.editText?.editableText.isNullOrEmpty()) {
                return ""
            }
            return textFieldSearch.editText?.text.toString()
        }

    /**
     * Color of input text.
     * In case color is null, the selected color theme will be used.
     */
    var inputTextColors: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    var hintText: String = ""
        set(value) {
            field = value
            textFieldSearch.hint = value
        }
        get() {
            if (textFieldSearch.editText?.editableText.isNullOrEmpty()) {
                return ""
            }
            return textFieldSearch.hint.toString()
        }

    val iconStartCellUnit: IconCellUnit by lazy { findViewById(R.id.inputLayoutImageStart) }
    val button: Button by lazy { findViewById(R.id.admiralInputButton) }

    /**
     * StateFlow for text input changes
     */
    val textFlow: StateFlow<String?>
        get() = textFieldSearch.textFlow

    private val textFieldSearch: TextFieldSearch by lazy { findViewById(R.id.admiralInputTextFieldSearch) }
    private val inputViewBackground: View by lazy { findViewById(R.id.admiralInputViewBackground) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_input, this)

        parseAttrs(attrs, R.styleable.Input).use {
            parseBackgroundColors(it)
            parseIconTintColors(it)

            text = it.getString(R.styleable.Input_admiralText) ?: ""
            hintText = it.getString(R.styleable.Input_admiralHintText) ?: ""
            iconStart = it.getDrawable(R.styleable.Input_admiralIconStart)
            button.drawableEnd = it.getDrawable(R.styleable.Input_admiralButtonIcon)
                ?: drawable(R.drawable.admiral_ic_arrow_down_outline)
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

    override fun onThemeChanged(theme: Theme) {
        invalidateBackgroundColors()
        invalidateTextColors()
        invalidateDrawableIconTintColors()
    }

    private fun parseBackgroundColors(a: TypedArray) {
        backgroundColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.Input_admiralBackgroundColorNormalEnabled),
            normalDisabled = a.getColorOrNull(R.styleable.Input_admiralBackgroundColorNormalDisabled),
            pressed = a.getColorOrNull(R.styleable.Input_admiralBackgroundColorPressed)
        )
    }

    private fun parseIconTintColors(a: TypedArray) {
        iconTintColors = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.Input_admiralIconTintColorNormalEnabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.Input_admiralIconTintColorPressed
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.Input_admiralIconTintColorNormalDisabled
            )
        )
    }

    private fun invalidateBackgroundColors() {
        inputViewBackground.backgroundTintList = colorStateList(
            enabled = backgroundColors?.normalEnabled ?: ThemeManager.theme.palette.backgroundAdditionalOne,
            pressed = backgroundColors?.pressed ?: ThemeManager.theme.palette.backgroundAdditionalOnePressed,
            disabled = backgroundColors?.normalDisabled
                ?: ThemeManager.theme.palette.backgroundAdditionalOne.withAlpha()
        )
    }

    private fun invalidateDrawableIconTintColors() {
        iconStartCellUnit.iconTintColors =
            ColorState(
                normalEnabled = iconTintColors?.normalEnabled ?: ThemeManager.theme.palette.elementPrimary,
                normalDisabled = iconTintColors?.normalDisabled ?: ThemeManager.theme.palette.elementPrimary,
                pressed = iconTintColors?.pressed ?: ThemeManager.theme.palette.elementPrimary
            )
    }

    private fun invalidateTextColors() {
        textFieldSearch.inputTextColors = ColorState(
            normalEnabled = inputTextColors?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = inputTextColors?.normalDisabled ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = inputTextColors?.pressed ?: ThemeManager.theme.palette.textPrimary
        )
    }
}