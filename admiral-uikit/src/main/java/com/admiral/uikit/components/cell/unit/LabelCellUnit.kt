package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isGone
import com.admiral.uikit.R
import com.admiral.uikit.components.badge.Badge
import com.admiral.uikit.common.components.cell.base.CellUnit
import com.admiral.uikit.common.components.cell.base.CellUnitType
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.view.CircularImageView

class LabelCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), CellUnit {

    override var unitType: CellUnitType = CellUnitType.LEADING

    /**
     * In case Drawable is null, the icon will be hidden.
     */
    var icon: Drawable? = null
        set(value) {
            field = value
            imageView.setImageDrawable(value)
            isGone = value == null
        }

    val badge: Badge by lazy { findViewById(R.id.admiralLabelCellBadge) }

    private val imageView: CircularImageView by lazy { findViewById(R.id.admiralLabelCellImage) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_cell_unit_label, this)

        parseAttrs(attrs, R.styleable.LabelCellUnit).use {
            icon = it.getDrawable(R.styleable.LabelCellUnit_admiralIcon)

            unitType = CellUnitType.from(it.getInt(R.styleable.LabelCellUnit_admiralCellUnitType, 0))
        }

        imageView.circleColor = Color.TRANSPARENT
        imageView.borderWidth = 0.0f
        imageView.shadowRadius = 0.0f
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        alpha = if (enabled) {
            ALPHA_ENABLED
        } else {
            ALPHA_DISABLED
        }
    }

    private companion object {
        const val ALPHA_ENABLED = 1f
        const val ALPHA_DISABLED = 0.6f
    }
}