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
import com.admiral.uikit.components.checkbox.CheckBox
import com.admiral.uikit.components.links.Link
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.parseAttrs

class ButtonRule @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Main text of button.
     */
    var actionText: String? = null
        set(value) {
            field = value
            ruleButton.text = value
        }

    /**
     * Additional text of button.
     */
    var ruleText: String? = null
        set(value) {
            field = value
            ruleTextView.text = value
            ruleTextView.isGone = value.isNullOrEmpty()
        }

    val ruleButton: Link by lazy { findViewById(R.id.ruleButton) }
    val ruleTextView: TextView by lazy { findViewById(R.id.ruleText) }
    val ruleCheckBox: CheckBox by lazy { findViewById(R.id.ruleCheckBox) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_button_rule, this)

        parseAttrs(attrs, R.styleable.ButtonRule).use {
            actionText = it.getString(R.styleable.ButtonRule_admiralTextAction)
            ruleText = it.getString(R.styleable.ButtonRule_admiralRuleText)
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

    override fun onThemeChanged(theme: Theme) {
        // TODO
    }

    override fun setEnabled(enabled: Boolean) {
        ruleButton.isEnabled = enabled
        ruleTextView.isEnabled = enabled
        ruleCheckBox.isEnabled = enabled
        super.setEnabled(enabled)
    }
}