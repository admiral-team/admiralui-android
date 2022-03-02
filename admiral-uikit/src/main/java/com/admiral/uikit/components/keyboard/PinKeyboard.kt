package com.admiral.uikit.components.keyboard

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.uikit.R
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.ripple
import com.admiral.uikit.layout.ConstraintLayout

class PinKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), View.OnClickListener {

    var keyListener: ((KeyboardNum) -> Unit)? = null

    var leftAdditionalButtonText: String? = null
        set(value) {
            field = value
            additionalLeftButton.text = leftAdditionalButtonText
            additionalLeftButton.isVisible = leftAdditionalButtonText?.isNotEmpty() ?: false
        }

    var rightAdditionalButtonIcon: Drawable? = null
        set(value) {
            field = value
            additionalRightButton.setImageDrawable(value)
        }

    var isRightAdditionalButtonVisible = false
        set(value) {
            field = value
            additionalRightButton.isVisible = value
        }

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled, pressed.
     */
    @ColorRes
    var rippleColor: Int? = null
        set(value) {
            field = value
            invalidateRippleColors()
        }

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled, pressed.
     */
    @ColorRes
    var textColor: Int? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled, pressed.
     */
    @ColorRes
    var additionalColor: Int? = null
        set(value) {
            field = value
            invalidateAdditionButtonsColors()
        }

    private val oneButton: TextView by lazy { findViewById(R.id.oneButton) }
    private val twoButton: TextView by lazy { findViewById(R.id.twoButton) }
    private val threeButton: TextView by lazy { findViewById(R.id.threeButton) }
    private val fourButton: TextView by lazy { findViewById(R.id.fourButton) }
    private val fiveButton: TextView by lazy { findViewById(R.id.fiveButton) }
    private val sixButton: TextView by lazy { findViewById(R.id.sixButton) }
    private val sevenButton: TextView by lazy { findViewById(R.id.sevenButton) }
    private val eightButton: TextView by lazy { findViewById(R.id.eightButton) }
    private val nineButton: TextView by lazy { findViewById(R.id.nineButton) }
    private val zeroButton: TextView by lazy { findViewById(R.id.zeroButton) }
    private val additionalLeftButton: TextView by lazy { findViewById(R.id.additionalLeftButton) }
    private val additionalRightButton: ImageView by lazy { findViewById(R.id.additionalRightButton) }
    private var numbers = listOf<TextView>()

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_pin_keyboard, this)
        parseAttrs(attrs, R.styleable.PinKeyboard).use {
            textColor = it.getColorOrNull(R.styleable.PinKeyboard_admiralTextColor)
            rippleColor = it.getColorOrNull(R.styleable.PinKeyboard_admiralRippleColor)
            additionalColor = it.getColorOrNull(R.styleable.PinKeyboard_admiralAdditionalButtonsColor)
        }

        numbers = listOf(
            oneButton, twoButton, threeButton, fourButton, fiveButton,
            sixButton, sevenButton, eightButton, nineButton, zeroButton
        )
        initListeners()
    }

    override fun onThemeChanged(theme: Theme) {
        super.onThemeChanged(theme)
        invalidateAdditionButtonsColors()
        invalidateTextColors()
        invalidateRippleColors()
    }

    private fun invalidateTextColors() {
        numbers.forEach {
            it.setTextColor(textColor ?: ThemeManager.theme.palette.textPrimary)
        }
    }

    private fun invalidateRippleColors() {
        val mask = context.drawable(R.drawable.admiral_bg_keynum_mask)
        numbers.forEach {
            it.background =
                ripple(rippleColor ?: ThemeManager.theme.palette.elementAdditional, null, mask)
        }

        additionalLeftButton.background =
            ripple(rippleColor ?: ThemeManager.theme.palette.elementAdditional, null, mask)

        additionalRightButton.background =
            ripple(rippleColor ?: ThemeManager.theme.palette.elementAdditional, null, mask)
    }

    private fun invalidateAdditionButtonsColors() {
        additionalLeftButton.setTextColor(additionalColor ?: ThemeManager.theme.palette.elementPrimary)

        additionalRightButton.imageTintList =
            ColorStateList.valueOf(additionalColor ?: ThemeManager.theme.palette.elementPrimary)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            oneButton.id -> keyListener?.invoke(KeyboardNum.ONE)
            twoButton.id -> keyListener?.invoke(KeyboardNum.TWO)
            threeButton.id -> keyListener?.invoke(KeyboardNum.THREE)
            fourButton.id -> keyListener?.invoke(KeyboardNum.FOUR)
            fiveButton.id -> keyListener?.invoke(KeyboardNum.FIVE)
            sixButton.id -> keyListener?.invoke(KeyboardNum.SIX)
            sevenButton.id -> keyListener?.invoke(KeyboardNum.SEVEN)
            eightButton.id -> keyListener?.invoke(KeyboardNum.EIGHT)
            nineButton.id -> keyListener?.invoke(KeyboardNum.NINE)
            zeroButton.id -> keyListener?.invoke(KeyboardNum.ZERO)
            additionalLeftButton.id -> keyListener?.invoke(KeyboardNum.ADDITIONAL_LEFT)
            additionalRightButton.id -> keyListener?.invoke(KeyboardNum.ADDITIONAL_RIGHT)
        }
    }

    private fun initListeners() {
        numbers.forEach {
            it.setOnClickListener(this)
        }
        additionalLeftButton.setOnClickListener(this)
        additionalRightButton.setOnClickListener(this)
    }
}