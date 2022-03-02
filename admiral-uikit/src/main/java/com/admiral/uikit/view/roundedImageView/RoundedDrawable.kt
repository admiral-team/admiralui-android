package com.admiral.uikit.view.roundedImageView

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Rect
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.BitmapShader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.Log
import android.widget.ImageView.ScaleType
import androidx.annotation.ColorInt
import java.lang.Float

class RoundedDrawable(val sourceBitmap: Bitmap) : Drawable() {

    private val bounds = RectF()
    private val drawableRect = RectF()
    private val bitmapRect = RectF()
    private val bitmapPaint: Paint
    private val bitmapWidth: Int
    private val bitmapHeight: Int
    private val borderRect = RectF()
    private val borderPaint: Paint
    private val shaderMatrix = Matrix()
    private val squareCornersRect = RectF()
    var tileModeX = Shader.TileMode.CLAMP
        private set
    var tileModeY = Shader.TileMode.CLAMP
        private set
    private var rebuildShader = true

    /**
     * @return the corner radius.
     */
    var cornerRadius = 0f
        private set

    // [ topLeft, topRight, bottomLeft, bottomRight ]
    private val mCornersRounded = booleanArrayOf(true, true, true, true)
    var isOval = false
        private set
    var borderWidth = 0f
        private set
    var borderColors = ColorStateList.valueOf(DEFAULT_BORDER_COLOR)
        private set
    var scaleType = ScaleType.FIT_CENTER
        private set

    override fun isStateful(): Boolean {
        return borderColors.isStateful
    }

    override fun onStateChange(state: IntArray): Boolean {
        val newColor = borderColors.getColorForState(state, 0)
        return if (borderPaint.color != newColor) {
            borderPaint.color = newColor
            true
        } else {
            super.onStateChange(state)
        }
    }

    @Suppress("LongMethod", "MagicNumber")
    private fun updateShaderMatrix() {
        val scale: kotlin.Float
        var dx: kotlin.Float
        var dy: kotlin.Float
        when (scaleType) {
            ScaleType.CENTER -> {
                borderRect.set(bounds)
                borderRect.inset(borderWidth / 2, borderWidth / 2)
                shaderMatrix.reset()
                shaderMatrix.setTranslate(
                    ((borderRect.width() - bitmapWidth) * 0.5f + 0.5f),
                    ((borderRect.height() - bitmapHeight) * 0.5f + 0.5f)
                )
            }
            ScaleType.CENTER_CROP -> {
                borderRect.set(bounds)
                borderRect.inset(borderWidth / 2, borderWidth / 2)
                shaderMatrix.reset()
                dx = 0f
                dy = 0f
                if (bitmapWidth * borderRect.height() > borderRect.width() * bitmapHeight) {
                    scale = borderRect.height() / bitmapHeight.toFloat()
                    dx = (borderRect.width() - bitmapWidth * scale) * 0.5f
                } else {
                    scale = borderRect.width() / bitmapWidth.toFloat()
                    dy = (borderRect.height() - bitmapHeight * scale) * 0.5f
                }
                shaderMatrix.setScale(scale, scale)
                shaderMatrix.postTranslate(
                    (dx + 0.5f).toInt() + borderWidth / 2,
                    (dy + 0.5f).toInt() + borderWidth / 2
                )
            }
            ScaleType.CENTER_INSIDE -> {
                shaderMatrix.reset()
                scale = if (bitmapWidth <= bounds.width() && bitmapHeight <= bounds.height()) {
                    1.0f
                } else {
                    Math.min(
                        bounds.width() / bitmapWidth.toFloat(),
                        bounds.height() / bitmapHeight.toFloat()
                    )
                }
                dx = ((bounds.width() - bitmapWidth * scale) * 0.5f + 0.5f)
                dy = ((bounds.height() - bitmapHeight * scale) * 0.5f + 0.5f)
                shaderMatrix.setScale(scale, scale)
                shaderMatrix.postTranslate(dx, dy)
                borderRect.set(bitmapRect)
                shaderMatrix.mapRect(borderRect)
                borderRect.inset(borderWidth / 2, borderWidth / 2)
                shaderMatrix.setRectToRect(bitmapRect, borderRect, Matrix.ScaleToFit.FILL)
            }
            ScaleType.FIT_CENTER -> {
                borderRect.set(bitmapRect)
                shaderMatrix.setRectToRect(bitmapRect, bounds, Matrix.ScaleToFit.CENTER)
                shaderMatrix.mapRect(borderRect)
                borderRect.inset(borderWidth / 2, borderWidth / 2)
                shaderMatrix.setRectToRect(bitmapRect, borderRect, Matrix.ScaleToFit.FILL)
            }
            ScaleType.FIT_END -> {
                borderRect.set(bitmapRect)
                shaderMatrix.setRectToRect(bitmapRect, bounds, Matrix.ScaleToFit.END)
                shaderMatrix.mapRect(borderRect)
                borderRect.inset(borderWidth / 2, borderWidth / 2)
                shaderMatrix.setRectToRect(bitmapRect, borderRect, Matrix.ScaleToFit.FILL)
            }
            ScaleType.FIT_START -> {
                borderRect.set(bitmapRect)
                shaderMatrix.setRectToRect(bitmapRect, bounds, Matrix.ScaleToFit.START)
                shaderMatrix.mapRect(borderRect)
                borderRect.inset(borderWidth / 2, borderWidth / 2)
                shaderMatrix.setRectToRect(bitmapRect, borderRect, Matrix.ScaleToFit.FILL)
            }
            ScaleType.FIT_XY -> {
                borderRect.set(bounds)
                borderRect.inset(borderWidth / 2, borderWidth / 2)
                shaderMatrix.reset()
                shaderMatrix.setRectToRect(bitmapRect, borderRect, Matrix.ScaleToFit.FILL)
            }
            else -> {
                borderRect.set(bitmapRect)
                shaderMatrix.setRectToRect(bitmapRect, bounds, Matrix.ScaleToFit.CENTER)
                shaderMatrix.mapRect(borderRect)
                borderRect.inset(borderWidth / 2, borderWidth / 2)
                shaderMatrix.setRectToRect(bitmapRect, borderRect, Matrix.ScaleToFit.FILL)
            }
        }
        drawableRect.set(borderRect)
        rebuildShader = true
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        this.bounds.set(bounds)
        updateShaderMatrix()
    }

