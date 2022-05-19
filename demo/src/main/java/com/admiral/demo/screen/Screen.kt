package com.admiral.demo.screen

import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.admiral.demo.ext.putEnumExtra
import com.admiral.demo.ext.withArgs
import com.admiral.demo.features.feedback.FeedbackFragment
import com.admiral.demo.features.home.CheckBoxFragment
import com.admiral.demo.features.home.LinksFragment
import com.admiral.demo.features.home.RadioButtonFragment
import com.admiral.demo.features.home.ShimmerFragment
import com.admiral.demo.features.home.SpinnerFragment
import com.admiral.demo.features.home.StepperFragment
import com.admiral.demo.features.home.SwitchFragment
import com.admiral.demo.features.home.TagsFragment
import com.admiral.demo.features.home.ToolbarFragment
import com.admiral.demo.features.home.alert.PopUpFragment
import com.admiral.demo.features.home.badges.BadgesFragment
import com.admiral.demo.features.home.badges.BadgesNormalFragment
import com.admiral.demo.features.home.badges.BadgesSmallFragment
import com.admiral.demo.features.home.buttons.ButtonsFragment
import com.admiral.demo.features.home.buttons.GhostButtonsFragment
import com.admiral.demo.features.home.buttons.OtherButtonsFragment
import com.admiral.demo.features.home.buttons.PrimaryButtonsFragment
import com.admiral.demo.features.home.buttons.RulesButtonsFragment
import com.admiral.demo.features.home.buttons.SecondaryButtonsFragment
import com.admiral.demo.features.home.calendar.CalendarFragment
import com.admiral.demo.features.home.calendar.CalendarHorizontalFragment
import com.admiral.demo.features.home.calendar.CalendarVerticalFragment
import com.admiral.demo.features.home.cell.ActionBarFragment
import com.admiral.demo.features.home.cell.BaseCellsCenterFragment
import com.admiral.demo.features.home.cell.BaseCellsFragment
import com.admiral.demo.features.home.cell.BaseCellsLeadingFragment
import com.admiral.demo.features.home.cell.BaseCellsTrailingFragment
import com.admiral.demo.features.home.cell.CellsFragment
import com.admiral.demo.features.home.chat.ChatFilesFragment
import com.admiral.demo.features.home.chat.ChatFragment
import com.admiral.demo.features.home.chat.ChatImagesFragment
import com.admiral.demo.features.home.chat.ChatInputFragment
import com.admiral.demo.features.home.chat.ChatTextOperationFragment
import com.admiral.demo.features.home.colors.ColorModel
import com.admiral.demo.features.home.colors.ColorPickerFragment
import com.admiral.demo.features.home.colors.ColorPickerFragment.Companion.ARG_COLOR
import com.admiral.demo.features.home.colors.ColorsFragment
import com.admiral.demo.features.home.control.PageControlCircleFragment
import com.admiral.demo.features.home.control.PageControlFragment
import com.admiral.demo.features.home.control.PageControlLinerFragment
import com.admiral.demo.features.home.currency.CurrencyDefaultFragment
import com.admiral.demo.features.home.currency.CurrencyFlagsFragment
import com.admiral.demo.features.home.currency.CurrencyFragment
import com.admiral.demo.features.home.currency.CurrencyIconsFlagsFragment
import com.admiral.demo.features.home.currency.CurrencyIconsFragment
import com.admiral.demo.features.home.dialog.BottomSheetFragment
import com.admiral.demo.features.home.home.HomeFragment
import com.admiral.demo.features.home.icons.IconsFragment
import com.admiral.demo.features.home.informers.InformersBigFragment
import com.admiral.demo.features.home.informers.InformersFragment
import com.admiral.demo.features.home.informers.InformersNotificationsFragment
import com.admiral.demo.features.home.informers.InformersSmallFragment
import com.admiral.demo.features.home.notifications.ActionNotificationFragment
import com.admiral.demo.features.home.notifications.NotificationsFragment
import com.admiral.demo.features.home.notifications.StaticNotificationFragment
import com.admiral.demo.features.home.notifications.ToastNotificationFragment
import com.admiral.demo.features.home.tabs.IconTabsFragment
import com.admiral.demo.features.home.tabs.InformerTabsFragment
import com.admiral.demo.features.home.tabs.LogoTabsFragment
import com.admiral.demo.features.home.tabs.OutlineTabsFragment
import com.admiral.demo.features.home.tabs.StandardTabsFragment
import com.admiral.demo.features.home.tabs.TabsFragment
import com.admiral.demo.features.home.tabs.UnderlineCenterTabsFragment
import com.admiral.demo.features.home.tabs.UnderlineSliderTabsFragment
import com.admiral.demo.features.home.tabs.UnderlineTabsFragment
import com.admiral.demo.features.home.textFields.DoubleTextFieldsFragment
import com.admiral.demo.features.home.textFields.FeedbackTextFieldsFragment
import com.admiral.demo.features.home.textFields.NumberTextFieldsFragment
import com.admiral.demo.features.home.textFields.PincodeTextFieldsFragment
import com.admiral.demo.features.home.textFields.SliderTextFieldsFragment
import com.admiral.demo.features.home.textFields.StandardCardTextFieldsFragment
import com.admiral.demo.features.home.textFields.StandardIconTextFieldsFragment
import com.admiral.demo.features.home.textFields.StandardSmsTextFieldsFragment
import com.admiral.demo.features.home.textFields.StandardTextFieldsFragment
import com.admiral.demo.features.home.textFields.TextFieldsFragment
import com.admiral.demo.features.home.theme.ThemeCreateFragment
import com.admiral.demo.features.home.theme.ThemeFragment
import com.admiral.demo.features.home.theme.ThemeListFragment
import com.admiral.demo.features.home.theme.ThemeListFragment.Companion.ARG_MODE
import com.admiral.demo.features.home.theme.ThemeListMode
import com.admiral.demo.features.home.timepicker.TimePickerFragment
import com.admiral.demo.features.home.typography.TypographyFragment
import com.admiral.demo.features.info.ContactsFragment
import com.admiral.demo.features.info.FaqFragment
import com.admiral.demo.features.info.InfoFragment
import com.admiral.demo.features.info.InfoMoreFragment
import com.admiral.demo.features.settings.SettingsFragment
import kotlinx.parcelize.Parcelize

