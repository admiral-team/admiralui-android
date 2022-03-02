package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.children
import androidx.core.view.isGone
import com.admiral.themes.ColorPaletteEnum
import com.admiral.uikit.R
import com.admiral.uikit.common.components.cell.base.CellUnit
import com.admiral.uikit.common.components.cell.base.CellUnitType
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.badge.Badge
import com.admiral.uikit.components.imageview.ImageView
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.getIntOrNull
import com.admiral.uikit.ext.parseAttrs

class IconCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), CellUnit {

    override lateinit var unitType: CellUnitType

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled, pressed.
     */
    var iconTintColors: ColorState? = null
        set(value) {
            field = value
            imageView.imageTintColorState = value
        }

    /**
     * In case Drawable is null, the icon will be hidden.
     */
    var icon: Drawable? = null
        set(value) {
            field = value
            imageView.setImageDrawable(icon)
            isGone = value == null
        }

    val badge: Badge by lazy { findViewById(R.id.admiralIconCellBadge) }

    private val imageView: ImageView by lazy { findViewById(R.id.admiralIconCellImage) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_cell_unit_icon, this)

        parseAttrs(attrs, R.styleable.IconCellUnit).use {
            parseIconColors(it)
            parseIcon(it)

            unitType = CellUnitType.from(it.getInt(R.styleable.IconCellUnit_admiralCellUnitType, 0))

            imageView.imageColorNormalEnabledPalette =
                ColorPaletteEnum.from(it.getIntOrNull(R.styleable.IconCellUnit_admiralIconColorNormalEnabledPalette))

            if (imageView.imageColorNormalEnabledPalette == null) {
                imageView.imageColorNormalEnabledPalette = ColorPaletteEnum.ELEMENT_PRIMARY
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        children.forEach {
            it.isEnabled = enabled
        }
    }

    private fun parseIcon(a: TypedArray) {
        icon = a.getDrawable(R.styleable.IconCellUnit_admiralIcon)
    }

    private fun parseIconColors(a: TypedArray) {
        imageView.imageTintColorState = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.IconCellUnit_admiralIconTintColorNormalEnabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.IconCellUnit_admiralIconTintColorPressed
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.IconCellUnit_admiralIconTintColorNormalDisabled
            ),
            focused = a.getColorOrNull(
                R.styleable.IconCellUnit_admiralIconTintColorPressed
            )
        )
    }
}