    override fun draw(canvas: Canvas) {
        if (rebuildShader) {
            val bitmapShader = BitmapShader(sourceBitmap, tileModeX, tileModeY)
            if (tileModeX == Shader.TileMode.CLAMP && tileModeY == Shader.TileMode.CLAMP) {
                bitmapShader.setLocalMatrix(shaderMatrix)
            }
            bitmapPaint.shader = bitmapShader
            rebuildShader = false
        }
        if (isOval) {
            if (borderWidth > 0) {
                canvas.drawOval(drawableRect, bitmapPaint)
                canvas.drawOval(borderRect, borderPaint)
            } else {
                canvas.drawOval(drawableRect, bitmapPaint)
            }
        } else {
            if (any(mCornersRounded)) {
                val radius = cornerRadius
                if (borderWidth > 0) {
                    canvas.drawRoundRect(drawableRect, radius, radius, bitmapPaint)
                    canvas.drawRoundRect(borderRect, radius, radius, borderPaint)
                    redrawBitmapForSquareCorners(canvas)
                    redrawBorderForSquareCorners(canvas)
                } else {
                    canvas.drawRoundRect(drawableRect, radius, radius, bitmapPaint)
                    redrawBitmapForSquareCorners(canvas)
                }
            } else {
                canvas.drawRect(drawableRect, bitmapPaint)
                if (borderWidth > 0) {
                    canvas.drawRect(borderRect, borderPaint)
                }
            }
        }
    }

    private fun redrawBitmapForSquareCorners(canvas: Canvas) {
        if (all(mCornersRounded)) {
            // no square corners
            return
        }
        if (cornerRadius == 0f) {
            return  // no round corners
        }
        val left = drawableRect.left
        val top = drawableRect.top
        val right = left + drawableRect.width()
        val bottom = top + drawableRect.height()
        val radius = cornerRadius
        if (!mCornersRounded[Corner.TOP_LEFT]) {
            squareCornersRect[left, top, left + radius] = top + radius
            canvas.drawRect(squareCornersRect, bitmapPaint)
        }
        if (!mCornersRounded[Corner.TOP_RIGHT]) {
            squareCornersRect[right - radius, top, right] = radius
            canvas.drawRect(squareCornersRect, bitmapPaint)
        }
        if (!mCornersRounded[Corner.BOTTOM_RIGHT]) {
            squareCornersRect[right - radius, bottom - radius, right] = bottom
            canvas.drawRect(squareCornersRect, bitmapPaint)
        }
        if (!mCornersRounded[Corner.BOTTOM_LEFT]) {
            squareCornersRect[left, bottom - radius, left + radius] = bottom
            canvas.drawRect(squareCornersRect, bitmapPaint)
        }
    }

