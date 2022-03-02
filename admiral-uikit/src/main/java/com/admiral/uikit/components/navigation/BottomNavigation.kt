package com.admiral.uikit.components.navigation

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.core.content.res.use
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.uikit.R
import com.admiral.uikit.components.badge.Badge
import com.admiral.uikit.ext.colorStateListForSelected
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState

class BottomNavigation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Color state for background.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var backgroundColor: ColorState? = null
        set(value) {
            field = value
            invalidateBackgroundColor()
        }

    /**
     * Color state for text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var textColor: ColorState? = null
        set(value) {
            field = value
            invalidateTextColor()
        }

    /**
     * Color state for icon.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var iconColor: ColorState? = null
        set(value) {
            field = value
            invalidateIconColor()
        }

    init {
        itemTextAppearanceInactive = Typography.getStyle(ThemeManager.theme.typography.caption2)
        itemTextAppearanceActive = Typography.getStyle(ThemeManager.theme.typography.caption2)
        parseAttrs(attrs, R.styleable.BottomNavigation).use {
            parseBackgroundColors(it)
            parseTextColors(it)
            parseIconColors(it)
        }

        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
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
        invalidateBackgroundColor()
        invalidateIconColor()
        invalidateTextColor()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            VIEW_HEIGHT,
            resources.displayMetrics
        ).toInt()
        super.onMeasure(widthMeasureSpec, desiredHeight)
        setMeasuredDimension(measuredWidth, desiredHeight)
    }

    /**
     * Add badge to the menu item.
     *
     * @param position defines items' position.
     * @param badge is a [Badge] to be added.
     */
    fun setBadge(position: Int, badge: Badge) {
        val bottomNavigationMenuView = getChildAt(0) as BottomNavigationMenuView
        val itemView: BottomNavigationItemView =
            bottomNavigationMenuView.getChildAt(position) as BottomNavigationItemView

        itemView.addView(badge.apply {
            layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
                setMargins(BADGE_MARGIN_START.dpToPx(context), 0, 0, 0)
            }
        })
    }

    /**
     * Removes badge from the menu item.
     *
     * @param position defines items' position.
     */
    override fun removeBadge(position: Int) {
        val bottomNavigationMenuView = getChildAt(0) as BottomNavigationMenuView
        val itemView = bottomNavigationMenuView.getChildAt(position) as BottomNavigationItemView
        itemView.removeViewAt(itemView.childCount - 1)
    }

    private fun parseBackgroundColors(typedArray: TypedArray) {
        backgroundColor = ColorState(
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.BottomNavigation_android_backgroundTint
            )
        )
    }

    private fun parseTextColors(typedArray: TypedArray) {
        textColor = ColorState(
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.BottomNavigation_admiralTextColorNormalEnabled
            ),
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.BottomNavigation_admiralTextColorNormalDisabled
            ),
            selectedEnabled = typedArray.getColorOrNull(
                R.styleable.BottomNavigation_admiralTextColorSelectedEnabled
            ),
            selectedDisabled = typedArray.getColorOrNull(
                R.styleable.BottomNavigation_admiralTextColorSelectedDisabled
            )
        )
    }

    private fun parseIconColors(typedArray: TypedArray) {
        iconColor = ColorState(
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.BottomNavigation_admiralIconTintColorNormalEnabled
            ),
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.BottomNavigation_admiralIconTintColorNormalDisabled
            ),
            selectedEnabled = typedArray.getColorOrNull(
                R.styleable.BottomNavigation_admiralIconTintColorSelectedEnabled
            ),
            selectedDisabled = typedArray.getColorOrNull(
                R.styleable.BottomNavigation_admiralIconTintColorSelectedDisabled
            )
        )
    }

    private fun invalidateBackgroundColor() {
        setBackgroundColor(backgroundColor?.normalEnabled ?: ThemeManager.theme.palette.backgroundAccentDark)
    }

    private fun invalidateTextColor() {
        itemTextColor = colorStateListForSelected(
            normalEnabled = textColor?.normalEnabled ?: ThemeManager.theme.palette.elementContrast,
            normalDisabled = textColor?.normalDisabled ?: ThemeManager.theme.palette.elementContrast.withAlpha(),
            selectedEnabled = textColor?.selectedEnabled ?: ThemeManager.theme.palette.textAccent,
            selectedDisabled = textColor?.selectedDisabled ?: ThemeManager.theme.palette.textAccent.withAlpha()
        )
    }

    private fun invalidateIconColor() {
        itemIconTintList = colorStateListForSelected(
            normalEnabled = iconColor?.normalEnabled ?: ThemeManager.theme.palette.elementContrast,
            normalDisabled = iconColor?.normalDisabled ?: ThemeManager.theme.palette.elementContrast.withAlpha(),
            selectedEnabled = iconColor?.selectedEnabled ?: ThemeManager.theme.palette.elementAccent,
            selectedDisabled = iconColor?.selectedDisabled ?: ThemeManager.theme.palette.elementAccent.withAlpha()
        )
    }

    private companion object {
        const val VIEW_HEIGHT = 60f
        const val BADGE_MARGIN_START = 10f
    }
}