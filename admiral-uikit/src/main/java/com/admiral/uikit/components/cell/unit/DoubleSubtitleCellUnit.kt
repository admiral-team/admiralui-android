package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
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

class DoubleSubtitleCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver, CellUnit {

    override lateinit var unitType: CellUnitType

    /**
     * Subtitle top text.
     * Gone if text is null or empty.
     */
    var subtitleTop: String? = null
        set(value) {
            field = value
            subtitleTopTextView.text = value
            subtitleTopTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Subtitle bottom text.
     * Gone if text is null or empty.
     */
    var subtitleBottom: String? = null
        set(value) {
            field = value
            subtitleBottomTextView.text = value
            subtitleBottomTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Color state for top text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var topTextColor: ColorState? = null
        set(value) {
            field = value
            invalidateTopTextColors()
        }

    /**
     * Color state for bottom text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var bottomTextColor: ColorState? = null
        set(value) {
            field = value
            invalidateBottomTextColors()
        }

    /**
     * Apply style for the top [TextView]
     */
    var topTextStyle: Font = ThemeManager.theme.typography.subhead3
        set(value) {
            field = value
            subtitleTopTextView.textStyle = field
        }

    /**
     * Apply style fot the bottom [TextView]
     */
    var bottomTextStyle: Font = ThemeManager.theme.typography.subhead3
        set(value) {
            field = value
            subtitleBottomTextView.textStyle = field
        }

    private val subtitleTopTextView: TextView by lazy { findViewById(R.id.subtitleTopTextView) }
    private val subtitleBottomTextView: TextView by lazy { findViewById(R.id.subtitleBottomTextView) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_cell_unit_double_subtitle, this)

        parseAttrs(attrs, R.styleable.DoubleSubtitleCellUnit).use {
            subtitleTop = it.getString(R.styleable.DoubleSubtitleCellUnit_admiralSubtitleText)
            subtitleBottom = it.getString(R.styleable.DoubleSubtitleCellUnit_admiralSubtitleTextSecond)

            topTextStyle = Typography.getStyleById(
                it.getInt(
                    R.styleable.DoubleSubtitleCellUnit_admiralSubtitleTextStyle, subhead3
                )
            )
            bottomTextStyle = Typography.getStyleById(
                it.getInt(R.styleable.DoubleSubtitleCellUnit_admiralSubtitleSecondTextStyle, subhead3)
            )

            unitType = CellUnitType.from(it.getInt(R.styleable.SubtitleCellUnit_admiralCellUnitType, 0))

            parseTextColors(it)
        }
    }

    private fun parseTextColors(a: TypedArray) {
        topTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.DoubleSubtitleCellUnit_admiralTitleColorNormalEnabled
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.DoubleSubtitleCellUnit_admiralTitleColorNormalDisabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.DoubleSubtitleCellUnit_admiralTitleColorPressed
            )
        )

        bottomTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.DoubleSubtitleCellUnit_admiralSubtitleColorNormalEnabled
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.DoubleSubtitleCellUnit_admiralSubtitleColorNormalDisabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.DoubleSubtitleCellUnit_admiralSubtitleColorPressed
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

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        subtitleTopTextView.isEnabled = enabled
        subtitleBottomTextView.isEnabled = enabled
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateTopTextColors()
        invalidateBottomTextColors()
    }

    private fun invalidateTopTextColors() {
        val colorState = ColorState(
            normalEnabled = topTextColor?.normalEnabled ?: ThemeManager.theme.palette.textSecondary,
            normalDisabled = topTextColor?.normalDisabled ?: ThemeManager.theme.palette.textSecondary.withAlpha(),
            pressed = topTextColor?.pressed ?: ThemeManager.theme.palette.textSecondary
        )

        subtitleTopTextView.textColor = colorState
    }

    private fun invalidateBottomTextColors() {
        val colorState = ColorState(
            normalEnabled = bottomTextColor?.normalEnabled ?: ThemeManager.theme.palette.textSecondary,
            normalDisabled = bottomTextColor?.normalDisabled ?: ThemeManager.theme.palette.textSecondary.withAlpha(),
            pressed = bottomTextColor?.pressed ?: ThemeManager.theme.palette.textSecondary
        )

        subtitleBottomTextView.textColor = colorState
    }
}