package com.admiral.uikit.components.select

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.widget.ImageView
import androidx.annotation.IntRange
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.ext.animatePulse
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

/**
 * Replacement for Rating Bar
 */
class Feedback @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled, pressed.
     */
    var iconTintColors: ColorState? = null
        set(value) {
            field = value
            invalidateIconTintColors()
        }

    /**
     * In case Drawable is null, the default icon will be used.
     */
    var icon: Drawable? = null
        set(value) {
            field = value
            invalidateIcons()
        }

    /**
     * Current rating
     */
    var currentRating: Int = 0
        private set

    /**
     * If the value is true, changing [currentRating] will be animated.
     */
    var isAnimationEnabled: Boolean = true

    private val stars: List<ImageView> by lazy {
        listOf(
            findViewById(R.id.firstImageView),
            findViewById(R.id.secondImageView),
            findViewById(R.id.thirdImageView),
            findViewById(R.id.fourthImageView),
            findViewById(R.id.fifthImageView)
        )
    }
    private val clickListener = OnClickListener {
        updateRatingWithAnimation(it.tag.toString().toInt())
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_feedback, this)

        parseAttrs(attrs, R.styleable.InputNumber).use {
            parseRating(it)
            parseIcon(it)
            parseIconColors(it)

            isEnabled = it.getBoolean(R.styleable.InputNumber_enabled, true)
        }

        applyClickListeners()
        invalidateIconTintColors()
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
        stars.forEach {
            it.isEnabled = enabled
        }

        invalidateIconTintColors()
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateIconTintColors()
    }

    fun updateRatingWithAnimation(@IntRange(from = 0, to = 5) chosenRating: Int) {
        var delay = 0L

        if (currentRating < chosenRating) {
            for (i in currentRating until chosenRating) {
                changeViewsColor(stars[i], delay++, i, chosenRating)
            }
        } else {
            for (i in currentRating downTo chosenRating) {
                changeViewsColor(stars[i - 1], delay++, i, chosenRating)
            }
        }

        currentRating = chosenRating
    }

    private fun changeViewsColor(view: ImageView, delay: Long, viewRating: Int, chosenRating: Int) {
        val color = if (viewRating > chosenRating) {
            iconTintColors?.normalEnabled ?: ThemeManager.theme.palette.elementAdditional
        } else {
            if (isEnabled) {
                iconTintColors?.selectedEnabled ?: ThemeManager.theme.palette.elementAccent
            } else {
                iconTintColors?.selectedEnabled ?: ThemeManager.theme.palette.elementAccent.withAlpha()
            }
        }

        if (isAnimationEnabled) {
            view.animatePulse(startDelay = delay * ANIMATION_DELAY, color = color).apply {
                doOnEnd {
                    val colorAtTheEnd = if (viewRating > chosenRating) {
                        iconTintColors?.normalEnabled ?: ThemeManager.theme.palette.elementAdditional
                    } else {
                        if (isEnabled) {
                            iconTintColors?.selectedEnabled ?: ThemeManager.theme.palette.elementAccent
                        } else {
                            iconTintColors?.selectedEnabled ?: ThemeManager.theme.palette.elementAccent.withAlpha()
                        }
                    }
                    view.imageTintList = ColorStateList.valueOf(colorAtTheEnd)
                }
            }.start()

        } else {
            view.imageTintList = ColorStateList.valueOf(color)
        }
    }

    private fun parseRating(a: TypedArray) {
        currentRating = a.getInt(R.styleable.Feedback_admiralRating, 0)
    }

    private fun parseIcon(a: TypedArray) {
        icon = a.getDrawable(R.styleable.Feedback_admiralIcon)
    }

    private fun parseIconColors(a: TypedArray) {
        val normal = a.hasValue(R.styleable.Feedback_admiralIconTintColorNormalEnabled)
        val selected = a.hasValue(R.styleable.Feedback_admiralIconTintColorSelected)
        val disabled = a.hasValue(R.styleable.Feedback_admiralIconTintColorNormalDisabled)

        if (normal && selected && disabled) {
            iconTintColors = ColorState(
                normalEnabled = a.getColorOrNull(R.styleable.Feedback_admiralIconTintColorNormalEnabled),
                normalDisabled = a.getColorOrNull(R.styleable.Feedback_admiralIconTintColorNormalDisabled),
                selectedEnabled = a.getColorOrNull(R.styleable.Feedback_admiralIconTintColorSelected)
            )
        } else if (normal || selected || disabled) {
            throw IllegalStateException("You must declare all icon tint colors: normal, selected, disabled.")
        }
    }

    private fun applyClickListeners() {
        stars.forEach {
            it.setOnClickListener(clickListener)
        }
    }

    private fun invalidateIconTintColors() {
        stars.forEach {
            val color = if (it.tag.toString().toInt() > currentRating) {
                iconTintColors?.normalEnabled ?: ThemeManager.theme.palette.elementAdditional
            } else {
                if (isEnabled) {
                    iconTintColors?.selectedEnabled ?: ThemeManager.theme.palette.elementAccent
                } else {
                    iconTintColors?.selectedEnabled?.withAlpha() ?: ThemeManager.theme.palette.elementAccent.withAlpha()
                }
            }

            it.imageTintList = ColorStateList.valueOf(color)
        }
    }

    private fun invalidateIcons() {
        stars.forEach {
            it.setImageDrawable(icon ?: context.drawable(R.drawable.admiral_ic_star_solid))
        }
    }

    private companion object {
        private const val ANIMATION_DELAY = 40L
    }
}