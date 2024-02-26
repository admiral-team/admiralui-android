package com.admiral.demo.features.home.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtCalendarPickBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.CalendarHorizontalScreen
import com.admiral.demo.screen.CalendarVerticalScreen

class CalendarFragment : BaseFragment(
    layoutId = R.layout.fmt_calendar_pick,
    menuId = R.menu.menu_appbar_info
) {

    private val binding by viewBinding(FmtCalendarPickBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnHorizontal.setOnClickListener {
            navigationViewModel.open(CalendarHorizontalScreen())
        }

        binding.btnVertical.setOnClickListener {
            navigationViewModel.open(CalendarVerticalScreen())
        }
    }
}