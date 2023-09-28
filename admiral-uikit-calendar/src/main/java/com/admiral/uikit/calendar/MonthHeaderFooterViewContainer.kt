package com.admiral.uikit.calendar

import android.view.View
import com.admiral.uikit.calendar.R
import com.admiral.uikit.core.layout.LinearLayout
import com.admiral.uikit.textview.TextView
import com.admiral.uikit.calendar.view.ui.ViewContainer

internal class MonthHeaderFooterViewContainer(view: View) : ViewContainer(view) {
    val legendLayout: LinearLayout = view.findViewById(R.id.legendLayout)
    val monthYearTextView: TextView = view.findViewById(R.id.monthYearTextView)
}