package com.admiral.uikit.components.textfield

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.doOnLayout
import androidx.core.view.isGone
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.databinding.AdmiralViewSliderDoubleBinding
import com.admiral.uikit.ext.applyStyle
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.ext.showKeyboard
import com.google.android.material.slider.RangeSlider

/**
 * Slider view with two inputs.
 */
class DoubleSlider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    private val binding = AdmiralViewSliderDoubleBinding
        .inflate(LayoutInflater.from(context), this)

    private val focusChangeListeners = mutableListOf<OnFocusChangeListener>()
    private var isNowFocused = false

    // region public fields
    /**
     * Current value to
     */
    var valueTo: Float = 0f
        set(value) {
            field = value
            invalidateValueTo()
        }

    /**
     * Current value from
     */
    var valueFrom: Float = 0f
        set(value) {
            field = value
            invalidateValueFrom()
        }

    /**
     * The variable holds current range' values.
     */
    var values: List<Float>
        set(value) {
            binding.rangeSlider.values = value
        }
        get() = binding.rangeSlider.values

    /**
     * Color state for all text elements in [DoubleSlider] except color of input text and error color.
     * States: normal, disabled, focused.
     * In case state is null, the selected color theme will be used.
     */
    var textColors: ColorState? = null
        set(value) {
            field = value
            invalidateColors()
        }

    /**
     * Color of input text.
     * In case color is null, the selected color theme will be used.
     */
    @ColorInt
    var inputTextColor: Int? = null
        set(value) {
            field = value
            invalidateColors()
        }

    /**
     * Color of error state. Acceptable only for divider, optional text and additional text.
     * In case color is null, the selected color theme will be used.
     */
    @ColorInt
    var errorColor: Int? = null
        set(value) {
            field = value
            invalidateColors()
        }

    /**
     * Floating optional text.
     */
    var optionalText: String? = null
        set(value) {
            field = value
            invalidateTextHint()
        }

    /**
     * Placeholder text that will be displayed in the input area when the [optionalText] is collapsed
     * before text is entered or when the [optionalText] is null or empty.
     */
    var placeholderText: String? = null
        set(value) {
            field = value
            binding.inputLayoutFrom.placeholderText = value
            invalidateTextHint()
        }

    /**
     * Placeholder text to that will be displayed in the input area when the [optionalText] is collapsed
     * before text is entered or when the [optionalText] is null or empty.
     */
    var placeholderTextTo: String? = null
        set(value) {
            field = value
            //binding.inputLayoutTo.placeholderText = value
            invalidateTextHint()
        }

    /**
     * Additional text which is placed under divider.
     */
    var additionalText: String? = null
        set(value) {
            field = value
            binding.additionalTextView.apply {
                text = value
                isGone = value.isNullOrEmpty()
            }
        }

    /**
     * Left text.
     */
    var leftText: String? = null
        set(value) {
            field = value
            binding.leftTextView.apply {
                text = value
                isGone = value.isNullOrEmpty()
            }
        }

    /**
     * Right text.
     */
    var rightText: String? = null
        set(value) {
            field = value
            binding.rightTextView.apply {
                text = value
                isGone = value.isNullOrEmpty()
            }
        }

    /**
     * Enable or disable error state.
     */
    var isError: Boolean = false
        set(value) {
            field = value
            invalidateColors()
        }

    /**
     * Standard [EditText] for settings filters, formatter, etc.
     */
    val editText: EditText
        get() = binding.textFieldSearchEditText

    /**
     * Standard [EditText] for settings filters, formatter, etc.
     */
    val editTextTo: EditText
        get() = binding.editTextTo

    /**
     * Listener for the seekBar. Called every time the value is changed.
     */
    var onChangeListener: OnValueChangeListener? = null
        set(value) {
            field = value
            invalidateListener()
        }
    // endregion

    // region public methods
    /**
     * Add input focus listener
     */
    fun addInputFocusListener(listener: OnFocusChangeListener) {
        focusChangeListeners.add(listener)
    }

    /**
     * Remove input focus listener
     */
    fun removeInputFocusListener(listener: OnFocusChangeListener) {
        focusChangeListeners.remove(listener)
    }

    /**
     * Request focus and show keyboard.
     */
    fun performFocus() {
        editText.showKeyboard()
    }
    // endregion

    init {
        parseAttrs(attrs, R.styleable.DoubleSlider).use {
            parseDefaultColors(it)
            parseTexts(it)
            parseErrorColor(it)

            isEnabled = it.getBoolean(R.styleable.DoubleSlider_enabled, true)
        }

        setPadding(
            pixels(R.dimen.module_x4),
            pixels(R.dimen.module_x4),
            pixels(R.dimen.module_x4),
            pixels(R.dimen.module_x1)
        )

        initEditTexts()

        binding.inputLayoutFrom.setHintTextAppearance(Typography.getStyle(ThemeManager.theme.typography.subhead2))
        binding.inputLayoutTo.setHintTextAppearance(Typography.getStyle(ThemeManager.theme.typography.subhead2))

        binding.additionalTextView.textStyle = ThemeManager.theme.typography.subhead2

        onChangeListener = null
        binding.rangeSlider.setValues(valueFrom, valueTo)
        updateEditTextValues()
        invalidateColors()
    }

    // region override methods
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.subscribe(this)
    }

    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        with(binding) {
            inputLayoutFrom.isEnabled = enabled
            inputLayoutTo.isEnabled = enabled
            editText.isEnabled = enabled
            additionalTextView.isEnabled = enabled
            invalidateColors()
            if (enabled) {
                rangeSlider.alpha = ALPHA_ENABLED
            } else {
                rangeSlider.alpha = ALPHA_DISABLED
            }

            rangeSlider.setOnTouchListener { v, event -> !enabled }
        }
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateColors()
    }
    // endregion

    // region private methods
    private fun initEditTexts() {
        with(binding) {
            editText.apply {
                applyStyle(Typography.getStyle(ThemeManager.theme.typography.body1))
                inputType = EditorInfo.TYPE_CLASS_NUMBER

                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        val text = editText.text.toString()
                        val newValue = if (text.isNotBlank()) text.toFloat() else null
                        with(rangeSlider) {
                            val second = values.last()
                            if (newValue != null && newValue in valueFrom..valueTo && newValue <= second) {
                                values = listOf(newValue, second)
                            } else {
                                updateEditTextValues()
                            }
                        }
                    }
                    false
                }

                setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        updateEditTextValues()
                    }
                }
            }

            editTextTo.apply {
                applyStyle(Typography.getStyle(ThemeManager.theme.typography.body1))
                inputType = EditorInfo.TYPE_CLASS_NUMBER

                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        val text = editTextTo.text.toString()
                        val newValue = if (text.isNotBlank()) text.toFloat() else null
                        with(rangeSlider) {
                            val first = values.first()
                            if (newValue != null && newValue in valueFrom..valueTo && newValue >= first) {
                                values = listOf(first, newValue)
                            } else {
                                updateEditTextValues()
                            }
                        }
                    }
                    false
                }

                setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        updateEditTextValues()
                    }
                }
            }
        }
    }

    private fun updateEditTextValues() {
        val first = binding.rangeSlider.values.first().toInt().toString()
        val second = binding.rangeSlider.values.last().toInt().toString()
        if (editText.text.toString() != first) {
            editText.setText(first)
        }
        if (editTextTo.text.toString() != second) {
            editTextTo.setText(second)
        }
    }

    private fun parseErrorColor(a: TypedArray) {
        if (a.hasValue(R.styleable.DoubleSlider_admiralErrorColor)) {
            errorColor = a.getInt(R.styleable.DoubleSlider_admiralErrorColor, ThemeManager.theme.palette.textError)
        }
    }

    private fun parseTexts(a: TypedArray) {
        leftText = a.getString(R.styleable.DoubleSlider_admiralLeftText)
        rightText = a.getString(R.styleable.DoubleSlider_admiralRightText)
        optionalText = a.getString(R.styleable.DoubleSlider_admiralTextOptional)
        placeholderText = a.getString(R.styleable.DoubleSlider_admiralTextOptional)
        additionalText = a.getString(R.styleable.DoubleSlider_admiralTextAdditional)
    }

    private fun parseDefaultColors(a: TypedArray) {
        inputTextColor = a.getColorOrNull(
            R.styleable.DoubleSlider_admiralTextInputColor,
        )

        textColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.DoubleSlider_admiralTextColorNormalEnabled),
            pressed = a.getColorOrNull(R.styleable.DoubleSlider_admiralTextColorFocused),
            normalDisabled = a.getColorOrNull(R.styleable.DoubleSlider_admiralTextColorNormalDisabled),
            focused = a.getColorOrNull(R.styleable.DoubleSlider_admiralTextColorFocused)
        )
    }

    private fun invalidateListener() {
        binding.rangeSlider.addOnChangeListener { slider, value, fromUser ->
            onChangeListener?.onValueChange(slider, value, fromUser)
            updateEditTextValues()
        }
    }

    private fun invalidateColors() {
        val editTextColorState = colorStateList(
            enabled = inputTextColor ?: ThemeManager.theme.palette.textPrimary,
            disabled = inputTextColor?.withAlpha() ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = inputTextColor ?: ThemeManager.theme.palette.textPrimary
        )
        editText.setTextColor(editTextColorState)
        editTextTo.setTextColor(editTextColorState)

        val colorStateList: ColorStateList = ColorStateList.valueOf(
            (textColors?.normalEnabled ?: ThemeManager.theme.palette.textSecondary)
                .let { if (isEnabled) it else it.withAlpha() }
        )
        binding.inputLayoutFrom.setPrefixTextColor(colorStateList)
        binding.inputLayoutTo.setPrefixTextColor(colorStateList)
        binding.leftTextView.setTextColor(colorStateList)
        binding.rightTextView.setTextColor(colorStateList)

        val defaultColor: Int = when {
            isError -> errorColor ?: ThemeManager.theme.palette.textError
            isNowFocused -> textColors?.focused ?: ThemeManager.theme.palette.textAccent
            !isEnabled -> textColors?.normalDisabled ?: ThemeManager.theme.palette.textSecondary.withAlpha()
            else -> textColors?.normalEnabled ?: ThemeManager.theme.palette.textSecondary
        }
        val placeholderTextColorStateList = ColorStateList.valueOf(ThemeManager.theme.palette.textMask)

        binding.inputLayoutFrom.apply {
            defaultHintTextColor = ColorStateList.valueOf(defaultColor)
            doOnLayout {
                placeholderTextColor = placeholderTextColorStateList
            }
        }
        binding.inputLayoutTo.apply {
            defaultHintTextColor = ColorStateList.valueOf(defaultColor)
            doOnLayout {
                placeholderTextColor = placeholderTextColorStateList
            }
        }

        val additionalTextColor: Int = when {
            isError -> errorColor ?: ThemeManager.theme.palette.textError
            !isEnabled -> textColors?.normalDisabled
                ?: ThemeManager.theme.palette.textSecondary.withAlpha()
            else -> textColors?.normalEnabled ?: ThemeManager.theme.palette.textSecondary
        }
        binding.additionalTextView.setTextColor(additionalTextColor)

        binding.rangeSlider.apply {
            thumbRadius = THUMB_RADIUS.dpToPx(context)
            thumbStrokeWidth = THUMB_STROKE_WIDTH.dpToPx(context).toFloat()
            thumbStrokeColor = ColorStateList.valueOf(ThemeManager.theme.palette.elementAccent)
            thumbTintList = ColorStateList.valueOf(ThemeManager.theme.palette.elementStaticWhite)

            trackHeight = TRACK_HEIGHT.dpToPx(context)
            trackActiveTintList = ColorStateList.valueOf(ThemeManager.theme.palette.elementAccent)
            trackInactiveTintList = ColorStateList.valueOf(ThemeManager.theme.palette.elementPrimary)
        }
    }

    private fun invalidateTextHint() {
        binding.inputLayoutFrom.apply {
            isHintEnabled = !optionalText.isNullOrEmpty()
            hint = optionalText
        }
        editText.hint = if (optionalText == null) placeholderText else null

//        binding.inputLayoutTo.apply {
//            isHintEnabled = !placeholderTextTo.isNullOrEmpty()
//            hint = placeholderTextTo
//        }
        editTextTo.hint = placeholderTextTo
    }

    private fun invalidateValueFrom() {
        with(binding) {
            rangeSlider.values = listOf(valueFrom, valueTo)
            rangeSlider.valueFrom = valueFrom
            leftText = valueFrom.toInt().toString()
        }
    }

    private fun invalidateValueTo() {
        with(binding) {
            rangeSlider.values = listOf(valueFrom, valueTo)
            rangeSlider.valueTo = valueTo
            rightText = valueTo.toInt().toString()
        }
    }
    // endregion

    interface OnValueChangeListener {
        fun onValueChange(slider: RangeSlider, value: Float, fromUser: Boolean)
    }

    private companion object {
        const val ALPHA_ENABLED = 1f
        const val ALPHA_DISABLED = 0.6f
        const val THUMB_RADIUS = 10
        const val THUMB_STROKE_WIDTH = 5
        const val TRACK_HEIGHT = 2
    }
}
