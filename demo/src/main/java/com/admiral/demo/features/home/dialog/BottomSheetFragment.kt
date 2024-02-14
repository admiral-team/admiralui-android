package com.admiral.demo.features.home.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtBottomSheetBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.themes.ColorPaletteEnum
import com.admiral.uikit.components.button.Button
import com.admiral.uikit.components.dialogs.AdmiralBottomSheetDialog
import com.admiral.uikit.components.text.TextView

class BottomSheetFragment : BaseFragment(
    layoutId = R.layout.fmt_bottom_sheet,
    menuId = R.menu.menu_appbar_info
) {

    val binding by viewBinding(FmtBottomSheetBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

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
        val admiralBottomDialog = AdmiralBottomSheetDialog
            .Builder(requireContext())
            .setContent(R.layout.fmt_bottom_sheet_dialog)
            .apply()

        val title: TextView = admiralBottomDialog.dialog.findViewById(R.id.title)
        val btnReady: Button = admiralBottomDialog.dialog.findViewById(R.id.btn_ready)

        btnReady.setOnClickListener {
            admiralBottomDialog.hide()
        }

        admiralBottomDialog.show()
    }

    private fun showDialogFragment() {
        val bottomSheetFragment = BottomSheetDialogFragment()
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }
}