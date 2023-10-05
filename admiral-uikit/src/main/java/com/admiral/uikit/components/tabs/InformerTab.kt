package com.admiral.uikit.components.tabs

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity.CENTER
import android.view.LayoutInflater
import androidx.core.content.res.use
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.ext.colorStateListUnion
import com.admiral.uikit.ext.coloredDrawable
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.ext.ripple
import com.admiral.uikit.view.checkable.CheckableLinearLayout
import com.admiral.uikit.view.checkable.CheckableTextView
import com.admiral.resources.R as res

class InformerTab @JvmOverloads constructor(
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
            invalidateBackground()
        }

    /**
     * Colors for tab stroke in checked state.
     * States: checkedEnabled, checkedDisabled.
     * In case state is null, the selected color theme will be used.
     */
    var titleColorState: ColorState? = null
        set(value) {
            field = value
            invalidateTitleColors()
        }

    /**
     * Colors for tab stroke in checked state.
     * States: checkedEnabled, checkedDisabled.
     * In case state is null, the selected color theme will be used.
     */
    var subtitleColorState: ColorState? = null
        set(value) {
            field = value
            invalidateSubtitleColors()
        }

    /**
     * Top text.
     * Gone if text is null or empty.
     */
    var titleText: String? = null
        set(value) {
            field = value
            title.text = value
            title.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Bottom text.
     * Gone if text is null or empty.
     */
    var subtitleText: String? = null
        set(value) {
            field = value
            subtitle.text = value
            subtitle.isVisible = value?.isNotEmpty() == true
        }

    private val title: CheckableTextView by lazy { findViewById(R.id.admiralTextTitle) }
    private val subtitle: CheckableTextView by lazy { findViewById(R.id.admiralTextSubtitle) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_tab_informer, this)

        parseAttrs(attrs, R.styleable.InformerTab).use {
            titleText = it.getString(R.styleable.InformerTab_admiralTitleText)
            subtitleText = it.getString(R.styleable.InformerTab_admiralSubtitleText)
            isEnabled = it.getBoolean(R.styleable.InformerTab_android_enabled, true)

            parseStrokeColors(it)
            parseTitleColors(it)
            parseSubtitleColors(it)
        }

        gravity = CENTER
        isClickable = true
        isFocusable = true

        updatePadding(
            top = context.pixels(res.dimen.module_x5),
            bottom = context.pixels(res.dimen.module_x4)
        )

        orientation = VERTICAL
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
        invalidateBackground()
        invalidateSubtitleColors()
        invalidateTitleColors()
    }

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        invalidateBackground()
    }

    private fun parseTitleColors(typedArray: TypedArray) {
        titleColorState = ColorState(
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.InformerTab_admiralTitleColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.InformerTab_admiralTitleColorCheckedDisabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.InformerTab_admiralTitleColorNormalEnabled
            ),
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.InformerTab_admiralTitleColorNormalDisabled
            )
        )
    }

    private fun parseSubtitleColors(typedArray: TypedArray) {
        subtitleColorState = ColorState(
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.InformerTab_admiralSubtitleColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.InformerTab_admiralSubtitleColorCheckedDisabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.InformerTab_admiralSubtitleColorNormalEnabled
            ),
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.InformerTab_admiralSubtitleColorNormalDisabled
            )
        )
    }

    private fun parseStrokeColors(a: TypedArray) {
        strokeColorState = ColorState(
            checkedEnabled = a.getColorOrNull(
                R.styleable.InformerTab_admiralStrokeColorCheckedEnabled
            ),
            checkedDisabled = a.getColorOrNull(
                R.styleable.InformerTab_admiralStrokeColorCheckedDisabled
            ),
            normalEnabled = a.getColorOrNull(
                R.styleable.InformerTab_admiralStrokeColorNormalEnabled
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.InformerTab_admiralStrokeColorNormalDisabled
            )
        )
    }

    private fun invalidateBackground() {
        val stateList = colorStateListUnion(
            normalEnabled = strokeColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.elementAdditional,
            normalDisabled = strokeColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.elementAdditional.withAlpha(),
            pressed = strokeColorState?.checkedEnabled ?: ThemeManager.theme.palette.elementAccent,
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

    private fun invalidateTitleColors() {
        title.textColor = ColorState(
            checkedEnabled = titleColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.textPrimary,
            checkedDisabled = titleColorState?.checkedDisabled
                ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            normalEnabled = titleColorState?.normalEnabled
                ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = titleColorState?.normalDisabled
                ?: ThemeManager.theme.palette.textPrimary.withAlpha()
        )
    }

    private fun invalidateSubtitleColors() {
        subtitle.textColor = ColorState(
            checkedEnabled = subtitleColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.textSecondary,
            checkedDisabled = subtitleColorState?.checkedDisabled
                ?: ThemeManager.theme.palette.textSecondary.withAlpha(),
            normalEnabled = subtitleColorState?.normalEnabled
                ?: ThemeManager.theme.palette.textSecondary,
            normalDisabled = subtitleColorState?.normalDisabled
                ?: ThemeManager.theme.palette.textSecondary.withAlpha()
        )
    }

    private companion object {
        private const val RIPPLE_ALPHA = 0.2f
    }
}
