package com.admiral.demo.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtHomeBinding
import com.admiral.demo.features.home.theme.utils.ThemeStorageDAO
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.common.components.cell.base.CellUnitType
import com.admiral.uikit.compose.cell.BaseCell
import com.admiral.uikit.compose.cell.unit.IconCellUnitCompose
import com.admiral.uikit.compose.cell.unit.TitleSubtitleCellUnitCompose
import com.admiral.uikit.compose.switcher
import com.admiral.uikit.compose.tabs.LogoTabs
import com.admiral.uikit.compose.util.DIMEN_X8
import com.admiral.resources.R as res

class ComposeHomeFragment : BaseFragment(R.layout.fmt_home) {

    private val binding by viewBinding(FmtHomeBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = DIMEN_X8)
                ) {
                    val curTheme = ThemeManagerCompose.theme.value
                    val isThemeDark = ThemeStorageDAO.getThemes()[0] == curTheme

                    val switch = switcher(checked = isThemeDark)
                    if (switch) {
                        ThemeManagerCompose.setCurrentTheme(ThemeStorageDAO.getThemes()[0])
                    } else {
                        ThemeManagerCompose.setCurrentTheme(ThemeStorageDAO.getThemes()[1])
                    }

                    LogoTabs(
                        items = listOf(
                            painterResource(id = R.drawable.test_ic_visa),
                            painterResource(id = R.drawable.test_ic_master_card),
                            painterResource(id = R.drawable.test_ic_mir),
                        )
                    )
                    BaseCell(
                        children = listOf(
                            TitleSubtitleCellUnitCompose(
                                unitType = CellUnitType.LEADING,
                                titleText = "Checkbox",
                                subtitleText = "Селектор"
                            ),
                            IconCellUnitCompose(
                                unitType = CellUnitType.TRAILING,
                                icon = painterResource(id = res.drawable.admiral_ic_chevron_right_outline)
                            )
                        ),
                        onClick = { }
                    )
                }
            }
        }
    }
}