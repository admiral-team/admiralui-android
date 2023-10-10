package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.res.use
import com.admiral.uikit.R
import com.admiral.uikit.core.components.cell.base.CellUnit
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.uikit.ext.parseAttrs

class CellUnitContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), CellUnit {

    override var unitType: CellUnitType = CellUnitType.LEADING

    init {
        parseAttrs(attrs, R.styleable.CellUnitContainer).use {
            unitType = CellUnitType.from(it.getInt(R.styleable.CellUnitContainer_admiralCellUnitType, 0))
        }
    }
}