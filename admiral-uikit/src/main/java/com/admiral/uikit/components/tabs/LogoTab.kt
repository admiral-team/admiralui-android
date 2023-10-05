package com.admiral.uikit.components.tabs

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.use
import androidx.core.view.updatePadding
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.ext.colorStateListUnion
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.coloredDrawable
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.ext.ripple
import com.admiral.uikit.view.checkable.CheckableImageView
import com.admiral.uikit.view.checkable.CheckableLinearLayout
import com.admiral.resources.R as res

class LogoTab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CheckableLinearLayout(context, attrs, defStyleAttr), ThemeObserver {

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

    var icon: Drawable? = null
        set(value) {
            field = value
            invalidateIcon()
        }

    internal var isRightDividerVisible: Boolean = false
        set(value) {
            field = value
            invalidateDivider()
        }

    private val imageView: CheckableImageView = CheckableImageView(context).apply {
        val layoutParams =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layoutParams.weight = 1f
        setLayoutParams(layoutParams)
    }

    private val divider = CheckableImageView(context).apply {
        isClickable = false
        val layoutParams = LayoutParams(4.dpToPx(context), ViewGroup.LayoutParams.MATCH_PARENT)
        layoutParams.weight = 0f
        setLayoutParams(layoutParams)
        setImageDrawable(
            drawable(R.drawable.admiral_bg_tabs_logo_divider)
                ?.colored(ThemeManager.theme.palette.elementAdditional)
        )
    }

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        parseAttrs(attrs, R.styleable.LogoTab).use {
            parseStrokeColors(it)
            icon = it.getDrawable(R.styleable.LogoTab_srcCompat)
            isEnabled = it.getBoolean(R.styleable.LogoTab_android_enabled, true)
        }

        isClickable = true
        isFocusable = true

        updatePadding(
            top = context.pixels(res.dimen.module_x4),
            bottom = context.pixels(res.dimen.module_x4)
        )
        addView(imageView)
        addView(divider)
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
        invalidateDivider()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        imageView.imageAlpha = if (enabled) {
            ALPHA_ENABLED
        } else {
            ALPHA_DISABLED
        }
    }

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        invalidateColors()
    }

    private fun invalidateDivider() {
        divider.setImageDrawable(
            drawable(R.drawable.admiral_bg_tabs_logo_divider)
                ?.colored(ThemeManager.theme.palette.elementAdditional)
        )

        if (isRightDividerVisible) {
            divider.visibility = View.VISIBLE
        } else {
            divider.visibility = View.INVISIBLE
        }
    }

    private fun invalidateIcon() {
        imageView.setImageDrawable(icon)
    }

    private fun parseStrokeColors(a: TypedArray) {
        strokeColorState = ColorState(
            checkedEnabled = a.getColorOrNull(
                R.styleable.LogoTab_admiralStrokeColorCheckedEnabled
            ),
            checkedDisabled = a.getColorOrNull(
                R.styleable.LogoTab_admiralStrokeColorCheckedDisabled
            )
        )
    }

    private fun invalidateColors() {
        val stateList = colorStateListUnion(
            normalEnabled = Color.TRANSPARENT,
            normalDisabled = Color.TRANSPARENT,
            pressed = strokeColorState?.checkedEnabled ?: ThemeManager.theme.palette.elementAccent,
            checkedEnabled = strokeColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.elementAccent,
            checkedDisabled = strokeColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
        )

        val color = strokeColorState?.checkedEnabled ?: ThemeManager.theme.palette.elementAccent
        val content = context.coloredDrawable(R.drawable.admiral_bg_btn_stroked_2dp, stateList)
        val mask = context.drawable(R.drawable.admiral_bg_btn_mask)

        background = ripple(color.withAlpha(RIPPLE_ALPHA), if (isChecked) content else null, mask)
    }

    private companion object {
        const val ALPHA_ENABLED = 255
        const val ALPHA_DISABLED = 153
        private const val RIPPLE_ALPHA = 0.2f
    }
}
