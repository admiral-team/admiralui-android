package com.admiral.uikit.components.spinner

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.core.R as core

class Spinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ProgressBar(
    ContextThemeWrapper(context, com.google.android.material.R.style.Widget_AppCompat_ProgressBar),
    attrs,
    defStyleAttr
), ThemeObserver {

    var spinnerSize: SpinnerSize = SpinnerSize.BIG

    var isContrast = false
        set(value) {
            field = value
            invalidateProgressBarColor()
        }

    var progressColor: ColorState? = null
        set(value) {
            field = value
            invalidateProgressBarColor()
        }

    init {
        parseAttrs(attrs, R.styleable.Spinner).use {
            val styleIndex = it.getInt(R.styleable.Spinner_admiralSpinnerSize, 0)
            isContrast = it.getBoolean(R.styleable.Spinner_isContrast, false)

            spinnerSize = when (styleIndex) {
                0 -> SpinnerSize.BIG
                1 -> SpinnerSize.MEDIUM
                2 -> SpinnerSize.SMALL
                else -> SpinnerSize.BIG
            }

            parseColors(it)
        }

        indeterminateDrawable =
            ContextCompat.getDrawable(context, R.drawable.admiral_progress_bar_states)

        stateListAnimator = null
        isIndeterminate = true
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val spec = when (spinnerSize) {
            SpinnerSize.SMALL -> MeasureSpec.makeMeasureSpec(
                pixels(core.dimen.module_x4),
                MeasureSpec.EXACTLY
            )

            SpinnerSize.MEDIUM -> MeasureSpec.makeMeasureSpec(
                pixels(core.dimen.module_x6),
                MeasureSpec.EXACTLY
            )

            SpinnerSize.BIG -> MeasureSpec.makeMeasureSpec(
                pixels(core.dimen.module_x12),
                MeasureSpec.EXACTLY
            )
        }

        super.onMeasure(spec, spec)
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateProgressBarColor()
    }

    private fun parseColors(a: TypedArray) {
        progressColor = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.Spinner_admiralProgressColorNormalEnabled),
            normalDisabled = a.getColorOrNull(R.styleable.Spinner_admiralProgressColorNormalDisabled),
            pressed = a.getColorOrNull(R.styleable.Spinner_admiralProgressColorPressed),
        )
    }

    private fun invalidateProgressBarColor() {
        val colorStateList = if (isContrast) {
            colorStateList(
                enabled = progressColor?.normalEnabled
                    ?: ThemeManager.theme.palette.elementStaticWhite,
                disabled = progressColor?.normalDisabled
                    ?: ThemeManager.theme.palette.elementStaticWhite.withAlpha(),
                pressed = progressColor?.pressed ?: ThemeManager.theme.palette.elementStaticWhite
            )
        } else {
            colorStateList(
                enabled = progressColor?.normalEnabled ?: ThemeManager.theme.palette.elementAccent,
                disabled = progressColor?.normalDisabled
                    ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                pressed = progressColor?.pressed ?: ThemeManager.theme.palette.elementAccent
            )
        }
        indeterminateTintList = colorStateList
    }
}