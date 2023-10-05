package com.admiral.uikit.components.tabs

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity.CENTER
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.badge.Badge
import com.admiral.uikit.databinding.AdmiralViewTabUnderlineBinding
import com.admiral.uikit.ext.colorStateListForChecked
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.ext.ripple
import com.admiral.uikit.ext.setMargins
import com.admiral.uikit.view.checkable.CheckableLinearLayout
import com.admiral.uikit.view.checkable.CheckableView
import com.admiral.resources.R as res

class UnderlineSliderTab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CheckableLinearLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Colors for underline in checked state.
     * States: checkedEnabled, checkedDisabled.
     * In case state is null, the selected color theme will be used.
     */
    var underlineColorState: ColorState? = null
        set(value) {
            field = value
            invalidateStrokeColors()
        }

    /**
     * Colors text.
     * States: checkedEnabled, checkedDisabled, normalEnabled, normalDisabled.
     * In case state is null, the selected color theme will be used.
     */
    var textColorState: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    /**
     * Text shown above divider.
     */
    var text: CharSequence = ""
        set(value) {
            field = value
            binding.admiralUnderlineSliderTabText.text = value
        }
        get() {
            return binding.admiralUnderlineSliderTabText.text
        }

    /**
     * Distance between text and underline in dp.
     */
    var underlinePadding: Int = DEFAULT_PADDING_LINE
        set(value) {
            field = value
            binding.admiralUnderline.setMargins(
                top = value
            )
        }

    /**
     * Handle visibility of the [badge], placed at the top-right corner of the tab.
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
            binding.admiralUnderlineSliderTabBadge.isEnabled = value
        }

    private val binding = AdmiralViewTabUnderlineBinding
        .inflate(LayoutInflater.from(context), this)

    /**
     * [Badge] that shown at the right-top corner of the tab.
     */
    var badge: Badge = binding.admiralUnderlineSliderTabBadge

    private var measuredWidthSaved: Int? = null
    private var measuredWidthSavedWithBadge: Int? = null

    init {
        parseAttrs(attrs, R.styleable.UnderlineSliderTab).use {
            text = it.getString(R.styleable.UnderlineSliderTab_admiralText) ?: ""
            underlinePadding =
                it.getDimension(R.styleable.UnderlineSliderTab_admiralUnderlinePadding, 0f).toInt()
            isEnabled = it.getBoolean(R.styleable.UnderlineSliderTab_android_enabled, true)

            parseStrokeColors(it)
            parseTextColors(it)

            isBadgeVisible =
                it.getBoolean(R.styleable.UnderlineSliderTab_admiralIsBadgeVisible, false)
            isBadgeEnabled =
                it.getBoolean(R.styleable.UnderlineSliderTab_admiralIsBadgeEnabled, true)
        }

        binding.admiralUnderlineSliderTabText.textStyle = ThemeManager.theme.typography.body2
        addOnCheckChangeListener(object : CheckableView.OnCheckedChangeListener {
            override fun onCheckedChanged(view: View, isChecked: Boolean) {
                binding.admiralUnderlineSliderTabText.textStyle =
                    (if (isChecked) ThemeManager.theme.typography.body1 else ThemeManager.theme.typography.body2)
                invalidateTextColors()
            }
        })

        gravity = CENTER
        isClickable = true
        isFocusable = true

        orientation = VERTICAL

        binding.admiralUnderline.isVisible = false
        binding.admiralUnderline.setImageDrawable(drawable(R.drawable.admiral_bg_rectangle_clickable))

        updatePadding(
            left = context.pixels(res.dimen.module_x2),
            right = context.pixels(res.dimen.module_x2),
            top = context.pixels(res.dimen.module_x2),
            bottom = context.pixels(res.dimen.module_x2)
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // NB: We should save our first calculation of the width to prevent
        // shifting when the style is changing but we do it only if the weight is 0
        // otherwise, we need to stretch the view

        if ((layoutParams as LayoutParams).weight == 0f) {
            if (measuredWidthSaved == null && !isBadgeVisible) {
                measuredWidthSaved = measuredWidth
            }

            if (measuredWidthSavedWithBadge == null && isBadgeVisible) {
                measuredWidthSavedWithBadge = measuredWidth
            }

            if (isBadgeVisible) {
                setMeasuredDimension(measuredWidthSavedWithBadge ?: measuredWidth, measuredHeight)
            } else {
                setMeasuredDimension(measuredWidthSaved ?: measuredWidth, measuredHeight)
            }
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
        invalidateStrokeColors()
        invalidateTextColors()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        binding.admiralUnderlineSliderTabText.isEnabled = enabled
        binding.admiralUnderline.isEnabled = enabled
        binding.admiralUnderlineSliderTabBadge.isEnabled = enabled
    }

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)

        binding.admiralUnderline.isVisible = checked
    }

    private fun parseTextColors(typedArray: TypedArray) {
        textColorState = ColorState(
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.UnderlineSliderTab_admiralTextColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.UnderlineSliderTab_admiralTextColorCheckedDisabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.UnderlineSliderTab_admiralTextColorNormalEnabled
            ),
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.UnderlineSliderTab_admiralTextColorNormalDisabled
            )
        )
    }

    private fun parseStrokeColors(a: TypedArray) {
        underlineColorState = ColorState(
            checkedEnabled = a.getColorOrNull(
                R.styleable.UnderlineSliderTab_admiralUnderlineColorCheckedEnabled
            ),
            checkedDisabled = a.getColorOrNull(
                R.styleable.UnderlineSliderTab_admiralUnderlineColorCheckedDisabled
            )
        )
    }

    private fun invalidateBadge() {
        binding.admiralUnderlineSliderTabBadge.isVisible = isBadgeVisible
        if (isBadgeVisible) {
            binding.admiralUnderlineSliderTabText.setMargins(
                0,
                MARGIN_WITH_BADGE,
                0,
                MARGIN_WITH_BADGE
            )
        } else {
            binding.admiralUnderlineSliderTabText.setMargins(0, 0, 0, 0)
            binding.admiralUnderlineSliderTabText.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = CENTER
            }
        }
    }

    private fun invalidateStrokeColors() {
        binding.admiralUnderline.imageTintList = colorStateListForChecked(
            checkedEnabled = textColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.elementAccent,
            checkedDisabled = textColorState?.checkedDisabled
                ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
            normalEnabled = textColorState?.normalEnabled
                ?: ThemeManager.theme.palette.elementAccent,
            normalDisabled = textColorState?.normalDisabled
                ?: ThemeManager.theme.palette.elementAccent.withAlpha()
        )

        val color = textColorState?.checkedEnabled ?: ThemeManager.theme.palette.elementAccent
        val mask = context.drawable(R.drawable.admiral_bg_btn_mask)

        background = ripple(color.withAlpha(RIPPLE_ALPHA), null, mask)
    }

    private fun invalidateTextColors() {
        binding.admiralUnderlineSliderTabText.textColor = ColorState(
            checkedEnabled = textColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.textPrimary,
            checkedDisabled = textColorState?.checkedDisabled
                ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            normalEnabled = textColorState?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = textColorState?.normalDisabled
                ?: ThemeManager.theme.palette.textPrimary.withAlpha()
        )
    }

    private companion object {
        private const val MARGIN_WITH_BADGE = 16
        private const val DEFAULT_PADDING_LINE = 6
        private const val RIPPLE_ALPHA = 0.2f
    }
}
