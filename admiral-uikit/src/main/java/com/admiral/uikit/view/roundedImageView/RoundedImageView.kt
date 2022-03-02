package com.admiral.uikit.view.roundedImageView

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.ColorFilter
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import com.admiral.uikit.components.imageview.ImageView

class RoundedImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    private val cornerRadii: FloatArray = floatArrayOf(DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS, DEFAULT_RADIUS)
    private var backgroundDrawable: Drawable? = null
    var borderColors: ColorStateList? = ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR)
        private set
    var borderWidth = DEFAULT_BORDER_WIDTH
        private set
    private var colorFilterRounded: ColorFilter? = null
    private var colorMod = false
    private var drawableRounded: Drawable? = null
    private var hasColorFilter = false
    private var isOval = false
    private var mutateBackground = false
    private var resource = 0
    private var backgroundResource = 0
    private var scaleType: ScaleType? = null
    private var tileModeX: Shader.TileMode? = DEFAULT_TILE_MODE
    private var tileModeY: Shader.TileMode? = DEFAULT_TILE_MODE

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        invalidate()
    }

    override fun getScaleType(): ScaleType {
        return scaleType!!
    }

    override fun setScaleType(scaleType: ScaleType) {
        assert(scaleType != null)
        if (this.scaleType != scaleType) {
            this.scaleType = scaleType
            when (scaleType) {
                ScaleType.CENTER, ScaleType.CENTER_CROP, ScaleType.CENTER_INSIDE, ScaleType.FIT_CENTER, ScaleType.FIT_START, ScaleType.FIT_END, ScaleType.FIT_XY -> super.setScaleType(
                    ScaleType.FIT_XY
                )
                else -> super.setScaleType(scaleType)
            }
            updateDrawableAttrs()
            updateBackgroundDrawableAttrs(false)
            invalidate()
        }
    }

    override fun setImageDrawable(drawable: Drawable?) {
        resource = 0
        drawableRounded = RoundedDrawable.fromDrawable(drawable)
        updateDrawableAttrs()
        super.setImageDrawable(drawableRounded)
    }

    override fun setImageBitmap(bm: Bitmap) {
        resource = 0
        drawableRounded = RoundedDrawable.fromBitmap(bm)
        updateDrawableAttrs()
        super.setImageDrawable(drawableRounded)
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        if (resource != resId) {
            resource = resId
            drawableRounded = resolveResource()
            updateDrawableAttrs()
            super.setImageDrawable(drawableRounded)
        }
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        setImageDrawable(drawable)
    }

    private fun resolveResource(): Drawable? {
        val rsrc = resources ?: return null
        var d: Drawable? = null
        if (resource != 0) {
            try {
                d = rsrc.getDrawable(resource)
            } catch (e: Resources.NotFoundException) {
                Log.w(TAG, "Unable to find resource: $resource", e)
                // Don't try again.
                resource = 0
            }
        }
        return RoundedDrawable.fromDrawable(d)
    }

    override fun setBackground(background: Drawable) {
        setBackgroundDrawable(background)
    }

    override fun setBackgroundResource(@DrawableRes resId: Int) {
        if (backgroundResource != resId) {
            backgroundResource = resId
            backgroundDrawable = resolveBackgroundResource()
            setBackgroundDrawable(backgroundDrawable!!)
        }
    }

    override fun setBackgroundColor(color: Int) {
        backgroundDrawable = ColorDrawable(color)
        setBackgroundDrawable(backgroundDrawable)
    }

    private fun resolveBackgroundResource(): Drawable? {
        val rsrc = resources ?: return null
        var d: Drawable? = null
        if (backgroundResource != 0) {
            try {
                d = rsrc.getDrawable(backgroundResource)
            } catch (e: Resources.NotFoundException) {
                Log.w(TAG, "Unable to find resource: $backgroundResource", e)
                // Don't try again.
                backgroundResource = 0
            }
        }
        return RoundedDrawable.fromDrawable(d)
    }

    private fun updateDrawableAttrs() {
        updateAttrs(drawableRounded, scaleType)
    }

    private fun updateBackgroundDrawableAttrs(convert: Boolean) {
        if (mutateBackground) {
            if (convert) {
                backgroundDrawable = RoundedDrawable.fromDrawable(backgroundDrawable)
            }
            updateAttrs(backgroundDrawable, ScaleType.FIT_XY)
        }
    }

    override fun setColorFilter(cf: ColorFilter) {
        if (colorFilterRounded !== cf) {
            colorFilterRounded = cf
            hasColorFilter = true
            colorMod = true
            applyColorMod()
            invalidate()
        }
    }

    private fun applyColorMod() {
        // Only mutate and apply when modifications have occurred. This should
        // not reset the mColorMod flag, since these filters need to be
        // re-applied if the Drawable is changed.
        if (drawableRounded != null && colorMod) {
            drawableRounded = drawableRounded!!.mutate()
            if (hasColorFilter) {
                drawableRounded!!.colorFilter = colorFilterRounded
            }
            // TODO: support, eventually...
            //mDrawable.setXfermode(mXfermode);
            //mDrawable.setAlpha(mAlpha * mViewAlphaScale >> 8);
        }
    }

    private fun updateAttrs(drawable: Drawable?, scaleType: ScaleType?) {
        if (drawable == null) {
            return
        }
        if (drawable is RoundedDrawable) {
            drawable
                .setScaleType(scaleType)
                .setBorderWidth(borderWidth)
                .setBorderColor(borderColors)
                .setOval(isOval)
                .setTileModeX(tileModeX!!)
                .setTileModeY(tileModeY!!)
            if (cornerRadii != null) {
                drawable.setCornerRadius(
                    cornerRadii[Corner.TOP_LEFT],
                    cornerRadii[Corner.TOP_RIGHT],
                    cornerRadii[Corner.BOTTOM_RIGHT],
                    cornerRadii[Corner.BOTTOM_LEFT]
                )
            }
            applyColorMod()
        } else if (drawable is LayerDrawable) {
            // loop through layers to and set drawable attrs
            val ld = drawable
            var i = 0
            val layers = ld.numberOfLayers
            while (i < layers) {
                updateAttrs(ld.getDrawable(i), scaleType)
                i++
            }
        }
    }

    override fun setBackgroundDrawable(background: Drawable?) {
        backgroundDrawable = background
        updateBackgroundDrawableAttrs(true)
        super.setBackgroundDrawable(backgroundDrawable)
    }
    /**
     * @return the largest corner radius.
     */
    /**
     * Set the corner radii of all corners in px.
     *
     * @param radius the radius to set.
     */
    var cornerRadius: Float
        get() = maxCornerRadius
        set(radius) {
            setCornerRadius(radius, radius, radius, radius)
        }

    /**
     * @return the largest corner radius.
     */
    val maxCornerRadius: Float
        get() {
            var maxRadius = 0f
            for (r in cornerRadii) {
                maxRadius = Math.max(r, maxRadius)
            }
            return maxRadius
        }

    /**
     * Get the corner radius of a specified corner.
     *
     * @param corner the corner.
     * @return the radius.
     */
    fun getCornerRadius(@Corner corner: Int): Float {
        return cornerRadii[corner]
    }

    /**
     * Set all the corner radii from a dimension resource id.
     *
     * @param resId dimension resource id of radii.
     */
    fun setCornerRadiusDimen(@DimenRes resId: Int) {
        val radius = resources.getDimension(resId)
        setCornerRadius(radius, radius, radius, radius)
    }

    /**
     * Set the corner radius of a specific corner from a dimension resource id.
     *
     * @param corner the corner to set.
     * @param resId the dimension resource id of the corner radius.
     */
    fun setCornerRadiusDimen(@Corner corner: Int, @DimenRes resId: Int) {
        setCornerRadius(corner, resources.getDimensionPixelSize(resId).toFloat())
    }

    /**
     * Set the corner radius of a specific corner in px.
     *
     * @param corner the corner to set.
     * @param radius the corner radius to set in px.
     */
    fun setCornerRadius(@Corner corner: Int, radius: Float) {
        if (cornerRadii[corner] == radius) {
            return
        }
        cornerRadii[corner] = radius
        updateDrawableAttrs()
        updateBackgroundDrawableAttrs(false)
        invalidate()
    }

    /**
     * Set the corner radii of each corner individually. Currently only one unique nonzero value is
     * supported.
     *
     * @param topLeft radius of the top left corner in px.
     * @param topRight radius of the top right corner in px.
     * @param bottomRight radius of the bottom right corner in px.
     * @param bottomLeft radius of the bottom left corner in px.
     */
    @Suppress("ComplexCondition")
    fun setCornerRadius(topLeft: Float, topRight: Float, bottomLeft: Float, bottomRight: Float) {
        if (cornerRadii[Corner.TOP_LEFT] == topLeft &&
            cornerRadii[Corner.TOP_RIGHT] == topRight &&
            cornerRadii[Corner.BOTTOM_RIGHT] == bottomRight &&
            cornerRadii[Corner.BOTTOM_LEFT] == bottomLeft
        ) {
            return
        }
        cornerRadii[Corner.TOP_LEFT] = topLeft
        cornerRadii[Corner.TOP_RIGHT] = topRight
        cornerRadii[Corner.BOTTOM_LEFT] = bottomLeft
        cornerRadii[Corner.BOTTOM_RIGHT] = bottomRight
        updateDrawableAttrs()
        updateBackgroundDrawableAttrs(false)
        invalidate()
    }

    companion object {
        // Constants for tile mode attributes
        private const val TILE_MODE_UNDEFINED = -2
        private const val TILE_MODE_CLAMP = 0
        private const val TILE_MODE_REPEAT = 1
        private const val TILE_MODE_MIRROR = 2
        const val TAG = "RoundedImageView"
        const val DEFAULT_RADIUS = 0f
        const val DEFAULT_BORDER_WIDTH = 0f
        val DEFAULT_TILE_MODE = Shader.TileMode.CLAMP
        private val SCALE_TYPES = arrayOf(
            ScaleType.MATRIX,
            ScaleType.FIT_XY,
            ScaleType.FIT_START,
            ScaleType.FIT_CENTER,
            ScaleType.FIT_END,
            ScaleType.CENTER,
            ScaleType.CENTER_CROP,
            ScaleType.CENTER_INSIDE
        )

        private fun parseTileMode(tileMode: Int): Shader.TileMode? {
            return when (tileMode) {
                TILE_MODE_CLAMP -> Shader.TileMode.CLAMP
                TILE_MODE_REPEAT -> Shader.TileMode.REPEAT
                TILE_MODE_MIRROR -> Shader.TileMode.MIRROR
                else -> null
            }
        }
    }
}