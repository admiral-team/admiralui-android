package com.admiral.uikit.components.badge

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.uikit.R
import com.admiral.uikit.ext.applyStyle
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

class Badge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), ThemeObserver {

    var badgeSize: BadgeSize = BadgeSize.NORMAL
        set(value) {
            field = value
            invalidateBadgeSize()
        }

    var badgeType: BadgeType = BadgeType.NORMAL
        set(value) {
            field = value
            invalidateBackgroundColors()
        }

    @ColorInt
    var textColor: Int? = null

    @ColorInt
    var badgeColorNormal: Int? = null
        set(value) {
            field = value
            invalidateBackgroundColors()
        }

    @ColorInt
    var badgeColorDisabled: Int? = null
        set(value) {
            field = value
            isEnabled = isEnabled
        }

    @ColorInt
    var borderColor: Int? = null
        set(value) {
            field = value
            invalidateBorderColors()
        }

    init {
        applyStyle(Typography.getStyle(ThemeManager.theme.typography.caption2, true))
        gravity = Gravity.CENTER
        background = ContextCompat.getDrawable(context, R.drawable.admiral_bg_rectangle_16dp_stroke_2dp)?.mutate()

        parseAttrs(attrs, R.styleable.Badge).use {
            text = it.getText(R.styleable.Badge_admiralText)

            textColor = it.getColorOrNull(R.styleable.Badge_admiralTextColor)
            badgeType = BadgeType.from(it.getInt(R.styleable.Badge_admiralBadgeStyle, 0))
            badgeSize = BadgeSize.from(it.getInt(R.styleable.Badge_admiralBadgeSize, 0))
            badgeColorNormal = it.getColorOrNull(R.styleable.Badge_admiralBadgeColor)
            badgeColorDisabled = it.getColorOrNull(R.styleable.Badge_admiralBadgeColorDisabled)
            borderColor = it.getColorOrNull(R.styleable.Badge_admiralBadgeBorderColor)
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
        invalidateTextColors()
        invalidateBorderColors()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightSpec = MeasureSpec.makeMeasureSpec(badgeSize.size.dpToPx(context), MeasureSpec.EXACTLY)
        val widthSpec = when (badgeSize) {
            BadgeSize.SMALL -> heightSpec
            BadgeSize.NORMAL -> {
                val additionalWidthInDp = maxOf(0, text.lastIndex * WIDTH_INCREASING_STEP_IN_DP)
                MeasureSpec.makeMeasureSpec((badgeSize.size + additionalWidthInDp).dpToPx(context), MeasureSpec.EXACTLY)
            }
        }

        super.onMeasure(widthSpec, heightSpec)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        invalidateBackgroundColors()
    }

    private fun invalidateTextColors() {
        val colorState = textColor ?: ThemeManager.theme.palette.textStaticWhite

        setTextColor(colorState)
    }

    private fun invalidateBackgroundColors() {
        if (isEnabled) {
            val backgroundColor = when (badgeType) {
                BadgeType.NORMAL -> ThemeManager.theme.palette.elementAccent
                BadgeType.ERROR -> ThemeManager.theme.palette.elementError
                BadgeType.ATTENTION -> ThemeManager.theme.palette.elementAttention
                BadgeType.SUCCESS -> ThemeManager.theme.palette.elementSuccess
                BadgeType.NEUTRAL -> ThemeManager.theme.palette.elementSecondary
                BadgeType.ADDITIONAL -> ThemeManager.theme.palette.elementPrimary
            }

            val drawable = background as GradientDrawable
            drawable.setColor(badgeColorNormal ?: backgroundColor)
        } else {
            val drawable = background as GradientDrawable
            drawable.setColor(badgeColorDisabled ?: ThemeManager.theme.palette.elementAdditional)
        }
    }

    private fun invalidateBadgeSize() {
        when (badgeSize) {
            BadgeSize.SMALL -> {
                textColor = Color.TRANSPARENT
            }
            BadgeSize.NORMAL -> {
                invalidateTextColors()
            }
        }
        invalidate()
    }

    private fun invalidateBorderColors() {
        val drawable = background as GradientDrawable
        drawable.setStroke(
            STROKE_SIZE_IN_DP.dpToPx(context),
            borderColor ?: ThemeManager.theme.palette.backgroundBasic
        )
    }

    private companion object {
        const val STROKE_SIZE_IN_DP = 2
        const val WIDTH_INCREASING_STEP_IN_DP = 6
    }
}