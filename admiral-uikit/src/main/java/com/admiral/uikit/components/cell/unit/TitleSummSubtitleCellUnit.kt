package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.core.components.cell.base.CellUnit
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

class TitleSummSubtitleCellUnit @JvmOverloads constructor(
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
            titleMoreTextView.isVisible = value?.isNotEmpty() == true
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
     * Middle text.
     * Gone if text is null or empty.
     */
    var summ: String? = null
        set(value) {
            field = value
            summTextView.text = value
            summTextView.isVisible = value?.isNotEmpty() == true
            summMoreTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Summ more text.
     * Gone if text or summ are null or empty.
     */
    var summMore: String? = null
        set(value) {
            field = value
            summMoreTextView.text = value
            summMoreTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Title more text.
     * Gone if text or title are null or empty.
     */
    var titleMore: String? = null
        set(value) {
            field = value
            titleMoreTextView.text = value
            titleMoreTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Percent text.
     * Invisible if text is null or empty.
     */
    var percent: String? = null
        set(value) {
            field = value
            percentTextView.text = value
            percentTextView.visibility = if (value?.isNotEmpty() == true) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

    /**
     * Color state for the title text.
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
     * Color state for the title more text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var titleMoreTextColor: ColorState? = null
        set(value) {
            field = value
            invalidateTitleMoreColors()
        }

    /**
     * Color state for the subtitle text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var subtitleTextColor: ColorState? = null
        set(value) {
            field = value
            invalidateSubtitleColors()
        }

    /**
     * Color state for the summ text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var summTextColor: ColorState? = null
        set(value) {
            field = value
            invalidateSummColors()
        }

    /**
     * Color state for the summ more text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var summMoreTextColor: ColorState? = null
        set(value) {
            field = value
            invalidateSummMoreColors()
        }

    /**
     * Color state for the percent text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var percentTextColor: ColorState? = null
        set(value) {
            field = value
            invalidatePercentColors()
        }

    /**
     * Color state for the percent text' background.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var percentBackgroundColor: ColorState? = null
        set(value) {
            field = value
            invalidatePercentBackgroundColors()
        }

    private val titleTextView: TextView by lazy { findViewById(R.id.titleTextView) }
    private val titleMoreTextView: TextView by lazy { findViewById(R.id.titleMoreTextView) }
    private val summTextView: TextView by lazy { findViewById(R.id.summTextView) }
    private val summMoreTextView: TextView by lazy { findViewById(R.id.summMoreTextView) }
    private val subtitleTextView: TextView by lazy { findViewById(R.id.subtitleTextView) }
    private val percentTextView: TextView by lazy { findViewById(R.id.percentTextView) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_cell_unit_title_summ_subtitle, this)

        parseAttrs(attrs, R.styleable.TitleSummSubtitleCellUnit).use {
            title = it.getString(R.styleable.TitleSummSubtitleCellUnit_admiralTitleText)
            titleMore = it.getString(R.styleable.TitleSummSubtitleCellUnit_admiralTitleMoreText)
            summ = it.getString(R.styleable.TitleSummSubtitleCellUnit_admiralSummText)
            summMore = it.getString(R.styleable.TitleSummSubtitleCellUnit_admiralSummMoreText)
            subtitle = it.getString(R.styleable.TitleSummSubtitleCellUnit_admiralSubtitleText)
            percent = it.getString(R.styleable.TitleSummSubtitleCellUnit_admiralPercentText)

            val drawable = it.getDrawable(R.styleable.TitleSummSubtitleCellUnit_admiralSummMoreIcon)
            summMoreTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)

            unitType = CellUnitType.from(it.getInt(R.styleable.TitleCellUnit_admiralCellUnitType, 0))

            parseColors(it)

            titleMaxLines = it.getInt(R.styleable.TitleSummSubtitleCellUnit_admiralTitleMaxLines, Int.MAX_VALUE)
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

    override fun setEnabled(enabled: Boolean) {
        titleTextView.isEnabled = enabled
        titleMoreTextView.isEnabled = enabled
        summTextView.isEnabled = enabled
        summMoreTextView.isEnabled = enabled
        subtitleTextView.isEnabled = enabled
        percentTextView.isEnabled = enabled
        super.setEnabled(enabled)
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateTitleColors()
        invalidateTitleMoreColors()
        invalidateSummColors()
        invalidateSummMoreColors()
        invalidatePercentColors()
        invalidateSubtitleColors()
        invalidatePercentBackgroundColors()
    }

    private fun parseColors(a: TypedArray) {
        parseTitleTextColor(a)
        parseTitleMoreTextColor(a)
        parseSubtitleTextColor(a)
        parseSummTextColor(a)
        parseSummMoreTextColor(a)
        parsePercentTextColor(a)
        parsePercentBackgroundColor(a)
    }

    private fun parsePercentBackgroundColor(a: TypedArray) {
        percentBackgroundColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralPercentBackgroundColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralPercentBackgroundColorNormalDisabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralPercentBackgroundColorPressed,
            )
        )
    }

    private fun parsePercentTextColor(a: TypedArray) {
        percentTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralPercentColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralPercentColorNormalDisabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralPercentColorPressed,
            )
        )
    }

    private fun parseSummMoreTextColor(a: TypedArray) {
        summMoreTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TitleSummSubtitleCellUnit_admiralSummMoreColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TitleSummSubtitleCellUnit_admiralSummMoreColorNormalDisabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TitleSummSubtitleCellUnit_admiralSummMoreColorPressed,
            )
        )
    }

    private fun parseSummTextColor(a: TypedArray) {
        summTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TitleSummSubtitleCellUnit_admiralSummColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TitleSummSubtitleCellUnit_admiralSummColorNormalDisabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TitleSummSubtitleCellUnit_admiralSummColorPressed,
            )
        )
    }

    private fun parseSubtitleTextColor(a: TypedArray) {
        subtitleTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralSubtitleColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralSubtitleColorNormalDisabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralSubtitleColorPressed,
            )
        )
    }

    private fun parseTitleMoreTextColor(a: TypedArray) {
        titleMoreTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TitleSummSubtitleCellUnit_admiralTitleMoreColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TitleSummSubtitleCellUnit_admiralTitleMoreColorNormalDisabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.TitleSummSubtitleCellUnit_admiralTitleMoreColorPressed,
            )
        )
    }

    private fun parseTitleTextColor(a: TypedArray) {
        titleTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TitleSummSubtitleCellUnit_admiralTitleColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TitleSummSubtitleCellUnit_admiralTitleColorNormalDisabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TitleSummSubtitleCellUnit_admiralTitleColorPressed,
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

    private fun invalidateSummColors() {
        val colorState = ColorState(
            normalEnabled = summTextColor?.normalEnabled ?: ThemeManager.theme.palette.textAccent,
            normalDisabled = summTextColor?.normalDisabled ?: ThemeManager.theme.palette.textAccent.withAlpha(),
            pressed = summTextColor?.pressed ?: ThemeManager.theme.palette.textAccent
        )

        summTextView.textColor = colorState
    }

    private fun invalidateTitleMoreColors() {
        val colorState = ColorState(
            normalEnabled = titleMoreTextColor?.normalEnabled ?: ThemeManager.theme.palette.textSecondary,
            normalDisabled = titleMoreTextColor?.normalDisabled ?: ThemeManager.theme.palette.textSecondary.withAlpha(),
            pressed = titleMoreTextColor?.pressed ?: ThemeManager.theme.palette.textSecondary
        )

        titleMoreTextView.textColor = colorState
    }

    private fun invalidateSummMoreColors() {
        val colorState = ColorState(
            normalEnabled = summMoreTextColor?.normalEnabled ?: ThemeManager.theme.palette.textAccentAdditional,
            normalDisabled = summMoreTextColor?.normalDisabled
                ?: ThemeManager.theme.palette.textAccentAdditional.withAlpha(),
            pressed = summMoreTextColor?.pressed ?: ThemeManager.theme.palette.textAccentAdditional
        )

        summMoreTextView.textColor = colorState
    }

    private fun invalidatePercentColors() {
        val colorState = ColorState(
            normalEnabled = percentTextColor?.normalEnabled ?: ThemeManager.theme.palette.textSecondary,
            normalDisabled = percentTextColor?.normalDisabled ?: ThemeManager.theme.palette.textSecondary.withAlpha(),
            pressed = percentTextColor?.pressed ?: ThemeManager.theme.palette.textSecondary
        )

        percentTextView.textColor = colorState
    }

    private fun invalidatePercentBackgroundColors() {
        percentTextView.backgroundTintList = ColorStateList.valueOf(
            percentBackgroundColor?.normalEnabled ?: ThemeManager.theme.palette.backgroundAdditionalOne
        )
    }
}
