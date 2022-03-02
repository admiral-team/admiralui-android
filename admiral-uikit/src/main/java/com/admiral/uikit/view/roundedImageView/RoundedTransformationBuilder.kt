package com.admiral.uikit.view.roundedImageView

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.animation.Transformation
import android.widget.ImageView.ScaleType
import java.util.Arrays

class RoundedTransformationBuilder {

    private val displayMetrics: DisplayMetrics
    private val cornerRadii = floatArrayOf(0f, 0f, 0f, 0f)
    private var oval = false
    private var borderWidth = 0f
    private var borderColor = ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR)
    private var scaleType = ScaleType.FIT_CENTER

    fun scaleType(scaleType: ScaleType): RoundedTransformationBuilder {
        this.scaleType = scaleType
        return this
    }

    /**
     * Set corner radius for all corners in px.
     *
     * @param radius the radius in px
     * @return the builder for chaining.
     */
    fun cornerRadius(radius: Float): RoundedTransformationBuilder {
        cornerRadii[Corner.TOP_LEFT] = radius
        cornerRadii[Corner.TOP_RIGHT] = radius
        cornerRadii[Corner.BOTTOM_RIGHT] = radius
        cornerRadii[Corner.BOTTOM_LEFT] = radius
        return this
    }

    /**
     * Set corner radius for a specific corner in px.
     *
     * @param corner the corner to set.
     * @param radius the radius in px.
     * @return the builder for chaning.
     */
    fun cornerRadius(@Corner corner: Int, radius: Float): RoundedTransformationBuilder {
        cornerRadii[corner] = radius
        return this
    }

    /**
     * Set corner radius for all corners in density independent pixels.
     *
     * @param radius the radius in density independent pixels.
     * @return the builder for chaining.
     */
    fun cornerRadiusDp(radius: Float): RoundedTransformationBuilder {
        return cornerRadius(
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, displayMetrics)
        )
    }

    /**
     * Set corner radius for a specific corner in density independent pixels.
     *
     * @param corner the corner to set
     * @param radius the radius in density independent pixels.
     * @return the builder for chaining.
     */
    fun cornerRadiusDp(@Corner corner: Int, radius: Float): RoundedTransformationBuilder {
        return cornerRadius(
            corner,
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, displayMetrics)
        )
    }

    /**
     * Set the border width in pixels.
     *
     * @param width border width in pixels.
     * @return the builder for chaining.
     */
    fun borderWidth(width: Float): RoundedTransformationBuilder {
        borderWidth = width
        return this
    }

    /**
     * Set the border width in density independent pixels.
     *
     * @param width border width in density independent pixels.
     * @return the builder for chaining.
     */
    fun borderWidthDp(width: Float): RoundedTransformationBuilder {
        borderWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, displayMetrics)
        return this
    }

    /**
     * Set the border color.
     *
     * @param color the color to set.
     * @return the builder for chaining.
     */
    fun borderColor(color: Int): RoundedTransformationBuilder {
        borderColor = ColorStateList.valueOf(color)
        return this
    }

    /**
     * Set the border color as a [ColorStateList].
     *
     * @param colors the [ColorStateList] to set.
     * @return the builder for chaining.
     */
    fun borderColor(colors: ColorStateList): RoundedTransformationBuilder {
        borderColor = colors
        return this
    }

    /**
     * Sets whether the image should be oval or not.
     *
     * @param oval if the image should be oval.
     * @return the builder for chaining.
     */
    fun oval(oval: Boolean): RoundedTransformationBuilder {
        this.oval = oval
        return this
    }

    /**
     * Creates a [Transformation] for use with picasso.
     *
     * @return the [Transformation]
     */
    fun build(): Transformation {
        return object : Transformation() {
            fun transform(source: Bitmap): Bitmap? {
                val transformed = RoundedDrawable.fromBitmap(source)
                    ?.setScaleType(scaleType)
                    ?.setCornerRadius(
                        cornerRadii[TOP_LEFT],
                        cornerRadii[TOP_RIGHT],
                        cornerRadii[BOTTOM_RIGHT],
                        cornerRadii[BOTTOM_LEFT]
                    )
                    ?.setBorderWidth(borderWidth)
                    ?.setBorderColor(borderColor)
                    ?.setOval(oval)
                    ?.toBitmap()
                if (source != transformed) {
                    source.recycle()
                }
                return transformed
            }

            fun key(): String {
                return ("r:" + Arrays.toString(cornerRadii)
                        + "b:" + borderWidth
                        + "c:" + borderColor
                        + "o:" + oval)
            }
        }
    }

    init {
        displayMetrics = Resources.getSystem().displayMetrics
    }

    private companion object {
        const val TOP_LEFT = 0
        const val TOP_RIGHT = 1
        const val BOTTOM_RIGHT = 2
        const val BOTTOM_LEFT = 3
    }
}