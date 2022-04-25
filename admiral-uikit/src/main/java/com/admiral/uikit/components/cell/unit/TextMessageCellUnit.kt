package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
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
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

class TextMessageCellUnit @JvmOverloads constructor(
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
     * Message text.
     * Invisible if text is null or empty.
     */
    var message: String? = null
        set(value) {
            field = value
            messageTextView.text = value
            messageTextView.isVisible = value?.isNotEmpty() == true
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

    /**
     * Max lines for the titleTextView.
     */
    var titleMaxLines: Int = Int.MAX_VALUE
        set(value) {
            field = value
            titleTextView.maxLines = value
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
     * Color state for the message text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var messageTextColor: ColorState? = null
        set(value) {
            field = value
            invalidateMessageTextColors()
        }

    /**
     * Color state for the message text' background.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var messageBackgroundColor: ColorState? = null
        set(value) {
            field = value
            invalidateMessageBackgroundColors()
        }

    /**
     * Color state for the message text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var summMoreIconTintColor: ColorState? = null
        set(value) {
            field = value
            invalidateSummMoreIconTintColors()
        }

    private val titleTextView: TextView by lazy { findViewById(R.id.titleTextView) }
    private val titleMoreTextView: TextView by lazy { findViewById(R.id.titleMoreTextView) }
    private val summTextView: TextView by lazy { findViewById(R.id.summTextView) }
    private val summMoreTextView: TextView by lazy { findViewById(R.id.summMoreTextView) }
    private val messageTextView: TextView by lazy { findViewById(R.id.messageTextView) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_cell_unit_text_message, this)

        parseAttrs(attrs, R.styleable.TextMessageCellUnit).use {
            title = it.getString(R.styleable.TextMessageCellUnit_admiralTitleText)
            titleMore = it.getString(R.styleable.TextMessageCellUnit_admiralTitleMoreText)
            summ = it.getString(R.styleable.TextMessageCellUnit_admiralSummText)
            summMore = it.getString(R.styleable.TextMessageCellUnit_admiralSummMoreText)
            message = it.getString(R.styleable.TextMessageCellUnit_admiralMessageText)

            val drawable = it.getDrawable(R.styleable.TextMessageCellUnit_admiralSummMoreIcon)
            summMoreTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)

            unitType = CellUnitType.from(it.getInt(R.styleable.TextMessageCellUnit_admiralCellUnitType, 0))

            parseColors(it)

            titleMaxLines = it.getInt(R.styleable.TextMessageCellUnit_admiralTitleMaxLines, Int.MAX_VALUE)
        }
    }

    private fun parseColors(a: TypedArray) {
        parseTitleTextColor(a)
        parseTitleMoreTextColor(a)
        parseSummTextColor(a)
        parseSummMoreTextColor(a)
        parseSummMoreIconTintColor(a)
        parseMessageTextColor(a)
        parseMessageBackgroundColor(a)
    }

    fun parseMessageBackgroundColor(a: TypedArray) {
        messageBackgroundColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralMessageBackgroundColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralMessageBackgroundColorNormalDisabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralMessageBackgroundColorPressed,
            )
        )
    }

    private fun parseMessageTextColor(a: TypedArray) {
        messageTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralMessageColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralMessageColorNormalDisabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralMessageColorPressed,
            )
        )
    }

    private fun parseSummMoreIconTintColor(a: TypedArray) {
        summMoreIconTintColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralSummMoreIconColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralSummMoreIconColorNormalDisabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralSummMoreIconColorPressed,
            )
        )
    }

    fun parseSummMoreTextColor(a: TypedArray) {
        summMoreTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralSummMoreColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralSummMoreColorNormalDisabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralSummMoreColorPressed,
            )
        )
    }

    private fun parseSummTextColor(a: TypedArray) {
        summTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralSummColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralSummColorNormalDisabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralSummColorPressed,
            )
        )
    }

    private fun parseTitleMoreTextColor(a: TypedArray) {
        titleMoreTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralTitleMoreColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralTitleMoreColorNormalDisabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralTitleMoreColorPressed,
            )
        )
    }

    private fun parseTitleTextColor(a: TypedArray) {
        titleTextColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralTitleColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralTitleColorNormalDisabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextMessageCellUnit_admiralTitleColorPressed,
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
        titleTextView.isEnabled = enabled
        titleMoreTextView.isEnabled = enabled
        summTextView.isEnabled = enabled
        summMoreTextView.isEnabled = enabled
        messageTextView.isEnabled = enabled
        super.setEnabled(enabled)
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateTitleColors()
        invalidateTitleMoreColors()
        invalidateSummColors()
        invalidateSummMoreColors()
        invalidateSummMoreIconTintColors()
        invalidateMessageTextColors()
        invalidateMessageBackgroundColors()
    }

    private fun invalidateTitleColors() {
        val colorState = ColorState(
            normalEnabled = titleTextColor?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = titleTextColor?.normalDisabled ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = titleTextColor?.pressed ?: ThemeManager.theme.palette.textPrimary
        )

        titleTextView.textColor = colorState
    }

    private fun invalidateTitleMoreColors() {
        val colorState = ColorState(
            normalEnabled = titleMoreTextColor?.normalEnabled ?: ThemeManager.theme.palette.textSecondary,
            normalDisabled = titleMoreTextColor?.normalDisabled ?: ThemeManager.theme.palette.textSecondary.withAlpha(),
            pressed = titleMoreTextColor?.pressed ?: ThemeManager.theme.palette.textSecondary
        )

        titleMoreTextView.textColor = colorState
    }

    private fun invalidateSummColors() {
        val colorState = ColorState(
            normalEnabled = summTextColor?.normalEnabled ?: ThemeManager.theme.palette.textAccent,
            normalDisabled = summTextColor?.normalDisabled ?: ThemeManager.theme.palette.textAccent.withAlpha(),
            pressed = summTextColor?.pressed ?: ThemeManager.theme.palette.textAccent
        )

        summTextView.textColor = colorState
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

    private fun invalidateMessageTextColors() {
        val colorState = ColorState(
            normalEnabled = messageTextColor?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = messageTextColor?.normalDisabled ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = messageTextColor?.pressed ?: ThemeManager.theme.palette.textPrimary
        )

        messageTextView.textColor = colorState
    }

    private fun invalidateMessageBackgroundColors() {
        messageTextView.backgroundTintList = colorStateList(
            enabled = messageBackgroundColor?.normalEnabled ?: ThemeManager.theme.palette.backgroundAdditionalOne,
            disabled = messageBackgroundColor?.normalDisabled ?: ThemeManager.theme.palette.backgroundAdditionalOne,
            pressed = messageBackgroundColor?.pressed ?: ThemeManager.theme.palette.backgroundAdditionalOne,
        )
    }

    private fun invalidateSummMoreIconTintColors() {
        val colorState = colorStateList(
            enabled = summMoreIconTintColor?.normalEnabled ?: ThemeManager.theme.palette.elementPrimary,
            disabled = summMoreIconTintColor?.normalDisabled ?: ThemeManager.theme.palette.elementPrimary.withAlpha(),
            pressed = summMoreIconTintColor?.pressed ?: ThemeManager.theme.palette.elementPrimary
        )

        for (drawable in summMoreTextView.compoundDrawables) {
            drawable?.setTintList(colorState)
        }
    }
}