    private fun redrawBorderForSquareCorners(canvas: Canvas) {
        if (all(mCornersRounded)) {
            // no square corners
            return
        }
        if (cornerRadius == 0f) {
            return  // no round corners
        }
        val left = drawableRect.left
        val top = drawableRect.top
        val right = left + drawableRect.width()
        val bottom = top + drawableRect.height()
        val radius = cornerRadius
        val offset = borderWidth / 2
        if (!mCornersRounded[Corner.TOP_LEFT]) {
            canvas.drawLine(left - offset, top, left + radius, top, borderPaint)
            canvas.drawLine(left, top - offset, left, top + radius, borderPaint)
        }
        if (!mCornersRounded[Corner.TOP_RIGHT]) {
            canvas.drawLine(right - radius - offset, top, right, top, borderPaint)
            canvas.drawLine(right, top - offset, right, top + radius, borderPaint)
        }
        if (!mCornersRounded[Corner.BOTTOM_RIGHT]) {
            canvas.drawLine(right - radius - offset, bottom, right + offset, bottom, borderPaint)
            canvas.drawLine(right, bottom - radius, right, bottom, borderPaint)
        }
        if (!mCornersRounded[Corner.BOTTOM_LEFT]) {
            canvas.drawLine(left - offset, bottom, left + radius, bottom, borderPaint)
            canvas.drawLine(left, bottom - radius, left, bottom, borderPaint)
        }
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun getAlpha(): Int {
        return bitmapPaint.alpha
    }

    override fun setAlpha(alpha: Int) {
        bitmapPaint.alpha = alpha
        invalidateSelf()
    }

    override fun getColorFilter(): ColorFilter? {
        return bitmapPaint.colorFilter
    }

    override fun setColorFilter(cf: ColorFilter?) {
        bitmapPaint.colorFilter = cf
        invalidateSelf()
    }

    override fun setDither(dither: Boolean) {
        bitmapPaint.isDither = dither
        invalidateSelf()
    }

    override fun setFilterBitmap(filter: Boolean) {
        bitmapPaint.isFilterBitmap = filter
        invalidateSelf()
    }

    override fun getIntrinsicWidth(): Int {
        return bitmapWidth
    }

    override fun getIntrinsicHeight(): Int {
        return bitmapHeight
    }

    /**
     * @param corner the specific corner to get radius of.
     * @return the corner radius of the specified corner.
     */
    fun getCornerRadius(@Corner corner: Int): kotlin.Float {
        return if (mCornersRounded[corner]) cornerRadius else 0f
    }

    /**
     * Sets all corners to the specified radius.
     *
     * @param radius the radius.
     * @return the [RoundedDrawable] for chaining.
     */
    fun setCornerRadius(radius: kotlin.Float): RoundedDrawable {
        setCornerRadius(radius, radius, radius, radius)
        return this
    }

    /**
     * Sets the corner radius of one specific corner.
     *
     * @param corner the corner.
     * @param radius the radius.
     * @return the [RoundedDrawable] for chaining.
     */
    fun setCornerRadius(@Corner corner: Int, radius: kotlin.Float): RoundedDrawable {
        require(!(radius != 0f && cornerRadius != 0f && cornerRadius != radius)) { "Multiple nonzero corner radii not yet supported." }
        if (radius == 0f) {
            if (only(corner, mCornersRounded)) {
                cornerRadius = 0f
            }
            mCornersRounded[corner] = false
        } else {
            if (cornerRadius == 0f) {
                cornerRadius = radius
            }
            mCornersRounded[corner] = true
        }
        return this
    }

    /**
     * Sets the corner radii of all the corners.
     *
     * @param topLeft top left corner radius.
     * @param topRight top right corner radius
     * @param bottomRight bototm right corner radius.
     * @param bottomLeft bottom left corner radius.
     * @return the [RoundedDrawable] for chaining.
     */
    fun setCornerRadius(
        topLeft: kotlin.Float, topRight: kotlin.Float, bottomRight: kotlin.Float,
        bottomLeft: kotlin.Float
    ): RoundedDrawable {
        val radiusSet: MutableSet<kotlin.Float> = HashSet(CORNERS_COUNT)
        radiusSet.add(topLeft)
        radiusSet.add(topRight)
        radiusSet.add(bottomRight)
        radiusSet.add(bottomLeft)
        radiusSet.remove(0f)
        require(radiusSet.size <= 1) { "Multiple nonzero corner radii not yet supported." }
        if (!radiusSet.isEmpty()) {
            val radius = radiusSet.iterator().next()
            require(!(Float.isInfinite(radius) || Float.isNaN(radius) || radius < 0)) { "Invalid radius value: $radius" }
            cornerRadius = radius
        } else {
            cornerRadius = 0f
        }
        mCornersRounded[Corner.TOP_LEFT] = topLeft > 0
        mCornersRounded[Corner.TOP_RIGHT] = topRight > 0
        mCornersRounded[Corner.BOTTOM_RIGHT] = bottomRight > 0
        mCornersRounded[Corner.BOTTOM_LEFT] = bottomLeft > 0
        return this
    }

    fun setBorderWidth(width: kotlin.Float): RoundedDrawable {
        borderWidth = width
        borderPaint.strokeWidth = borderWidth
        return this
    }

    val borderColor: Int
        get() = borderColors.defaultColor

    fun setBorderColor(@ColorInt color: Int): RoundedDrawable {
        return setBorderColor(ColorStateList.valueOf(color))
    }

    fun setBorderColor(colors: ColorStateList?): RoundedDrawable {
        borderColors = colors ?: ColorStateList.valueOf(0)
        borderPaint.color = borderColors.getColorForState(state, DEFAULT_BORDER_COLOR)
        return this
    }

    fun setOval(oval: Boolean): RoundedDrawable {
        isOval = oval
        return this
    }

    fun setScaleType(scaleType: ScaleType?): RoundedDrawable {
        var scaleType = scaleType
        if (scaleType == null) {
            scaleType = ScaleType.FIT_CENTER
        }
        if (this.scaleType != scaleType) {
            this.scaleType = scaleType
            updateShaderMatrix()
        }
        return this
    }

    fun setTileModeX(tileModeX: Shader.TileMode): RoundedDrawable {
        if (this.tileModeX != tileModeX) {
            this.tileModeX = tileModeX
            rebuildShader = true
            invalidateSelf()
        }
        return this
    }

    fun setTileModeY(tileModeY: Shader.TileMode): RoundedDrawable {
        if (this.tileModeY != tileModeY) {
            this.tileModeY = tileModeY
            rebuildShader = true
            invalidateSelf()
        }
        return this
    }

    fun toBitmap(): Bitmap? {
        return drawableToBitmap(this)
    }

    companion object {
        const val CORNERS_COUNT = 4
        const val TAG = "RoundedDrawable"
        const val DEFAULT_BORDER_COLOR = Color.BLACK
        fun fromBitmap(bitmap: Bitmap?): RoundedDrawable? {
            return bitmap?.let { RoundedDrawable(it) }
        }

        @Suppress("ReturnCount")
        fun fromDrawable(drawable: Drawable?): Drawable? {
            if (drawable != null) {
                if (drawable is RoundedDrawable) {
                    // just return if it's already a RoundedDrawable
                    return drawable
                } else if (drawable is LayerDrawable) {
                    val cs = drawable.mutate().constantState
                    val ld = (cs?.newDrawable() ?: drawable) as LayerDrawable
                    val num = ld.numberOfLayers

                    // loop through layers to and change to RoundedDrawables if possible
                    for (i in 0 until num) {
                        val d = ld.getDrawable(i)
                        ld.setDrawableByLayerId(ld.getId(i), fromDrawable(d))
                    }
                    return ld
                }

                // try to get a bitmap from the drawable and
                val bm = drawableToBitmap(drawable)
                if (bm != null) {
                    return RoundedDrawable(bm)
                }
            }
            return drawable
        }

        fun drawableToBitmap(drawable: Drawable): Bitmap? {
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }
            var bitmap: Bitmap?
            val width = Math.max(drawable.intrinsicWidth, 2)
            val height = Math.max(drawable.intrinsicHeight, 2)
            try {
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
                Log.w(TAG, "Failed to create bitmap from drawable!")
                bitmap = null
            }
            return bitmap
        }

        private fun only(index: Int, booleans: BooleanArray): Boolean {
            var i = 0
            val len = booleans.size
            while (i < len) {
                if (booleans[i] != (i == index)) {
                    return false
                }
                i++
            }
            return true
        }

        private fun any(booleans: BooleanArray): Boolean {
            for (b in booleans) {
                if (b) {
                    return true
                }
            }
            return false
        }

        private fun all(booleans: BooleanArray): Boolean {
            for (b in booleans) {
                if (b) {
                    return false
                }
            }
            return true
        }
    }

    init {
        bitmapWidth = sourceBitmap.width
        bitmapHeight = sourceBitmap.height
        bitmapRect[0f, 0f, bitmapWidth.toFloat()] = bitmapHeight.toFloat()
        bitmapPaint = Paint()
        bitmapPaint.style = Paint.Style.FILL
        bitmapPaint.isAntiAlias = true
        borderPaint = Paint()
        borderPaint.style = Paint.Style.STROKE
        borderPaint.isAntiAlias = true
        borderPaint.color = borderColors.getColorForState(state, DEFAULT_BORDER_COLOR)
        borderPaint.strokeWidth = borderWidth
    }
}