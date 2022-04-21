package com.admiral.uikit.components.tabs

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.res.use
import androidx.core.view.children
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.uikit.R
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.ext.colorStateListForChecked
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.setMargins
import com.admiral.uikit.layout.LinearLayout
import com.admiral.uikit.view.checkable.CheckableGroup

class InformerTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    /**
     * Colors for polygon.
     * In case state is null, the selected color theme will be used.
     */
    var polygonColorState: ColorState? = null
        set(value) {
            field = value
            invalidatePolygonColor()
        }

    /**
     * View that points to the selected tab.
     */
    var polygonView: ImageView = ImageView(context).apply {
        setImageResource(R.drawable.admiral_ic_polygon)
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        visibility = View.INVISIBLE
        layoutParams = params
    }

    private var isPolygonVisible = false

    init {
        orientation = VERTICAL

        parseAttrs(attrs, R.styleable.InformerTabLayout).use {
            isPolygonVisible = it.getBoolean(R.styleable.InformerTabLayout_admiralIsPolygonVisible, true)

            parsePolygonColor(it)
            parsePolygonMargin(it)
        }
    }

    override fun addView(child: View?) {
        super.addView(child)
        onFinishInflate()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        addPolygonView()
    }

    private fun addPolygonView() {
        removeView(polygonView)
        addView(polygonView, 1)

        // initialize polygon if there is a selected tab.
        val group = getChildAt(0) as InformerTabs

        fun updatePolygonPosition(isAnimationNeeded: Boolean = true) {
            group.children.forEach {
                if (it.id == group.getCheckedId()) {
                    val polygonPosition = it.x + it.width / 2 - polygonView.width / 2

                    polygonView.isVisible = isPolygonVisible
                    if (isAnimationNeeded) {
                        polygonView.animate().x(polygonPosition).start()
                    } else {
                        polygonView.x = polygonPosition
                    }
                }
            }
        }

        // as soon as selected tab is changed, we must move our polygon to the center of the new selected tab.
        group.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                updatePolygonPosition(isAnimationNeeded = true)
            }
        }

        doOnLayout {
            updatePolygonPosition(isAnimationNeeded = false)
        }
    }

    override fun onThemeChanged(theme: Theme) {
        super.onThemeChanged(theme)
        invalidatePolygonColor()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        children.forEach {
            it.isEnabled = enabled
        }
    }

    private fun parsePolygonColor(typedArray: TypedArray) {
        polygonColorState = ColorState(
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.InformerTabLayout_polygonColorNormalEnabled
            ),
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.InformerTabLayout_polygonColorNormalDisabled
            )
        )
    }

    private fun parsePolygonMargin(typedArray: TypedArray) {
        val parsedMargin = typedArray.getDimension(R.styleable.InformerTabLayout_polygonMarginTop, 0f)
        polygonView.setMargins(top = parsedMargin.toInt())
    }

    private fun invalidatePolygonColor() {
        polygonView.imageTintList = colorStateListForChecked(
            checkedEnabled = polygonColorState?.normalEnabled ?: ThemeManager.theme.palette.backgroundAdditionalOne,
            checkedDisabled = polygonColorState?.normalDisabled ?: ThemeManager.theme.palette.backgroundAdditionalOne,
            normalEnabled = polygonColorState?.normalEnabled ?: ThemeManager.theme.palette.backgroundAdditionalOne,
            normalDisabled = polygonColorState?.normalDisabled ?: ThemeManager.theme.palette.backgroundAdditionalOne
        )
    }
}