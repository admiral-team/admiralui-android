package com.admiral.uikit.components.textfield

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.inputmethod.EditorInfo
import androidx.annotation.ColorRes
import androidx.core.content.res.use
import androidx.core.widget.doOnTextChanged
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.uikit.R
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.ext.applyStyle
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.admiral.resources.R as res
import com.admiral.uikit.core.R as core

internal class TextFieldSearchOld @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatEditText(
    ContextThemeWrapper(context, com.google.android.material.R.style.Widget_AppCompat_EditText),
    attrs,
    defStyleAttr
), ThemeObserver {

    private var textFlowField = MutableStateFlow<String?>(null)

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

    /**
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorRes
    var iconTintColor: Int? = null
        set(value) {
            field = value
            invalidateIconColors()
        }

    /**
     * In case Drawable is null, the default icon will be chosen.
     */
    var drawableStart: Drawable? = null
        set(value) {
            field = value
            invalidateIcon()
        }

    var isDrawableStartVisible: Boolean = true
        set(value) {
            field = value
            invalidateIcon()
        }

    /**
     * Listener to detect text changing.
     */
    var onTextChangeListener: OnTextChangeListener? = null
        set(value) {
            field = value
            setTextChangedListener()
        }

    private val drawableClose =
        drawable(res.drawable.admiral_ic_close_outline)?.colored(
            colorStateList(
                enabled = iconTintColor ?: ThemeManager.theme.palette.elementPrimary,
                disabled = iconTintColor ?: ThemeManager.theme.palette.elementPrimary.withAlpha(),
                pressed = iconTintColor ?: ThemeManager.theme.palette.elementPrimary
            )
        )

    init {
        background = drawable(R.drawable.admiral_bg_rectangle_10dp)
        setPadding(PADDING_START.dpToPx(context), 0, PADDING_END.dpToPx(context), 0)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE)

        parseAttrs(attrs, R.styleable.TextFieldSearch).use {
            parseTextColors(it)
            parseBackgroundColors(it)
            parseIcon(it)
            parseIconColors(it)

            inputType =
                it.getInt(R.styleable.TextFieldSearch_android_inputType, EditorInfo.TYPE_CLASS_TEXT)
            imeOptions = it.getInt(
                R.styleable.TextFieldSearch_android_imeOptions,
                EditorInfo.IME_ACTION_SEARCH
            )
            isDrawableStartVisible =
                it.getBoolean(R.styleable.TextFieldSearch_admiralIsDrawableStartVisible, true)
        }

        setupClearButtonWithAction()

        setPadding(
            paddingStart,
            0,
            paddingEnd,
            0
        )

        applyStyle(Typography.getStyle(ThemeManager.theme.typography.body2))

        doOnTextChanged { text, _, _, _ ->
            textFlowField.value = text.toString()
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
        invalidateHintColors()
        invalidateIconColors()
        invalidateBackgroundColors()
        invalidateTextColors()
    }

    private fun parseIcon(a: TypedArray) {
        drawableStart =
            a.getDrawable(R.styleable.TextFieldSearch_admiralIcon)
                ?: drawable(res.drawable.admiral_ic_search_outline)
    }

    private fun parseIconColors(a: TypedArray) {
        iconTintColor =
            a.getColorOrNull(R.styleable.TextFieldSearch_admiralIconTintColorNormalEnabled)
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
        setHintTextColor(
            colorStateList(
                enabled = hintTextColors?.normalEnabled ?: ThemeManager.theme.palette.textSecondary,
                pressed = hintTextColors?.pressed ?: ThemeManager.theme.palette.textSecondary,
                disabled = hintTextColors?.normalDisabled
                    ?: ThemeManager.theme.palette.textSecondary.withAlpha()
            )
        )
    }

    private fun invalidateTextColors() {
        setTextColor(
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

    private fun invalidateIconColors() {
        if (isDrawableStartVisible) {
            drawableStart?.colored(iconTintColor ?: ThemeManager.theme.palette.elementPrimary)
            setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, null, null)
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        }
    }

    private fun invalidateIcon() {
        if (isDrawableStartVisible) {
            compoundDrawablePadding = pixels(core.dimen.module_x2)
            drawableStart?.colored(iconTintColor ?: ThemeManager.theme.palette.elementPrimary)
            setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, null, null)
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        }
    }

    private fun setupClearButtonWithAction() {
        setTextChangedListener()

        setOnTouchListener(OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (this.right - this.compoundPaddingRight)) {
                    this.setText("")
                    this.clearFocus()
                    return@OnTouchListener true
                }
            }
            return@OnTouchListener false
        })
    }

    private fun setTextChangedListener() {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                if (editable?.isNotEmpty() == true) {
                    if (isDrawableStartVisible) {
                        setCompoundDrawablesWithIntrinsicBounds(
                            drawableStart,
                            null,
                            drawableClose,
                            null
                        )
                    } else {
                        setCompoundDrawablesWithIntrinsicBounds(null, null, drawableClose, null)
                    }
                } else {
                    if (isDrawableStartVisible) {
                        setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, null, null)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChangeListener?.onTextChanged(s, start, before, count)
            }
        })
    }

    private companion object {
        const val PADDING_START = 12
        const val PADDING_END = 12
        const val TEXT_SIZE = 16F
    }

    interface OnTextChangeListener {
        fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int)
    }
}