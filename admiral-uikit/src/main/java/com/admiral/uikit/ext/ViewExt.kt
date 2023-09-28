package com.admiral.uikit.ext

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Outline
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.core.view.doOnLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.core.util.ComponentsRadius
import kotlin.math.min

@ColorInt
fun View.color(@ColorRes colorRes: Int): Int = context.color(colorRes)

fun View.pixels(@DimenRes dimenRes: Int): Int = context.pixels(dimenRes)

internal fun View.drawable(@DrawableRes drawableRes: Int): Drawable? = context.drawable(drawableRes)

internal fun View.createRoundedRectangleDrawable(radius: ComponentsRadius): Drawable =
    context.createRoundedRectangleDrawable(radius)

internal fun View.createRoundedRectangleDrawable(
    topLeft: ComponentsRadius,
    topRight: ComponentsRadius,
    bottomLeft: ComponentsRadius,
    bottomRight: ComponentsRadius
): Drawable = context.createRoundedRectangleDrawable(topLeft, topRight, bottomLeft, bottomRight)

internal fun View.createRoundedColoredRectangleDrawable(
    radius: ComponentsRadius,
    colorState: ColorState
): Drawable =
    context.createRoundedColoredRectangleDrawable(radius, colorState)

internal fun View.createRoundedColoredStrokeDrawable(
    radius: ComponentsRadius,
    colorState: ColorState
): Drawable =
    context.createRoundedColoredStrokeDrawable(radius, colorState)

internal fun View.roundTopCorners(@Px cornerRadius: Float) =
    roundCertainCorners(cornerRadius) { view, outline, radius ->
        outline.setRoundRect(0, 0, view.width, (view.height + radius).toInt(), radius)
    }

internal fun View.roundCertainCorners(
    @Px cornerRadius: Float,
    outlineRoundRectAction: (view: View, outline: Outline, radius: Float) -> Unit,
) {
    doOnLayout {
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                val correctRadius = getCorrectRadius(cornerRadius)
                outlineRoundRectAction.invoke(view, outline, correctRadius)
            }
        }
    }
    clipToOutline = true
}

internal fun View.getCorrectRadius(@Px cornerRadius: Float): Float {
    /* Исправляет ошибку с чрезмерным скруглением на Android API 26 */
    val maxRadius = min(height, width) / 2f
    return if (maxRadius < cornerRadius) maxRadius else cornerRadius
}

internal fun View.parseAttrs(
    set: AttributeSet?,
    @StyleableRes attrs: IntArray,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
): TypedArray {
    return context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes)
}

internal fun View.colorStateList(
    @ColorInt enabled: Int,
    @ColorInt disabled: Int? = null,
    @ColorInt pressed: Int? = null
): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            pressed ?: enabled,
            disabled ?: enabled,
            enabled
        )
    )
}

internal fun View.colorStateListForChecked(
    @ColorInt normalEnabled: Int,
    @ColorInt normalDisabled: Int,
    @ColorInt checkedEnabled: Int,
    @ColorInt checkedDisabled: Int
): ColorStateList {

    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_checked, -android.R.attr.state_enabled),
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked, -android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_checked),
        ),
        intArrayOf(
            checkedDisabled,
            checkedEnabled,
            normalDisabled,
            normalEnabled,
        )
    )
}

@Suppress("LongParameterList")
internal fun View.colorStateListUnion(
    @ColorInt normalEnabled: Int,
    @ColorInt normalDisabled: Int = 0,
    @ColorInt checkedEnabled: Int = 0,
    @ColorInt checkedDisabled: Int = 0,
    @ColorInt selectedEnabled: Int = 0,
    @ColorInt selectedDisabled: Int = 0,
    @ColorInt pressed: Int = 0
): ColorStateList {

    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_pressed), // pressed
            intArrayOf(
                android.R.attr.state_checked,
                android.R.attr.state_enabled
            ), // checked enabled
            intArrayOf(
                android.R.attr.state_checked,
                -android.R.attr.state_enabled
            ), // checked disabled
            intArrayOf(
                android.R.attr.state_selected,
                android.R.attr.state_enabled
            ), // selected enabled
            intArrayOf(
                android.R.attr.state_selected,
                -android.R.attr.state_enabled
            ), // selected disabled
            intArrayOf(
                -android.R.attr.state_selected,
                -android.R.attr.state_checked,
                android.R.attr.state_enabled
            ), // normal enabled
            intArrayOf(
                -android.R.attr.state_selected,
                -android.R.attr.state_checked,
                -android.R.attr.state_enabled
            ), // normal disabled
        ),
        intArrayOf(
            pressed,
            checkedEnabled,
            checkedDisabled,
            selectedEnabled,
            selectedDisabled,
            normalEnabled,
            normalDisabled,
        )
    )
}

