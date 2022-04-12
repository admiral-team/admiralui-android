package com.admiral.uikit.components.calendar

import android.view.View
import com.admiral.uikit.R
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.layout.LinearLayout
import com.admiral.uikit.view.calendar.ui.ViewContainer

internal class MonthHeaderFooterViewContainer(view: View) : ViewContainer(view) {
    val legendLayout: LinearLayout = view.findViewById(R.id.legendLayout)
    val monthYearTextView: TextView = view.findViewById(R.id.monthYearTextView)
}