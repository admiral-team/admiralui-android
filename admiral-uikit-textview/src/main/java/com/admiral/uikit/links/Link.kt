package com.admiral.uikit.links

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.content.res.use
import com.admiral.uikit.textview.R
import com.admiral.uikit.textview.TextView
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.core.components.link.LinkSize
import com.admiral.uikit.core.ext.colorStateList
import com.admiral.uikit.core.ext.colored
import com.admiral.uikit.core.ext.getColorOrNull
import com.admiral.uikit.core.ext.parseAttrs
import com.admiral.uikit.core.ext.pixels
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState

class Link @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr), ThemeObserver {

    var drawableStart: Drawable? = null
    var drawableEnd: Drawable? = null

    /**
     * Color state for the drawable icon.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var drawableColorTint: ColorState? = null
        set(value) {
            field = value
            invalidateIconColors()
        }

    /**
     * Color state for text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var textColorState: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    /**
     * Define size of the link.
     * States: BIG, SMALL
     * Default size is [LinkSize.SMALL]
     */
    var linkSize: LinkSize = LinkSize.SMALL
        set(value) {
            field = value
            invalidateLinkSize()
        }

    init {
        textStyle = ThemeManager.theme.typography.body2
        gravity = Gravity.START

        parseAttrs(attrs, R.styleable.Link).use {
            text = it.getText(R.styleable.Link_admiralText)

            parseColors(it)
            parseSize(it)
            parseDrawableStart(it)
            parseDrawableEnd(it)
        }

        isClickable = true
        isFocusable = true
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
        super.onThemeChanged(theme)
        invalidateTextColors()
        invalidateIconColors()
    }

    private fun parseSize(typedArray: TypedArray) {
        when (typedArray.getInt(R.styleable.Link_admiralLinkSize, 1)) {
            0 -> linkSize = LinkSize.BIG
            1 -> linkSize = LinkSize.SMALL
        }
    }

    private fun parseDrawableEnd(typedArray: TypedArray) {
        drawableEnd = typedArray.getDrawable(R.styleable.Link_drawableEndCompat)
        compoundDrawablePadding = pixels(R.dimen.module_x2)
        drawableEnd?.colored(
            colorStateList(
                enabled = drawableColorTint?.normalEnabled ?: ThemeManager.theme.palette.elementAccent,
                disabled = drawableColorTint?.normalDisabled ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                pressed = drawableColorTint?.pressed ?: ThemeManager.theme.palette.elementAccentPressed
            )
        )
        setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, drawableEnd, null)
    }

    private fun parseDrawableStart(typedArray: TypedArray) {
        drawableStart = typedArray.getDrawable(R.styleable.Link_drawableStartCompat)
        compoundDrawablePadding = pixels(R.dimen.module_x2)
        drawableStart?.colored(
            colorStateList(
                enabled = drawableColorTint?.normalEnabled ?: ThemeManager.theme.palette.elementAccent,
                disabled = drawableColorTint?.normalDisabled ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                pressed = drawableColorTint?.pressed ?: ThemeManager.theme.palette.elementAccentPressed
            )
        )
        setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, drawableEnd, null)
    }

    private fun parseColors(a: TypedArray) {
        drawableColorTint = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.Link_admiralIconTintColorNormalEnabled),
            normalDisabled = a.getColorOrNull(R.styleable.Link_admiralIconTintColorNormalDisabled),
            pressed = a.getColorOrNull(R.styleable.Link_admiralIconTintColorPressed)
        )

        textColorState = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.Link_admiralTextColorNormalEnabled),
            normalDisabled = a.getColorOrNull(R.styleable.Link_admiralTextColorNormalDisabled),
            pressed = a.getColorOrNull(R.styleable.Link_admiralTextColorPressed)
        )
    }

    private fun invalidateTextColors() {
        val colorState = ColorState(
            normalEnabled = textColorState?.normalEnabled ?: ThemeManager.theme.palette.textAccent,
            normalDisabled = textColorState?.normalDisabled ?: ThemeManager.theme.palette.textAccent.withAlpha(),
            pressed = textColorState?.pressed ?: ThemeManager.theme.palette.textAccentPressed
        )

        textColor = colorState
    }

    private fun invalidateIconColors() {
        drawableEnd?.setTintList(
            colorStateList(
                enabled = drawableColorTint?.normalEnabled ?: ThemeManager.theme.palette.elementAccent,
                disabled = drawableColorTint?.normalDisabled ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                pressed = drawableColorTint?.pressed ?: ThemeManager.theme.palette.elementAccentPressed
            )
        )
        drawableStart?.setTintList(
            colorStateList(
                enabled = drawableColorTint?.normalEnabled ?: ThemeManager.theme.palette.elementAccent,
                disabled = drawableColorTint?.normalDisabled ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                pressed = drawableColorTint?.pressed ?: ThemeManager.theme.palette.elementAccentPressed
            )
        )
        setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, drawableEnd, null)
    }

    private fun invalidateLinkSize() {
        textStyle = when (linkSize) {
            LinkSize.BIG -> ThemeManager.theme.typography.body2
            LinkSize.SMALL -> ThemeManager.theme.typography.subhead3
        }
    }
}