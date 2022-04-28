package com.admiral.uikit.components.control

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ProgressBar
import androidx.core.content.res.use
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.admiral.themes.ColorPaletteEnum
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.components.imageview.ImageView
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.coloredDrawable
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.ext.ripple
import com.admiral.uikit.layout.FrameLayout

class CirclePageControl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * If the value is true, changing [setProgress] will be animated.
     */
    var isAnimationEnabled: Boolean = true

    /**
     * Handles color of the icon and progress bar.
     */
    var isContrast: Boolean = false
        set(value) {
            field = value
            invalidateBackgroundColor()
            invalidateProgressColors()
            invalidateIconColors()
        }

    private val progressBar: ProgressBar by lazy { findViewById(R.id.progressBar) }
    private val icon: ImageView by lazy { findViewById(R.id.imageArrow) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_control_page_circle, this)
        isBackgroundTransparent = true
        isClickable = true
        isFocusable = true

        parseAttrs(attrs, R.styleable.CirclePageControl).use {
            isAnimationEnabled = it.getBoolean(R.styleable.CirclePageControl_admiralIsAnimationEnabled, true)
            setProgress(it.getInt(R.styleable.CirclePageControl_admiralProgress, 0))
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
        invalidateProgressColors()
        invalidateBackgroundColor()
        invalidateIconColors()
    }

    fun setProgress(progress: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(progress, isAnimationEnabled)
        } else {
            progressBar.progress = progress
        }
    }

    fun getProgress(): Int {
        return progressBar.progress
    }

    private fun invalidateProgressColors() {
        val progressBarDrawable = progressBar.progressDrawable as LayerDrawable
        val progressDrawable = progressBarDrawable.getDrawable(0)

        if (isContrast) {
            progressDrawable.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                backgroundColors?.normalEnabled ?: ThemeManager.theme.palette.elementStaticWhite,
                BlendModeCompat.SRC_ATOP
            )
        } else {
            progressDrawable.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                backgroundColors?.normalEnabled ?: ThemeManager.theme.palette.backgroundAccent,
                BlendModeCompat.SRC_ATOP
            )
        }
    }

    private fun invalidateBackgroundColor() {
        if (isContrast) {
            val rippleColor = ThemeManager.theme.palette.elementAccent

            val mask = context.drawable(R.drawable.admiral_bg_circle_mask)
            val contentStateList = colorStateList(
                enabled = ThemeManager.theme.palette.elementStaticWhite,
                disabled = ThemeManager.theme.palette.elementStaticWhite.withAlpha(),
                pressed = ThemeManager.theme.palette.elementStaticWhite
            )
            val content = context.coloredDrawable(R.drawable.admiral_bg_circle_mask, contentStateList)


            icon.background = ripple(rippleColor, content, mask)
        } else {
            val rippleColor = ThemeManager.theme.palette.elementStaticWhite

            val mask = context.drawable(R.drawable.admiral_bg_circle_mask)
            val contentStateList = colorStateList(
                enabled = ThemeManager.theme.palette.elementAccent,
                disabled = ThemeManager.theme.palette.elementAccent.withAlpha(),
                pressed = ThemeManager.theme.palette.elementAccent
            )
            val content = context.coloredDrawable(R.drawable.admiral_bg_circle_mask, contentStateList)


            icon.background = ripple(rippleColor, content, mask)
        }
    }

    private fun invalidateIconColors() {
        if (isContrast) {
            icon.imageColorNormalEnabledPalette = ColorPaletteEnum.ELEMENT_ACCENT
        } else {
            icon.imageColorNormalEnabledPalette = ColorPaletteEnum.ELEMENT_STATIC_WHITE
        }
    }
}