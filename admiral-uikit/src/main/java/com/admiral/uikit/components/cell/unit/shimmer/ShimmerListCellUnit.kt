package com.admiral.uikit.components.cell.unit.shimmer

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.components.cell.base.CellUnit
import com.admiral.uikit.common.components.cell.base.CellUnitType
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.view.shimmer.Shimmer
import com.admiral.uikit.view.shimmer.ShimmerFrameLayout

class ShimmerListCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ShimmerFrameLayout(context, attrs, defStyleAttr), CellUnit, ThemeObserver {

    override var unitType: CellUnitType = CellUnitType.LEADING_TEXT

    /**
     * Color of shimmer
     * In case color is null, the selected color theme will be used.
     */
    @ColorRes
    var shimmerColor: Int? = null
        set(value) {
            field = value
            linearLayout.removeAllViews()
            invalidateShimmer()
            invalidateItems()
        }

    private val linearLayout: LinearLayout = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL

        showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        dividerDrawable = ContextCompat.getDrawable(context, R.drawable.admiral_devider_space_vertical_4dp)
    }

    var itemsCount = 1
        set(value) {
            field = value
            invalidateItems()
        }

    init {
        addView(linearLayout)

        parseAttrs(attrs, R.styleable.ShimmerListCellUnit).use {
            unitType = CellUnitType.from(it.getInt(R.styleable.IconCellUnit_admiralCellUnitType, 0))
            itemsCount = it.getInt(R.styleable.ShimmerListCellUnit_admiralViewsCount, 1)
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
        linearLayout.removeAllViews()
        invalidateShimmer()
        invalidateItems()
    }

    private fun invalidateShimmer() {
        val shimmer = Shimmer.AlphaHighlightBuilder()
        shimmer.setBaseAlpha(BASE_ALPHA)
        shimmer.setDropoff(DROP_OFF)
        shimmer.setHighlightAlpha(HIGHLIGHT_ALPHA)
        shimmer.setTilt(TILT)
        setShimmer(shimmer.build())
    }

    private fun invalidateItems() {
        val prevItemsCount = linearLayout.childCount
        when {
            prevItemsCount < itemsCount -> {
                for (i in prevItemsCount until itemsCount) {
                    linearLayout.addView(View(context).apply {
                        setBackgroundColor(shimmerColor ?: ThemeManager.theme.palette.backgroundAdditionalOne)
                        layoutParams = LayoutParams(VIEW_WIDTH.dpToPx(context), VIEW_HEIGHT.dpToPx(context))
                    })
                }
            }

            prevItemsCount > itemsCount -> {
                for (i in prevItemsCount - 1 downTo itemsCount) {
                    linearLayout.removeViewAt(i)
                }
            }
        }
    }

    private companion object {
        const val BASE_ALPHA = 1F
        const val DROP_OFF = 0.9F
        const val HIGHLIGHT_ALPHA = 0F
        const val TILT = 0F

        const val VIEW_WIDTH = 200
        const val VIEW_HEIGHT = 16
    }
}