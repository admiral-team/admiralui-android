package com.admiral.demo.features.home.alert

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtAlertsAlertBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.components.button.Button
import com.admiral.uikit.components.dialogs.AlertDialog

class AlertFragment : BaseFragment(
    layoutId = R.layout.fmt_alerts_alert,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtAlertsAlertBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)
        binding.buttonShowBottomSheet.setOnClickListener {
            showDialogFragment()
        }
    }

    private fun showDialog() {
        val dialog =
            AlertDialog.Builder(requireContext()).setContent(R.layout.admiral_view_pop_up_dialog).apply().dialog

        val showBtn: Button = dialog.findViewById(R.id.buttonAction)
        val cancelBtn: Button = dialog.findViewById(R.id.buttonAlternative)
        showBtn.text = "hello main button"
        cancelBtn.text = "hello cancel button"
        showBtn.setOnClickListener {
            dialog.hide()
        }
        dialog.show()
    }

    fun showDialogFragment() {
        val bottomSheetFragment = PopUpDialogFragment()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }
}