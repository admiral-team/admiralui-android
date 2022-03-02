package com.admiral.uikit.components.appbar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.MenuRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.cell.unit.IconCellUnit
import com.admiral.uikit.components.links.Link
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.components.textfield.TextFieldSearch
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.setMargins
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Appbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(ContextThemeWrapper(context, R.style.Widget_AppCompat_Toolbar), attrs, defStyleAttr),
    ThemeObserver {

    private var textFlowField = MutableStateFlow<String?>(null)

    val textFlow: StateFlow<String?> = textFlowField

    /**
     * Container fot the title text and menu text.
     */
    private val normalContainer: FrameLayout = FrameLayout(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setMargins(MARGIN_VIEWS_CONTAINER_TOP)
    }

    /**
     *Container fot the edit text and icons.
     */
    private val searchContainer: LinearLayout = LinearLayout(context).apply {
        layoutParams =
            LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        gravity = Gravity.CENTER
    }

    /**
     * TextView shown at the middle of the app bar.
     */
    private val textViewTitle = TextView(context)

    /**
     * TextView shown at the right of the app bar.
     */
    private val textViewMenu = Link(context).apply {
        setMargins(right = MARGIN_MENU_TEXT_RIGHT)
        gravity = Gravity.END or Gravity.CENTER_VERTICAL
        visibility = View.GONE
    }

    /**
     * Gravity of the Title text view.
     */
    var textViewTitleGravity = Gravity.CENTER_HORIZONTAL
        set(value) {
            field = value
            textViewTitle.gravity = value
        }

    var appbarType: AppbarType = AppbarType.NORMAL
        set(value) {
            field = value
            invalidateType()
        }

    @ColorRes
    var titleTextColor: Int? = null
        set(value) {
            field = value
            invalidateTitleTextColor()
        }

    var titleText: String? = null
        set(value) {
            field = value
            textViewTitle.text = value
        }
        get() {
            return textViewTitle.text.toString()
        }

    @ColorRes
    var menuTextColor: Int? = null
        set(value) {
            field = value
            invalidateMenuTextColor()
        }

    var menuText: String? = null
        set(value) {
            field = value
            textViewMenu.text = value
            textViewMenu.isVisible = value?.isEmpty() == false
        }
        get() {
            return textViewMenu.text.toString()
        }

    /**
     * Listener to detect menu click.
     */
    var onMenuClickListener: OnClickListener? = null
        set(value) {
            field = value
            textViewMenu.setOnClickListener {
                value?.onClick(it)
            }
        }

    var size = AppbarSize.SMALL
        set(value) {
            field = value
            invalidateSize()
        }

    /**
     * Colors for back button tint.
     * In case state is null, the selected color theme will be used.
     */
    @ColorRes
    var backButtonColor: Int? = null
        set(value) {
            field = value
            invalidateBackButtonTintColors()
        }

    /**
     * Drawable for back button icon.
     * In case state is null, default drawable will be used.
     */
    var backButtonIcon: Drawable? = null
        set(value) {
            field = value
            invalidateBackButtonIcon()
        }

    /**
     * Drawable for back button icon.
     * In case state is null, default drawable will be used.
     */
    var isBackButtonEnabled: Boolean = true
        set(value) {
            field = value
            invalidateBackButtonIcon()
        }

    /**
     * Edit text shown at the middle of the app bar.
     */
    private val editText = TextFieldSearch(context).apply {
        layoutParams = LinearLayout.LayoutParams(0, EDIT_TEXT_HEIGHT.dpToPx(context), 1f)
        setMargins(0, EDIT_TEXT_MARGIN, 0, 0)
    }

    /**
     * Icon shown at the beginning of the app bar.
     */
    val iconStart = IconCellUnit(context).apply {
        layoutParams = LayoutParams(ICONS_SIZE.dpToPx(context), ICONS_SIZE.dpToPx(context))
        setMargins(0, EDIT_TEXT_MARGIN, 0, 0)
    }

    /**
     * Icon shown at the end of the app bar.
     */
    val iconEnd = IconCellUnit(context).apply {
        layoutParams = LayoutParams(ICONS_SIZE.dpToPx(context), ICONS_SIZE.dpToPx(context))
        setMargins(0, EDIT_TEXT_MARGIN, 0, 0)
    }

    var editTextDrawableStart: Drawable? = null
        set(value) {
            field = value
            editText.drawableStart = value
        }

    @ColorRes
    var editTextTextColor: Int? = null
        set(value) {
            field = value
            invalidateEditTextColors()
        }

    @ColorRes
    var hintTextColor: Int? = null
        set(value) {
            field = value
            invalidateEditTextColors()
        }

    var editTextText: String? = null
        set(value) {
            field = value
            editText.setText(value)
        }
        get() {
            return editText.editableText.toString()
        }

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
        parseAttrs(attrs, R.styleable.Appbar).use {
            appbarType = AppbarType.from(it.getInt(R.styleable.Appbar_admiralAppbarType, 0))

            titleTextColor = it.getColorOrNull(R.styleable.Appbar_admiralTitleColorNormalEnabled)
            menuTextColor = it.getColorOrNull(R.styleable.Appbar_admiralMenuTextColorNormalEnabled)

            val styleIndex = it.getInt(R.styleable.Appbar_admiralAppbarSize, 1)

            size = when (styleIndex) {
                0 -> AppbarSize.BIG
                1 -> AppbarSize.SMALL
                else -> AppbarSize.SMALL
            }

            titleText = it.getString(R.styleable.Appbar_admiralTitleText) ?: " "
            menuText = it.getString(R.styleable.Appbar_admiralMenuText) ?: ""

            isBackButtonEnabled = it.getBoolean(R.styleable.Appbar_admiralIsBackButtonEnabled, true)
            backButtonIcon = it.getDrawable(R.styleable.Appbar_admiralBackButtonIcon)
            backButtonColor = it.getColorOrNull(R.styleable.Appbar_admiralBackButtonColor)

            textViewTitleGravity =
                it.getInt(R.styleable.Appbar_admiralTitleTextGravity, Gravity.START)

            editTextDrawableStart = it.getDrawable(R.styleable.Appbar_android_drawableStart)
                ?: drawable(R.drawable.admiral_ic_search_outline)

            editTextTextColor = it.getColorOrNull(R.styleable.Appbar_admiralTextColorNormalEnabled)
            hintTextColor = it.getColorOrNull(R.styleable.Appbar_admiralHintTextColorNormalEnabled)

            editTextText = it.getString(R.styleable.Appbar_admiralText)
            hintText = it.getString(R.styleable.Appbar_admiralHintText)

            iconStart.icon = it.getDrawable(R.styleable.Appbar_admiralIconStart)
            iconEnd.icon = it.getDrawable(R.styleable.Appbar_admiralIconEnd)
            parseIconsColors(it)
        }

        textViewTitle.isSingleLine = false

        normalContainer.addView(textViewTitle)
        normalContainer.addView(textViewMenu)
        addView(normalContainer)

        searchContainer.addView(iconStart)
        searchContainer.addView(editText)
        searchContainer.addView(iconEnd)
        addView(searchContainer)

        editText.doOnTextChanged { text, _, _, _ ->
            textFlowField.value = text.toString()
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

    fun inflateMenu(@MenuRes menuRes: Int, menu: Menu, inflater: MenuInflater) {
        inflater.inflate(menuRes, menu)
        for (i in 0 until menu.size()) {
            val drawable = menu.getItem(i).icon
            if (drawable != null) {
                drawable.mutate()
                drawable.setTint(ThemeManager.theme.palette.elementAccent)
            }
        }
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateMenuTextColor()
        invalidateTitleTextColor()
        invalidateBackButtonTintColors()

        invalidateIconsColors()
        invalidateEditTextColors()

        invalidateMenuIcons()
    }

    private fun parseIconsColors(a: TypedArray) {
        iconsTintColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.Appbar_admiralIconTintColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.Appbar_admiralIconTintColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.Appbar_admiralIconTintColorNormalDisabled,
            )
        )
    }

    private fun invalidateBackButtonIcon() {
        navigationIcon = if (isBackButtonEnabled) {
            backButtonIcon ?: ContextCompat.getDrawable(
                context,
                R.drawable.admiral_ic_arrow_left_outline
            )
        } else {
            null
        }
    }

    private fun invalidateBackButtonTintColors() {
        navigationIcon?.colored(backButtonColor ?: ThemeManager.theme.palette.elementAccent)
    }

    private fun invalidateTitleTextColor() {
        textViewTitle.textColor = ColorState(
            normalEnabled = titleTextColor ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = titleTextColor?.withAlpha()
                ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = titleTextColor ?: ThemeManager.theme.palette.textPrimary
        )
    }

    private fun invalidateMenuTextColor() {
        textViewMenu.textColor = ColorState(
            normalEnabled = menuTextColor ?: ThemeManager.theme.palette.textAccent,
            normalDisabled = menuTextColor?.withAlpha()
                ?: ThemeManager.theme.palette.textAccent.withAlpha(),
            pressed = menuTextColor ?: ThemeManager.theme.palette.textAccent
        )
    }

    private fun invalidateEditTextColors() {
        editText.inputTextColors = ColorState(
            normalEnabled = editTextTextColor ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = editTextTextColor?.withAlpha()
                ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = editTextTextColor ?: ThemeManager.theme.palette.textPrimary
        )

        editText.hintTextColors = ColorState(
            normalEnabled = hintTextColor ?: ThemeManager.theme.palette.textSecondary,
            normalDisabled = hintTextColor?.withAlpha()
                ?: ThemeManager.theme.palette.textSecondary.withAlpha(),
            pressed = hintTextColor ?: ThemeManager.theme.palette.textSecondary
        )
    }

    private fun invalidateIconsColors() {
        iconStart.iconTintColors = iconsTintColor
        iconEnd.iconTintColors = iconsTintColor
    }

    private fun invalidateType() {
        when (appbarType) {
            AppbarType.NORMAL -> {
                normalContainer.isVisible = true
                searchContainer.isVisible = false
            }
            AppbarType.SEARCH -> {
                normalContainer.isVisible = false
                searchContainer.isVisible = true
            }
        }
    }

    private fun invalidateSize() {
        if (size == AppbarSize.BIG) {
            textViewTitle.setMargins(BIG_MARGIN)
            textViewTitle.textStyle = ThemeManager.theme.typography.largetitle1
        } else {
            textViewTitle.setMargins(0)
            textViewTitle.textStyle = ThemeManager.theme.typography.subtitle2
        }
    }

    private fun invalidateMenuIcons() {
        val colorState = colorStateList(
            enabled = ThemeManager.theme.palette.elementAccent,
            pressed = ThemeManager.theme.palette.elementAccentPressed,
            disabled = ThemeManager.theme.palette.elementAccent.withAlpha()
        )
        menu.children.iterator().forEach { menuItem ->
            menuItem.icon?.setTintList(colorState)
        }
    }

    private companion object {
        const val BIG_MARGIN = 32
        const val MARGIN_MENU_TEXT_RIGHT = 20
        const val MARGIN_VIEWS_CONTAINER_TOP = 16

        const val EDIT_TEXT_MARGIN = 16
        const val ICONS_SIZE = 32
        const val EDIT_TEXT_HEIGHT = 36
    }
}