const val KEY_THEME = "theme"

abstract class Screen : Parcelable {
    val key: String = javaClass.simpleName
    abstract fun getFragment(): Fragment
}

@Parcelize
class InfoScreen : Screen() {
    override fun getFragment() = InfoFragment()
}

@Parcelize
class FeedbackScreen : Screen() {
    override fun getFragment() = FeedbackFragment()
}

@Parcelize
class SettingsScreen : Screen() {
    override fun getFragment() = SettingsFragment()
}

@Parcelize
class HomeScreen : Screen() {
    override fun getFragment() = HomeFragment()
}

@Parcelize
class ButtonsScreen : Screen() {
    override fun getFragment() = ButtonsFragment()
}

@Parcelize
class TabsScreen : Screen() {
    override fun getFragment() = TabsFragment()
}

@Parcelize
class ToolbarScreen : Screen() {
    override fun getFragment() = ToolbarFragment()
}

@Parcelize
class BottomSheetScreen : Screen() {
    override fun getFragment() = BottomSheetFragment()
}

@Parcelize
class CalendarScreen : Screen() {
    override fun getFragment() = CalendarFragment()
}

@Parcelize
class CalendarVerticalScreen : Screen() {
    override fun getFragment() = CalendarVerticalFragment()
}

@Parcelize
class CalendarHorizontalScreen : Screen() {
    override fun getFragment() = CalendarHorizontalFragment()
}

@Parcelize
class StandardTabsScreen : Screen() {
    override fun getFragment() = StandardTabsFragment()
}

@Parcelize
class LogoTabsScreen : Screen() {
    override fun getFragment() = LogoTabsFragment()
}

@Parcelize
class InformerTabsScreen : Screen() {
    override fun getFragment() = InformerTabsFragment()
}

@Parcelize
class OutlineTabsScreen : Screen() {
    override fun getFragment() = OutlineTabsFragment()
}

@Parcelize
class UnderlineTabsScreen : Screen() {
    override fun getFragment() = UnderlineTabsFragment()
}

@Parcelize
class UnderlineSliderTabsScreen : Screen() {
    override fun getFragment() = UnderlineSliderTabsFragment()
}

@Parcelize
class UnderlineCenterTabsScreen : Screen() {
    override fun getFragment() = UnderlineCenterTabsFragment()
}

