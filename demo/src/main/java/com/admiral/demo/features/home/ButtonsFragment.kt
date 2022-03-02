package com.admiral.demo.features.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtButtonsBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class ButtonsFragment : BaseFragment(R.layout.fmt_buttons) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtButtonsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabsState.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    R.id.defaultTab -> {
                        binding.btnPrimaryBig.isEnabled = true
                        binding.btnPrimarySmall.isEnabled = true
                        binding.btnPrimaryMedium.isEnabled = true
                        binding.btnSecondaryBig.isEnabled = true
                        binding.btnSecondarySmall.isEnabled = true
                        binding.btnSecondaryMedium.isEnabled = true
                        binding.btnGhostBig.isEnabled = true
                        binding.btnGhostMedium.isEnabled = true
                        binding.btnGhostAddition.isEnabled = true
                        binding.btnPrimaryAddition.isEnabled = true
                        binding.rule.isEnabled = true
                        binding.btnGoogle.isEnabled = true
                    }
                    R.id.disabledTab -> {
                        binding.btnPrimaryBig.isEnabled = false
                        binding.btnPrimarySmall.isEnabled = false
                        binding.btnPrimaryMedium.isEnabled = false
                        binding.btnSecondaryBig.isEnabled = false
                        binding.btnSecondaryMedium.isEnabled = false
                        binding.btnSecondarySmall.isEnabled = false
                        binding.btnGhostBig.isEnabled = false
                        binding.btnGhostMedium.isEnabled = false
                        binding.btnGhostAddition.isEnabled = false
                        binding.btnPrimaryAddition.isEnabled = false
                        binding.rule.isEnabled = false
                        binding.btnGoogle.isEnabled = false
                    }
                }
            }
        }

        binding.rule.ruleTextView.setOnClickListener {
            binding.rule.ruleCheckBox.isChecked = !binding.rule.ruleCheckBox.isChecked
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}