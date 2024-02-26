package com.admiral.demo.features.home.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtCalendarVerticalBinding
import com.admiral.demo.features.main.NavigationViewModel

class CalendarVerticalFragment : BaseFragment(
    layoutId = R.layout.fmt_calendar_vertical,
    menuId = R.menu.menu_appbar_info
) {

    private val binding by viewBinding(FmtCalendarVerticalBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)
        initCalendar()
    }

    private fun initCalendar() = with(binding) {
        verticalCalendar.calendarState = MockCalendarState
    }
}