@Parcelize
class IconTabsScreen : Screen() {
    override fun getFragment() = IconTabsFragment()
}

@Parcelize
class StepperScreen : Screen() {
    override fun getFragment() = StepperFragment()
}

@Parcelize
class RadioButtonScreen : Screen() {
    override fun getFragment() = RadioButtonFragment()
}

@Parcelize
class CheckBoxScreen : Screen() {
    override fun getFragment() = CheckBoxFragment()
}

@Parcelize
class SwitchScreen : Screen() {
    override fun getFragment() = SwitchFragment()
}

@Parcelize
class PopUpScreen : Screen() {
    override fun getFragment() = PopUpFragment()
}

@Parcelize
class CellsScreen : Screen() {
    override fun getFragment() = CellsFragment()
}

@Parcelize
class BaseCellsScreen : Screen() {
    override fun getFragment() = BaseCellsFragment()
}

@Parcelize
class BaseCellsLeadingScreen : Screen() {
    override fun getFragment() = BaseCellsLeadingFragment()
}

@Parcelize
class BaseCellsCenterScreen : Screen() {
    override fun getFragment() = BaseCellsCenterFragment()
}

@Parcelize
class BaseCellsTrailingScreen : Screen() {
    override fun getFragment() = BaseCellsTrailingFragment()
}

@Parcelize
class TextFieldsScreen : Screen() {
    override fun getFragment() = TextFieldsFragment()
}

@Parcelize
class StandardTextFieldsScreen : Screen() {
    override fun getFragment() = StandardTextFieldsFragment()
}

@Parcelize
class StandardIconTextFieldsScreen : Screen() {
    override fun getFragment() = StandardIconTextFieldsFragment()
}

@Parcelize
class StandardDoubleTextFieldsScreen : Screen() {
    override fun getFragment() = DoubleTextFieldsFragment()
}

@Parcelize
class StandardSliderTextFieldsScreen : Screen() {
    override fun getFragment() = SliderTextFieldsFragment()
}

@Parcelize
class StandardNumberTextFieldsScreen : Screen() {
    override fun getFragment() = NumberTextFieldsFragment()
}

@Parcelize
class StandardFeedbackTextFieldsScreen : Screen() {
    override fun getFragment() = FeedbackTextFieldsFragment()
}

@Parcelize
class StandardPincodeTextFieldsScreen : Screen() {
    override fun getFragment() = PincodeTextFieldsFragment()
}

@Parcelize
class StandardCardTextFieldsScreen : Screen() {
    override fun getFragment() = StandardCardTextFieldsFragment()
}

@Parcelize
class StandardSmsTextFieldsScreen : Screen() {
    override fun getFragment() = StandardSmsTextFieldsFragment()
}

@Parcelize
class ActionBarScreen : Screen() {
    override fun getFragment() = ActionBarFragment()
}

@Parcelize
class ShimmerScreen : Screen() {
    override fun getFragment() = ShimmerFragment()
}

@Parcelize
class TagsScreen : Screen() {
    override fun getFragment() = TagsFragment()
}

@Parcelize
class InformersScreen : Screen() {
    override fun getFragment() = InformersFragment()
}

@Parcelize
class InformersBigScreen : Screen() {
    override fun getFragment() = InformersBigFragment()
}

@Parcelize
class InformersSmallScreen : Screen() {
    override fun getFragment() = InformersSmallFragment()
}

@Parcelize
class LinksScreen : Screen() {
    override fun getFragment() = LinksFragment()
}

@Parcelize
class SpinnerScreen : Screen() {
    override fun getFragment() = SpinnerFragment()
}

@Parcelize
class BadgesScreen : Screen() {
    override fun getFragment() = BadgesFragment()
}

@Parcelize
class BadgesNormalScreen : Screen() {
    override fun getFragment() = BadgesNormalFragment()
}

@Parcelize
class BadgesSmallScreen : Screen() {
    override fun getFragment() = BadgesSmallFragment()
}

@Parcelize
class PageControlScreen : Screen() {
    override fun getFragment() = PageControlFragment()
}

@Parcelize
class PageControlLinerScreen : Screen() {
    override fun getFragment() = PageControlLinerFragment()
}

@Parcelize
class PageControlCircleScreen : Screen() {
    override fun getFragment() = PageControlCircleFragment()
}

