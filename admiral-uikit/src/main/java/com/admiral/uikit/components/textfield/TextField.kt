package com.admiral.uikit.components.textfield

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.KeyListener
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.SparseArray
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doOnTextChanged
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.imageview.ImageView
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.applyStyle
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.doOnPreDrawOnce
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.ext.setSelectionEnd
import com.admiral.uikit.ext.showKeyboard
import com.admiral.uikit.layout.ConstraintLayout
import com.admiral.uikit.layout.LinearLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize
import android.widget.LinearLayout.LayoutParams as LinearLayoutParams

/**
 * Replacement of TextFieldInputLayout and TextInputEditText.
 */
class TextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Color state for hint text color.
     * In case state is null, the selected color theme will be used.
     */
    var hintTextColors: ColorState? = null
        set(value) {
            field = value
            invalidateHintTextColors()
        }

    @Deprecated("Use hintTextColors, additionalTextColors or inputTextColor method variable")
    var textColors: ColorState? = null
        set(value) {
            field = value
            invalidateAdditionalTextColors()
        }

    /**
     * Color state for additional text view.
     * States: normal, disabled, focused.
     * In case state is null, the selected color theme will be used.
     */
    var additionalTextColors: ColorState? = null
        set(value) {
            field = value
            invalidateAdditionalTextColors()
        }

    /**
     * Color of input text.
     * In case color is null, the selected color theme will be used.
     */
    @ColorInt
    var inputTextColor: Int? = null
        set(value) {
            field = value
            invalidateInputTextColors()
        }

    /**
     * Color of error state. Acceptable only for divider, optional text and additional text.
     * In case color is null, the selected color theme will be used.
     */
    @ColorInt
    var errorColor: Int? = null
        set(value) {
            field = value
            invalidateColors()
        }

    /**
     * Displayed text in input.
     */
    var inputText: String
        set(value) = editText.setText(value)
        get() {
            if (editText.text.isNullOrEmpty()) {
                return ""
            }
            return editText.text.toString()
        }

    /**
     * Floating optional text.
     */
    var optionalText: String? = null
        set(value) {
            field = value
            invalidateTextHint()
        }

    /**
     * Placeholder text that will be displayed in the input area when the [optionalText] is collapsed
     * before text is entered or when the [optionalText] is null or empty.
     */
    var placeholderText: String? = null
        set(value) {
            field = value
            inputLayout.placeholderText = value
        }

    /**
     * Additional text which is placed under divider.
     */
    var additionalText: String? = null
        set(value) {
            field = value
            additionalTextView.text = value
        }

    /**
     * Manage visibility of additionalTextView.
     */
    var isAdditionalTextVisible: Boolean = true
        set(value) {
            field = value
            setAdditionalTextVisibility()
        }

    /**
     * Error text which is placed under divider when the view is in a Error state.
     */
    var errorText: String? = null
        set(value) {
            field = value
            invalidateError()
        }

    /**
     * Gravity of input text.
     *
     * @see [TextGravity]
     */
    var inputTextGravity: TextGravity = TextGravity.Start
        set(value) {
            field = value
            editText.gravity = value.gravity
        }

    /**
     * In case background color is [Color.TRANSPARENT] or [textFieldStyle] is [TextFieldStyle.Clipped],
     * [R.dimen.module_x10] is used as size of [icon],
     * [R.dimen.module_x6] otherwise.
     */
    @ColorInt
    var iconBackgroundColor: Int = Color.TRANSPARENT
        set(value) {
            field = value
            invalidateIcon()
        }

    /**
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorInt
    var iconTintColor: Int? = null
        set(value) {
            field = value
            invalidateIconColors()
        }

    /**
     * In case Drawable is null, the icon will be hidden.
     */
    var icon: Drawable? = null
        set(value) {
            field = value
            invalidateIconDrawable()
        }

    /**
     * In case the value is true, icon's drawable and color will be fixed. Otherwise it will be changed if isError == true.
     */
    var isIconFixed: Boolean = true
        set(value) {
            field = value
            invalidateIconDrawable()
            invalidateIconColors()
        }

    /**
     * Enable or disable error state.
     */
    var isError: Boolean = false
        set(value) {
            field = value
            invalidateError()
        }

    /**
     * Hide or show password text.
     */
    var isTextHidden: Boolean = false
        set(value) {
            field = value
            invalidateTextHidden()
        }

    /**
     * Define the state of [TextField].
     */
    var textFieldStyle: TextFieldStyle = TextFieldStyle.Extended
        set(value) {
            field = value
            invalidateIcon()
            invalidateStyle()
            invalidateTextHint()
            invalidateColors()
        }

    /**
     * Enable or disable editing.
     */
    var isEditEnabled: Boolean = true
        set(value) {
            handleEditStatus(value)
        }

    /**
     * Listener to detect text changing.
     */
    var onIconClickListener: OnIconClickListener? = null
        set(value) {
            field = value
            iconImageView.setOnClickListener {
                value?.onClick()
            }
        }

    /**
     * Hide / show password's icon (an eye).
     */
    var isPasswordIconEnabled: Boolean = false
        set(value) {
            field = value
            invalidatePasswordIcon()
        }

    /**
     * Drawable that will be shown if [isPasswordIconEnabled] == true and [isTextHidden] == true
     *
     * In case the field is null, there will be a default drawable from the icon pack.
     */
    var iconCloseHidden: Drawable? = null
        set(value) {
            field = value
            invalidateTextHidden()
        }

    /**
     * Drawable that will be shown if [isPasswordIconEnabled] == true and [isTextHidden] == false
     *
     * In case the field is null, there will be a default drawable from the icon pack.
     */
    var iconCloseShown: Drawable? = null
        set(value) {
            field = value
            invalidateTextHidden()
        }

    /**
     * Determines max length of the text that may be printed.
     */
    var maxLength: Int = Int.MAX_VALUE
        set(value) {
            field = value
            invalidateMaxLength()
        }

    /**
     * Determines max lines of the text that may be printed.
     */
    var maxLines: Int = Int.MAX_VALUE
        set(value) {
            field = value
            invalidateMaxLines()
        }

    /**
     * Max lines for the bottom text view.
     */
    var bottomTextMaxLines: Int = Int.MAX_VALUE
        set(value) {
            field = value
            additionalTextView.maxLines = value
        }

    /**
     * InputType for the EditText.
     * Bit definitions for an integer defining the basic content type of text held in an Editable object.
     * Supported classes may be combined with variations and flags to indicate desired behaviors.
     */
    var inputType: Int = EditorInfo.TYPE_CLASS_TEXT
        set(value) {
            field = value
            editText.inputType = inputType
        }

    /**
     * ImeOptions for the EditText.
     * This IME action is honored by IME and may show specific icons on the keyboard.
     * For example, search icon may be shown if ImeAction.Search is specified.
     * When singleLine is false, the IME might show return key rather than the action requested here.
     */
    var imeOptions: Int = EditorInfo.IME_ACTION_NEXT
        set(value) {
            field = value
            editText.imeOptions = imeOptions
        }

    /**
     * [KeyListener] for the EditText.
     */
    var keyListener: KeyListener? = null
        set(value) {
            field = value
            editText.keyListener = keyListener
        }

    /**
     * When an object of this type is attached to an Editable, its methods will be called when the text is changed.
     */
    var textWatcher: TextWatcher? = null
        set(value) {
            field = value
            invalidateListeners()
        }

    /**
     * Determines if [dividerView] is Visible or not.
     */
    var isBottomLineVisible: Boolean = true
        set(value) {
            field = value
            dividerView.visibility = if (value) View.VISIBLE else View.INVISIBLE
        }

    /**
     * Determines if [placeholderText] is always visible In the input area even when the text field is
     * unpopulated and not focused.
     */
    var isHintAlwaysVisible: Boolean = false
        set(value) {
            field = value
            inputLayout.isExpandedHintEnabled = !value
            invalidateHintTextColors()
        }

    val inputLayout: TextInputLayout by lazy { findViewById(R.id.inputLayout) }

    private var textFlowField = MutableStateFlow<String?>(null)

    val textFlow: StateFlow<String?> = textFlowField

    /**
     * Standard [TextInputEditText]. It's better to use it only for settings filters, formatter, etc.
     */
    val editText: TextInputEditText by lazy { findViewById(R.id.editText) }

    private val mainContentContainer: LinearLayout by lazy { findViewById(R.id.admiralViewTextFieldRightViews) }

    private val iconImageView: ImageView by lazy { findViewById(R.id.iconImageView) }
    private val iconCloseImageView: ImageView by lazy { findViewById(R.id.iconCloseImageView) }
    private val additionalTextView: TextView by lazy { findViewById(R.id.additionalTextView) }
    private val dividerView: View by lazy { findViewById(R.id.dividerView) }

    private val focusChangeListeners = mutableListOf<OnFocusChangeListener>()
    private var isNowFocused = false

    private var currentUnderlineDrawable: Drawable? = null
    private var additionalRightView: View? = null

    init {
        isBackgroundTransparent = true
        LayoutInflater.from(context).inflate(R.layout.admiral_view_text_field, this)

        parseAttrs(attrs, R.styleable.TextField).use {
            iconCloseHidden = it.getDrawable(R.styleable.TextField_admiralIconCloseHidden)
            iconCloseShown = it.getDrawable(R.styleable.TextField_admiralIconCloseShown)

            parseStyle(it)
            parseColors(it)
            parseTexts(it)
            parseGravity(it)
            parseIcon(it)
            parseIsShowPasswordIconEnabled(it)

            isEnabled = it.getBoolean(R.styleable.TextField_enabled, true)

            bottomTextMaxLines = it.getInt(R.styleable.TextField_admiralBottomTextMaxLines, Int.MAX_VALUE)
            isError = it.getBoolean(R.styleable.TextField_admiralIsError, false)

            inputType = it.getInt(R.styleable.TextField_android_inputType, EditorInfo.TYPE_CLASS_TEXT)
            imeOptions = it.getInt(R.styleable.TextField_android_imeOptions, EditorInfo.IME_ACTION_NEXT)

            maxLines = it.getInt(R.styleable.TextField_android_maxLines, Int.MAX_VALUE)
            maxLength = it.getInt(R.styleable.TextField_android_maxLength, Int.MAX_VALUE)

            isBottomLineVisible = it.getBoolean(R.styleable.TextField_admiralIsBottomLineVisible, true)
            isAdditionalTextVisible = it.getBoolean(R.styleable.TextField_admiralIsAdditionalTextVisible, true)
            isHintAlwaysVisible = it.getBoolean(R.styleable.TextField_admiralIsHintAlwaysVisible, false)
            isSaveEnabled = it.getBoolean(R.styleable.TextField_android_saveEnabled, true)
        }

        keyListener = editText.keyListener
        isTextHidden = editText.transformationMethod == PasswordTransformationMethod.getInstance()

        enableInput()
        invalidateListeners()

        invalidateStyle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.subscribe(this)
    }

    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        inputLayout.isEnabled = enabled
        editText.isEnabled = enabled
        additionalTextView.isEnabled = enabled
        iconImageView.isEnabled = enabled
        dividerView.isEnabled = enabled
        iconCloseImageView.isEnabled = enabled

        ellipsize(enabled)

        invalidateColors()
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateColors()
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    override fun onSaveInstanceState(): Parcelable {
        return SavedState(super.onSaveInstanceState(), isError, isTextHidden).also {
            for (i in 0 until childCount) getChildAt(i).saveHierarchyState(it.childrenStates)
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        (state as? SavedState)?.let {
            super.onRestoreInstanceState(it.superState)

            isError = it.isError
            isTextHidden = it.isTextHidden
            for (i in 0 until childCount) getChildAt(i).restoreHierarchyState(it.childrenStates)
        }
    }

    @Parcelize
    class SavedState(
        private val state: Parcelable?,
        val isError: Boolean,
        val isTextHidden: Boolean,
        val childrenStates: SparseArray<Parcelable> = SparseArray()
    ) : View.BaseSavedState(state)

    fun addInputFocusListener(listener: OnFocusChangeListener) {
        focusChangeListeners.add(listener)
    }

    fun removeInputFocusListener(listener: OnFocusChangeListener) {
        focusChangeListeners.remove(listener)
    }

    /**
     * Request focus and show keyboard.
     */
    fun performFocus() {
        editText.showKeyboard()
    }

    /**
     * Returns true if this view has focus.
     */
    fun isNowFocused(): Boolean {
        return isNowFocused
    }

    fun setSelection(index: Int) {
        editText.setSelection(index)
    }

    fun addEndView(view: View) {
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = params

        additionalRightView = view
        mainContentContainer.addView(view, mainContentContainer.childCount)
        invalidateIcon()
    }

    private fun parseTexts(a: TypedArray) {
        inputText = a.getString(R.styleable.TextField_admiralText) ?: ""
        optionalText = a.getString(R.styleable.TextField_admiralTextOptional)
        placeholderText = a.getString(R.styleable.TextField_admiralTextPlaceholder)
        additionalText = a.getString(R.styleable.TextField_admiralTextAdditional)
        errorText = a.getString(R.styleable.TextField_admiralTextError)
    }

    private fun parseStyle(a: TypedArray) {
        val styleIndex = a.getInt(R.styleable.TextField_admiralTextFieldStyle, 0)

        textFieldStyle = when (styleIndex) {
            0 -> TextFieldStyle.Extended
            1 -> TextFieldStyle.Clipped
            else -> TextFieldStyle.Extended
        }
    }

    private fun parseIcon(a: TypedArray) {
        iconBackgroundColor = a.getColor(R.styleable.TextField_admiralIconBackgroundColor, Color.TRANSPARENT)
        icon = a.getDrawable(R.styleable.TextField_admiralIcon)
        iconImageView.isVisible = icon != null

        iconTintColor = a.getColorOrNull(R.styleable.TextField_admiralIconTintColor)
        isIconFixed = a.getBoolean(R.styleable.TextField_admiralIsIconFixed, true)
    }

    private fun parseGravity(a: TypedArray) {
        inputTextGravity =
            TextGravity.fromGravity(a.getInt(R.styleable.TextField_admiralTextInputGravity, Gravity.START))
    }

    private fun parseColors(a: TypedArray) {
        errorColor = a.getInt(R.styleable.TextField_admiralErrorColor, ThemeManager.theme.palette.textError)

        inputTextColor = a.getColorOrNull(R.styleable.TextField_admiralTextInputColor)

        additionalTextColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.TextField_admiralTextColorNormalEnabled),
            pressed = a.getColorOrNull(R.styleable.TextField_admiralTextColorFocused),
            normalDisabled = a.getColorOrNull(R.styleable.TextField_admiralTextColorNormalDisabled),
            focused = a.getColorOrNull(R.styleable.TextField_admiralTextColorFocused)
        )

        hintTextColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.TextField_admiralHintTextColorNormalEnabled),
            normalDisabled = a.getColorOrNull(R.styleable.TextField_admiralHintTextColorNormalDisabled),
            pressed = a.getColorOrNull(R.styleable.TextField_admiralHintTextColorPressed)
        )
    }

    private fun parseIsShowPasswordIconEnabled(it: TypedArray) {
        isPasswordIconEnabled = it.getBoolean(R.styleable.TextField_admiralIsShowPasswordIconEnabled, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun enableInput() {
        val onFocusChange = OnFocusChangeListener { v, hasFocus ->
            isNowFocused = hasFocus
            focusChangeListeners.forEach { it.onFocusChange(v, hasFocus) }
            doOnPreDrawOnce(::invalidateColors)
        }
        editText.onFocusChangeListener = onFocusChange
        editText.setOnTouchListener(null)

        handleEditStatus(true)
    }

    private fun handleEditStatus(isEditEnabled: Boolean) {
        editText.isClickable = isEditEnabled
        editText.isFocusable = isEditEnabled
        editText.isFocusableInTouchMode = isEditEnabled
        editText.isLongClickable = isEditEnabled
        editText.isCursorVisible = isEditEnabled

        currentUnderlineDrawable = if (isEditEnabled) {
            ContextCompat.getDrawable(context, R.drawable.admiral_line)
        } else {
            ContextCompat.getDrawable(context, R.drawable.admiral_dotted_line)
        }
        dividerView.background = currentUnderlineDrawable

        ellipsize(isEditEnabled)
    }

    fun ellipsize(enabled: Boolean) {
        if (enabled) {
            editText.inputType = inputType
            editText.keyListener = keyListener
            editText.ellipsize = null
        } else {
            editText.keyListener = null
            editText.ellipsize = TextUtils.TruncateAt.END
        }
        invalidateStyle()
        invalidateColors()
    }

    private fun invalidateTextHidden() {
        if (isTextHidden) {
            iconCloseImageView.setImageDrawable(iconCloseHidden ?: drawable(R.drawable.admiral_ic_eye_close_outline))
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            editText.setSelectionEnd()
        } else {
            iconCloseImageView.setImageDrawable(iconCloseShown ?: drawable(R.drawable.admiral_ic_eye_outline))
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            editText.setSelectionEnd()
        }
    }

    private fun invalidatePasswordIcon() {
        iconCloseImageView.isVisible = isPasswordIconEnabled

        iconCloseImageView.setOnClickListener {
            isTextHidden = !isTextHidden
        }
    }

    private fun invalidateColors() {
        invalidateHintTextColors()
        invalidateAdditionalTextColors()
        invalidateInputTextColors()
        invalidateIconColors()
        invalidateDividerColor()
        invalidateCursorColor()
    }

    private fun invalidateHintTextColors() {
        val hintTextColor: Int = when {
            isError -> errorColor ?: ThemeManager.theme.palette.textError
            isNowFocused -> hintTextColors?.focused ?: ThemeManager.theme.palette.textAccent
            !isEnabled -> hintTextColors?.normalDisabled ?: ThemeManager.theme.palette.textSecondary.withAlpha()
            else -> hintTextColors?.normalEnabled
                ?: if (isHintAlwaysVisible) {
                    ThemeManager.theme.palette.textAccent
                } else {
                    ThemeManager.theme.palette.textSecondary
                }
        }

        inputLayout.hintTextColor = ColorStateList.valueOf(hintTextColor)
        inputLayout.defaultHintTextColor = ColorStateList.valueOf(hintTextColor)

        /*
        We need to place this hack-line because placeholder wouldn't change
        color as there is the check: "if (this.placeholderTextColor != placeholderTextColor)".
        But when we put font style to the placeholder, it changes the color and we need to get it back.
        */
        inputLayout.placeholderTextColor = ColorStateList.valueOf(ThemeManager.theme.palette.textAccent)
        inputLayout.placeholderTextColor = ColorStateList.valueOf(ThemeManager.theme.palette.textMask)
    }

    private fun invalidateAdditionalTextColors() {
        val additionalTextColor: Int = when {
            isError -> errorColor ?: ThemeManager.theme.palette.textError
            !isEnabled -> additionalTextColors?.normalDisabled ?: ThemeManager.theme.palette.textSecondary.withAlpha()
            else -> additionalTextColors?.normalEnabled ?: ThemeManager.theme.palette.textSecondary
        }

        additionalTextView.textColor =
            ColorState(normalEnabled = additionalTextColor, normalDisabled = additionalTextColor)
    }

    private fun invalidateInputTextColors() {
        editText.setTextColor(
            colorStateList(
                enabled = inputTextColor ?: ThemeManager.theme.palette.textPrimary,
                disabled = inputTextColor?.withAlpha() ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
                pressed = inputTextColor ?: ThemeManager.theme.palette.textPrimary
            )
        )
    }

    private fun invalidateIconDrawable() {
        if (isError && !isIconFixed) {
            iconImageView.setImageDrawable(drawable(R.drawable.admiral_ic_error_solid))
        } else {
            iconImageView.setImageDrawable(icon)
            iconImageView.isGone = icon == null
        }
    }

    private fun invalidateIconColors() {
        if (isError && !isIconFixed) {
            iconImageView.imageTintColorState = ColorState(ThemeManager.theme.palette.elementError)
            iconCloseImageView.imageTintColorState = ColorState(ThemeManager.theme.palette.elementError)
        } else {
            iconImageView.imageTintColorState = ColorState(iconTintColor ?: ThemeManager.theme.palette.elementPrimary)
            iconCloseImageView.imageTintColorState =
                ColorState(iconTintColor ?: ThemeManager.theme.palette.elementPrimary)
        }
    }

    private fun invalidateDividerColor() {
        val dividerColor: Int = when {
            isError -> errorColor ?: ThemeManager.theme.palette.textError
            isNowFocused -> additionalTextColors?.focused ?: ThemeManager.theme.palette.textAccent
            !isEnabled -> additionalTextColors?.normalDisabled ?: ThemeManager.theme.palette.textSecondary.withAlpha()
            else -> additionalTextColors?.normalEnabled ?: ThemeManager.theme.palette.textSecondary
        }

        dividerView.background = currentUnderlineDrawable
        dividerView.backgroundTintList = ColorStateList.valueOf(dividerColor)
    }

    private fun invalidateCursorColor() {
        editText.highlightColor = ThemeManager.theme.palette.backgroundAccent.withAlpha()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            editText.textCursorDrawable =
                drawable(R.drawable.admiral_img_cursor_drawable)?.colored(ThemeManager.theme.palette.elementAccent)
        }
    }

    private fun invalidateError() {
        val text = if (isError) {
            if (errorText.isNullOrEmpty()) {
                additionalText
            } else {
                errorText
            }
        } else {
            additionalText
        }

        additionalTextView.text = text

        invalidateColors()
        invalidateIconDrawable()
    }

    private fun invalidateIcon() {
        val topMargin = pixels(
            when {
                textFieldStyle == TextFieldStyle.Clipped -> R.dimen.module_x4
                iconBackgroundColor == Color.TRANSPARENT -> R.dimen.module_x5
                else -> R.dimen.module_x5
            }
        )

        val size = pixels(
            when {
                textFieldStyle == TextFieldStyle.Clipped -> R.dimen.module_x6
                iconBackgroundColor == Color.TRANSPARENT -> R.dimen.module_x6
                else -> R.dimen.module_x10
            }
        )

        iconImageView.updateLayoutParams<LinearLayoutParams> {
            width = size
            height = size
        }

        iconCloseImageView.updateLayoutParams<LinearLayoutParams> {
            width = size
            height = size
        }

        additionalRightView?.updateLayoutParams<LinearLayoutParams> {
            width = size
            height = size
        }

        mainContentContainer.updateLayoutParams<LayoutParams> {
            setMargins(0, topMargin, 0, 0)
        }
    }

    private fun invalidateStyle() {
        Handler(Looper.getMainLooper()).post {
            inputLayout.setHintTextAppearance(Typography.getStyle(ThemeManager.theme.typography.subhead2))
        }
        additionalTextView.textStyle = ThemeManager.theme.typography.subhead2
        inputLayout.placeholderTextAppearance = Typography.getStyle(textFieldStyle.textStyle)
        editText.applyStyle(Typography.getStyle(textFieldStyle.textStyle))

        additionalTextView.gravity = textFieldStyle.additionalTextGravity.gravity
        additionalTextView.updateLayoutParams<LayoutParams> {
            setMargins(0, pixels(textFieldStyle.additionalTextPadding), 0, 0)
        }
    }

    private fun invalidateTextHint() {
        inputLayout.isHintEnabled = textFieldStyle != TextFieldStyle.Clipped && !optionalText.isNullOrEmpty()
        inputLayout.hint = optionalText
    }

    private fun setAdditionalTextVisibility() {
        additionalTextView.isGone = !isAdditionalTextVisible
    }

    private fun invalidateMaxLength() {
        editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
    }

    private fun invalidateMaxLines() {
        editText.maxLines = maxLines
    }

    private fun invalidateListeners() {
        if (textWatcher == null) {
            editText.doOnTextChanged { text, _, _, _ ->
                textFlowField.value = text.toString()
            }
        } else {
            editText.addTextChangedListener(textWatcher)
        }
    }

    fun interface OnIconClickListener {
        fun onClick()
    }
}