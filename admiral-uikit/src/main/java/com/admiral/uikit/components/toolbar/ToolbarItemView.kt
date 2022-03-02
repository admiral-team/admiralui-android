package com.admiral.uikit.components.toolbar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity.CENTER
import android.view.LayoutInflater
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.badge.Badge
import com.admiral.uikit.ext.colorStateListForChecked
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.setMargins
import com.admiral.uikit.view.checkable.CheckableImageView
import com.admiral.uikit.view.checkable.CheckableLinearLayout
import com.admiral.uikit.view.checkable.CheckableTextView

class ToolbarItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CheckableLinearLayout(context, attrs, defStyleAttr), ThemeObserver {

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

    var badge: Badge? = null
        set(value) {
            field = value
            invalidateBadge()
        }

    private val iconView: CheckableImageView by lazy { findViewById(R.id.admiralToolbarItemImageView) }
    private val textView: CheckableTextView by lazy { findViewById(R.id.admiralToolbarItemText) }
    private val badgeView: Badge by lazy { findViewById(R.id.admiralToolbarItemBadge) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_toolbar_item, this)

        parseAttrs(attrs, R.styleable.IconTab).use {
            text = it.getString(R.styleable.IconTab_admiralText)
            icon = it.getDrawable(R.styleable.IconTab_admiralIcon)

            parseIconColors(it)
            parseTitleColors(it)
        }

        isClickable = true
        isFocusable = true
        minimumHeight = VERTICAL_WIDTH.dpToPx(context)
        gravity = CENTER
        requestVertical()
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

    override fun onFinishInflate() {
        super.onFinishInflate()
        requestVertical()
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateIconColors()
        invalidateTextColors()
    }

    fun requestVertical() {
        orientation = VERTICAL
        textView.setMargins(top = TEXT_MARGIN, right = 0, bottom = 0, left = 0)
        textView.textStyle = ThemeManager.theme.typography.caption2
        minimumWidth = VERTICAL_WIDTH.dpToPx(context)
    }

    fun requestHorizontal() {
        orientation = HORIZONTAL
        textView.setMargins(top = 0, right = 0, bottom = 0, left = TEXT_MARGIN)
        textView.textStyle = ThemeManager.theme.typography.subhead2
        minimumWidth = HORIZONTAL_WIDTH.dpToPx(context)
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

    private fun invalidateTextColors() {
        textView.textColor = ColorState(
            checkedEnabled = textColorState?.checkedEnabled ?: ThemeManager.theme.palette.textAccent,
            checkedDisabled = textColorState?.checkedDisabled ?: ThemeManager.theme.palette.textAccent.withAlpha(),
            normalEnabled = textColorState?.normalEnabled ?: ThemeManager.theme.palette.textStaticWhite,
            normalDisabled = textColorState?.normalDisabled ?: ThemeManager.theme.palette.textStaticWhite.withAlpha(),
            pressed = textColorState?.pressed ?: ThemeManager.theme.palette.textStaticWhite,
        )
    }

    private fun invalidateIconColors() {
        iconView.imageTintList =
            colorStateListForChecked(
                checkedEnabled = iconColorState?.checkedEnabled ?: ThemeManager.theme.palette.elementAccent,
                checkedDisabled = iconColorState?.checkedDisabled
                    ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                normalEnabled = iconColorState?.normalEnabled ?: ThemeManager.theme.palette.elementStaticWhite,
                normalDisabled = iconColorState?.normalDisabled
                    ?: ThemeManager.theme.palette.elementStaticWhite.withAlpha()
            )
    }

    private fun invalidateBadge() {
        badgeView.isVisible = false
        badge?.let {
            badgeView.isVisible = it.isVisible
            badgeView.text = it.text
            badgeView.badgeColorNormal = it.badgeColorNormal
            badgeView.badgeColorDisabled = it.badgeColorDisabled
            badgeView.borderColor = it.borderColor
            badgeView.badgeType = it.badgeType
        }
    }

    private companion object {
        private const val TEXT_MARGIN = 4
        private const val VERTICAL_WIDTH = 80
        private const val HORIZONTAL_WIDTH = 160
    }
}