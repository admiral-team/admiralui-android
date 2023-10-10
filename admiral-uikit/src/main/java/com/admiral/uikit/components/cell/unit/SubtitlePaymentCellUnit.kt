package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.admiral.themes.Font
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.themes.Typography.Companion.subhead3
import com.admiral.uikit.R
import com.admiral.uikit.core.components.cell.base.CellUnit
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

class SubtitlePaymentCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver, CellUnit {

    override var unitType: CellUnitType = CellUnitType.LEADING

    /**
     * Payment icon.
     * Gone if text is null or empty.
     */
    var payment: Drawable? = null
        set(value) {
            field = value
            paymentImageView.setImageDrawable(value)
            paymentImageView.isVisible = value != null
        }

    /**
     * Subtitle text.
     * Gone if text is null or empty.
     */
    var subtitle: String? = null
        set(value) {
            field = value
            subtitleTextView.text = value
        }

    /**
     * Color state for the text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var textColor: ColorState? = null
        set(value) {
            field = value
            invalidateSubtitleColors()
        }

    /**
     * Apply style for the [TextView]
     */
    var textStyle: Font = ThemeManager.theme.typography.subhead3
        set(value) {
            field = value
            subtitleTextView.textStyle = field
        }

    private val paymentImageView: ImageView by lazy { findViewById(R.id.paymentImageView) }
    private val subtitleTextView: TextView by lazy { findViewById(R.id.subtitleTextView) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_cell_unit_subtitle_payment, this)

        parseAttrs(attrs, R.styleable.SubtitlePaymentCellUnit).use {
            payment = it.getDrawable(R.styleable.SubtitlePaymentCellUnit_admiralIcon)
            subtitle = it.getString(R.styleable.SubtitlePaymentCellUnit_admiralSubtitleText)

            textStyle = Typography.getStyleById(
                it.getInt(
                    R.styleable.SubtitlePaymentCellUnit_admiralSubtitleTextStyle, subhead3
                )
            )

            unitType = CellUnitType.from(it.getInt(R.styleable.SubtitlePaymentCellUnit_admiralCellUnitType, 0))

            parseTextColors(it)
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
        invalidateSubtitleColors()
    }

    private fun parseTextColors(a: TypedArray) {
        textColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.SubtitlePaymentCellUnit_admiralTextColorNormalEnabled
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.SubtitlePaymentCellUnit_admiralTextColorNormalDisabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.SubtitlePaymentCellUnit_admiralTextColorPressed
            )
        )
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        subtitleTextView.isEnabled = enabled

        paymentImageView.imageAlpha = if (enabled) {
            ALPHA_ENABLED
        } else {
            ALPHA_DISABLED
        }
    }

    private fun invalidateSubtitleColors() {
        val colorState = ColorState(
            normalEnabled = textColor?.normalEnabled ?: ThemeManager.theme.palette.textSecondary,
            normalDisabled = textColor?.normalDisabled ?: ThemeManager.theme.palette.textSecondary.withAlpha(),
            pressed = textColor?.pressed ?: ThemeManager.theme.palette.textSecondary
        )

        subtitleTextView.textColor = colorState
    }

    private companion object {
        const val ALPHA_ENABLED = 255
        const val ALPHA_DISABLED = 130
    }
}
