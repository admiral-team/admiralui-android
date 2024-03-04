package com.admiral.uikit.components.tabs

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity.CENTER
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.ext.colorStateListForChecked
import com.admiral.uikit.ext.colorStateListUnion
import com.admiral.uikit.ext.coloredDrawable
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.ext.ripple
import com.admiral.uikit.view.checkable.CheckableImageView
import com.admiral.uikit.view.checkable.CheckableLinearLayout
import com.admiral.uikit.view.checkable.CheckableTextView
import com.admiral.uikit.view.checkable.CheckableView
import com.admiral.uikit.core.R as core

class IconTab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CheckableLinearLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Colors for icon' background.
     * States: checkedEnabled, checkedDisabled, normalEnabled, normalDisabled.
     * In case state is null, the selected color theme will be used.
     */
    var iconBackgroundColorState: ColorState? = null
        set(value) {
            field = value
            invalidateBackgroundColors()
        }

    /**
     * Colors for text.
     * States: checkedEnabled, checkedDisabled, normalEnabled, normalDisabled.
     * In case state is null, the selected color theme will be used.
     */
    var textColorState: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    /**
     * Colors for icon.
     * States: checkedEnabled, checkedDisabled, normalEnabled, normalDisabled.
     * In case state is null, the selected color theme will be used.
     */
    var iconColorState: ColorState? = null
        set(value) {
            field = value
            invalidateIconColors()
        }

    var icon: Drawable? = null
        set(value) {
            iconView.setImageDrawable(value)
            field = value
        }

    var text: String? = null
        set(value) {
            textView.text = value
            field = value
        }

    private val iconView: CheckableImageView by lazy { findViewById<CheckableImageView>(R.id.admiralIcon) }
    private val textView: CheckableTextView by lazy { findViewById<CheckableTextView>(R.id.admiralText) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_tab_icon, this)

        parseAttrs(attrs, R.styleable.IconTab).use {
            text = it.getString(R.styleable.IconTab_admiralText)
            icon = it.getDrawable(R.styleable.IconTab_admiralIcon)

            parseIconColors(it)
            parseTitleColors(it)
            parseIconBackgroundColors(it)
        }

        addOnCheckChangeListener(object : CheckableView.OnCheckedChangeListener {
            override fun onCheckedChanged(view: View, isChecked: Boolean) {
                textView.textStyle =
                    (if (isChecked) ThemeManager.theme.typography.subhead2 else ThemeManager.theme.typography.subhead3)
                invalidateTextColors()
            }
        })

        gravity = CENTER
        isClickable = true
        isFocusable = true
        orientation = VERTICAL

        invalidateIconBackground()
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.makeMeasureSpec(pixels(core.dimen.module_x20), MeasureSpec.EXACTLY)
        super.onMeasure(width, measuredHeight)
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateBackgroundColors()
        invalidateIconColors()
        invalidateTextColors()
    }

    private fun parseTitleColors(typedArray: TypedArray) {
        textColorState = ColorState(
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.IconTab_admiralTextColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.IconTab_admiralTextColorCheckedDisabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.IconTab_admiralTextColorNormalEnabled
            ),
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.IconTab_admiralTextColorNormalDisabled
            )
        )
    }

    private fun parseIconBackgroundColors(typedArray: TypedArray) {
        iconBackgroundColorState = ColorState(
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.IconTab_admiralIconBackgroundColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.IconTab_admiralIconBackgroundColorCheckedDisabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.IconTab_admiralIconBackgroundColorNormalEnabled
            ),
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.IconTab_admiralIconBackgroundColorNormalDisabled
            )
        )
    }

    private fun parseIconColors(a: TypedArray) {
        iconColorState = ColorState(
            checkedEnabled = a.getColorOrNull(
                R.styleable.IconTab_admiralIconTintColorCheckedEnabled
            ),
            checkedDisabled = a.getColorOrNull(
                R.styleable.IconTab_admiralIconTintColorCheckedDisabled
            ),
            normalEnabled = a.getColorOrNull(
                R.styleable.IconTab_admiralIconTintColorNormalEnabled
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.IconTab_admiralIconTintColorNormalDisabled
            )
        )
    }

    private fun invalidateIconBackground() {
//        iconView.background = drawable(R.drawable.admiral_bg_round)
        val padding = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            PADDING,
            resources.displayMetrics
        ).toInt()
        iconView.setPadding(padding, padding, padding, padding)
    }

    private fun invalidateBackgroundColors() {
//        iconView.backgroundTintList = colorStateListForChecked(
//            checkedEnabled = iconBackgroundColorState?.checkedEnabled
//                ?: ThemeManager.theme.palette.backgroundAccent,
//            checkedDisabled = iconBackgroundColorState?.checkedDisabled
//                ?: ThemeManager.theme.palette.backgroundAccent.withAlpha(),
//            normalEnabled = iconBackgroundColorState?.normalEnabled
//                ?: ThemeManager.theme.palette.backgroundAdditionalOne,
//            normalDisabled = iconBackgroundColorState?.normalDisabled
//                ?: ThemeManager.theme.palette.backgroundAdditionalOne.withAlpha()
//        )

        val stateList = colorStateListUnion(
            normalEnabled = iconBackgroundColorState?.normalEnabled
                ?: ThemeManager.theme.palette.backgroundAdditionalOne,
            normalDisabled = iconBackgroundColorState?.normalDisabled
                ?: ThemeManager.theme.palette.backgroundAdditionalOne.withAlpha(),
            pressed = iconBackgroundColorState?.checkedEnabled ?: ThemeManager.theme.palette.backgroundAdditionalOne,
            checkedEnabled = iconBackgroundColorState?.checkedEnabled ?: ThemeManager.theme.palette.backgroundAccent,
            checkedDisabled = iconBackgroundColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.backgroundAccent.withAlpha(),
        )

        val color = iconBackgroundColorState?.checkedEnabled ?: ThemeManager.theme.palette.backgroundAccent
        val content = context.coloredDrawable(R.drawable.admiral_bg_round, stateList)
        val mask = context.drawable(R.drawable.admiral_bg_round)

        iconView.background = ripple(color.withAlpha(RIPPLE_ALPHA), content, mask)
    }

    private fun invalidateTextColors() {
        textView.setTextColor(
            colorStateListForChecked(
                checkedEnabled = textColorState?.checkedEnabled ?: ThemeManager.theme.palette.textAccent,
                checkedDisabled = textColorState?.checkedDisabled ?: ThemeManager.theme.palette.textAccent.withAlpha(),
                normalEnabled = textColorState?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
                normalDisabled = textColorState?.normalDisabled ?: ThemeManager.theme.palette.textPrimary.withAlpha()
            )
        )
    }

    private fun invalidateIconColors() {
        iconView.imageTintList =
            colorStateListForChecked(
                checkedEnabled = iconColorState?.checkedEnabled ?: ThemeManager.theme.palette.elementStaticWhite,
                checkedDisabled = iconColorState?.checkedDisabled
                    ?: ThemeManager.theme.palette.elementStaticWhite.withAlpha(),
                normalEnabled = iconColorState?.normalEnabled ?: ThemeManager.theme.palette.elementAccent,
                normalDisabled = iconColorState?.normalDisabled ?: ThemeManager.theme.palette.elementAccent.withAlpha()
            )
    }

    private companion object {
        private const val PADDING = 10f
        private const val RIPPLE_ALPHA = 0.2f
    }
}