package com.admiral.uikit.view.checkable

import android.content.Context
import android.util.AttributeSet
import android.view.SoundEffectConstants
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.widget.LinearLayout
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityEventCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.children

open class CheckableLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), CheckableView {

    private var checkable = true
    private var isViewChecked: Boolean = false
    private val onCheckedChangeListeners = ArrayList<CheckableView.OnCheckedChangeListener>()

    init {
        ViewCompat.setAccessibilityDelegate(
            this,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityEvent(host: View, event: AccessibilityEvent) {
                    super.onInitializeAccessibilityEvent(host, event)
                    event.isChecked = isChecked
                }

                override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    info.isCheckable = isCheckable()
                    info.isChecked = isChecked
                }
            })
    }

    /**
     * Sets view to be checkable or not.
     */
    override fun setCheckable(checkable: Boolean) {
        if (this.checkable != checkable) {
            this.checkable = checkable
            sendAccessibilityEvent(AccessibilityEventCompat.CONTENT_CHANGE_TYPE_UNDEFINED)
        }
    }

    /**
     * Returns whether the view is checkable.
     */
    override fun isCheckable(): Boolean {
        return checkable
    }

    /**
     * Sets view to be checked or not.
     */
    override fun setChecked(checked: Boolean) {
        if (isViewChecked != checked) {
            isViewChecked = checked
            refreshDrawableState()
            onCheckedChangeListeners.forEach { it.onCheckedChanged(this, checked) }

            children.forEach {
                if (it is CheckableView) {
                    it.isChecked = checked
                }
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        children.forEach { it.isEnabled = enabled }
    }

    /**
     * Returns whether the view is checked.
     */
    override fun isChecked(): Boolean {
        return isViewChecked
    }

    /**
     * Changes view checked status.
     */
    override fun toggle() {
        if (!isChecked) {
            isChecked = !isViewChecked
        }
    }

    override fun performClick(): Boolean {
        toggle()
        val handled = super.performClick()
        if (!handled) {
            // View only makes a sound effect if the onClickListener was
            // called, so we'll need to make one here instead.
            playSoundEffect(SoundEffectConstants.CLICK)
        }
        return handled
    }

    override fun addOnCheckChangeListener(onCheckedChangeListener: CheckableView.OnCheckedChangeListener) {
        onCheckedChangeListeners.add(onCheckedChangeListener)
    }

    override fun removeOnCheckChangeListener(onCheckedChangeListener: CheckableView.OnCheckedChangeListener) {
        onCheckedChangeListeners.remove(onCheckedChangeListener)
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray? {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)

        if (isChecked) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }

        return drawableState
    }

    private companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }
}