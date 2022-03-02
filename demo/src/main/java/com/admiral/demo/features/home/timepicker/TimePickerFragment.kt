package com.admiral.demo.features.home.timepicker

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTimePickerBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.components.timepicker.MaterialTimePicker
import com.admiral.uikit.view.checkable.CheckableGroup

class TimePickerFragment : BaseFragment(R.layout.fmt_time_picker), ThemeObserver {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtTimePickerBinding::bind)

    private val materialTimePicker = MaterialTimePicker.Builder()
        .setHour(DEFAULT_HOURS)
        .setMinute(DEFAULT_MINUTES)
        .build().also { timePicker ->
            timePicker.addOnPositiveButtonClickListener {
                updateTimeValue(timePicker.hour, timePicker.minute)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        initTabs()
        initButton()
        initTimeValue()
        ThemeManager.subscribe(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ThemeManager.unsubscribe(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateTimeValueBackgroundColor()
    }

    private fun initTabs() {
        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    R.id.defaultTab -> {
                        binding.time.isEnabled = true
                        binding.timeValue.isEnabled = true
                        binding.button.isEnabled = true
                    }
                    R.id.disabledTab -> {
                        binding.time.isEnabled = false
                        binding.timeValue.isEnabled = false
                        binding.button.isEnabled = false
                    }
                }
            }
        }
    }

    private fun initButton() {
        binding.button.setOnClickListener {
            materialTimePicker.show(childFragmentManager, "picker")
        }
    }

    private fun initTimeValue() {
        updateTimeValue(DEFAULT_HOURS, DEFAULT_MINUTES)
    }

    private fun updateTimeValue(hour: Int, minute: Int) {
        binding.timeValue.text = getString(
            R.string.timepicker_time_value,
            hour,
            minute
        )
    }

    private fun invalidateTimeValueBackgroundColor() {
        binding.timeValue.backgroundTintList =
            ColorStateList.valueOf(ThemeManager.theme.palette.backgroundAdditionalOne)
        binding.timeValue.invalidate()
    }

    companion object {
        private const val DEFAULT_HOURS = 12
        private const val DEFAULT_MINUTES = 10
    }
}