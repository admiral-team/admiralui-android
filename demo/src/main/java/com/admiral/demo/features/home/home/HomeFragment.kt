package com.admiral.demo.features.home.home

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.features.home.theme.ThemeListMode
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.AlertsOnboardingScreen
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
import com.admiral.demo.screen.RadioButtonScreen
import com.admiral.demo.screen.ShimmerScreen
import com.admiral.demo.screen.SpinnerScreen
import com.admiral.demo.screen.SwitchScreen
import com.admiral.demo.screen.TabsScreen
import com.admiral.demo.screen.TagsScreen
import com.admiral.demo.screen.TextFieldsScreen
import com.admiral.demo.screen.ThemeListScreen
import com.admiral.demo.screen.TimePickerScreen
import com.admiral.demo.screen.ToolbarScreen
import com.admiral.uikit.components.cell.BaseCell
import com.admiral.uikit.components.cell.unit.TitleSubtitleCellUnit
import com.admiral.demo.R
import com.admiral.demo.databinding.FmtHomeBinding
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment : BaseFragment(R.layout.fmt_home) {

    private val binding by viewBinding(FmtHomeBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    @OptIn(FlowPreview::class)
    @Suppress("LongMethod", "OPT_IN_IS_NOT_ENABLED")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            registerToolbar(toolbar, true, navigationViewModel::close)

            search.textFlow.debounce(DEBOUNCE_TIME)
                .distinctUntilChanged()
                .onEach { filter ->
                    if (filter != null) {
                        rootList.children.forEach { view ->
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
                }.launchIn(lifecycleScope)

            chatButton.setOnClickListener {
                navigationViewModel.open(ChatScreen())
            }

            tagsButton.setOnClickListener {
                navigationViewModel.open(TagsScreen())
            }

            buttonsButton.setOnClickListener {
                navigationViewModel.open(ButtonsScreen())
            }

            tabsButton.setOnClickListener {
                navigationViewModel.open(TabsScreen())
            }

            toolbarButton.setOnClickListener {
                navigationViewModel.open(ToolbarScreen())
            }

            bottomSheetButton.setOnClickListener {
                navigationViewModel.open(BottomSheetScreen())
            }

            calendarButton.setOnClickListener {
                navigationViewModel.open(CalendarScreen())
            }

            radioButtonsButton.setOnClickListener {
                navigationViewModel.open(RadioButtonScreen())
            }

            checkBoxButton.setOnClickListener {
                navigationViewModel.open(CheckBoxScreen())
            }

            switchButton.setOnClickListener {
                navigationViewModel.open(SwitchScreen())
            }

            alertsButton.setOnClickListener {
                navigationViewModel.open(AlertsOnboardingScreen())
            }

            textFieldsButton.setOnClickListener {
                navigationViewModel.open(TextFieldsScreen())
            }

            cellsButton.setOnClickListener {
                navigationViewModel.open(CellsScreen())
            }

            shimmerButton.setOnClickListener {
                navigationViewModel.open(ShimmerScreen())
            }

            informerButton.setOnClickListener {
                navigationViewModel.open(InformersNotificationsScreen())
            }

            linksButton.setOnClickListener {
                navigationViewModel.open(LinksScreen())
            }

            spinnerButton.setOnClickListener {
                navigationViewModel.open(SpinnerScreen())
            }

            badgesButton.setOnClickListener {
                navigationViewModel.open(BadgesScreen())
            }

            pageControlButton.setOnClickListener {
                navigationViewModel.open(PageControlScreen())
            }

            themeButton.setOnClickListener {
                navigationViewModel.open(ThemeListScreen(ThemeListMode.SHOW))
            }

            iconsButton.setOnClickListener {
                navigationViewModel.open(IconsScreen())
            }

            timePickerButton.setOnClickListener {
                navigationViewModel.open(TimePickerScreen())
            }

            currencyButton.setOnClickListener {
                navigationViewModel.open(CurrencyScreen())
            }
        }
    }

    private companion object {
        const val DEBOUNCE_TIME = 300L
    }
}