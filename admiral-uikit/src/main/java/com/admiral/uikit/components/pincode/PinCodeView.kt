package com.admiral.uikit.components.pincode

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import com.admiral.uikit.layout.LinearLayout
import androidx.annotation.IntRange
import androidx.core.content.res.use
import androidx.core.view.forEachIndexed
import com.admiral.uikit.R
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.uikit.ext.getColorOrNull

/**
 * It represents a set of indicator dots.
 */
class PinCodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    /**
     * In case color is null, the selected color theme will be used.
     */
    var defaultColor: Int? = null
        set(value) {
            field = value
            invalidateColors()
        }

    /**
     * In case color is null, the selected color theme will be used.
     */
    var activeColor: Int? = null
        set(value) {
            field = value
            invalidateColors()
        }

    /**
     * In case color is null, the selected color theme will be used.
     */
    var successColor: Int? = null
        set(value) {
            field = value
            invalidateColors()
        }

    /**
     * In case color is null, the selected color theme will be used.
     */
    var errorColor: Int? = null
        set(value) {
            field = value
            invalidateColors()
        }

    /**
     * Current count of dots.
     * Must be in range between 2 and 8.
     */
    @IntRange(from = 2, to = 8)
    var dotsCount: Int = DEFAULT_DOTS_COUNT
        set(value) {
            if (value < MIN_DOTS_COUNT || value > MAX_DOTS_COUNT) return

            field = value

            if (value < activeDotsCount) {
                activeDotsCount = value
            }

            invalidateDots()
        }

    /**
     * Current count of active dots.
     * Must be in range between 0 and 8.
     */
    @IntRange(from = 0, to = 8)
    var activeDotsCount: Int = 0
        set(value) {
            if (value < 0 || value > MAX_DOTS_COUNT || value > dotsCount) return

            field = value
            invalidateColors()
        }

    /**
     * State of PinCodeView
     */
    var state = PinCodeState.DEFAULT
        set(value) {
            field = value
            animateState()
            invalidateColors()
        }

    /**
     * Disable or enable shake animation for error state.
     * @see PinCodeState.ERROR
     */
    var isAnimationEnabled: Boolean = false

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        val padding = pixels(R.dimen.module_x5) / 2

        setPadding(padding, 0, padding, 0)

        parseAttrs(attrs, R.styleable.PinCodeView).use {
            defaultColor = it.getColorOrNull(R.styleable.PinCodeView_admiralDefaultColor)
            activeColor = it.getColorOrNull(R.styleable.PinCodeView_admiralActiveColor)
            successColor = it.getColorOrNull(R.styleable.PinCodeView_admiralSuccessColor)
            errorColor = it.getColorOrNull(R.styleable.PinCodeView_admiralErrorColor)

            dotsCount = it.getInt(R.styleable.PinCodeView_admiralDotCount, DEFAULT_DOTS_COUNT)
            isAnimationEnabled = it.getBoolean(
                R.styleable.PinCodeView_admiralAnimationEnabled,
                false
            )
        }
    }

    override fun onThemeChanged(theme: Theme) {
        super.onThemeChanged(theme)
        invalidateColors()
    }

    private fun animateState() {
        if (state == PinCodeState.ERROR && isAnimationEnabled) {
            startAnimation(AnimationUtils.loadAnimation(context, R.anim.admiral_anim_shake))
        }
    }

    private fun invalidateColors() {
        forEachIndexed(::setBackgroundForDot)
    }

    private fun setBackgroundForDot(index: Int, view: View) {
        val color = when (state) {
            PinCodeState.DEFAULT -> {
                if (index + 1 <= activeDotsCount) {
                    activeColor ?: ThemeManager.theme.palette.elementAccent
                } else {
                    defaultColor ?: ThemeManager.theme.palette.elementAdditional
                }
            }
            PinCodeState.SUCCESS -> successColor ?: ThemeManager.theme.palette.elementSuccess
            PinCodeState.ERROR -> errorColor ?: ThemeManager.theme.palette.elementError
        }

        view.backgroundTintList = ColorStateList.valueOf(color)
    }

    private fun invalidateDots() {
        when {
            childCount == 0 -> addViews(dotsCount)
            childCount > dotsCount -> removeViews(dotsCount, childCount - dotsCount)
            childCount < dotsCount -> addViews(dotsCount - childCount)
        }
    }

    private fun addViews(count: Int) {
        val size = pixels(R.dimen.module_x3)
        val margin = pixels(R.dimen.module_x5) / 2

        for (index in dotsCount - count until dotsCount) {
            addView(
                View(context).apply {
                    id = View.generateViewId()
                    layoutParams = LayoutParams(size, size).apply {
                        this.marginStart = margin
                        this.marginEnd = margin
                    }
                    background = drawable(R.drawable.admiral_bg_round)
                    setBackgroundForDot(index, this)
                }
            )
        }
    }

    private companion object {
        private const val DEFAULT_DOTS_COUNT = 4
        private const val MIN_DOTS_COUNT = 2
        private const val MAX_DOTS_COUNT = 8
    }
}