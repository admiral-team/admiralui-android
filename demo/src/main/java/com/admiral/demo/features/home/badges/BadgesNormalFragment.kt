package com.admiral.demo.features.home.badges

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtBadgesNormalBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class BadgesNormalFragment : BaseFragment(R.layout.fmt_badges_normal) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtBadgesNormalBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        initTabs()
        initInputs()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }

    private fun initTabs() {
        fun updateViewsIsEnabledState(isEnabled: Boolean) {
            with(binding) {
                admiralBadge.isEnabled = isEnabled
                admiralBadge1.isEnabled = isEnabled
                admiralBadge2.isEnabled = isEnabled
                admiralBadge3.isEnabled = isEnabled
                admiralBadge4.isEnabled = isEnabled
                admiralBadge5.isEnabled = isEnabled

                inputNumber.isEnabled = isEnabled
                inputNumber1.isEnabled = isEnabled
                inputNumber2.isEnabled = isEnabled
                inputNumber3.isEnabled = isEnabled
                inputNumber4.isEnabled = isEnabled
                inputNumber5.isEnabled = isEnabled
            }
        }

        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    R.id.defaultTab -> updateViewsIsEnabledState(true)
                    R.id.disabledTab -> updateViewsIsEnabledState(false)
                }
            }
        }
    }

    private fun initInputs() {
        with(binding) {
            inputNumber.onValueChange = { _, new ->
                admiralBadge.text = new.toString()
            }
            inputNumber1.onValueChange = { _, new ->
                admiralBadge1.text = new.toString()
            }
            inputNumber2.onValueChange = { _, new ->
                admiralBadge2.text = new.toString()
            }
            inputNumber3.onValueChange = { _, new ->
                admiralBadge3.text = new.toString()
            }
            inputNumber4.onValueChange = { _, new ->
                admiralBadge4.text = new.toString()
            }
            inputNumber5.onValueChange = { _, new ->
                admiralBadge5.text = new.toString()
            }
        }
    }
}