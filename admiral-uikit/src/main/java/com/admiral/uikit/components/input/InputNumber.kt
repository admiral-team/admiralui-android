package com.admiral.uikit.components.input

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isGone
import androidx.core.view.updateLayoutParams
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.components.textfield.TextField
import com.admiral.uikit.core.components.input.InputType
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.core.util.ComponentsRadius
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.coloredDrawable
import com.admiral.uikit.ext.createRoundedColoredStrokeDrawable
import com.admiral.uikit.ext.createRoundedRectangleDrawable
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.ripple
import com.admiral.uikit.ext.setSelectionEnd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.abs
import kotlin.math.max

class InputNumber @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * If the value is true - the size of the view that displays current value will automatically resize.
     * Otherwise the size will be calculated from the [minValue] and [maxValue] values.
     */
    var isAutoWidth: Boolean = true

    /**
     * Optional text.
     */
    var optionalText: String? = null
        set(value) {
            field = value
            optionalLabelTextView.text = value
            optionalLabelTextView.isGone = value.isNullOrEmpty()
        }

    /**
     * Current value.
     */
    var value: Int = 0
        set(value) {
            fun updateValue() {
                onValueChange?.invoke(field, value)
                field = value
            }

            when {
                value in minValue..maxValue -> {
                    updateValue()
                }

                value > maxValue -> {
                    autoIncrement = false
                }

                value < minValue -> {
                    autoDecrement = false
                }
            }

            val dfs = DecimalFormatSymbols()
            dfs.groupingSeparator = ' '
            val df = DecimalFormat("###,###", dfs)
            isChangeable = false
            valueEditText.inputText = df.format(field)
            isChangeable = true
            if (isAutoWidth) {
                setupValueTextViewWidth()
            }

            valueEditText.editText.setSelectionEnd()
            updateIncrementDecrementEnablingState()
        }

    /**
     * Max value of input number.
     */
    var maxValue: Int = DEFAULT_MAX_VALUE
        set(maxValue) {
            field = maxValue

            if (maxValue < value) {
                value = maxValue
            }
            setupValueTextViewWidth()
        }

    /**
     * Min value of input number.
     */
    var minValue: Int = DEFAULT_MIN_VALUE
        set(minValue) {
            field = minValue

            if (minValue > value) {
                value = minValue
            }
            setupValueTextViewWidth()
        }

    /**
     * Color state for all text elements in [InputNumber].
     * States: normal, disabled.
     * In case state is null, the selected color theme will be used.
     */
    var textColors: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled, pressed.
     */
    var iconBackgroundColors: ColorState? = null
        set(value) {
            field = value
            invalidateImageViews()
        }

    /**
     * Set the type of the view.
     * Currently available 3 types: [InputType.RECTANGLE], [InputType.OVAL], [InputType.TEXT_FIELD].
     * In [InputType.TEXT_FIELD] state it's allowed to set value by printing into [valueEditText].
     */
    var inputType: InputType = InputType.RECTANGLE
        set(value) {
            field = value
            invalidateType()
        }

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled, pressed.
     */
    var iconTintColors: ColorState? = null
        set(value) {
            field = value
            invalidateImageViews()
        }

    /**
     * In case Drawable is null, the default icon will be used.
     */
    var incrementIcon: Drawable? = null
        set(value) {
            field = value
            incrementImageView.setImageDrawable(
                value ?: context.drawable(R.drawable.admiral_ic_plus_outline)
            )
        }

    /**
     * In case Drawable is null, the default icon will be used.
     */
    var decrementIcon: Drawable? = null
        set(value) {
            field = value
            decrementImageView.setImageDrawable(
                value ?: context.drawable(R.drawable.admiral_ic_minus_outline)
            )
        }

    /**
     * Invoked when the value changes.
     * The value is always between [DEFAULT_MIN_VALUE] and [DEFAULT_MAX_VALUE]).
     */
    var onValueChange: ((old: Int, new: Int) -> Unit)? = null

    val valueEditText: TextField by lazy { findViewById(R.id.valueTextField) }
    private val optionalLabelTextView: TextView by lazy { findViewById(R.id.optionalLabelTextView) }
    private val decrementImageView: ImageView by lazy { findViewById(R.id.decrementImageView) }
    private val incrementImageView: ImageView by lazy { findViewById(R.id.incrementImageView) }
    private val editTextBackground: View by lazy { findViewById(R.id.editTextBackground) }

    private var coroutineScope: CoroutineScope? = null

    private var autoIncrement = false
        set(value) {
            field = value
            updateIncrementDecrementEnablingState()
        }

    private var autoDecrement = false
        set(value) {
            field = value
            updateIncrementDecrementEnablingState()
        }

    private var isChangeable = true

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (isChangeable) {
                val text = s.toString().replace(" ", "")
                value = if (text != "" && text != "-") {
                    text.toInt()
                } else {
                    0
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_input_number, this)

        parseAttrs(attrs, R.styleable.InputNumber).use {
            parseTextColors(it)
            parseIconBackgroundColors(it)
            parseIconColors(it)
            parseIcons(it)

            isEnabled = it.getBoolean(R.styleable.InputNumber_enabled, true)
            inputType = InputType.from(it.getInt(R.styleable.InputNumber_admiralinputType, 0))

            minValue = it.getInt(R.styleable.InputNumber_admiralInputMinValue, DEFAULT_MIN_VALUE)
            maxValue = it.getInt(R.styleable.InputNumber_admiralInputMaxValue, DEFAULT_MAX_VALUE)

            parseTexts(it)
        }

        setupIncrementView()
        setupDecrementView()
        setupValueEditText()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.subscribe(this)
        coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main).also {
            it.setupAutoIncrement()
        }
    }

    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
        coroutineScope?.cancel()
        coroutineScope = null
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        updateIncrementDecrementEnablingState()
        optionalLabelTextView.isEnabled = enabled
        valueEditText.isEnabled = enabled
        invalidateEditText()
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateTextColors()
        invalidateImageViews()
        invalidateEditText()
    }

    private fun CoroutineScope.setupAutoIncrement() = launch {
        while (true) {
            delay(DELAY_AUTO_INCREMENT)
            when {
                autoIncrement -> increment(isSingleTap = false)
                autoDecrement -> decrement(isSingleTap = false)
            }
        }
    }

    private fun setupValueTextViewWidth() {
        // if the size is auto we just take text from the edit text
        val max = if (isAutoWidth) {
            valueEditText.inputText + "    "
            // else we calculate which value will be higher
        } else {
            -max(abs(minValue), abs(maxValue))
        }

        // add spaces for the DecimalFormat("###,###", dfs) if the size is fixed
        val emptySpaces = if (isAutoWidth) {
            ""
        } else {
            " ".repeat(max(abs(minValue), abs(maxValue)).toString().length % NUMBER_SPACES)
        }

        val textWidth = valueEditText.editText.paint.measureText("$max $emptySpaces")
        valueEditText.updateLayoutParams {
            this.width = textWidth.toInt()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupIncrementView() {
        incrementImageView.setOnClickListener {
            increment(isSingleTap = true)
        }

        incrementImageView.setOnLongClickListener {
            autoIncrement = true
            valueEditText.editText.clearFocus()
            return@setOnLongClickListener true
        }

        incrementImageView.post {
            incrementImageView.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP && autoIncrement) {
                    autoIncrement = false
                }
                return@setOnTouchListener false
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupDecrementView() {
        decrementImageView.setOnClickListener {
            decrement(isSingleTap = true)
        }

        decrementImageView.setOnLongClickListener {
            autoDecrement = true
            valueEditText.editText.clearFocus()
            return@setOnLongClickListener true
        }

        decrementImageView.post {
            decrementImageView.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP && autoDecrement) {
                    autoDecrement = false
                }
                return@setOnTouchListener false
            }
        }
    }

    private fun setupValueEditText() {
        valueEditText.apply {
            isBottomLineVisible = false
            editText.gravity = Gravity.CENTER_HORIZONTAL
            isAdditionalTextVisible = false
            inputType = EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_SIGNED
            textWatcher = watcher

            editText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    autoDecrement = false
                    autoIncrement = false
                }
            }
        }
    }

    private fun calculateModifier(isSingleTap: Boolean): Int {
        if (isSingleTap || value == 0) return DEFAULT_MODIFIER

        var tempValue = value
        var modifier = DEFAULT_MODIFIER

        while (tempValue % DEFAULT_MULTIPLIER == 0) {
            modifier *= DEFAULT_MULTIPLIER
            tempValue /= DEFAULT_MULTIPLIER
        }

        return modifier
    }

    private fun updateIncrementDecrementEnablingState() {
        invalidateImageViews()
    }

    private fun increment(isSingleTap: Boolean = false) {
        val newValue = value + calculateModifier(isSingleTap)
        value = newValue
    }

    private fun decrement(isSingleTap: Boolean = false) {
        val newValue = value - calculateModifier(isSingleTap)
        value = newValue
    }

    private fun invalidateType() {
        valueEditText.isEditEnabled = inputType == InputType.TEXT_FIELD

        invalidateImageViews()
        invalidateEditText()
    }

    private fun invalidateEditText() {
        if (inputType == InputType.TEXT_FIELD) {
            editTextBackground.background =
                if (isEnabled) {
                    drawable(R.drawable.admiral_bg_rectangle)
                        ?.colored(ThemeManager.theme.palette.backgroundAdditionalOne)
                } else {
                    drawable(R.drawable.admiral_bg_rectangle)
                        ?.colored(ThemeManager.theme.palette.backgroundAdditionalOne.withAlpha())
                }

        } else {
            editTextBackground.background = null
        }
    }

    private fun invalidateImageViews() {
        val isDecrementEnabled = isEnabled && value != minValue && !autoIncrement
        val inIncrementEnabled = isEnabled && value != maxValue && !autoDecrement

        decrementImageView.isEnabled = isDecrementEnabled
        incrementImageView.isEnabled = inIncrementEnabled

        incrementImageView.invalidateIconBackgroundColors(inIncrementEnabled, false)
        decrementImageView.invalidateIconBackgroundColors(isDecrementEnabled, true)

        incrementImageView.invalidateIconTintColors(inIncrementEnabled)
        decrementImageView.invalidateIconTintColors(isDecrementEnabled)
    }

    private fun parseIcons(a: TypedArray) {
        decrementIcon = a.getDrawable(R.styleable.InputNumber_admiralDecrementIcon)
        incrementIcon = a.getDrawable(R.styleable.InputNumber_admiralIncrementIcon)
    }

    private fun parseIconColors(a: TypedArray) {
        iconTintColors = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.InputNumber_admiralIconTintColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.InputNumber_admiralIconTintColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.InputNumber_admiralIconTintColorNormalDisabled,
            )
        )
    }

    private fun parseIconBackgroundColors(a: TypedArray) {
        iconBackgroundColors = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.InputNumber_admiralIconBackgroundColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.InputNumber_admiralIconBackgroundColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.InputNumber_admiralIconBackgroundColorNormalDisabled,
            )
        )
    }

    private fun parseTexts(a: TypedArray) {
        optionalText = a.getString(R.styleable.InputNumber_admiralTextOptional)
        value = a.getInt(R.styleable.InputNumber_admiralInputValue, 0)
    }

    private fun parseTextColors(a: TypedArray) {
        textColors = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.InputNumber_admiralTextColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.InputNumber_admiralTextColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.InputNumber_admiralTextColorNormalDisabled,
            )
        )
    }

    private fun invalidateTextColors() {
        val textColorStateList = colorStateList(
            enabled = textColors?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
            disabled = textColors?.normalDisabled
                ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = textColors?.pressed ?: ThemeManager.theme.palette.textPrimary
        )

        optionalLabelTextView.setTextColor(textColorStateList)
        valueEditText.editText.setTextColor(textColorStateList)
    }

    private fun ImageView.invalidateIconBackgroundColors(isEnabled: Boolean, isLeft: Boolean) {
        when (inputType) {
            InputType.OVAL -> {
                this.setOvalBackgroundShape(isEnabled)
            }

            InputType.RECTANGLE -> {
                this.setRectangleBackgroundShape(isEnabled)
            }

            InputType.TEXT_FIELD -> {
                this.setTextFieldBackgroundShape(isEnabled, isLeft)
            }
        }
    }

    private fun ImageView.setOvalBackgroundShape(isEnabled: Boolean) {
        val backgroundNormalColor =
            iconBackgroundColors?.normalEnabled
                ?: ThemeManager.theme.palette.backgroundAdditionalOne

        val backgroundDisabledColor =
            iconBackgroundColors?.normalEnabled
                ?: ThemeManager.theme.palette.backgroundAdditionalOne.withAlpha()

        val backgroundColorState = if (isEnabled) {
            ColorStateList.valueOf(backgroundNormalColor)
        } else {
            ColorStateList.valueOf(backgroundDisabledColor)
        }

        val rippleColor =
            iconBackgroundColors?.pressed ?: ThemeManager.theme.palette.textPrimary.withAlpha(
                RIPPLE_ALPHA
            )
        val mask = context.drawable(R.drawable.admiral_bg_round)
        val content = context.coloredDrawable(R.drawable.admiral_bg_round, backgroundColorState)

        this.backgroundTintList = backgroundColorState
        this.background = ripple(rippleColor, content, mask)
        this.updateLayoutParams {
            width = SIZE_OVAL.dpToPx(context)
            height = SIZE_OVAL.dpToPx(context)
        }
    }

    private fun ImageView.setRectangleBackgroundShape(isEnabled: Boolean) {
        val radius = ComponentsRadius.RADIUS_8
        val rippleColor =
            iconBackgroundColors?.pressed ?: ThemeManager.theme.palette.textPrimary.withAlpha(
                RIPPLE_ALPHA
            )
        val mask = createRoundedRectangleDrawable(radius)

        val color = if (isEnabled) {
            ColorStateList.valueOf(
                iconBackgroundColors?.normalDisabled ?: ThemeManager.theme.palette.backgroundAccent
            )
        } else {
            ColorStateList.valueOf(
                iconBackgroundColors?.normalDisabled
                    ?: ThemeManager.theme.palette.backgroundAccent.withAlpha()
            )
        }
        val content = context.createRoundedColoredStrokeDrawable(radius, color)

        this.background = ripple(rippleColor, content, mask)
        this.updateLayoutParams {
            width = SIZE_RECTANGLE.dpToPx(context)
            height = SIZE_RECTANGLE.dpToPx(context)
        }
    }

    private fun ImageView.setTextFieldBackgroundShape(isEnabled: Boolean, isLeft: Boolean) {
        val radius = ComponentsRadius.RADIUS_8
        val rippleColor =
            iconBackgroundColors?.pressed ?: ThemeManager.theme.palette.textPrimary.withAlpha(
                RIPPLE_ALPHA
            )

        val mask = if (isLeft) {
            createRoundedRectangleDrawable(
                radius,
                ComponentsRadius.NONE,
                radius,
                ComponentsRadius.NONE
            )
        } else {
            createRoundedRectangleDrawable(
                ComponentsRadius.NONE,
                radius,
                ComponentsRadius.NONE,
                radius
            )
        }

        val backgroundNormalColor =
            iconBackgroundColors?.normalEnabled
                ?: ThemeManager.theme.palette.backgroundAdditionalOne

        val backgroundDisabledColor =
            iconBackgroundColors?.normalEnabled
                ?: ThemeManager.theme.palette.backgroundAdditionalOne.withAlpha()

        val backgroundColorState = if (isEnabled) {
            ColorStateList.valueOf(backgroundNormalColor)
        } else {
            ColorStateList.valueOf(backgroundDisabledColor)
        }

        this.backgroundTintList = backgroundColorState
        this.background = ripple(rippleColor, mask, mask)
        this.updateLayoutParams {
            width = SIZE_RECTANGLE.dpToPx(context)
            height = SIZE_RECTANGLE.dpToPx(context)
        }
    }

    private fun ImageView.invalidateIconTintColors(isEnabled: Boolean) {
        when (inputType) {
            InputType.OVAL -> {
                val iconTintColorStateList = if (isEnabled) {
                    ColorStateList.valueOf(
                        iconTintColors?.normalEnabled ?: ThemeManager.theme.palette.elementPrimary
                    )
                } else {
                    ColorStateList.valueOf(
                        iconTintColors?.normalEnabled
                            ?: ThemeManager.theme.palette.elementPrimary.withAlpha()
                    )
                }

                this.imageTintList = iconTintColorStateList
            }

            else -> {
                val iconTintColorStateList = if (isEnabled) {
                    ColorStateList.valueOf(
                        iconTintColors?.normalEnabled ?: ThemeManager.theme.palette.elementAccent
                    )
                } else {
                    ColorStateList.valueOf(
                        iconTintColors?.normalEnabled
                            ?: ThemeManager.theme.palette.elementAccent.withAlpha()
                    )
                }

                this.imageTintList = iconTintColorStateList
            }
        }
    }

    private companion object {
        private const val NUMBER_SPACES = 3
        private const val DELAY_AUTO_INCREMENT = 300L
        private const val DEFAULT_MIN_VALUE = -99999
        private const val DEFAULT_MAX_VALUE = 99999
        private const val DEFAULT_MODIFIER = 1
        private const val DEFAULT_MULTIPLIER = 10
        private const val RIPPLE_ALPHA = 0.1f
        private const val SIZE_RECTANGLE = 38
        private const val SIZE_OVAL = 40
    }
}