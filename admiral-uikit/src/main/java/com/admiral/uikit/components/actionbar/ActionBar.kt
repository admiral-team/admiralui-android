package com.admiral.uikit.components.actionbar

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.children
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.components.imageview.ImageView
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.resources.R as res
import com.admiral.uikit.core.R as core

class ActionBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Colors for the background.
     * States: normalDisabled, normalEnabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var backgroundColors: ColorState? = null
        set(value) {
            field = value
            invalidateBackgroundColor()
        }

    /**
     * Colors for the button background.
     * States: normalDisabled, normalEnabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var buttonBackgroundColors: ColorState? = null
        set(value) {
            field = value
            invalidateButtonBackgroundColor()
        }

    /**
     * Colors for the dots icon tint.
     * States: normalDisabled, normalEnabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var dotsIconTintColors: ColorState? = null
        set(value) {
            field = value
            invalidateDotsTintColor()
        }

    /**
     * Colors for the up icon tint.
     * States: normalDisabled, normalEnabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var upIconTintColors: ColorState? = null
        set(value) {
            field = value
            invalidateUpTintColor()
        }

    /**
     * Colors for the down icon tint.
     * States: normalDisabled, normalEnabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var downIconTintColors: ColorState? = null
        set(value) {
            field = value
            invalidateDownTintColor()
        }

    /**
     * Colors for the edit icon tint.
     * States: normalDisabled, normalEnabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var editIconTintColors: ColorState? = null
        set(value) {
            field = value
            invalidateEditTintColor()
        }

    /**
     * Colors for the delete icon tint.
     * States: normalDisabled, normalEnabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var deleteIconTintColors: ColorState? = null
        set(value) {
            field = value
            invalidateDeleteTintColor()
        }

    val dots = createImageView(res.drawable.admiral_ic_more_outline)
    val down = createImageView(res.drawable.admiral_ic_arrow_down_outline)
    val up = createImageView(res.drawable.admiral_ic_arrow_up_outline)
    val edit = createImageView(res.drawable.admiral_ic_edit_outline)
    val delete = createImageView(res.drawable.admiral_ic_close_outline)

    init {
        parseAttrs(attrs, R.styleable.ActionBar).use {
            parseBackgroundColors(it)
            parseButtonBackgroundColors(it)
            parseDotsIconTintColors(it)
            parseUpIconTintColors(it)
            parseDownIconTintColors(it)
            parseEditIconTintColors(it)
            parseDeleteIconTintColors(it)
        }

        orientation = HORIZONTAL

        showDividers = SHOW_DIVIDER_MIDDLE
        dividerDrawable = ContextCompat.getDrawable(
            context,
            R.drawable.admiral_devider_space_horizontal_8dp
        )
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        children.forEach {
            it.isEnabled = enabled
        }
    }

    fun addItem(@DrawableRes icon: Int, onClickListener: OnClickListener? = null): ImageView {
        val view = ImageView(context).apply {
            background = drawable(R.drawable.admiral_bg_round_clickable)
                ?.colored(ThemeManager.theme.palette.backgroundBasic)

            setImageDrawable(drawable(icon))

            val padding = pixels(core.dimen.module_x1)
            setPadding(padding, padding, padding, padding)

            isClickable = true
            isFocusable = true

            setOnClickListener(onClickListener)
        }

        addView(view)
        return view
    }

    fun initDefaultViews() {
        addView(dots)
        addView(up)
        addView(down)
        addView(edit)
        addView(delete)
    }

    private fun createImageView(@DrawableRes icon: Int) = ImageView(context).apply {
        isColored = true
        background = drawable(R.drawable.admiral_bg_round_clickable)
            ?.colored(ThemeManager.theme.palette.backgroundBasic)

        setImageDrawable(drawable(icon))

        val padding = pixels(core.dimen.module_x1)
        setPadding(padding, padding, padding, padding)

        isClickable = true
        isFocusable = true
    }

    private fun parseBackgroundColors(typedArray: TypedArray) {
        backgroundColors = ColorState(
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralBackgroundColorNormalDisabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralBackgroundColorNormalEnabled
            ),
            pressed = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralBackgroundColorPressed
            )
        )
    }

    private fun parseButtonBackgroundColors(typedArray: TypedArray) {
        buttonBackgroundColors = ColorState(
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralButtonBackgroundColorNormalDisabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralButtonBackgroundColorNormalEnabled
            ),
            pressed = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralButtonBackgroundColorPressed
            )
        )
    }

    private fun parseDotsIconTintColors(typedArray: TypedArray) {
        dotsIconTintColors = ColorState(
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralDotsColorNormalDisabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralDotsColorNormalEnabled
            ),
            pressed = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralDotsColorPressed
            )
        )
    }

    private fun parseUpIconTintColors(typedArray: TypedArray) {
        upIconTintColors = ColorState(
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralUpColorNormalDisabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralUpColorNormalEnabled
            ),
            pressed = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralUpColorPressed
            )
        )
    }

    private fun parseDownIconTintColors(typedArray: TypedArray) {
        downIconTintColors = ColorState(
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralDownColorNormalDisabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralDownColorNormalEnabled
            ),
            pressed = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralDownColorPressed
            )
        )
    }

    private fun parseEditIconTintColors(typedArray: TypedArray) {
        editIconTintColors = ColorState(
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralEditColorNormalDisabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralEditColorNormalEnabled
            ),
            pressed = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralEditColorPressed
            )
        )
    }

    private fun parseDeleteIconTintColors(typedArray: TypedArray) {
        deleteIconTintColors = ColorState(
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralDeleteColorNormalDisabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralDeleteColorNormalEnabled
            ),
            pressed = typedArray.getColorOrNull(
                R.styleable.ActionBar_admiralDeleteColorPressed
            )
        )
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
        invalidateButtonBackgroundColor()
        invalidateBackgroundColor()
        invalidateDotsTintColor()
        invalidateUpTintColor()
        invalidateDownTintColor()
        invalidateEditTintColor()
        invalidateDeleteTintColor()
    }

    private fun invalidateBackgroundColor() {
        setBackgroundColor(
            backgroundColors?.normalEnabled ?: ThemeManager.theme.palette.backgroundAdditionalOne
        )
    }

    private fun invalidateButtonBackgroundColor() {
        val backgroundColors = colorStateList(
            enabled = buttonBackgroundColors?.normalEnabled
                ?: ThemeManager.theme.palette.backgroundBasic,
            disabled = buttonBackgroundColors?.normalDisabled
                ?: ThemeManager.theme.palette.backgroundBasic,
            pressed = buttonBackgroundColors?.pressed
                ?: ThemeManager.theme.palette.backgroundSelected
        )

        dots.backgroundTintList = backgroundColors
        up.backgroundTintList = backgroundColors
        down.backgroundTintList = backgroundColors
        edit.backgroundTintList = backgroundColors
        delete.backgroundTintList = backgroundColors
    }

    private fun invalidateDotsTintColor() {
        dots.imageTintColorState = ColorState(
            normalEnabled = dotsIconTintColors?.normalEnabled
                ?: ThemeManager.theme.palette.elementAccent,
            normalDisabled = dotsIconTintColors?.normalDisabled
                ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
            pressed = dotsIconTintColors?.pressed ?: ThemeManager.theme.palette.elementAccentPressed
        )
    }

    private fun invalidateUpTintColor() {
        up.imageTintColorState = ColorState(
            normalEnabled = upIconTintColors?.normalEnabled
                ?: ThemeManager.theme.palette.elementPrimary,
            normalDisabled = upIconTintColors?.normalDisabled
                ?: ThemeManager.theme.palette.elementPrimary.withAlpha(),
            pressed = upIconTintColors?.pressed ?: ThemeManager.theme.palette.elementPrimary
        )
    }

    private fun invalidateDownTintColor() {
        down.imageTintColorState =
            ColorState(
                normalEnabled = downIconTintColors?.normalEnabled
                    ?: ThemeManager.theme.palette.elementPrimary,
                normalDisabled = downIconTintColors?.normalDisabled
                    ?: ThemeManager.theme.palette.elementPrimary.withAlpha(),
                pressed = downIconTintColors?.pressed ?: ThemeManager.theme.palette.elementPrimary
            )
    }

    private fun invalidateEditTintColor() {
        edit.imageTintColorState =
            ColorState(
                normalEnabled = editIconTintColors?.normalEnabled
                    ?: ThemeManager.theme.palette.elementAccent,
                normalDisabled = editIconTintColors?.normalDisabled
                    ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                pressed = editIconTintColors?.pressed
                    ?: ThemeManager.theme.palette.elementAccentPressed
            )

    }

    private fun invalidateDeleteTintColor() {
        delete.imageTintColorState =
            ColorState(
                normalEnabled = deleteIconTintColors?.normalEnabled
                    ?: ThemeManager.theme.palette.elementError,
                normalDisabled = deleteIconTintColors?.normalDisabled
                    ?: ThemeManager.theme.palette.elementError.withAlpha(),
                pressed = deleteIconTintColors?.pressed
                    ?: ThemeManager.theme.palette.elementErrorPressed
            )
    }
}