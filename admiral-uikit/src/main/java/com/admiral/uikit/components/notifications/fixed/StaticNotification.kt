package com.admiral.uikit.components.notifications.fixed

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextUtils.TruncateAt
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.links.Link
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.getIntOrNull
import com.admiral.uikit.ext.parseAttrs

class StaticNotification @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Color state for background.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var backgroundColors: ColorState? = null
        set(value) {
            field = value
            invalidateBackground()
        }

    /**
     * Central text.
     * Gone if text is null or empty.
     */
    var notificationText: CharSequence? = null
        set(value) {
            field = value
            notificationTextView.text = value
            notificationTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Determines max lines of the [notificationText] that may be printed.
     */
    var maxLines: Int = Int.MAX_VALUE
        set(value) {
            field = value
            invalidateMaxLines()
        }

    /**
     * Determines how to ellipsize the [notificationText].
     */
    var ellipsize: TruncateAt? = null
        set(value) {
            field = value
            invalidateEllipsize()
        }

    /**
     * Color state for text.
     * States: normal, disabled.
     * In case state is null, the selected color theme will be used.
     */
    var notificationTextColors: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    /**
     * Link text.
     * If you want to add click listener, please see [setLinkOnClickListener].
     * Gone if text is null or empty.
     */
    var linkText: String? = null
        set(value) {
            field = value
            linkTextView.text = value
            linkTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Color state for link.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var linkColors: ColorState? = null
        set(value) {
            field = value
            invalidateLinkColors()
        }

    /**
     * Color state for the icon.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var icon: Drawable? = null
        set(value) {
            field = value
            invalidateIcon()
        }

    /**
     * Color state for the icon.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var iconColors: ColorState? = null
        set(value) {
            field = value
            invalidateIconColors()
        }

    /**
     * Default style is [StaticNotificationStyle.Info]
     */
    var notificationStyle: StaticNotificationStyle = StaticNotificationStyle.Info
        set(value) {
            field = value
            invalidateBackground()
            invalidateTextColors()
            invalidateLinkColors()
            invalidateIconColors()
            invalidateIcon()
        }

    /**
     * Default style is [StaticNotificationStyle.Info]
     */
    var isBackgroundColorDefault: Boolean = false
        set(value) {
            field = value
            invalidateBackground()
        }

    /**
     * Show / hide icon it the top left corner
     */
    var isIconVisible: Boolean = true
        set(value) {
            field = value
            invalidateIconsVisibility()
        }

    /**
     * Show / hide icon it the top right corner
     */
    var isCloseIconVisible: Boolean = true
        set(value) {
            field = value
            invalidateIconsVisibility()
        }

    private val notificationTextView: TextView by lazy { findViewById(R.id.admiralToastTextView) }
    private val linkTextView: Link by lazy { findViewById(R.id.admiralLinkTextView) }
    private val iconImageView: ImageView by lazy { findViewById(R.id.admiralIcon) }
    private val iconCloseImageView: ImageView by lazy { findViewById(R.id.admiralCloseIcon) }
    private val rootView: ConstraintLayout by lazy { findViewById(R.id.custom_toast_container) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_notification_static, this)

        parseAttrs(attrs, R.styleable.StaticNotification).use {
            parseBackgroundColors(it)
            parseTextColors(it)
            parseLinkColors(it)
            parseIconColors(it)
            parseIcon(it)
            parseStyle(it)

            isEnabled = it.getBoolean(R.styleable.StaticNotification_enabled, true)
            linkText = it.getString(R.styleable.StaticNotification_admiralLinkText)

            notificationText = it.getString(R.styleable.StaticNotification_admiralText)
            maxLines = it.getInt(R.styleable.StaticNotification_android_maxLines, Int.MAX_VALUE)
            ellipsize = findTruncateAt(
                it.getIntOrNull(
                    R.styleable.StaticNotification_android_ellipsize,
                )
            )

            isIconVisible = it.getBoolean(R.styleable.StaticNotification_admiralIsIconVisible, true)
            isCloseIconVisible =
                it.getBoolean(R.styleable.StaticNotification_admiralIsCloseIconVisible, true)
        }

        backgroundTintList = colorStateList(
            enabled = Color.TRANSPARENT,
            disabled = Color.TRANSPARENT,
            pressed = Color.TRANSPARENT
        )
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

    override fun setEnabled(enabled: Boolean) {
        notificationTextView.isEnabled = enabled
        linkTextView.isEnabled = enabled
        iconImageView.isEnabled = enabled
        iconCloseImageView.isEnabled = enabled
        super.setEnabled(enabled)
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateBackground()
        invalidateTextColors()
        invalidateLinkColors()
        invalidateIconColors()
    }

    fun setLinkOnClickListener(onClickListener: OnClickListener) {
        linkTextView.setOnClickListener(onClickListener)
    }

    fun setOnIconCloseClickListener(onClickListener: OnClickListener) {
        iconCloseImageView.setOnClickListener(onClickListener)
    }

    private fun parseStyle(a: TypedArray) {
        notificationStyle = StaticNotificationStyle.fromIndex(
            a.getInt(R.styleable.StaticNotification_admiralNotificationStyle, 0)
        )
    }

    private fun parseBackgroundColors(a: TypedArray) {
        backgroundColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.StaticNotification_admiralBackgroundColorNormalEnabled),
            normalDisabled = a.getColorOrNull(R.styleable.StaticNotification_admiralBackgroundColorNormalDisabled),
            pressed = a.getColorOrNull(R.styleable.StaticNotification_admiralBackgroundColorPressed),
        )

        isBackgroundColorDefault =
            a.getBoolean(R.styleable.StaticNotification_admiralIsBackgroundDefault, false)
    }

    private fun parseTextColors(a: TypedArray) {
        notificationTextColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.StaticNotification_admiralTextColorNormalEnabled),
            normalDisabled = a.getColorOrNull(R.styleable.StaticNotification_admiralTextColorNormalDisabled),
            pressed = a.getColorOrNull(R.styleable.StaticNotification_admiralTextColorPressed),
        )
    }

    private fun parseLinkColors(a: TypedArray) {
        linkColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.StaticNotification_admiralLinkColorNormal),
            normalDisabled = a.getColorOrNull(R.styleable.StaticNotification_admiralLinkColorDisabled),
            pressed = a.getColorOrNull(R.styleable.StaticNotification_admiralLinkColorPressed),
        )
    }

    private fun parseIcon(a: TypedArray) {
        icon = a.getDrawable(R.styleable.StaticNotification_admiralIcon)
    }

    private fun parseIconColors(a: TypedArray) {
        linkColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.StaticNotification_admiralIconTintColorNormalEnabled),
            normalDisabled = a.getColorOrNull(R.styleable.StaticNotification_admiralIconTintColorNormalDisabled),
            pressed = a.getColorOrNull(R.styleable.StaticNotification_admiralIconTintColorPressed)
        )
    }

    private fun invalidateBackground() {
        var backgroundColor = when (notificationStyle) {
            StaticNotificationStyle.Info -> ThemeManager.theme.palette.backgroundSelected
            StaticNotificationStyle.Attention -> ThemeManager.theme.palette.backgroundAttention
            StaticNotificationStyle.Success -> ThemeManager.theme.palette.backgroundSuccess
            StaticNotificationStyle.Error -> ThemeManager.theme.palette.backgroundError
        }
        if (isBackgroundColorDefault) {
            backgroundColor = ThemeManager.theme.palette.backgroundAdditionalOne
        }

        val state = colorStateList(
            enabled = backgroundColors?.normalEnabled ?: backgroundColor,
            disabled = backgroundColors?.normalDisabled ?: backgroundColor.withAlpha(),
            pressed = backgroundColors?.pressed ?: backgroundColor
        )

        rootView.backgroundTintList = state
    }

    private fun invalidateTextColors() {
        val colorState = ColorState(
            normalEnabled = notificationTextColors?.normalEnabled
                ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = notificationTextColors?.normalDisabled
                ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = notificationTextColors?.pressed ?: ThemeManager.theme.palette.textPrimary
        )

        notificationTextView.textColor = colorState
    }

    private fun invalidateMaxLines() {
        notificationTextView.maxLines = maxLines
    }

    private fun invalidateEllipsize() {
        notificationTextView.ellipsize = ellipsize
    }

    private fun invalidateLinkColors() {
        val colorState = ColorState(
            normalEnabled = linkColors?.normalEnabled ?: ThemeManager.theme.palette.textAccent,
            normalDisabled = linkColors?.normalDisabled
                ?: ThemeManager.theme.palette.textAccent.withAlpha(),
            pressed = linkColors?.pressed ?: ThemeManager.theme.palette.textAccentPressed
        )

        linkTextView.textColor = colorState
    }

    private fun invalidateIcon() {
        val iconDefault = when (notificationStyle) {
            StaticNotificationStyle.Info -> drawable(R.drawable.admiral_ic_info_solid)
            StaticNotificationStyle.Attention -> drawable(R.drawable.admiral_ic_error_triangle_solid)
            StaticNotificationStyle.Success -> drawable(R.drawable.admiral_ic_check_solid)
            StaticNotificationStyle.Error -> drawable(R.drawable.admiral_ic_close_circle_solid)
        }

        val icon = icon ?: iconDefault

        iconImageView.setImageDrawable(icon)
    }

    private fun invalidateIconColors() {
        val iconColorDefault = when (notificationStyle) {
            StaticNotificationStyle.Info -> ThemeManager.theme.palette.elementAccent
            StaticNotificationStyle.Attention -> ThemeManager.theme.palette.elementAttention
            StaticNotificationStyle.Success -> ThemeManager.theme.palette.elementSuccess
            StaticNotificationStyle.Error -> ThemeManager.theme.palette.elementError
        }

        val colorState = colorStateList(
            enabled = iconColors?.normalEnabled ?: iconColorDefault,
            disabled = iconColors?.normalDisabled ?: iconColorDefault.withAlpha(),
            pressed = iconColors?.pressed ?: iconColorDefault
        )

        iconImageView.imageTintList = colorState

        iconCloseImageView.imageTintList = colorStateList(
            enabled = ThemeManager.theme.palette.elementPrimary,
            disabled = ThemeManager.theme.palette.elementPrimary.withAlpha(),
            pressed = ThemeManager.theme.palette.elementPrimary
        )
    }

    private fun invalidateIconsVisibility() {
        iconImageView.isVisible = isIconVisible
        iconCloseImageView.isVisible = isCloseIconVisible
    }

    private companion object {
        fun findTruncateAt(value: Int?): TruncateAt? =
            TruncateAt.values().find { it.ordinal == (value?.minus(1)) }
    }
}