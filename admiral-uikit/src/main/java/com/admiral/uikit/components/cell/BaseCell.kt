package com.admiral.uikit.components.cell

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.constraintlayout.widget.Barrier
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.use
import androidx.core.view.children
import androidx.core.view.marginStart
import com.admiral.themes.ThemeManager.theme
import com.admiral.uikit.R
import com.admiral.uikit.common.components.cell.base.CellUnit
import com.admiral.uikit.common.components.cell.base.CellUnitType
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.common.util.ComponentsRadius
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.ext.ripple
import com.admiral.uikit.ext.createRoundedColoredRectangleDrawable
import com.admiral.uikit.ext.createRoundedRectangleDrawable
import com.admiral.uikit.layout.ConstraintLayout

class BaseCell @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val baseCellElements = HashMap<CellUnitType, CellUnit>()
    private var leadingGravity = Gravity.CENTER_HORIZONTAL
    private var trailingGravity = Gravity.CENTER_HORIZONTAL

    /**
     * Sets the radius to the view. By default the radius is 0. You can use admiralRadius attribute from xml.
     */
    var radius: ComponentsRadius = ComponentsRadius.NONE
        set(value) {
            field = value
            invalidateBackgroundColor()
        }

    init {
        parseAttrs(attrs, R.styleable.BaseCell).use {
            radius = ComponentsRadius.from(it.getInt(R.styleable.BaseCell_admiralRadius, 0))
            leadingGravity = it.getInt(R.styleable.BaseCell_admiralLeadingElementGravity, Gravity.CENTER_HORIZONTAL)
            trailingGravity = it.getInt(R.styleable.BaseCell_admiralTrailingElementGravity, Gravity.CENTER_HORIZONTAL)
        }

        this.setPadding(
            paddingLeft,
            paddingTop + pixels(R.dimen.module_x3),
            paddingRight,
            paddingBottom + pixels(R.dimen.module_x3)
        )

        minHeight = MIN_HEIGHT.dpToPx(context)
    }

    // TODO add lint rule that user must use addViews instead of addView
    /**
     * Method for adding children views. Do not use [addView]! It's necessary for calculating
     */
    fun addViews(children: List<View>) {
        children.forEach { super.addView(it) }
        initViews()
    }

    override fun onFinishInflate() {
        initViews()
        super.onFinishInflate()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        children.forEach {
            it.isEnabled = enabled
        }
    }

    private fun invalidateBackgroundColor() {
        val rippleColor =
            backgroundColors?.pressed ?: theme.palette.textPrimary.withAlpha(RIPPLE_ALPHA)
        val colorState = ColorState(
            normalEnabled = backgroundColors?.normalEnabled ?: theme.palette.backgroundBasic,
            normalDisabled = backgroundColors?.normalDisabled ?: theme.palette.backgroundBasic.withAlpha(),
            pressed = backgroundColors?.pressed ?: theme.palette.backgroundBasic
        )

        val content = createRoundedColoredRectangleDrawable(radius, colorState)
        val mask = createRoundedRectangleDrawable(radius)

        background = ripple(rippleColor, content, mask)
    }

    /**
     * Initialize views:
     * There must be at most one element of each type. There can't be elements with a different type.
     */
    private fun initViews() {
        baseCellElements.clear()
        children.forEach { cellUnit ->
            if (cellUnit.id == View.NO_ID) {
                cellUnit.id = View.generateViewId()
            }

            if (cellUnit !is CellUnit) {
                if (cellUnit is Barrier) return
                throw IllegalStateException("Expected view with type of CellUnit but found ${cellUnit.javaClass.name}")
            }

            if (baseCellElements.containsKey(cellUnit.unitType)) {
                throw IllegalStateException("BaseCell can't hold multiple views with ${cellUnit.unitType.name} type")
            }

            baseCellElements[cellUnit.unitType] = cellUnit
        }

        setConstraints()
    }

    /**
     * Initialize proper position for the elements:
     * TRAILING  – connects to the end and to the top of the parent.
     * LEADING – connects to the start and to the top of the parent, and to the start of the TRAILING.
     * LEADING_TEXT – connects to the end of the LEADING and to the start of the TRAILING.
     */
    @Suppress("LongMethod")
    private fun setConstraints() {
        val set = ConstraintSet()
        set.clone(this)

        // Trailing
        val trailingView = (baseCellElements[CellUnitType.TRAILING] as? View)
        trailingView?.apply {
            set.connect(id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)

            if (trailingGravity == Gravity.CENTER_HORIZONTAL) {
                set.connect(id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            }
        }

        // Leading
        val leadingView = (baseCellElements[CellUnitType.LEADING] as? View)
        leadingView?.apply {
            set.connect(id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)

            if (leadingGravity == Gravity.CENTER_HORIZONTAL) {
                set.connect(id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            }
        }

        // LeadingText
        val leadingTextView = (baseCellElements[CellUnitType.LEADING_TEXT] as? View)
        leadingTextView?.apply {
            set.connect(id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            set.connect(id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            set.constrainWidth(id, 0)
        }

        when {
            leadingView != null && trailingView != null && leadingTextView != null -> {
                set.createHorizontalChain(
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.LEFT,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.RIGHT,
                    listOfNotNull(leadingView.id, leadingTextView.id, trailingView.id).toIntArray(),
                    null,
                    ConstraintSet.CHAIN_SPREAD_INSIDE
                )
                set.setMargin(
                    leadingTextView.id,
                    ConstraintSet.START,
                    pixels(R.dimen.module_x4) + this.marginStart
                )
                set.setMargin(
                    leadingTextView.id,
                    ConstraintSet.END,
                    pixels(R.dimen.module_x4)
                )
            }
            leadingView != null && leadingTextView != null -> {
                set.createHorizontalChain(
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.LEFT,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.RIGHT,
                    listOfNotNull(leadingView.id, leadingTextView.id).toIntArray(),
                    null,
                    ConstraintSet.CHAIN_SPREAD_INSIDE
                )
                set.setMargin(
                    leadingTextView.id,
                    ConstraintSet.START,
                    pixels(R.dimen.module_x4) + this.marginStart
                )
            }
            trailingView != null && leadingTextView != null -> {
                set.createHorizontalChain(
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.LEFT,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.RIGHT,
                    listOfNotNull(leadingTextView.id, trailingView.id).toIntArray(),
                    null,
                    ConstraintSet.CHAIN_SPREAD_INSIDE
                )
                set.setMargin(
                    leadingTextView.id,
                    ConstraintSet.END,
                    pixels(R.dimen.module_x4)
                )
            }
            leadingTextView != null -> {
                set.connect(
                    leadingTextView.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START
                )
                set.connect(
                    leadingTextView.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END
                )
            }
        }

        // NB: Unavailable combinations
        // 1) LeadingView + TrailingView
        // 2) Only LeadingView
        // 3) Only TrailingView

        set.applyTo(this)
    }

    companion object {
        const val RIPPLE_ALPHA = 0.1F
        const val MIN_HEIGHT = 72
    }
}