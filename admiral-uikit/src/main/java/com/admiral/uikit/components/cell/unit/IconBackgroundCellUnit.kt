package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.admiral.themes.ColorPaletteEnum
import com.admiral.themes.ColorPaletteEnum.Companion.colorResToToken
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.core.components.cell.base.CellUnit
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.components.badge.Badge
import com.admiral.uikit.components.imageview.ImageView
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.getIntOrNull
import com.admiral.uikit.ext.parseAttrs

class IconBackgroundCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver, CellUnit {

    override lateinit var unitType: CellUnitType

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled, pressed.
     */
    var iconBackgroundColors: ColorState? = null
        set(value) {
            field = value
            invalidateIconBackgroundColors()
        }

    var iconBackgroundColorNormalEnabledPalette: ColorPaletteEnum? = null
        set(value) {
            field = value
            invalidateIconBackgroundColors()
        }

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled, pressed.
     */
    var iconTintColors: ColorState? = null
        set(value) {
            field = value
            invalidateIconTintColors()
        }

    var iconTintColorNormalEnabledPalette: ColorPaletteEnum? = null
        set(value) {
            field = value
            invalidateIconTintColors()
        }

    var icon: Drawable? = null
        set(value) {
            field = value
            imageView.setImageDrawable(icon)
            if (icon == null) {
                imageView.layoutParams = LayoutParams(
                    DEFAULT_SIZE_IMAGE.dpToPx(context),
                    DEFAULT_SIZE_IMAGE.dpToPx(context)
                )
            } else {
                imageView.layoutParams = LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
                )
            }
        }

    val badge: Badge by lazy { findViewById(R.id.admiralIconBackgroundCellBadge) }

    private val imageView: ImageView by lazy { findViewById(R.id.admiralIconBackgroundCellImage) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_cell_unit_icon_background, this)

        imageView.background = drawable(R.drawable.admiral_bg_round)

        parseAttrs(attrs, R.styleable.IconBackgroundCellUnit).use {
            parseIconColors(it)
            parseIconBackgroundColors(it)
            parseIcon(it)

            unitType = CellUnitType.from(
                it.getInt(
                    R.styleable.IconBackgroundCellUnit_admiralCellUnitType,
                    0
                )
            )
        }
    }

    /**
     * Subscribe for theme change.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.subscribe(this)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        imageView.alpha = if (enabled) {
            ALPHA_ENABLED
        } else {
            ALPHA_DISABLED
        }
    }

    /**
     * Unsubscribe for theme change.
     */
    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateIconBackgroundColors()
        invalidateIconTintColors()
    }

    private fun parseIcon(a: TypedArray) {
        icon = a.getDrawable(R.styleable.IconBackgroundCellUnit_admiralIcon)
    }

    private fun parseIconColors(typedArray: TypedArray) {
        imageView.isColored =
            typedArray.getBoolean(R.styleable.IconBackgroundCellUnit_isIconColored, true)

        iconTintColorNormalEnabledPalette =
            ColorPaletteEnum.from(typedArray.getIntOrNull(R.styleable.IconBackgroundCellUnit_admiralIconColorNormalEnabledPalette))

        iconTintColors = ColorState(
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.IconBackgroundCellUnit_admiralIconTintColorNormalEnabled,
            ),
            pressed = typedArray.getColorOrNull(
                R.styleable.IconBackgroundCellUnit_admiralIconTintColorPressed,
            ),
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.IconBackgroundCellUnit_admiralIconTintColorNormalDisabled,
            )
        )
    }

    private fun parseIconBackgroundColors(typedArray: TypedArray) {
        iconBackgroundColorNormalEnabledPalette =
            ColorPaletteEnum.from(typedArray.getIntOrNull(R.styleable.IconBackgroundCellUnit_admiralBackgroundColorNormalEnabledPalette))

        iconBackgroundColors = ColorState(
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.IconBackgroundCellUnit_admiralIconBackgroundColorNormalEnabled,
            ),
            pressed = typedArray.getColorOrNull(
                R.styleable.IconBackgroundCellUnit_admiralIconBackgroundColorPressed,
            ),
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.IconBackgroundCellUnit_admiralIconBackgroundColorNormalDisabled,
            )
        )
    }

    private fun invalidateIconBackgroundColors() {
        imageView.backgroundTintList = colorStateList(
            enabled = iconBackgroundColorNormalEnabledPalette.colorResToToken()
                ?: iconBackgroundColors?.normalEnabled
                ?: ThemeManager.theme.palette.backgroundAdditionalOne,
            disabled = iconBackgroundColorNormalEnabledPalette.colorResToToken()
                ?: iconBackgroundColors?.normalDisabled
                ?: ThemeManager.theme.palette.backgroundAdditionalOne.withAlpha(),
            pressed = iconBackgroundColorNormalEnabledPalette.colorResToToken()
                ?: iconBackgroundColors?.pressed
                ?: ThemeManager.theme.palette.backgroundAdditionalOnePressed
        )
    }

    private fun invalidateIconTintColors() {
        imageView.imageTintColorState = ColorState(
            normalEnabled = iconTintColorNormalEnabledPalette.colorResToToken()
                ?: iconTintColors?.normalEnabled
                ?: ThemeManager.theme.palette.elementAccent,
            normalDisabled = iconTintColors?.normalDisabled
                ?: iconTintColorNormalEnabledPalette.colorResToToken()
                ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
            pressed = iconTintColors?.pressed
                ?: iconTintColorNormalEnabledPalette.colorResToToken()
                ?: ThemeManager.theme.palette.elementAccentPressed
        )
    }

    private companion object {
        const val ALPHA_ENABLED = 1f
        const val ALPHA_DISABLED = 0.6f
        const val DEFAULT_SIZE_IMAGE = 40f
    }
}