@Parcelize
class ThemeListScreen(private val mode: ThemeListMode) : Screen() {
    override fun getFragment() = ThemeListFragment().withArgs {
        putEnumExtra(ARG_MODE, mode)
    }
}

@Parcelize
class ThemeScreen(private val theme: com.admiral.themes.Theme) : Screen() {
    override fun getFragment() = ThemeFragment().withArgs {
        putParcelable(KEY_THEME, theme)
    }
}

@Parcelize
class ColorsScreen(private val theme: com.admiral.themes.Theme) : Screen() {
    override fun getFragment() = ColorsFragment().withArgs {
        putParcelable(KEY_THEME, theme)
    }
}

@Parcelize
class TypographyScreen(private val theme: com.admiral.themes.Theme) : Screen() {
    override fun getFragment() = TypographyFragment().withArgs {
        putParcelable(KEY_THEME, theme)
    }
}

@Parcelize
class ColorPickerScreen(private val colorModel: ColorModel) : Screen() {
    override fun getFragment() = ColorPickerFragment().withArgs {
        putParcelable(ARG_COLOR, colorModel)
    }
}

@Parcelize
class ThemeCreateScreen : Screen() {
    override fun getFragment() = ThemeCreateFragment()
}

@Parcelize
class InfoMoreScreen : Screen() {
    override fun getFragment() = InfoMoreFragment()
}

@Parcelize
class ContactsScreen : Screen() {
    override fun getFragment() = ContactsFragment()
}

@Parcelize
class FaqScreen : Screen() {
    override fun getFragment() = FaqFragment()
}

@Parcelize
class IconsScreen : Screen() {
    override fun getFragment() = IconsFragment()
}

@Parcelize
class NotificationsScreen : Screen() {
    override fun getFragment() = NotificationsFragment()
}

@Parcelize
class InformersNotificationsScreen : Screen() {
    override fun getFragment() = InformersNotificationsFragment()
}

@Parcelize
class ToastScreen : Screen() {
    override fun getFragment() = ToastNotificationFragment()
}

@Parcelize
class StaticScreen : Screen() {
    override fun getFragment() = StaticNotificationFragment()
}

@Parcelize
class ActionScreen : Screen() {
    override fun getFragment() = ActionNotificationFragment()
}

@Parcelize
class TimePickerScreen : Screen() {
    override fun getFragment() = TimePickerFragment()
}

@Parcelize
class CurrencyScreen : Screen() {
    override fun getFragment() = CurrencyFragment()
}

@Parcelize
class CurrencyDefaultScreen : Screen() {
    override fun getFragment() = CurrencyDefaultFragment()
}

@Parcelize
class CurrencyIconsScreen : Screen() {
    override fun getFragment() = CurrencyIconsFragment()
}

@Parcelize
class CurrencyFlagsScreen : Screen() {
    override fun getFragment() = CurrencyFlagsFragment()
}

@Parcelize
class CurrencyIconsFlagsScreen : Screen() {
    override fun getFragment() = CurrencyIconsFlagsFragment()
}

@Parcelize
class ChatScreen : Screen() {
    override fun getFragment() = ChatFragment()
}

@Parcelize
class ChatInputScreen : Screen() {
    override fun getFragment() = ChatInputFragment()
}

@Parcelize
class ChatImagesScreen : Screen() {
    override fun getFragment() = ChatImagesFragment()
}

@Parcelize
class ChatFilesScreen : Screen() {
    override fun getFragment() = ChatFilesFragment()
}

@Parcelize
class ChatTextOperationScreen : Screen() {
    override fun getFragment() = ChatTextOperationFragment()
}

@Parcelize
class PrimaryButtonsScreen : Screen() {
    override fun getFragment() = PrimaryButtonsFragment()
}

@Parcelize
class SecondaryButtonsScreen : Screen() {
    override fun getFragment() = SecondaryButtonsFragment()
}

@Parcelize
class GhostButtonsScreen : Screen() {
    override fun getFragment() = GhostButtonsFragment()
}

@Parcelize
class RulesButtonsScreen : Screen() {
    override fun getFragment() = RulesButtonsFragment()
}

@Parcelize
class OtherButtonsScreen : Screen() {
    override fun getFragment() = OtherButtonsFragment()
}
