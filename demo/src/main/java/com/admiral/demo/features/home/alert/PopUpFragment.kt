package com.admiral.demo.features.home.alert

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtPopUpBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.themes.ColorPaletteEnum
import com.admiral.uikit.components.button.Button
import com.admiral.uikit.components.dialogs.AlertDialog

class PopUpFragment : BaseFragment(R.layout.fmt_pop_up) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtPopUpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)
        binding.buttonShowBottomSheet.setOnClickListener {
            showDialogFragment()
        }

        binding.hintOpenDialog.compoundDrawablesNormalEnabledPalette =
            ColorPaletteEnum.ELEMENT_ACCENT
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}