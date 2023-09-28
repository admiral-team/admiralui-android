package com.admiral.uikit.components.currency

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState

class CurrencyCell @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    var isHeader: Boolean = false
        set(value) {
            field = value
            invalidateType()
        }

    /**
     * Color state for background.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var backgroundColor: ColorState? = null
        set(value) {
            field = value
            invalidateBackground()
        }

    /**
     * Color state for currency text, placed at the start.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var textColorCurrency: ColorState? = null
        set(value) {
            field = value
            invalidateCurrencyTextColors()
        }

    /**
     * Color state for buy text, placed at the end.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var textColorBuy: ColorState? = null
        set(value) {
            field = value
            invalidateBuyTextColors()
        }

    /**
     * Color state for sell text, placed in the middle.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var textColorSell: ColorState? = null
        set(value) {
            field = value
            invalidateSellTextColors()
        }

    /**
     * Color state for texts if the cell is a header.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var textColorHeader: ColorState? = null
        set(value) {
            field = value
            invalidateType()
        }

    /**
     * Currency text, placed at the start.
     */
    var textCurrency: String? = null
        set(value) {
            field = value
            currencyTextView.text = value
        }

    /**
     * Buy text, placed in the middle.
     */
    var textBuy: String? = null
        set(value) {
            field = value
            buyTextView.text = value
        }

    /**
     * Sell text, placed at the end.
     */
    var textSell: String? = null
        set(value) {
            field = value
            sellTextView.text = value
        }

    /**
     * In case Drawable is null, the icon will be hidden.
     */
    var drawableCurrency: Drawable? = null
        set(value) {
            field = value
            invalidateCurrencyDrawable()
        }

    /**
     * In case Drawable is null, the icon will be chosen via drawableBuyType.
     */
    var drawableBuy: Drawable? = null
        set(value) {
            field = value
            invalidateBuyDrawable()
        }

    /**
     * The variable to choose between default drawables: arrow up or arrow down.
     */
    var drawableBuyType: CurrencyDrawableType = CurrencyDrawableType.NONE
        set(value) {
            field = value
            invalidateBuyDrawable()
        }

    /**
     * In case Drawable is null, the icon will be chosen via drawableSellType.
     */
    var drawableSell: Drawable? = null
        set(value) {
            field = value
            invalidateSellDrawable()
        }

    /**
     * The variable to choose between default drawables: arrow up or arrow down.
     */
    var drawableSellType: CurrencyDrawableType = CurrencyDrawableType.NONE
        set(value) {
            field = value
            invalidateSellDrawable()
        }

    /**
     * Color state for currency text drawable.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var drawableCurrencyTintColor: ColorState? = null
        set(value) {
            field = value
            invalidateCurrencyDrawable()
        }

    /**
     * Color state for buy text drawable.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var drawableBuyTintColor: ColorState? = null
        set(value) {
            field = value
            invalidateBuyDrawable()
        }

    /**
     * Color state for sell text drawable.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var drawableSellTintColor: ColorState? = null
        set(value) {
            field = value
            invalidateSellDrawable()
        }

    private val currencyTextView: TextView by lazy { findViewById(R.id.admiralCurrencyTextView) }
    private val buyTextView: TextView by lazy { findViewById(R.id.admiralBuyTextView) }
    private val sellTextView: TextView by lazy { findViewById(R.id.admiralSellTextView) }
    private val sellImageView: ImageView by lazy { findViewById(R.id.admiralCurrencySellImage) }
    private val buyImageView: ImageView by lazy { findViewById(R.id.admiralCurrencyBuyImage) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_cell_currency, this)

        parseAttrs(attrs, R.styleable.CurrencyCell).use {
            parseBackgroundColors(it)

            parseCurrencyTextColors(it)
            parseBuyTextColors(it)
            parseSellTextColors(it)
            parseHeaderTextColors(it)

            val drawableCurrencyId = it.getResourceId(R.styleable.CurrencyCell_admiralCurrencyDrawable, 0)
            val drawableBuyId = it.getResourceId(R.styleable.CurrencyCell_admiralBuyDrawable, 0)
            val drawableSellId = it.getResourceId(R.styleable.CurrencyCell_admiralSellDrawable, 0)

            if (drawableCurrencyId != 0) drawableCurrency = drawable(drawableCurrencyId)
            if (drawableBuyId != 0) drawableBuy = drawable(drawableBuyId)
            if (drawableSellId != 0) drawableSell = drawable(drawableSellId)

            parseBuyDrawableColors(it)
            parseSellDrawableColors(it)

            textCurrency = it.getString(R.styleable.CurrencyCell_admiralCurrencyText)
            textSell = it.getString(R.styleable.CurrencyCell_admiralSellText)
            textBuy = it.getString(R.styleable.CurrencyCell_admiralBuyText)
            isHeader = it.getBoolean(R.styleable.CurrencyCell_admiralIsHeader, false)

            drawableSellType = CurrencyDrawableType.from(it.getInt(R.styleable.CurrencyCell_admiralSellIconType, 0))
            drawableBuyType = CurrencyDrawableType.from(it.getInt(R.styleable.CurrencyCell_admiralBuyIconType, 0))
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
        invalidateBackground()
        invalidateCurrencyTextColors()
        invalidateSellTextColors()
        invalidateBuyTextColors()
        invalidateSellDrawable()
        invalidateBuyDrawable()
        invalidateType()
    }

    private fun parseBackgroundColors(a: TypedArray) {
        backgroundColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralBackgroundColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralBackgroundColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralBackgroundColorNormalDisabled,
            )
        )
    }

    private fun parseCurrencyTextColors(a: TypedArray) {
        textColorCurrency = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralCurrencyTextColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralCurrencyTextColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralCurrencyTextColorNormalDisabled,
            )
        )
    }

    private fun parseSellTextColors(a: TypedArray) {
        textColorSell = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralSellTextColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralSellTextColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralSellTextColorNormalDisabled,
            )
        )
    }

    private fun parseBuyTextColors(a: TypedArray) {
        textColorBuy = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralBuyTextColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralBuyTextColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralBuyTextColorNormalDisabled,
            )
        )
    }

    private fun parseHeaderTextColors(a: TypedArray) {
        textColorHeader = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralHeaderTextColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralHeaderTextColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralHeaderTextColorNormalDisabled,
            )
        )
    }

    private fun parseBuyDrawableColors(a: TypedArray) {
        drawableBuyTintColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralBuyDrawableTintColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralBuyDrawableTintColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralBuyDrawableTintColorNormalDisabled,
            )
        )
    }

    private fun parseSellDrawableColors(a: TypedArray) {
        drawableSellTintColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralSellDrawableTintColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralSellDrawableTintColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.CurrencyCell_admiralSellDrawableTintColorNormalDisabled,
            )
        )
    }

    private fun invalidateBackground() {
        val contentStateList = colorStateList(
            enabled = backgroundColor?.normalEnabled ?: ThemeManager.theme.palette.backgroundAccent,
            disabled = backgroundColor?.normalDisabled ?: ThemeManager.theme.palette.backgroundAccent.withAlpha(),
            pressed = backgroundColor?.pressed ?: ThemeManager.theme.palette.backgroundAccent
        )

        backgroundTintList = contentStateList
    }

    private fun invalidateCurrencyTextColors() {
        val colorState = ColorState(
            normalEnabled = this.textColorCurrency?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = this.textColorCurrency?.normalDisabled
                ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = this.textColorCurrency?.pressed ?: ThemeManager.theme.palette.textPrimary
        )

        currencyTextView.textColor = colorState
    }

    private fun invalidateBuyTextColors() {
        val colorState = ColorState(
            normalEnabled = this.textColorBuy?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = this.textColorBuy?.normalDisabled
                ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = this.textColorBuy?.pressed ?: ThemeManager.theme.palette.textPrimary
        )

        buyTextView.textColor = colorState
    }

    private fun invalidateSellTextColors() {
        val colorState = ColorState(
            normalEnabled = textColorSell?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = textColorSell?.normalDisabled
                ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = textColorSell?.pressed ?: ThemeManager.theme.palette.textPrimary
        )

        sellTextView.textColor = colorState
    }

    private fun invalidateCurrencyDrawable() {
        if (drawableCurrencyTintColor == null) {
            currencyTextView.setCompoundDrawablesWithIntrinsicBounds(
                drawableCurrency,
                null,
                null,
                null
            )
        } else {
            val colorState = colorStateList(
                enabled = drawableCurrencyTintColor?.normalEnabled ?: ThemeManager.theme.palette.elementAccent,
                disabled = drawableCurrencyTintColor?.normalDisabled
                    ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                pressed = drawableCurrencyTintColor?.pressed ?: ThemeManager.theme.palette.elementAccent
            )

            currencyTextView.setCompoundDrawablesWithIntrinsicBounds(
                drawableCurrency?.colored(colorState),
                null,
                null,
                null
            )
        }
    }

    private fun invalidateBuyDrawable() {
        val colorState = colorStateList(
            enabled = drawableBuyTintColor?.normalEnabled ?: ThemeManager.theme.palette.elementPrimary,
            disabled = drawableBuyTintColor?.normalDisabled
                ?: ThemeManager.theme.palette.elementPrimary.withAlpha(),
            pressed = drawableBuyTintColor?.pressed ?: ThemeManager.theme.palette.elementPrimary
        )

        val drawable = if (drawableBuy == null) {
            when (drawableBuyType) {
                CurrencyDrawableType.DOWN -> drawable(R.drawable.admiral_ic_arrow_down_outline)
                CurrencyDrawableType.UP -> drawable(R.drawable.admiral_ic_arrow_up_outline)
                CurrencyDrawableType.NONE -> null
            }
        } else {
            drawableBuy
        }

        sellImageView.setImageDrawable(drawable?.colored(colorState))
    }

    private fun invalidateSellDrawable() {
        val colorState = colorStateList(
            enabled = drawableSellTintColor?.normalEnabled ?: ThemeManager.theme.palette.elementPrimary,
            disabled = drawableSellTintColor?.normalDisabled
                ?: ThemeManager.theme.palette.elementPrimary.withAlpha(),
            pressed = drawableSellTintColor?.pressed ?: ThemeManager.theme.palette.elementPrimary
        )

        val drawable = if (drawableSell == null) {
            when (drawableSellType) {
                CurrencyDrawableType.DOWN -> drawable(R.drawable.admiral_ic_arrow_down_outline)
                CurrencyDrawableType.UP -> drawable(R.drawable.admiral_ic_arrow_up_outline)
                CurrencyDrawableType.NONE -> null
            }
        } else {
            drawableSell
        }

        buyImageView.setImageDrawable(drawable?.colored(colorState))
    }

    private fun invalidateType() {
        if (isHeader) {
            val colorState = ColorState(
                normalEnabled = textColorHeader?.normalEnabled ?: ThemeManager.theme.palette.textSecondary,
                normalDisabled = textColorHeader?.normalDisabled
                    ?: ThemeManager.theme.palette.textSecondary.withAlpha(),
                pressed = textColorHeader?.pressed ?: ThemeManager.theme.palette.textSecondary
            )

            sellTextView.textColor = colorState
            buyTextView.textColor = colorState
            currencyTextView.textColor = colorState

            sellTextView.textStyle = ThemeManager.theme.typography.subhead3
            buyTextView.textStyle = ThemeManager.theme.typography.subhead3
            currencyTextView.textStyle = ThemeManager.theme.typography.subhead3
        } else {
            invalidateSellTextColors()
            invalidateBuyTextColors()
            invalidateCurrencyTextColors()

            sellTextView.textStyle = ThemeManager.theme.typography.body1
            buyTextView.textStyle = ThemeManager.theme.typography.body1
            currencyTextView.textStyle = ThemeManager.theme.typography.body1
        }
    }
}