internal fun View.colorStateListForSelected(
    @ColorInt normalEnabled: Int = 0,
    @ColorInt normalDisabled: Int = 0,
    @ColorInt selectedEnabled: Int = 0,
    @ColorInt selectedDisabled: Int = 0
): ColorStateList {

    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_selected, -android.R.attr.state_enabled),
            intArrayOf(android.R.attr.state_selected),
            intArrayOf(-android.R.attr.state_selected, -android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_selected),
        ),
        intArrayOf(
            selectedDisabled,
            selectedEnabled,
            normalDisabled,
            normalEnabled,
        )
    )
}

internal fun View.emptyColorStateList(): ColorStateList {
    return ColorStateList(arrayOf(), intArrayOf())
}

internal fun View.ripple(
    @ColorInt color: Int,
    content: Drawable?,
    mask: Drawable?
): RippleDrawable {
    val stateList = ColorStateList(arrayOf(intArrayOf()), intArrayOf(color))

    return RippleDrawable(stateList, content, mask)
}

@SuppressLint("RestrictedApi")
internal fun View.animateBackgroundColor(
    @ColorInt vararg colors: Int,
    duration: Long = 250
): Animator {
    val argbEvaluator = ArgbEvaluator.getInstance()

    return ObjectAnimator.ofInt(*colors).apply {
        addUpdateListener {
            setBackgroundColor(it.animatedValue as Int)
        }
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationCancel(animation: Animator?) {
                setBackgroundColor(colors.last())
            }
        })
        setEvaluator(argbEvaluator)
        setDuration(duration)
    }
}

@SuppressWarnings("MagicNumber")
internal fun ImageView.animatePulse(
    decreaseRatio: Float = 0.9f,
    increaseRatio: Float = 1.5f,
    duration: Long = 544L,
    startDelay: Long = 0,
    @ColorInt color: Int? = null
): ObjectAnimator {
    val k0 = Keyframe.ofFloat(0f, 1f)
    val k1 = Keyframe.ofFloat(0.275f, decreaseRatio)
    val k2 = Keyframe.ofFloat(0.69f, increaseRatio)
    val k3 = Keyframe.ofFloat(1f, 1f)

    val scaleX = PropertyValuesHolder.ofKeyframe("scaleX", k0, k1, k2, k3)
    val scaleY = PropertyValuesHolder.ofKeyframe("scaleY", k0, k1, k2, k3)

    val update = UpdateListener(this, color)

    return ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY).apply {
        this.duration = duration
        this.startDelay = startDelay
        addUpdateListener(update)
    }
}

@SuppressWarnings("MagicNumber")
internal class UpdateListener(
    private val view: ImageView,
    @ColorInt private val color: Int? = null
) : ValueAnimator.AnimatorUpdateListener {
    private var count = 0

    override fun onAnimationUpdate(animation: ValueAnimator) {
        if (count < 15) {
            count++
        } else if (color != null) {
            view.imageTintList = ColorStateList.valueOf(color)
        }
    }
}

internal fun View.doOnPreDrawOnce(action: () -> Unit) {
    if (viewTreeObserver.isAlive) {
        viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                if (viewTreeObserver.isAlive) viewTreeObserver.removeOnPreDrawListener(this)
                action()
                return true
            }
        })
    }
}

internal fun View.setMargins(
    top: Int = this.marginTop,
    right: Int = this.marginEnd,
    bottom: Int = this.marginBottom,
    left: Int = this.marginStart
) {
    val params = this.layoutParams
    if (params != null) {
        if (params is ViewGroup.MarginLayoutParams) {
            this.layoutParams = params.apply {
                this.topMargin = top.dpToPx(context)
                this.marginEnd = right.dpToPx(context)
                this.bottomMargin = bottom.dpToPx(context)
                this.marginStart = left.dpToPx(context)
            }
        }
    } else {
        this.layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            this.topMargin = top.dpToPx(context)
            this.marginEnd = right.dpToPx(context)
            this.bottomMargin = bottom.dpToPx(context)
            this.marginStart = left.dpToPx(context)
        }
    }
}

internal fun View.getVerticalMargins(): Int {
    val marginParams = layoutParams as? ViewGroup.MarginLayoutParams
    return marginParams?.topMargin.orZero() + marginParams?.bottomMargin.orZero()
}