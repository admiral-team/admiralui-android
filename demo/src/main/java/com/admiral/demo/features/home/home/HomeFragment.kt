package com.admiral.demo.features.home.home

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtHomeBinding
import com.admiral.demo.features.home.theme.ThemeListMode
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.BadgesScreen
import com.admiral.demo.screen.BottomSheetScreen
import com.admiral.demo.screen.ButtonsScreen
import com.admiral.demo.screen.CalendarScreen
import com.admiral.demo.screen.CellsScreen
import com.admiral.demo.screen.ChatScreen
import com.admiral.demo.screen.CheckBoxScreen
import com.admiral.demo.screen.CurrencyScreen
import com.admiral.demo.screen.IconsScreen
import com.admiral.demo.screen.InformersNotificationsScreen
import com.admiral.demo.screen.LinksScreen
import com.admiral.demo.screen.PageControlScreen
import com.admiral.demo.screen.PopUpScreen
import com.admiral.demo.screen.RadioButtonScreen
import com.admiral.demo.screen.ShimmerScreen
import com.admiral.demo.screen.SpinnerScreen
import com.admiral.demo.screen.StepperScreen
import com.admiral.demo.screen.SwitchScreen
import com.admiral.demo.screen.TabsScreen
import com.admiral.demo.screen.TagsScreen
import com.admiral.demo.screen.TextFieldsScreen
import com.admiral.demo.screen.ThemeListScreen
import com.admiral.demo.screen.TimePickerScreen
import com.admiral.demo.screen.ToolbarScreen
import com.admiral.uikit.components.cell.BaseCell
import com.admiral.uikit.components.cell.unit.TitleSubtitleCellUnit
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment : BaseFragment(R.layout.fmt_home) {

    private val binding by viewBinding(FmtHomeBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    @Suppress("LongMethod")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.search.textFlow.debounce(DEBOUNCE_TIME)
            .distinctUntilChanged()
            .onEach { filter ->
                if (filter != null) {
                    binding.rootList.children.forEach { view ->
                        if (view is BaseCell) {
                            val cellUnit = (view[1] as? TitleSubtitleCellUnit) ?: return@forEach
                            val isTitleContains = cellUnit.title
                                ?.contains(filter.toString().trim(), ignoreCase = true) ?: false
                            val isSubtitleContains = cellUnit.subtitle
                                ?.contains(filter.toString().trim(), ignoreCase = true) ?: false

                            view.isVisible = isTitleContains || isSubtitleContains
                        }
                    }
                }
            }
            .launchIn(lifecycleScope)

        binding.chatButton.setOnClickListener {
            navigationViewModel.open(ChatScreen())
        }

        binding.tagsButton.setOnClickListener {
            navigationViewModel.open(TagsScreen())
        }

        binding.buttonsButton.setOnClickListener {
            navigationViewModel.open(ButtonsScreen())
        }

        binding.tabsButton.setOnClickListener {
            navigationViewModel.open(TabsScreen())
        }

        binding.toolbarButton.setOnClickListener {
            navigationViewModel.open(ToolbarScreen())
        }

        binding.bottomSheetButton.setOnClickListener {
            navigationViewModel.open(BottomSheetScreen())
        }

        binding.calendarButton.setOnClickListener {
            navigationViewModel.open(CalendarScreen())
        }

        binding.stepperButton.setOnClickListener {
            navigationViewModel.open(StepperScreen())
        }

        binding.radioButtonsButton.setOnClickListener {
            navigationViewModel.open(RadioButtonScreen())
        }

        binding.checkBoxButton.setOnClickListener {
            navigationViewModel.open(CheckBoxScreen())
        }

        binding.switchButton.setOnClickListener {
            navigationViewModel.open(SwitchScreen())
        }

        binding.popUpButton.setOnClickListener {
            navigationViewModel.open(PopUpScreen())
        }

        binding.textFieldsButton.setOnClickListener {
            navigationViewModel.open(TextFieldsScreen())
        }

        binding.cellsButton.setOnClickListener {
            navigationViewModel.open(CellsScreen())
        }

        binding.shimmerButton.setOnClickListener {
            navigationViewModel.open(ShimmerScreen())
        }

        binding.informerButton.setOnClickListener {
            navigationViewModel.open(InformersNotificationsScreen())
        }

        binding.linksButton.setOnClickListener {
            navigationViewModel.open(LinksScreen())
        }

        binding.spinnerButton.setOnClickListener {
            navigationViewModel.open(SpinnerScreen())
        }

        binding.badgesButton.setOnClickListener {
            navigationViewModel.open(BadgesScreen())
        }

        binding.pageControlButton.setOnClickListener {
            navigationViewModel.open(PageControlScreen())
        }

        binding.themeButton.setOnClickListener {
            navigationViewModel.open(ThemeListScreen(ThemeListMode.SHOW))
        }

        binding.iconsButton.setOnClickListener {
            navigationViewModel.open(IconsScreen())
        }

        binding.timePickerButton.setOnClickListener {
            navigationViewModel.open(TimePickerScreen())
        }

        binding.currencyButton.setOnClickListener {
            navigationViewModel.open(CurrencyScreen())
        }
    }

    private companion object {
        const val DEBOUNCE_TIME = 300L
    }
}