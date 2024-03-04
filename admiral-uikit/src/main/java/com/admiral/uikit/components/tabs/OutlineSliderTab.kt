package com.admiral.uikit.components.tabs

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.view.Gravity.CENTER
import android.view.View
import androidx.core.content.res.use
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.components.badge.Badge
import com.admiral.uikit.components.badge.BadgeSize
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.ext.colorStateListForChecked
import com.admiral.uikit.ext.colorStateListUnion
import com.admiral.uikit.ext.coloredDrawable
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.ext.ripple
import com.admiral.uikit.ext.setMargins
import com.admiral.uikit.view.checkable.CheckableFrameLayout
import com.admiral.uikit.view.checkable.CheckableView
import com.admiral.uikit.core.R as core

class OutlineSliderTab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CheckableFrameLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Colors for tab stroke in checked state.
     * States: checkedEnabled, checkedDisabled.
     * In case state is null, the selected color theme will be used.
     */
    var strokeColorState: ColorState? = null
        set(value) {
            field = value
            invalidateColors()
        }

    /**
     * Colors for tab stroke in checked state.
     * States: checkedEnabled, checkedDisabled.
     * In case state is null, the selected color theme will be used.
     */
    var textColorState: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    /**
     * CharSequence that is placed for the [textView]
     */
    var text: CharSequence = ""
        set(value) {
            field = value
            textView.text = value
        }
        get() {
            return textView.text
        }

    /**
     * Handle visibility of the [badge].
     */
    var isBadgeVisible: Boolean = false
        set(value) {
            field = value
            invalidateBadge()
        }

    /**
     * Handle if the [badge], is enabled or disabled.
     */
    var isBadgeEnabled: Boolean = false
        set(value) {
            field = value
            badge.isEnabled = value
        }

    /**
     * [Badge] that shown at the right-top corner of the tab.
     */
    var badge: Badge = Badge(context).apply {
        badgeSize = BadgeSize.SMALL
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.END or Gravity.TOP
        }
    }

    private var measuredWidthSaved: Int? = null
    private var measuredWidthSavedWithBadge: Int? = null

    internal var textView: TextView = TextView(context)

    init {
        addView(textView)
        addView(badge)
        textView.setSingleLine()

        textView.textStyle = ThemeManager.theme.typography.subhead3
        addOnCheckChangeListener(object : CheckableView.OnCheckedChangeListener {
            override fun onCheckedChanged(view: View, isChecked: Boolean) {
                textView.textStyle =
                    (if (isChecked) ThemeManager.theme.typography.subhead2 else ThemeManager.theme.typography.subhead3)
                invalidateTextColors()
            }
        })

        parseAttrs(attrs, R.styleable.OutlineSliderTab).use {
            parseStrokeColors(it)
            parseTextColors(it)
            parseText(it)
            isBadgeVisible =
                it.getBoolean(R.styleable.OutlineSliderTab_admiralIsBadgeVisible, false)
            isBadgeEnabled = it.getBoolean(R.styleable.OutlineSliderTab_admiralIsBadgeEnabled, true)
        }

        textView.gravity = CENTER
        isClickable = true
        isFocusable = true

        updatePadding(
            top = context.pixels(core.dimen.module_x2),
            bottom = context.pixels(core.dimen.module_x2),
            left = context.pixels(core.dimen.module_x2),
            right = context.pixels(core.dimen.module_x2)
        )
    }

    /**
     * We should save our first calculation of the width to prevent shifting when style is changing
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (measuredWidthSaved == null && !isBadgeVisible) {
            measuredWidthSaved = measuredWidth + PADDING_TO_FIT.dpToPx(context)
        }

        if (measuredWidthSavedWithBadge == null && isBadgeVisible) {
            measuredWidthSavedWithBadge = measuredWidth + PADDING_TO_FIT.dpToPx(context)
        }

        if (isBadgeVisible) {
            setMeasuredDimension(measuredWidthSavedWithBadge ?: measuredWidth, measuredHeight)
        } else {
            setMeasuredDimension(measuredWidthSaved ?: measuredWidth, measuredHeight)
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
        invalidateColors()
        invalidateTextColors()
    }

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        invalidateColors()
    }

    private fun parseStrokeColors(a: TypedArray) {
        strokeColorState = ColorState(
            checkedEnabled = a.getColorOrNull(R.styleable.OutlineSliderTab_admiralStrokeColorCheckedEnabled),
            checkedDisabled = a.getColorOrNull(R.styleable.OutlineSliderTab_admiralStrokeColorCheckedDisabled)
        )
    }

    private fun parseTextColors(typedArray: TypedArray) {
        textColorState = ColorState(
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.OutlineSliderTab_admiralTextColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.OutlineSliderTab_admiralTextColorCheckedDisabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.OutlineSliderTab_admiralTextColorNormalEnabled
            ),
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.OutlineSliderTab_admiralTextColorNormalDisabled
            )
        )
    }

    private fun parseText(typedArray: TypedArray) {
        text = typedArray.getString(R.styleable.OutlineSliderTab_android_text) ?: ""
    }

    private fun invalidateBadge() {
        badge.isVisible = isBadgeVisible
        if (isBadgeVisible) {
            textView.setMargins(0, MARGIN_WITH_BADGE, 0, MARGIN_WITH_BADGE)
        } else {
            textView.setMargins(0, 0, 0, 0)
            textView.layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    gravity = CENTER
                }
        }
    }

    private fun invalidateColors() {
        val stateList = colorStateListUnion(
            normalEnabled = strokeColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.elementAdditional,
            normalDisabled = strokeColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.elementAdditional,
            pressed = strokeColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.elementAdditional,
            checkedEnabled = strokeColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.elementAccent,
            checkedDisabled = strokeColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
        )

        val color = strokeColorState?.checkedEnabled ?: ThemeManager.theme.palette.elementAccent
        val content = if (isChecked) {
            context.coloredDrawable(R.drawable.admiral_bg_btn_stroked_2dp, stateList)
        } else {
            context.coloredDrawable(R.drawable.admiral_bg_btn_stroked_1dp, stateList)
        }
        val mask = context.drawable(R.drawable.admiral_bg_btn_mask)

        background = ripple(color.withAlpha(RIPPLE_ALPHA), content, mask)
    }

    private fun invalidateTextColors() {
        textView.setTextColor(
            colorStateListForChecked(
                checkedEnabled = textColorState?.checkedEnabled
                    ?: ThemeManager.theme.palette.textPrimary,
                checkedDisabled = textColorState?.checkedDisabled
                    ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
                normalEnabled = textColorState?.normalEnabled
                    ?: ThemeManager.theme.palette.textPrimary,
                normalDisabled = textColorState?.normalDisabled
                    ?: ThemeManager.theme.palette.textPrimary.withAlpha()
            )
        )
    }

    private companion object {
        private const val MARGIN_WITH_BADGE = 16
        private const val RIPPLE_ALPHA = 0.2f
        private const val PADDING_TO_FIT = 2
    }
}
