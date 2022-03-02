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
import com.admiral.uikit.common.components.cell.base.CellUnit
import com.admiral.uikit.common.components.cell.base.CellUnitType
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

class TitleSubtitleTextbuttonCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver, CellUnit {

    override var unitType: CellUnitType = CellUnitType.LEADING

    /**
     * Title text.
     * Gone if text is null or empty.
     */
    var title: String? = null
        set(value) {
            field = value
            titleTextView.text = value
            titleTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Subtitle text.
     * Gone if text is null or empty.
     */
    var subtitle: String? = null
        set(value) {
            field = value
            subtitleTextView.text = value
            subtitleTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Subtitle second text.
     * Gone if text is null or empty.
     */
    var subtitleSecond: String? = null
        set(value) {
            field = value
            subtitleSecondTextView.text = value
            subtitleSecondTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Button text.
     * Gone if text is null or empty.
     */
    var button: String? = null
        set(value) {
            field = value
            buttonTextView.text = value
            buttonTextView.isVisible = value?.isNotEmpty() == true
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
                View.INVISIBLE
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
     * Color state for the subtitle second text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var subtitleSecondTextColor: ColorState? = null
        set(value) {
            field = value
            invalidateSubtitleSecondColors()
        }

    /**
     * Color state for the button text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var buttonTextColor: ColorState? = null
        set(value) {
            field = value
            invalidateButtonColors()
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
    private val subtitleTextView: TextView by lazy { findViewById(R.id.subtitleTextView) }
    private val subtitleSecondTextView: TextView by lazy { findViewById(R.id.subtitleSecondTextView) }
    private val buttonTextView: TextView by lazy { findViewById(R.id.buttonTextView) }
    private val percentTextView: TextView by lazy { findViewById(R.id.percentTextView) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_cell_unit_title_subtitle_textbutton, this)

        parseAttrs(attrs, R.styleable.TitleSubtitleTextbuttonCellUnit).use {
            title = it.getString(R.styleable.TitleSubtitleTextbuttonCellUnit_admiralTitleText)
            subtitle = it.getString(R.styleable.TitleSubtitleTextbuttonCellUnit_admiralSubtitleText)
            subtitleSecond = it.getString(R.styleable.TitleSubtitleTextbuttonCellUnit_admiralSubtitleSecondText)
            button = it.getString(R.styleable.TitleSubtitleTextbuttonCellUnit_admiralButtonText)
            percent = it.getString(R.styleable.TitleSubtitleTextbuttonCellUnit_admiralPercentText)

            unitType = CellUnitType.from(it.getInt(R.styleable.TitleSubtitleTextbuttonCellUnit_admiralCellUnitType, 0))
            parseColors(it)

            titleMaxLines = it.getInt(R.styleable.TitleSubtitleTextbuttonCellUnit_admiralTitleMaxLines, Int.MAX_VALUE)
        }
    }

    override fun setEnabled(enabled: Boolean) {
        titleTextView.isEnabled = enabled
        subtitleTextView.isEnabled = enabled
        subtitleSecondTextView.isEnabled = enabled
        buttonTextView.isEnabled = enabled
        percentTextView.isEnabled = enabled
        super.setEnabled(enabled)
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
        invalidateSubtitleSecondColors()
        invalidateButtonColors()
        invalidatePercentColors()
        invalidatePercentBackgroundColors()
    }

    private fun parseColors(a: TypedArray) {
        parseTitleTextColor(a)
        parseSubtitleSecondTextColor(a)
        parseSubtitleTextColor(a)
        parseButtonTextColor(a)
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

    private fun parseButtonTextColor(a: TypedArray) {
        buttonTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralButtonTextColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralButtonTextColorNormalDisabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralButtonTextColorPressed,
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

    private fun parseSubtitleSecondTextColor(a: TypedArray) {
        subtitleSecondTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralSubtitleSecondColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralSubtitleSecondColorNormalDisabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralSubtitleSecondColorPressed,
            )
        )
    }

    private fun parseTitleTextColor(a: TypedArray) {
        titleTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralTitleColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralTitleColorNormalDisabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TitleSubtitleTextbuttonCellUnit_admiralTitleColorPressed,
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
            normalEnabled = subtitleTextColor?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = subtitleTextColor?.normalDisabled
                ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = subtitleTextColor?.pressed ?: ThemeManager.theme.palette.textPrimary
        )

        subtitleTextView.textColor = colorState
    }

    private fun invalidateSubtitleSecondColors() {
        val colorState = ColorState(
            normalEnabled = subtitleSecondTextColor?.normalEnabled ?: ThemeManager.theme.palette.textSecondary,
            normalDisabled = subtitleSecondTextColor?.normalDisabled
                ?: ThemeManager.theme.palette.textSecondary.withAlpha(),
            pressed = subtitleSecondTextColor?.pressed ?: ThemeManager.theme.palette.textSecondary
        )

        subtitleSecondTextView.textColor = colorState
    }

    private fun invalidateButtonColors() {
        val colorState = ColorState(
            normalEnabled = buttonTextColor?.normalEnabled ?: ThemeManager.theme.palette.textAccent,
            normalDisabled = buttonTextColor?.normalDisabled ?: ThemeManager.theme.palette.textAccent.withAlpha(),
            pressed = buttonTextColor?.pressed ?: ThemeManager.theme.palette.textAccent
        )

        buttonTextView.textColor = colorState
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
