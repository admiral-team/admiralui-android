package com.admiral.uikit.components.control

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.res.use
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.components.cell.base.CellUnit
import com.admiral.uikit.common.components.cell.base.CellUnitType
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels

class CirclePageControl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ThemeObserver, CellUnit {

    override lateinit var unitType: CellUnitType

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled, pressed.
     */
    var backgroundColors: ColorState? = null
        set(value) {
            field = value
            invalidateBackgroundColors()
            invalidateProgressColors()
        }

    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.progressBar) }
    private val icon: ImageView by lazy { findViewById<ImageView>(R.id.imageArrow) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_control_page_circle, this)
        isClickable = true
        isFocusable = true

        parseAttrs(attrs, R.styleable.CirclePageControl).use {
            backgroundColors = ColorState(
                normalEnabled = it.getColorOrNull(R.styleable.CirclePageControl_admiralBackgroundColorNormalEnabled),
                normalDisabled = it.getColorOrNull(R.styleable.CirclePageControl_admiralBackgroundColorNormalDisabled),
                pressed = it.getColorOrNull(R.styleable.CirclePageControl_admiralBackgroundColorPressed),
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

    /**
     * Unsubscribe for theme change.
     */
    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val spec = MeasureSpec.makeMeasureSpec(pixels(R.dimen.module_x18), MeasureSpec.EXACTLY)
        super.onMeasure(spec, spec)
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateBackgroundColors()
        invalidateProgressColors()
    }

    private fun invalidateBackgroundColors() {
        icon.background.setTintList(
            colorStateList(
                enabled = backgroundColors?.normalEnabled ?: ThemeManager.theme.palette.backgroundAccent,
                disabled = backgroundColors?.normalDisabled ?: ThemeManager.theme.palette.backgroundAccent.withAlpha(),
                pressed = backgroundColors?.pressed ?: ThemeManager.theme.palette.backgroundAccent
            )
        )
    }

    private fun invalidateProgressColors() {
        val progressBarDrawable = progressBar.progressDrawable as LayerDrawable
        val progressDrawable = progressBarDrawable.getDrawable(0)

        progressDrawable.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            backgroundColors?.normalEnabled ?: ThemeManager.theme.palette.backgroundAccent,
            BlendModeCompat.SRC_ATOP
        )
    }

    fun setProgress(progress: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(progress, true)
        } else {
            progressBar.progress = progress
        }
    }

    fun getProgress(): Int {
        return progressBar.progress
    }
}