package com.admiral.uikit.components.textfield

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.doOnLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.informer.InformerSmall
import com.admiral.uikit.databinding.AdmiralViewSliderBinding
import com.admiral.uikit.ext.applyStyle
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.ext.showKeyboard
import com.google.android.material.slider.Slider as MaterialSlider

/**
 * Slider view with input.
 * Please, use filter, formatters, listeners from standard SDK or use custom.
 */
class Slider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    private val binding = AdmiralViewSliderBinding
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
     * The variable holds current value of the slider.
     */
    var value: Float = 0f
        set(value) {
            field = value
            if (value < valueFrom) {
                field = valueFrom
            }
            if (value > valueTo) {
                field = valueTo
            }

            binding.materialSlider.value = field
            updateEditTextValue()
        }

    /**
     * Color state for all text elements in [Slider] except color of input text and error color.
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
            binding.inputLayout.placeholderText = value
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
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorInt
    var iconTintColor: Int? = null
        set(value) {
            field = value
            binding.iconImageView.imageTintList = value?.let(ColorStateList::valueOf)
        }

    /**
     * In case Drawable is null, the icon will be hidden.
     */
    var icon: Drawable? = null
        set(value) {
            field = value
            binding.iconImageView.apply {
                setImageDrawable(icon)
                isGone = value == null
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
     * Listener for the slider. Called every time the value is changed.
     */
    var onChangeListener: OnValueChangeListener? = null
        set(value) {
            field = value
            invalidateListener()
        }

    /**
     * Variable that holds if [InformerSmall] is visible
     */
    val isInformerVisible: Boolean
        get() {
            return informer.isVisible
        }

    /**
     * Standard [InformerSmall] for showing hint text.
     */
    val informer: InformerSmall
        get() = binding.admiralSliderInformer
    // endregion

    // region public methods
    /**
     * Listener to detect icon clicking.
     */
    fun setOnIconClickListener(onClickListener: OnClickListener) {
        binding.iconImageView.setOnClickListener(onClickListener)
    }

    /**
     * Show an informer with given text
     */
    fun showInformer(informerText: String) {
        informer.info = informerText
        informer.isVisible = true
    }

    /**
     * Hide the informer
     */
    fun hideInformer() {
        informer.isVisible = false
    }

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
        parseAttrs(attrs, R.styleable.Slider).use {
            parseDefaultColors(it)
            parseTexts(it)
            parseErrorColor(it)
            parseIcon(it)

            isEnabled = it.getBoolean(R.styleable.Slider_enabled, true)
        }

        setPadding(
            pixels(R.dimen.module_x4),
            pixels(R.dimen.module_x4),
            pixels(R.dimen.module_x4),
            pixels(R.dimen.module_x1)
        )

        initEditText()

        binding.inputLayout.setHintTextAppearance(Typography.getStyle(ThemeManager.theme.typography.subhead2))
        binding.additionalTextView.textStyle = ThemeManager.theme.typography.subhead2

        onChangeListener = null
        binding.materialSlider.value = valueFrom
        updateEditTextValue()
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
            Handler(Looper.getMainLooper()).post {
                iconImageView.isEnabled = enabled
                additionalTextView.isEnabled = enabled
                inputLayout.isEnabled = enabled
                editText.isEnabled = enabled
                leftTextView.isEnabled = enabled
                rightTextView.isEnabled = enabled
            }
            invalidateColors()
            if (enabled) {
                materialSlider.alpha = ALPHA_ENABLED
            } else {
                materialSlider.alpha = ALPHA_DISABLED
            }
            materialSlider.setOnTouchListener { v, event -> !enabled }
        }
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateColors()
    }
    // endregion

    // region private methods
    private fun initEditText() {
        with(binding) {
            editText.apply {
                applyStyle(Typography.getStyle(ThemeManager.theme.typography.body1))
                inputType = EditorInfo.TYPE_CLASS_NUMBER

                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        val text = editText.text.toString()
                        val newValue = if (text.isNotBlank()) text.toFloat() else null
                        with(materialSlider) {
                            if (newValue != null && newValue in valueFrom..valueTo) {
                                value = newValue
                            } else {
                                updateEditTextValue()
                            }
                        }
                    }
                    false
                }

                setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        updateEditTextValue()
                    }
                }
            }
        }
    }

    private fun updateEditTextValue() {
        val value = binding.materialSlider.value.toInt().toString()
        if (editText.text.toString() != value) {
            Handler(Looper.getMainLooper()).post {
                editText.setText(value)
                editText.requestFocus()
            }
        }
    }

    private fun parseErrorColor(a: TypedArray) {
        if (a.hasValue(R.styleable.Slider_admiralErrorColor)) {
            errorColor =
                a.getInt(R.styleable.Slider_admiralErrorColor, ThemeManager.theme.palette.textError)
        }
    }

    private fun parseTexts(a: TypedArray) {
        leftText = a.getString(R.styleable.Slider_admiralLeftText)
        rightText = a.getString(R.styleable.Slider_admiralRightText)
        optionalText = a.getString(R.styleable.Slider_admiralTextOptional)
        placeholderText = a.getString(R.styleable.Slider_admiralTextPlaceholder)
        additionalText = a.getString(R.styleable.Slider_admiralTextAdditional)
    }

    private fun parseIcon(a: TypedArray) {
        icon = a.getDrawable(R.styleable.Slider_admiralIcon)

        if (a.hasValue(R.styleable.Slider_admiralIconTintColor)) {
            iconTintColor = a.getColor(R.styleable.Slider_admiralIconTintColor, Color.TRANSPARENT)
        }
    }

    private fun parseDefaultColors(a: TypedArray) {
        inputTextColor = a.getColorOrNull(
            R.styleable.Slider_admiralTextInputColor,
        )

        textColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.Slider_admiralTextColorNormalEnabled),
            pressed = a.getColorOrNull(R.styleable.Slider_admiralTextColorFocused),
            normalDisabled = a.getColorOrNull(R.styleable.Slider_admiralTextColorNormalDisabled),
            focused = a.getColorOrNull(R.styleable.Slider_admiralTextColorFocused)
        )
    }

    private fun invalidateListener() {
        binding.materialSlider.addOnChangeListener { slider, value, fromUser ->
            onChangeListener?.onValueChange(slider, value, fromUser)
            updateEditTextValue()
        }
    }

    private fun invalidateColors() {
        invalidateEditTextColor()
        invalidateRangeTextsColor()
        invalidateHintTextColor()
        invalidateAdditionalTextColor()
        invalidateSliderTextColor()
    }

    private fun invalidateSliderTextColor() {
        binding.materialSlider.apply {
            thumbRadius = THUMB_RADIUS.dpToPx(context)
            thumbStrokeWidth = THUMB_STROKE_WIDTH.dpToPx(context).toFloat()
            thumbStrokeColor = ColorStateList.valueOf(ThemeManager.theme.palette.elementAccent)
            thumbTintList = ColorStateList.valueOf(ThemeManager.theme.palette.elementStaticWhite)
            trackHeight = TRACK_HEIGHT.dpToPx(context)
            trackActiveTintList = ColorStateList.valueOf(ThemeManager.theme.palette.elementAccent)
            trackInactiveTintList = ColorStateList.valueOf(ThemeManager.theme.palette.elementPrimary)
        }
    }

    private fun invalidateAdditionalTextColor() {
        val additionalTextColor: Int = when {
            isError -> errorColor ?: ThemeManager.theme.palette.textError
            !isEnabled -> textColors?.normalDisabled ?: ThemeManager.theme.palette.textSecondary.withAlpha()
            else -> textColors?.normalEnabled ?: ThemeManager.theme.palette.textSecondary
        }
        binding.additionalTextView.textColor = ColorState(additionalTextColor)
    }

    private fun invalidateHintTextColor() {
        var defaultColor: Int = when {
            isError -> errorColor ?: ThemeManager.theme.palette.textError
            isNowFocused -> textColors?.focused ?: ThemeManager.theme.palette.textAccent
            !isEnabled -> textColors?.normalDisabled ?: ThemeManager.theme.palette.textSecondary.withAlpha()
            else -> textColors?.normalEnabled ?: ThemeManager.theme.palette.textSecondary
        }

        if (isError && !isEnabled) {
            defaultColor = errorColor?.withAlpha() ?: ThemeManager.theme.palette.textError.withAlpha()
        }

        binding.inputLayout.apply {
            Handler(Looper.getMainLooper()).post {
                defaultHintTextColor = ColorStateList.valueOf(defaultColor)
            }

            doOnLayout {
                placeholderTextColor = ColorStateList.valueOf(ThemeManager.theme.palette.textMask)
            }
        }
    }

    private fun invalidateRangeTextsColor() {
        val colorStateList: ColorStateList = ColorStateList.valueOf(
            (textColors?.normalEnabled ?: ThemeManager.theme.palette.textSecondary)
                .let { if (isEnabled) it else it.withAlpha() }
        )

        binding.leftTextView.setTextColor(colorStateList)
        binding.rightTextView.setTextColor(colorStateList)
    }

    private fun invalidateEditTextColor() {
        val editTextColorState = colorStateList(
            enabled = inputTextColor ?: ThemeManager.theme.palette.textPrimary,
            disabled = inputTextColor?.withAlpha() ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = inputTextColor ?: ThemeManager.theme.palette.textPrimary
        )
        binding.textFieldSearchEditText.setTextColor(editTextColorState)
    }

    private fun invalidateTextHint() {
        binding.inputLayout.apply {
            isHintEnabled = !optionalText.isNullOrEmpty()
            hint = optionalText
        }
        editText.hint = if (optionalText == null) placeholderText else null
    }

    private fun invalidateValueFrom() {
        if (binding.materialSlider.value < valueFrom) {
            binding.materialSlider.value = valueFrom
        }
        binding.materialSlider.valueFrom = valueFrom
        leftText = valueFrom.toInt().toString()
    }

    private fun invalidateValueTo() {
        if (binding.materialSlider.value > valueTo) {
            binding.materialSlider.value = valueFrom
        }
        binding.materialSlider.valueTo = valueTo
        rightText = valueTo.toInt().toString()
    }
    // endregion

    interface OnValueChangeListener {
        fun onValueChange(slider: MaterialSlider, value: Float, fromUser: Boolean)
    }

    private companion object {
        const val ALPHA_ENABLED = 1f
        const val ALPHA_DISABLED = 0.6f
        const val THUMB_RADIUS = 10
        const val THUMB_STROKE_WIDTH = 5
        const val TRACK_HEIGHT = 2
    }
}