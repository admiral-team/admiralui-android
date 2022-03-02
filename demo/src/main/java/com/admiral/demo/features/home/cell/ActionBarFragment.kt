package com.admiral.demo.features.home.cell

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtCellsActionbarBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.components.actionbar.ActionBarOnSwipeTouchListener
import com.admiral.uikit.view.checkable.CheckableGroup

class ActionBarFragment : BaseFragment(R.layout.fmt_cells_actionbar) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtCellsActionbarBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        initTabs()
        initActionBar()
        initTitleCell()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }

    private fun initTabs() {
        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    binding.defaultTab.id -> {
                        binding.actionBar.isEnabled = true
                    }
                    binding.disabledTab.id -> {
                        binding.actionBar.isEnabled = false
                    }
                }
            }
        }
    }

    private fun initActionBar() {
        with(binding.actionBar) {
            initDefaultViews()
            dots.setOnClickListener {
                showToast(getString(R.string.actionbar_more_clicked))
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTitleCell() {
        binding.titleCell.setOnTouchListener(
            object : ActionBarOnSwipeTouchListener(binding.titleCell, binding.actionBar) {}
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
