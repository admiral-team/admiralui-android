package com.admiral.uikit.components.lines

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState

class Line @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Define size of the line.
     * States: thin, thick: 1dp and 2dp.
     * Default size is [LineSize.THIN]
     */
    var lineSize: LineSize = LineSize.THIN
        set(value) {
            field = value
            invalidate()
        }

    /**
     * Color state for the view.
     * States: normal, disabled.
     * In case state is null, the selected color theme will be used.
     */
    var lineColor: ColorState? = null
        set(value) {
            field = value
            invalidateLineColor()
        }

    init {
        setBackgroundColor(ThemeManager.theme.palette.elementAccent)

        parseAttrs(attrs, R.styleable.Line).use {
            parseSize(it)
            parseLineColor(it)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = if (lineSize == LineSize.THIN) {
            HEIGHT_THIN
        } else {
            HEIGHT_THICK
        }

        val desiredHeight = height.dpToPx(context)
        super.onMeasure(widthMeasureSpec, desiredHeight)
        setMeasuredDimension(measuredWidth, desiredHeight)
    }

    private fun parseSize(typedArray: TypedArray) {
        lineSize = when (typedArray.getInt(R.styleable.Line_admiralLineThickness, 1)) {
            0 -> LineSize.THICK
            1 -> LineSize.THIN
            else -> LineSize.THIN
        }
    }

    private fun parseLineColor(typedArray: TypedArray) {
        lineColor = ColorState(
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.Line_admiralLineColorNormalEnabled
            ),
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.Line_admiralLineColorNormalDisabled
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
        invalidateLineColor()
    }

    private fun invalidateLineColor() {
        val colorState = colorStateList(
            enabled = lineColor?.normalEnabled ?: ThemeManager.theme.palette.elementAccent,
            disabled = lineColor?.normalDisabled ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
            pressed = lineColor?.normalEnabled ?: ThemeManager.theme.palette.elementAccentPressed,
        )

        backgroundTintList = colorState
    }

    private companion object {
        const val HEIGHT_THIN = 1F
        const val HEIGHT_THICK = 2F
    }
}