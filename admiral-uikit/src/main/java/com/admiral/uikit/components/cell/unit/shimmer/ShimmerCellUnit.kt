package com.admiral.uikit.components.cell.unit.shimmer

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.core.components.cell.base.CellUnit
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.view.shimmer.Shimmer
import com.admiral.uikit.view.shimmer.ShimmerFrameLayout

class ShimmerCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ShimmerFrameLayout(context, attrs, defStyleAttr), CellUnit, ThemeObserver {

    override var unitType: CellUnitType = CellUnitType.LEADING

    /**
     * Color of shimmer
     * In case color is null, the selected color theme will be used.
     */
    @ColorRes
    var shimmerColor: Int? = null
        set(value) {
            field = value
            removeAllViews()
            invalidateShimmer()
        }

    init {
        parseAttrs(attrs, R.styleable.ShimmerCellUnit).use {
            unitType = CellUnitType.from(it.getInt(R.styleable.IconCellUnit_admiralCellUnitType, 0))
            shimmerColor = it.getColorOrNull(R.styleable.ShimmerCellUnit_admiralShimmerColor)
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
        removeAllViews()
        invalidateShimmer()
    }

    private fun invalidateShimmer() {
        val shimmer = Shimmer.AlphaHighlightBuilder()
        shimmer.setBaseAlpha(BASE_ALPHA)
        shimmer.setDropoff(DROP_OFF)
        shimmer.setHighlightAlpha(HIGHLIGHT_ALPHA)
        shimmer.setTilt(TILT)
        setShimmer(shimmer.build())
        addView(View(context).apply {
            setBackgroundColor(shimmerColor ?: ThemeManager.theme.palette.backgroundAdditionalOne)
        })
    }

    private companion object {
        const val BASE_ALPHA = 1F
        const val DROP_OFF = 0.9F
        const val HIGHLIGHT_ALPHA = 0F
        const val TILT = 0F
    }
}