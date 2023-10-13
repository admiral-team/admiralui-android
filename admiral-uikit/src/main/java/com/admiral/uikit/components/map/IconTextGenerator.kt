package com.admiral.uikit.components.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BlurMaskFilter
import android.graphics.BlurMaskFilter.Blur
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Matrix.ScaleToFit
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.admiral.themes.Font
import com.admiral.themes.ThemeManager
import com.admiral.uikit.R
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.setMargins

/**
 * IconGenerator generates icons that contain text (or custom content) within an info
 * window-like shape.
 */
class IconTextGenerator(private val context: Context) {

    private val mContainer: ViewGroup =
        LayoutInflater.from(context).inflate(R.layout.admiral_view_map_text_icon, null) as ViewGroup

    private var mTextView: TextView? = mContainer.findViewById(R.id.admiral_map_icon_text)

    /**
     * Creates a new IconGenerator with the default style.
     */
    init {
        setStrokeColor()
    }

    /**
     * Sets the text content, then creates an icon with the current style.
     *
     * @param text the text content to display inside the icon.
     */
    fun makeTextIcon(text: CharSequence?, font: Font? = null, textColorState: ColorState? = null): Bitmap {
        if (mTextView != null) {
            mTextView!!.text = text
            font?.let {
                mTextView?.textStyle = font
            }
            mTextView?.textColor = textColorState
        }
        return makeTextIcon()
    }

    /**
     * Sets the background to the default, with a given color tint.
     *
     * @param color the color for the stroke tint.
     */
    fun setStrokeColor(color: Int? = null) {
        val background = ContextCompat.getDrawable(context, R.drawable.admiral_bg_round_stroke)?.mutate()
        val drawable = background as GradientDrawable
        drawable.setColor(Color.WHITE)
        drawable.setStroke(2.dpToPx(context), color ?: ThemeManager.theme.palette.backgroundAccent)
        mContainer.background = background
        mContainer.elevation = ELEVATION
        mContainer.setMargins(MARGIN, MARGIN, MARGIN, MARGIN)
    }

    /**
     * Sets the padding of the content view. The default padding of the content view (i.e. text
     * view) is 5dp top/bottom and 10dp left/right.
     *
     * @param left   the left padding in pixels.
     * @param top    the top padding in pixels.
     * @param right  the right padding in pixels.
     * @param bottom the bottom padding in pixels.
     */
    fun setContentPadding(left: Int, top: Int, right: Int, bottom: Int) {
        mTextView?.setPadding(left, top, right, bottom)
    }

    /**
     * Creates an icon with the current content and style.
     *
     *
     * This method is useful if a custom view has previously been set, or if text content is not
     * applicable.
     */
    private fun makeTextIcon(): Bitmap {
        val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        mContainer.measure(measureSpec, measureSpec)
        var measuredWidth = mContainer.measuredWidth
        var measuredHeight = mContainer.measuredHeight

        if (measuredWidth > measuredHeight) {
            measuredHeight = measuredWidth + ADDITIONAL_SPACE
            measuredWidth += ADDITIONAL_SPACE
        } else {
            measuredWidth = measuredHeight + ADDITIONAL_SPACE
            measuredHeight += ADDITIONAL_SPACE
        }
        mContainer.layout(0, 0, measuredWidth, measuredHeight)

        val r = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        r.eraseColor(Color.TRANSPARENT)
        val canvas = Canvas(r)

        mContainer.draw(canvas)

        val shadow = addShadow(r, r.height, r.width, SHADOW_SIZE, 0f, SHADOW_DY)
        return shadow
    }

    private companion object {
        const val MARGIN = 4
        const val ELEVATION = 24f
        const val ADDITIONAL_SPACE = 10
        const val SHADOW_SIZE = 8f
        const val SHADOW_DY = 3f
    }
}

const val ADDITIONAL_HEIGHT = 10

@Suppress("LongParameterList")
fun addShadow(bm: Bitmap, dstHeight: Int, dstWidth: Int, size: Float, dx: Float, dy: Float): Bitmap {
    val mask = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ALPHA_8)
    val scaleToFit = Matrix()
    val src = RectF(0f, 0f, bm.width.toFloat(), bm.height.toFloat())
    val dst = RectF(0f, 0f, dstWidth - dx, dstHeight - dy)
    scaleToFit.setRectToRect(src, dst, ScaleToFit.CENTER)

    val dropShadow = Matrix(scaleToFit)
    dropShadow.postTranslate(dx, dy)

    val maskCanvas = Canvas(mask)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    maskCanvas.drawBitmap(bm, scaleToFit, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
    maskCanvas.drawBitmap(bm, dropShadow, paint)
    val filter = BlurMaskFilter(size, Blur.NORMAL)

    paint.reset()
    paint.isAntiAlias = true
    paint.color = Color.BLACK
    paint.maskFilter = filter
    paint.isFilterBitmap = true

    val ret = Bitmap.createBitmap(dstWidth, dstHeight + ADDITIONAL_HEIGHT, Bitmap.Config.ARGB_8888)
    val retCanvas = Canvas(ret)
    retCanvas.drawBitmap(mask, 0f, 0f, paint)
    retCanvas.drawBitmap(bm, scaleToFit, null)
    mask.recycle()

    return ret
}