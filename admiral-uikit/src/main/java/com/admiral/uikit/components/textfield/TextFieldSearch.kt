package com.admiral.uikit.components.textfield

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.core.content.res.use
import androidx.core.widget.doAfterTextChanged
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.databinding.AdmiralViewTextFieldSearchBinding
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.admiral.resources.R as res

class TextFieldSearch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextFieldInputLayout(
    context,
    attrs,
    defStyleAttr
), ThemeObserver {

    private val binding = AdmiralViewTextFieldSearchBinding
        .inflate(LayoutInflater.from(context), this)

    private var textFlowField = MutableStateFlow<String?>(null)

    /**
     * StateFlow for text input changes
     */
    val textFlow: StateFlow<String?> = textFlowField

    /**
     * Color of hint text.
     * In case color is null, the selected color theme will be used.
     */
    var hintTextColors: ColorState? = null
        set(value) {
            field = value
            invalidateHintColors()
        }

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
     * Color of input text.
     * In case color is null, the selected color theme will be used.
     */
    var inputTextColors: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    // region Drawable start
    /**
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorRes
    var drawableStartTintColor: Int? = null
        set(value) {
            field = value
            invalidateDrawableStart()
        }

    /**
     * In case Drawable is null, the default icon will be chosen.
     */
    var drawableStart: Drawable? = null
        set(value) {
            field = value
            invalidateDrawableStart()
        }

    /**
     * This property is responsible for visibility of [drawableStart]
     */
    var isDrawableStartVisible: Boolean = true
        set(value) {
            field = value
            invalidateDrawableStart()
        }
    // endregion

    /**
     * Listener to detect text changing.
     */
    var onTextChangeListener: OnTextChangeListener? = null
        set(value) {
            field = value
            setTextChangedListener()
        }

    /**
     * Displayed text in input.
     */
    var inputText: String
        set(value) {
            Handler(Looper.getMainLooper()).post {
                editText?.setText(value) ?: Unit
            }
        }
        get() {
            if (editText?.text.isNullOrEmpty()) {
                return ""
            }
            return editText?.text.toString()
        }

    init {
        background = drawable(R.drawable.admiral_bg_rectangle_10dp)
        endIconMode = END_ICON_CLEAR_TEXT
        endIconDrawable = drawable(res.drawable.admiral_ic_close_outline)?.colored(
            drawableStartTintColor ?: ThemeManager.theme.palette.elementPrimary
        )
        isHintEnabled = false

        setPadding(0, 0, 0, 0)

        parseAttrs(attrs, R.styleable.TextFieldSearch).use {
            parseTextColors(it)
            parseBackgroundColors(it)
            parseDrawableStart(it)
            parseTexts(it)
        }

        binding.editText.doAfterTextChanged { text ->
            textFlowField.value = text.toString()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.subscribe(this)
    }

    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateHintColors()
        invalidateDrawableStart()
        invalidateBackgroundColors()
        invalidateTextColors()
        invalidateCursorColor()
    }

    override fun setHint(hint: CharSequence?) {
        isHintEnabled = true
        super.setHint(hint)
        isHintEnabled = false
    }

    private fun parseTexts(a: TypedArray) {
        inputText = a.getString(R.styleable.TextFieldSearch_admiralText) ?: ""
        placeholderText = a.getString(R.styleable.TextFieldSearch_admiralTextPlaceholder)
    }

    private fun parseDrawableStart(a: TypedArray) {
        drawableStart = a.getDrawable(R.styleable.TextFieldSearch_admiralIcon)
            ?: drawable(res.drawable.admiral_ic_search_outline)

        drawableStartTintColor = a.getColorOrNull(
            R.styleable.TextFieldSearch_admiralIconTintColorNormalEnabled
        )

        isDrawableStartVisible = a.getBoolean(
            R.styleable.TextFieldSearch_admiralIsDrawableStartVisible,
            true
        )
    }

    private fun parseTextColors(a: TypedArray) {
        inputTextColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.TextFieldSearch_admiralTextColorNormalDisabled),
            normalDisabled = a.getColorOrNull(R.styleable.TextFieldSearch_admiralTextColorNormalDisabled),
            pressed = a.getColorOrNull(R.styleable.TextFieldSearch_admiralTextColorPressed),
        )

        hintTextColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.TextFieldSearch_admiralHintTextColorNormalEnabled),
            normalDisabled = a.getColorOrNull(R.styleable.TextFieldSearch_admiralHintTextColorNormalDisabled),
            pressed = a.getColorOrNull(R.styleable.TextFieldSearch_admiralHintTextColorPressed),
        )
    }

    private fun parseBackgroundColors(a: TypedArray) {
        backgroundColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.TextFieldSearch_admiralBackgroundColorNormalEnabled),
            normalDisabled = a.getColorOrNull(R.styleable.TextFieldSearch_admiralBackgroundColorNormalDisabled),
            pressed = a.getColorOrNull(R.styleable.TextFieldSearch_admiralBackgroundColorPressed)
        )
    }

    private fun invalidateHintColors() {
        binding.editText.setHintTextColor(
            colorStateList(
                enabled = hintTextColors?.normalEnabled ?: ThemeManager.theme.palette.textSecondary,
                pressed = hintTextColors?.pressed ?: ThemeManager.theme.palette.textSecondary,
                disabled = hintTextColors?.normalDisabled
                    ?: ThemeManager.theme.palette.textSecondary.withAlpha()
            )
        )
    }

    private fun invalidateTextColors() {
        binding.editText.setTextColor(
            colorStateList(
                enabled = inputTextColors?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
                pressed = inputTextColors?.pressed ?: ThemeManager.theme.palette.textPrimary,
                disabled = inputTextColors?.normalDisabled
                    ?: ThemeManager.theme.palette.textPrimary.withAlpha()
            )
        )
    }

    private fun invalidateBackgroundColors() {
        backgroundTintList = colorStateList(
            enabled = backgroundColors?.normalEnabled
                ?: ThemeManager.theme.palette.backgroundAdditionalOne,
            pressed = backgroundColors?.pressed
                ?: ThemeManager.theme.palette.backgroundAdditionalOnePressed,
            disabled = backgroundColors?.normalDisabled
                ?: ThemeManager.theme.palette.backgroundAdditionalOne.withAlpha()
        )
    }

    private fun invalidateDrawableStart() {
        startIconDrawable = if (isDrawableStartVisible) {
            drawableStart?.colored(
                drawableStartTintColor ?: ThemeManager.theme.palette.elementPrimary
            )
        } else null
    }

    private fun setTextChangedListener() {
        with(binding.editText) {
            addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(editable: Editable?) = Unit

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) = Unit

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        onTextChangeListener?.onTextChanged(s, start, before, count)
                    }
                })
        }
    }

    private fun invalidateCursorColor() {
        binding.editText.apply {
            highlightColor = ThemeManager.theme.palette.backgroundAccent.withAlpha()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                textCursorDrawable = drawable(R.drawable.admiral_img_cursor_drawable)
                    ?.colored(ThemeManager.theme.palette.elementAccent)
            }
        }
    }

    interface OnTextChangeListener {
        fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int)
    }
}