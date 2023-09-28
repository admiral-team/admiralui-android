package com.admiral.uikit.components.stepper

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.core.components.cell.base.CellUnit
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.core.foundation.ColorStateStepper
import com.admiral.uikit.components.links.Link
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

class Stepper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver, CellUnit {

    override var unitType: CellUnitType = CellUnitType.LEADING

    /**
     * Top text.
     * Gone if text is null or empty.
     */
    var titleText: String? = null
        set(value) {
            field = value
            titleTextView.text = value
            titleTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Middle text.
     * Gone if text is null or empty.
     */
    var subtitleText: String? = null
        set(value) {
            field = value
            subtitleTextView.text = value
            subtitleTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Bottom text.
     * Gone if text is null or empty.
     */
    var linkText: String? = null
        set(value) {
            field = value
            linkTextView.text = value
            linkTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Color for the title.
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorRes
    var titleTextColor: Int? = null
        set(value) {
            field = value
            invalidateTitleColors()
        }

    /**
     * Color for the subtitle.
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorRes
    var subtitleTextColor: Int? = null
        set(value) {
            field = value
            invalidateSubtitleColors()
        }

    /**
     * Color for the link.
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorRes
    var linkTextColor: Int? = null
        set(value) {
            field = value
            invalidateLinkColors()
        }

    /**
     * Colors for line element.
     * States: done, finished, next, error, disabled.
     * In case state is null, the selected color theme will be used.
     */
    var lineColorState: ColorStateStepper? = null
        set(value) {
            field = value
            invalidateLineColorsTint()
        }

    /**
     * Colors for outer circle element.
     * States: done, finished, next, error, disabled.
     * In case state is null, the selected color theme will be used.
     */
    var circleOuterColorState: ColorStateStepper? = null
        set(value) {
            field = value
            invalidateCircleOuterColorsTint()
        }

    /**
     * Colors for inner circle element.
     * States: done, finished, next, error, disabled.
     * In case state is null, the selected color theme will be used.
     */
    var circleInnerColorState: ColorStateStepper? = null
        set(value) {
            field = value
            invalidateCircleInnerColorsTint()
        }

    var stepperStyle: StepperStyle = StepperStyle.Done
        set(value) {
            field = value
            invalidateColors()
        }

    var isStepFirst: Boolean = false
        set(value) {
            field = value
            invalidateTopView()
        }

    private val titleTextView: TextView by lazy { findViewById(R.id.textViewTitle) }
    private val subtitleTextView: TextView by lazy { findViewById(R.id.textViewSubtitle) }
    private val linkTextView: Link by lazy { findViewById(R.id.textViewLink) }

    private val viewLine: View by lazy { findViewById(R.id.viewLine) }
    private val viewLineTop: View by lazy { findViewById(R.id.viewLineTop) }
    private val viewCircleOuter: View by lazy { findViewById(R.id.viewCircleOuter) }
    private val viewCircleInner: View by lazy { findViewById(R.id.viewCircleInner) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_steper, this)

        parseAttrs(attrs, R.styleable.Stepper).use {
            titleText = it.getString(R.styleable.Stepper_admiralTitleText)
            subtitleText = it.getString(R.styleable.Stepper_admiralSubtitleText)
            linkText = it.getString(R.styleable.Stepper_admiralLinkText)

            titleTextColor = it.getColorOrNull(R.styleable.Stepper_admiralTitleTextColor)
            subtitleTextColor = it.getColorOrNull(R.styleable.Stepper_admiralSubtitleTextColor)
            linkTextColor = it.getColorOrNull(R.styleable.Stepper_admiralLinkTextColor)

            stepperStyle = StepperStyle.fromIndex(it.getInt(R.styleable.Stepper_admiralStepperStyle, 0))
            isStepFirst = it.getBoolean(R.styleable.Stepper_admiralIsStepFirst, false)

            parseLineColors(it)
            parseCircleOuterColors(it)
            parseCircleInnerColors(it)
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
        invalidateColors()
    }

    private fun invalidateColors() {
        invalidateTitleColors()
        invalidateSubtitleColors()
        invalidateLinkColors()

        invalidateLineColorsTint()
        invalidateCircleOuterColorsTint()
        invalidateCircleInnerColorsTint()
    }

    private fun parseCircleInnerColors(typedArray: TypedArray) {
        circleInnerColorState = ColorStateStepper(
            done = typedArray.getColorOrNull(R.styleable.Stepper_admiralCircleInnerColorDone),
            current = typedArray.getColorOrNull(R.styleable.Stepper_admiralCircleInnerColorCurrent),
            next = typedArray.getColorOrNull(R.styleable.Stepper_admiralCircleInnerColorNext),
            error = typedArray.getColorOrNull(R.styleable.Stepper_admiralCircleInnerColorError),
            disabled = typedArray.getColorOrNull(R.styleable.Stepper_admiralCircleInnerColorDisabled)
        )
    }

    private fun parseCircleOuterColors(typedArray: TypedArray) {
        circleOuterColorState = ColorStateStepper(
            done = typedArray.getColorOrNull(R.styleable.Stepper_admiralCircleOuterColorDone),
            current = typedArray.getColorOrNull(R.styleable.Stepper_admiralCircleOuterColorCurrent),
            next = typedArray.getColorOrNull(R.styleable.Stepper_admiralCircleOuterColorNext),
            error = typedArray.getColorOrNull(R.styleable.Stepper_admiralCircleOuterColorError),
            disabled = typedArray.getColorOrNull(R.styleable.Stepper_admiralCircleOuterColorDisabled)
        )
    }

    private fun parseLineColors(typedArray: TypedArray) {
        lineColorState = ColorStateStepper(
            done = typedArray.getColorOrNull(R.styleable.Stepper_admiralLineColorDone),
            current = typedArray.getColorOrNull(R.styleable.Stepper_admiralLineColorCurrent),
            next = typedArray.getColorOrNull(R.styleable.Stepper_admiralLineColorNext),
            error = typedArray.getColorOrNull(R.styleable.Stepper_admiralLineColorError),
            disabled = typedArray.getColorOrNull(R.styleable.Stepper_admiralLineColorDisabled)
        )
    }

    private fun invalidateTopView() {
        viewLineTop.isVisible = isStepFirst
    }

    private fun invalidateTitleColors() {
        val color = titleTextColor ?: ThemeManager.theme.palette.textPrimary

        titleTextView.textColor = ColorState(color)
    }

    private fun invalidateSubtitleColors() {
        val color = subtitleTextColor ?: ThemeManager.theme.palette.textSecondary

        subtitleTextView.textColor = ColorState(color)
    }

    private fun invalidateLinkColors() {
        val color = linkTextColor ?: ThemeManager.theme.palette.textAccent

        linkTextView.textColorState = ColorState(color)
    }

    private fun invalidateLineColorsTint() {
        val colorState = when (stepperStyle) {
            StepperStyle.Done -> {
                lineColorState?.done ?: ThemeManager.theme.palette.elementAccent
            }
            StepperStyle.Current -> {
                lineColorState?.current ?: ThemeManager.theme.palette.elementAccent
            }
            StepperStyle.Next -> {
                lineColorState?.next ?: ThemeManager.theme.palette.elementPrimary
            }
            StepperStyle.Error -> {
                lineColorState?.error ?: ThemeManager.theme.palette.elementError
            }
            StepperStyle.Disabled -> {
                lineColorState?.disabled ?: ThemeManager.theme.palette.elementAdditional
            }
        }

        viewLine.setBackgroundColor(colorState)
    }

    private fun invalidateCircleOuterColorsTint() {
        val colorState = getColorStateForCircleOuter()

        viewLineTop.backgroundTintList = colorState
        viewCircleOuter.backgroundTintList = colorState
    }

    private fun getColorStateForCircleOuter(): ColorStateList {
        return when (stepperStyle) {
            StepperStyle.Done -> {
                colorStateList(
                    enabled = circleOuterColorState?.done ?: ThemeManager.theme.palette.elementAccent,
                    disabled = circleOuterColorState?.done ?: ThemeManager.theme.palette.elementAccent,
                    pressed = circleOuterColorState?.done ?: ThemeManager.theme.palette.elementAccent
                )
            }
            StepperStyle.Current -> {
                colorStateList(
                    enabled = circleOuterColorState?.current ?: ThemeManager.theme.palette.elementAccent,
                    disabled = circleOuterColorState?.current ?: ThemeManager.theme.palette.elementAccent,
                    pressed = circleOuterColorState?.current ?: ThemeManager.theme.palette.elementAccent
                )
            }
            StepperStyle.Next -> {
                colorStateList(
                    enabled = circleOuterColorState?.next ?: ThemeManager.theme.palette.elementPrimary,
                    disabled = circleOuterColorState?.next ?: ThemeManager.theme.palette.elementPrimary,
                    pressed = circleOuterColorState?.next ?: ThemeManager.theme.palette.elementPrimary
                )
            }
            StepperStyle.Error -> {
                colorStateList(
                    enabled = circleOuterColorState?.error ?: ThemeManager.theme.palette.elementError,
                    disabled = circleOuterColorState?.error ?: ThemeManager.theme.palette.elementError,
                    pressed = circleOuterColorState?.error ?: ThemeManager.theme.palette.elementError
                )
            }
            StepperStyle.Disabled -> {
                colorStateList(
                    enabled = circleOuterColorState?.disabled ?: ThemeManager.theme.palette.elementAdditional,
                    disabled = circleOuterColorState?.disabled ?: ThemeManager.theme.palette.elementAdditional,
                    pressed = circleOuterColorState?.disabled ?: ThemeManager.theme.palette.elementAdditional,
                )
            }
        }
    }

    private fun invalidateCircleInnerColorsTint() {
        val colorState = getColorStateForCircleInner()

        viewCircleInner.backgroundTintList = colorState
    }

    private fun getColorStateForCircleInner(): ColorStateList {
        return when (stepperStyle) {
            StepperStyle.Done -> {
                colorStateList(
                    enabled = circleInnerColorState?.done ?: ThemeManager.theme.palette.elementAccent,
                    disabled = circleInnerColorState?.done ?: ThemeManager.theme.palette.elementAccent,
                    pressed = circleInnerColorState?.done ?: ThemeManager.theme.palette.elementAccent
                )
            }
            StepperStyle.Current -> {
                colorStateList(
                    enabled = circleInnerColorState?.current ?: ThemeManager.theme.palette.elementStaticWhite,
                    disabled = circleInnerColorState?.current ?: ThemeManager.theme.palette.elementStaticWhite,
                    pressed = circleInnerColorState?.current ?: ThemeManager.theme.palette.elementStaticWhite
                )
            }
            StepperStyle.Next -> {
                colorStateList(
                    enabled = circleInnerColorState?.next ?: ThemeManager.theme.palette.elementPrimary,
                    disabled = circleInnerColorState?.next ?: ThemeManager.theme.palette.elementPrimary,
                    pressed = circleInnerColorState?.next ?: ThemeManager.theme.palette.elementPrimary
                )
            }
            StepperStyle.Error -> {
                colorStateList(
                    enabled = circleInnerColorState?.error ?: ThemeManager.theme.palette.elementError,
                    disabled = circleInnerColorState?.error ?: ThemeManager.theme.palette.elementError,
                    pressed = circleInnerColorState?.error ?: ThemeManager.theme.palette.elementError
                )
            }
            StepperStyle.Disabled -> {
                colorStateList(
                    enabled = circleInnerColorState?.disabled ?: ThemeManager.theme.palette.elementAdditional,
                    disabled = circleInnerColorState?.disabled ?: ThemeManager.theme.palette.elementAdditional,
                    pressed = circleInnerColorState?.disabled ?: ThemeManager.theme.palette.elementAdditional
                )
            }
        }
    }
}
