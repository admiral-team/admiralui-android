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
import com.admiral.themes.Typography.Companion.body1
import com.admiral.themes.Typography.Companion.subhead3
import com.admiral.uikit.R
import com.admiral.uikit.core.components.cell.base.CellUnit
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

class TitleSubtitleCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver, CellUnit {

    override var unitType: CellUnitType = CellUnitType.LEADING

    /**
     * Top text.
     * Gone if text is null or empty.
     */
    var title: String? = null
        set(value) {
            field = value
            titleTextView.text = value
            titleTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Bottom text.
     * Gone if text is null or empty.
     */
    var subtitle: String? = null
        set(value) {
            field = value
            subtitleTextView.text = value
            subtitleTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Color state for top text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var titleTextColor: ColorState? = null
        set(value) {
            field = value
            invalidateTitleColors()
        }

    /*
    * Max lines for the titleTextView.
    * */
    var titleMaxLines: Int = Int.MAX_VALUE
        set(value) {
            field = value
            titleTextView.maxLines = value
        }

    /**
     * Color state for bottom text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var subtitleTextColor: ColorState? = null
        set(value) {
            field = value
            invalidateSubtitleColors()
        }

    /**
     * Apply style for the title.
     */
    var titleTextStyle: Font = ThemeManager.theme.typography.body1
        set(value) {
            field = value
            titleTextView.textStyle = value
        }

    /**
     * Apply style for the subtitle.
     */
    var subtitleTextStyle: Font = ThemeManager.theme.typography.subtitle3
        set(value) {
            field = value
            subtitleTextView.textStyle = value
        }

    private val titleTextView: TextView by lazy { findViewById(R.id.titleTextView) }
    private val subtitleTextView: TextView by lazy { findViewById(R.id.subtitleTextView) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_cell_unit_title_subtitle, this)

        parseAttrs(attrs, R.styleable.TitleSubtitleCellUnit).use {
            title = it.getString(R.styleable.TitleSubtitleCellUnit_admiralTitleText)
            subtitle = it.getString(R.styleable.TitleSubtitleCellUnit_admiralSubtitleText)

            titleTextStyle = Typography.getStyleById(
                it.getInt(
                    R.styleable.TitleSubtitleCellUnit_admiralTitleTextStyle, body1
                )
            )
            subtitleTextStyle = Typography.getStyleById(
                it.getInt(
                    R.styleable.TitleSubtitleCellUnit_admiralSubtitleTextStyle, subhead3
                )
            )

            unitType = CellUnitType.from(it.getInt(R.styleable.TitleCellUnit_admiralCellUnitType, 0))
            parseTextColors(it)

            titleMaxLines = it.getInt(R.styleable.TitleSubtitleCellUnit_admiralTitleMaxLines, Int.MAX_VALUE)
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
        invalidateTitleColors()
        invalidateSubtitleColors()
    }

    override fun setEnabled(enabled: Boolean) {
        titleTextView.isEnabled = enabled
        subtitleTextView.isEnabled = enabled
        super.setEnabled(enabled)
    }

    private fun parseTextColors(a: TypedArray) {
        titleTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TitleSubtitleCellUnit_admiralTitleColorNormalEnabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.TitleSubtitleCellUnit_admiralTitleColorPressed
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TitleSubtitleCellUnit_admiralTitleColorNormalDisabled
            )
        )

        subtitleTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TitleSubtitleCellUnit_admiralSubtitleColorNormalEnabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.TitleSubtitleCellUnit_admiralSubtitleColorPressed
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TitleSubtitleCellUnit_admiralSubtitleColorNormalDisabled
            )
        )
    }

    private fun invalidateTitleColors() {
        val colorState = ColorState(
            normalEnabled = titleTextColor?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = titleTextColor?.normalDisabled ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = titleTextColor?.pressed ?: ThemeManager.theme.palette.textPrimary
        )

        titleTextView.textColor = colorState
    }

    private fun invalidateSubtitleColors() {
        val colorState = ColorState(
            normalEnabled = subtitleTextColor?.normalEnabled ?: ThemeManager.theme.palette.textSecondary,
            normalDisabled = subtitleTextColor?.normalDisabled ?: ThemeManager.theme.palette.textSecondary.withAlpha(),
            pressed = subtitleTextColor?.pressed ?: ThemeManager.theme.palette.textSecondary
        )

        subtitleTextView.textColor = colorState
    }
}
