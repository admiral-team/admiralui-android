package com.admiral.uikit.components.appbar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.cell.unit.IconCellUnit
import com.admiral.uikit.components.textfield.TextFieldSearch
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.setMargins

class AppbarSearch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(ContextThemeWrapper(context, R.style.Widget_AppCompat_Toolbar), attrs, defStyleAttr), ThemeObserver {

    /**
     * Container fot the edit text and icons.
     */
    private val linearLayout: LinearLayout = LinearLayout(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    /**
     * [TextFieldSearch] shown at the middle of the app bar.
     */
    val editText = TextFieldSearch(context).apply {
        layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f)
        setMargins(0, MARGIN, 0, 0)
    }

    /**
     * [IconCellUnit] shown at the beginning of the app bar.
     */
    val iconStart = IconCellUnit(context).apply {
        layoutParams = LayoutParams(Gravity.CENTER)
        setMargins(0, MARGIN, 0, 0)
    }

    /**
     * [IconCellUnit] shown at the end of the app bar.
     */
    val iconEnd = IconCellUnit(context).apply {
        layoutParams = LayoutParams(Gravity.CENTER)
        setMargins(0, MARGIN, 0, 0)
    }

    /**
     * [Drawable] shown inside of the [editText], at the left side.
     */
    var editTextDrawableStart: Drawable? = null
        set(value) {
            field = value
            editText.drawableStart = value
        }

    /**
     * Determine if the [editTextDrawableStart] should be visible.
     */
    var isEditTextDrawableStartVisible: Boolean = true
        set(value) {
            field = value
            if (value) {
                editText.drawableStart = editTextDrawableStart
            } else {
                editText.drawableStart = null
            }
        }

    /**
     * InputTextColors of the [editText]
     */
    @ColorRes
    var textColor: Int? = null
        set(value) {
            field = value
            initTextsColor()
        }

    /**
     * HintTextColors of the [editText]
     */
    @ColorRes
    var hintTextColor: Int? = null
        set(value) {
            field = value
            initTextsColor()
        }

    /**
     * Text of the [editText].
     */
    var text: String? = null
        set(value) {
            field = value
            editText.setText(value)
        }
        get() {
            if (editText.editableText.isNullOrEmpty()) {
                return ""
            }
            return editText.text.toString()
        }

    /**
     * Hint text of the [editText].
     */
    var hintText: String? = null
        set(value) {
            field = value
            editText.hint = value
        }
        get() {
            return editText.hint.toString()
        }

    /**
     * Listener to detect text changing.
     */
    var onTextChangeListener: TextFieldSearch.OnTextChangeListener? = null
        set(value) {
            field = value
            editText.onTextChangeListener = value
        }

    /**
     * Color state for icons.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var iconsTintColor: ColorState? = null
        set(value) {
            field = value
            invalidateIconsColors()
        }

    init {
        parseAttrs(attrs, R.styleable.AppbarSearch).use {
            editTextDrawableStart = it.getDrawable(R.styleable.AppbarSearch_android_drawableStart)
                ?: drawable(R.drawable.admiral_ic_search_outline)
            isEditTextDrawableStartVisible = it.getBoolean(R.styleable.AppbarSearch_admiralIsDrawableStartVisible, true)

            textColor = it.getColorOrNull(R.styleable.AppbarSearch_admiralTextColorNormalEnabled)
            hintTextColor = it.getColorOrNull(R.styleable.AppbarSearch_admiralHintTextColorNormalEnabled)

            text = it.getString(R.styleable.AppbarSearch_admiralText)
            hintText = it.getString(R.styleable.AppbarSearch_admiralHintText)

            iconStart.icon = it.getDrawable(R.styleable.AppbarSearch_admiralIconStart)
            iconEnd.icon = it.getDrawable(R.styleable.AppbarSearch_admiralIconEnd)
            parseIconsColors(it)
        }

        linearLayout.addView(iconStart)
        linearLayout.addView(editText)
        linearLayout.addView(iconEnd)
        addView(linearLayout)
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
        initTextsColor()
        invalidateIconsColors()
    }

    private fun parseIconsColors(a: TypedArray) {
        iconsTintColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.AppbarSearch_admiralIconTintColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.AppbarSearch_admiralIconTintColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.AppbarSearch_admiralIconTintColorNormalDisabled,
            )
        )
    }

    private fun initTextsColor() {
        editText.inputTextColors = ColorState(
            normalEnabled = textColor ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = textColor?.withAlpha() ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = textColor ?: ThemeManager.theme.palette.textPrimary
        )

        editText.hintTextColors = ColorState(
            normalEnabled = hintTextColor ?: ThemeManager.theme.palette.textSecondary,
            normalDisabled = hintTextColor?.withAlpha() ?: ThemeManager.theme.palette.textSecondary.withAlpha(),
            pressed = hintTextColor ?: ThemeManager.theme.palette.textSecondary
        )
    }

    private fun invalidateIconsColors() {
        iconStart.iconTintColors = iconsTintColor
        iconEnd.iconTintColors = iconsTintColor
    }

    private companion object {
        const val MARGIN = 16
    }
}