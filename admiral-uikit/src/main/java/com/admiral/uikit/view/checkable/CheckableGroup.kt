package com.admiral.uikit.view.checkable

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.content.res.use
import com.admiral.uikit.R
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.layout.LinearLayout

open class CheckableGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var onCheckedChangeListener: OnCheckedChangeListener? = null

    private var checkedId = View.NO_ID
    private var checkedChangeBlocked = false
    private val passThroughListener = PassThroughHierarchyChangeListener()
    private val childOnCheckedChangeListener = CheckedStateTracker()
    private val childViewsMap = mutableMapOf<Int, View>()

    init {
        parseAttrs(attrs, R.styleable.CheckableGroup).use {
            checkedId = it.getResourceId(R.styleable.CheckableGroup_checkedId, View.NO_ID)
        }

        super.setOnHierarchyChangeListener(passThroughListener)
    }

    override fun addView(child: View) {
        if (child is CheckableView) {
            if (child.isChecked) {
                protectFromCheckedChange {
                    if (checkedId != View.NO_ID) {
                        setCheckedStateForView(checkedId, false)
                    }
                }
                setCheckedId(child.id, true)
            }
        } else {
            throw IllegalStateException("Expected view with type of CheckableView")
        }

        super.addView(child)
    }

    override fun setOnHierarchyChangeListener(listener: OnHierarchyChangeListener) {
        // the user listener is delegated to our pass-through listener
        passThroughListener.onHierarchyChangeListener = listener
    }

    override fun generateLayoutParams(attrs: AttributeSet) = LayoutParams(context, attrs)

    override fun checkLayoutParams(p: ViewGroup.LayoutParams) = p is LayoutParams

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (checkedId != View.NO_ID) {
            protectFromCheckedChange {
                setCheckedStateForView(checkedId, true)
            }
            setCheckedId(checkedId, true)
        }
    }

    open fun setCheckedId(@IdRes id: Int, isChecked: Boolean) {
        checkedId = id
        onCheckedChangeListener?.onCheckedChanged(childViewsMap[id], isChecked, checkedId)
    }

    fun getCheckedId(): Int {
        return checkedId
    }

    fun clearCheck() = check(View.NO_ID)

    fun check(@IdRes id: Int) {
        if (id != View.NO_ID && id == checkedId) return

        if (id != View.NO_ID) {
            setCheckedStateForView(id, true)
        }
    }

    private fun setCheckedStateForView(viewId: Int, checked: Boolean) {
        val view: View? = childViewsMap[viewId] ?: findViewById<View>(viewId)?.let {
            it.also {
                childViewsMap[viewId] = it
            }
        }

        (view as? CheckableView)?.isChecked = checked
    }

    private fun protectFromCheckedChange(action: () -> Unit) {
        checkedChangeBlocked = true
        action.invoke()
        checkedChangeBlocked = false
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int)
    }

    private inner class CheckedStateTracker : CheckableView.OnCheckedChangeListener {
        override fun onCheckedChanged(view: View, isChecked: Boolean) {
            // prevents from infinite recursion
            if (checkedChangeBlocked) return

            protectFromCheckedChange {
                if (checkedId != View.NO_ID) {
                    setCheckedStateForView(checkedId, false)
                }
            }

            setCheckedId(view.id, isChecked)
        }
    }

    private inner class PassThroughHierarchyChangeListener : OnHierarchyChangeListener {
        var onHierarchyChangeListener: OnHierarchyChangeListener? = null

        override fun onChildViewAdded(parent: View, child: View) {
            if (parent === this@CheckableGroup && child is CheckableView) {
                if (child.id == View.NO_ID) {
                    child.id = View.generateViewId()
                }

                child.addOnCheckChangeListener(childOnCheckedChangeListener)

                childViewsMap[child.id] = child
            }

            onHierarchyChangeListener?.onChildViewAdded(parent, child)
        }

        override fun onChildViewRemoved(parent: View, child: View) {
            if (parent === this@CheckableGroup && child is CheckableView) {
                child.removeOnCheckChangeListener(childOnCheckedChangeListener)
            }
            childViewsMap.remove(child.id)
            onHierarchyChangeListener?.onChildViewRemoved(parent, child)
        }
    }
}