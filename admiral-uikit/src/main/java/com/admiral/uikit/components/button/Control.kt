package com.admiral.uikit.components.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isGone
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.ext.parseAttrs

class Control @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Text of main button.
     */
    var actionButtonText: String? = null
        set(value) {
            field = value
            actionButton.text = value
            actionButton.isGone = value.isNullOrEmpty()
        }

    /**
     * Text of additional button.
     */
    var secondaryButtonText: String? = null
        set(value) {
            field = value
            secondaryButton.text = value
            secondaryButton.isGone = value.isNullOrEmpty()
        }

    /**
     * Rule text of ButtonRule.
     */
    var ruleText: String? = null
        set(value) {
            field = value
            ruleButton.ruleText = value
        }

    /**
     * ButtonText text of ButtonRule.
     */
    var ruleTextAction: String? = null
        set(value) {
            field = value
            ruleButton.actionText = value
        }

    val ruleButton: ButtonRule by lazy { findViewById(R.id.admiralControlRule) }
    val actionButton: Button by lazy { findViewById(R.id.admiralControlAction) }
    val secondaryButton: Button by lazy { findViewById(R.id.admiralControlAlternative) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_button_control, this)

        parseAttrs(attrs, R.styleable.Control).use {
            ruleText = it.getString(R.styleable.Control_admiralTextAction)
            ruleTextAction = it.getString(R.styleable.Control_admiralRuleText)
            actionButtonText = it.getString(R.styleable.Control_admiralActionButtonText)
            secondaryButtonText = it.getString(R.styleable.Control_admiralSecondaryButtonText)
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

    override fun onThemeChanged(theme: Theme) {}

    override fun setEnabled(enabled: Boolean) {
        ruleButton.isEnabled = enabled
        actionButton.isEnabled = enabled
        secondaryButton.isEnabled = enabled
        super.setEnabled(enabled)
    }
}