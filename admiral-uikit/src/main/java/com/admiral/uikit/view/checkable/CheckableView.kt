package com.admiral.uikit.view.checkable

import android.view.View
import android.widget.Checkable

interface CheckableView : Checkable {
    fun setCheckable(checkable: Boolean)
    fun isCheckable(): Boolean
    fun addOnCheckChangeListener(onCheckedChangeListener: OnCheckedChangeListener)
    fun removeOnCheckChangeListener(onCheckedChangeListener: OnCheckedChangeListener)

    interface OnCheckedChangeListener {
        fun onCheckedChanged(view: View, isChecked: Boolean)
    }
}