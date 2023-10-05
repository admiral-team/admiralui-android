package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.use
import androidx.core.view.isGone
import com.admiral.uikit.R
import com.admiral.uikit.common.components.cell.base.CellUnit
import com.admiral.uikit.common.components.cell.base.CellUnitType
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.resources.R as res

class CardCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), CellUnit {

    override var unitType: CellUnitType = CellUnitType.LEADING

    /**
     * In case Drawable is null, the icon will be hidden.
     */
    var icon: Drawable? = null
        set(value) {
            field = value
            setImageDrawable(icon)
            isGone = value == null
        }

    init {
        parseAttrs(attrs, R.styleable.CellUnit).use {
            unitType = CellUnitType.from(it.getInt(R.styleable.CellUnit_admiralCellUnitType, 0))

            icon = it.getDrawable(R.styleable.CellUnit_admiralIcon)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val spec = MeasureSpec.makeMeasureSpec(pixels(res.dimen.module_x11), MeasureSpec.EXACTLY)
        super.onMeasure(spec, spec)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        imageAlpha = if (enabled) {
            ALPHA_ENABLED
        } else {
            ALPHA_DISABLED
        }
    }

    private companion object {
        const val ALPHA_ENABLED = 255
        const val ALPHA_DISABLED = 130
    }
}