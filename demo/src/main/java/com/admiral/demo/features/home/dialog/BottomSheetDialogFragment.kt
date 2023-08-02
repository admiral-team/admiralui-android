package com.admiral.demo.features.home.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.databinding.FmtBottomSheetDialogBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.components.dialogsfragment.AdmiralBottomSheetDialogFragment

class BottomSheetDialogFragment :
    AdmiralBottomSheetDialogFragment(
        layoutResId = R.layout.fmt_bottom_sheet_dialog,
        isFullScreen = false,
    ) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtBottomSheetDialogBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTitle()
        initButtonReady()
        initButtonChooseAllCards()
        initBaseCells()
    }

    private fun initTitle() {
        with(binding) {
            // TODO refactor: split title with closing image to TextView and ImageView and remove this OnTouchListener
            title.setOnTouchListener(View.OnTouchListener { v, event ->
                if (event.rawX >= title.right - title.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    dismiss()
                    return@OnTouchListener true
                }
                false
            })
        }
    }

    private fun initButtonChooseAllCards() {
        with(binding) {
            btnChooseAllCards.setOnClickListener {
                checkBoxCellUnit1.isChecked = true
                checkBoxCellUnit2.isChecked = true
                checkBoxCellUnit3.isChecked = true
                checkBoxCellUnit4.isChecked = true
                checkBoxCellUnit5.isChecked = true
                checkBoxCellUnit6.isChecked = true
            }
        }
    }

    private fun initButtonReady() {
        binding.btnReady.setOnClickListener {
            dismiss()
        }
    }

    private fun initBaseCells() {
        with(binding) {
            baseCell1.setOnClickListener {
                checkBoxCellUnit1.isChecked = !checkBoxCellUnit1.isChecked
            }
            baseCell2.setOnClickListener {
                checkBoxCellUnit2.isChecked = !checkBoxCellUnit2.isChecked
            }
            baseCell3.setOnClickListener {
                checkBoxCellUnit3.isChecked = !checkBoxCellUnit3.isChecked
            }
            baseCell4.setOnClickListener {
                checkBoxCellUnit4.isChecked = !checkBoxCellUnit4.isChecked
            }
            baseCell5.setOnClickListener {
                checkBoxCellUnit5.isChecked = !checkBoxCellUnit5.isChecked
            }
            baseCell6.setOnClickListener {
                checkBoxCellUnit6.isChecked = !checkBoxCellUnit6.isChecked
            }
        }
    }

    companion object {
        const val DRAWABLE_RIGHT = 2
    }
}