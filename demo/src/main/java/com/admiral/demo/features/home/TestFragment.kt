package com.admiral.demo.features.home

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtButtonsBinding
import com.admiral.demo.databinding.FmtTestBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.common.components.cell.base.CellUnitType
import com.admiral.uikit.components.cell.unit.CardCellUnit
import com.admiral.uikit.components.cell.unit.IconCellUnit
import com.admiral.uikit.components.cell.unit.SubtitleTitleCellUnit
import com.admiral.uikit.view.checkable.CheckableGroup

class TestFragment : BaseFragment(R.layout.fmt_test) {

    private val binding by viewBinding(FmtTestBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.baseCell.apply {
//            addView(
//                IconCellUnit(requireContext()).apply {
//                    unitType = CellUnitType.LEADING
//                    icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_diamond_outline)
//                }
//            )
//            addView(
//                SubtitleTitleCellUnit(requireContext()).apply {
//                    unitType = CellUnitType.LEADING_TEXT
//                    title = "TitleCellUnit"
//                    subtitle = "SubtitleTitleCellUnit"
//                }
//            )
//            addView(
//                CardCellUnit(requireContext()).apply {
//                    unitType = CellUnitType.TRAILING
//                    icon = ContextCompat.getDrawable(context, R.drawable.test_ic_card_start)
//                }
//            )
            addViews(
                listOf(
                    SubtitleTitleCellUnit(requireContext()).apply {
                        unitType = CellUnitType.LEADING_TEXT
                        title = "TitleCellUnit"
                        subtitle = "SubtitleTitleCellUnit"
                    },
                    CardCellUnit(requireContext()).apply {
                        unitType = CellUnitType.TRAILING
                        icon = ContextCompat.getDrawable(context, R.drawable.test_ic_card_start)
                    }
                )
            )
        }
    }
}