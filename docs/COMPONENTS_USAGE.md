## Оглавление

[Темизация](#Темизация)

[Buttons](#buttons)

[Text fields](#text-fields)

[Selects](#selects)

[Cells](#cells)

[Informers](#informers)

[Controls](#controls)

[Radio, Checkbox, Switch](#radio-checkbox-switch)

[Tabs](#tabs)

[System](#system)

[Bottom navigation](#bottom-navigation)

[Toolbar](#toolbar)

[Calendar](#calendar)

[Notifications](#notifications)

[Base elements](#base-elements)

[Bottomsheets](#bottom-sheets)

[Alerts](#alerts)

<br/>

## Темизация

#### Theme

Содержит все цветовые токены и шрифты.

#### ThemeManager

Переключает текущую тему приложения и сообщает компонентам, что она
изменилась. Позволяет создавать собственную тему.

<br/>

## Buttons

#### Button

Базовая кнопка.

Представлена в трех размерностях (см. класс ButtonSize):

- **Big** - основная кнопка, ширина которой зависит от ширины экрана;
- **Medium** - дополнительная кнопка меньшего размера, свой размер
  кнопка не меняет в зависимости от ширины экрана;
- **Small** - менят свою ширину в зависимости от контента внутри нее,
  часто используется с клавиатурой.

В зависимости от контекста применяются разные типы кнопок (см. класс
ButtonStyle), использование на экране больше одной-двух главных
(Primary) кнопок нежелательно.

- **Primary** - основная кнопка, для самых важных действий;
- **Secondary** - второстепенная кнопка, для дополнительных действий,
  используется самостоятельно или в паре с другими типами кнопок;
- **Ghost** - используется в случаях, когда основной кнопки
  недостаточно, часто идет с ней в паре, когда нужно обозначить
  несколько действий, одно из которых является основным.

Для каждой кнопки доступен основной текст и дополнительный текст:

```kotlin
button.text = "Выбрать"
button.additionalText = "08.06.20 — 14.08.20"
```

Каждая кнопка имеет встроенный Spinner, который можно активировать
с помощью переменной **isLoading**. При активации Spinner текст кнопки
становится прозрачным.

#### ButtonGooglePay

Кнопка для добавления карты в Google Pay

<br/>

## Text fields

#### TextField

Стандартное поле ввода.

Можно настроить размер поля (см. класс `TextFieldStyle`).

Можно настроить расположение текста (см. класс `TextGravity`).

Можно добавить иконку и listener для обработки нажатия на иконку

Присутсвует состояние ошибки.

```kotlin
textField.textFieldStyle = TextFieldStyle.Clipped
textField.inputTextGravity = TextGravity.Start
textField.isError = true
```

#### DoubleTextField

Двойной TextField с возможностью изменять отношение ширины между друг
другом (см. класс `DoubleTextFieldRatio`).

#### Slider

Поле ввода со слайдером позволяет выбирать значение из обозначенного
диапазона свайпом или ввод вручную.

```kotlin
slider.leftText = "Left text"
slider.rightText="Right text"
slider.additionalText="Additional ext"
slider.optionalText="Optional text"
```

<br/>

## Selects

#### InputNumber

Поле ввода с возможностью ввода числовых значений через нажатие с
заданным шагом.

Реализовано долгое нажатие с плавным увеличением шага.

Есть ограничения на числовое значение: min=-9999, max=99999.

```kotlin
inputNumber.optionalText = "Optional label"
inputNumber.value = 1
inputNumber.minValue = 0
inputNumber.maxValue = 1000
```

#### PinCodeView

Поле ввода пин-кода с возможностью ввода от 2 до 8 значений.

Может быть в трёх состояниях (см. класс `PinCodeState`).

```kotlin
pinCodeView.dotsCount = 4
pinCodeView.activeDotsCount = 2
```

#### Feedback

Поле для оценки приложения, содержит пять иконок в ряд.

Можно изменить сами иконки и поменять их цвет.

```kotlin
feedback.icon = getDrawable(R.drawable.ic_feedback)
feedback.iconTintColors = ColorState()
```

<br/>

## Cells

#### BaseCell

Компонент существует в различных видах, и в зависимости от назначения
применяется тот или иной вариант.

Компонент состоит из трёх видов субкомпонентов:

* TRAILING - соединяется с концом и верхом родителя.
* LEADING - подключается к началу и верху родительского элемента, а
  также к началу TRAILING.
* LEADING_TEXT - соединяется с концом LEADING и началом TRAILING.

Конфигурация может быть любая.

Обязательно наличие id у каждого субкомпонента BaseCell для правильного
позиционирования.

Дублировать типы субкомпонентов запрещается.

```xml
<com.admiral.uikit.components.cell.BaseCell
	android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <com.admiral.uikit.components.cell.unit.TextCellUnit
    	android:id="@+id/logoTabText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:admiralCellUnitType="leading"
        app:admiralText="@string/cells_base_label_title"
        app:admiralTextStyle="body1" />

    <com.admiral.uikit.components.cell.unit.IconCellUnit
         android:id="@+id/logoTabIcon"
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         app:admiralCellUnitType="trailing"
         app:admiralIcon="@drawable/admiral_ic_chevron_right_outline" />

</com.admiral.uikit.components.cell.BaseCell>
```

<br/>

## Informers

#### InformerBig / InformerSmall

Компонент в четырех статусных цветах (см. класс `InformerStyle`).

Можно настроить расположение указателя.

```
informer.pointerBias = 1.0f
informer.informerStyle = InformerStyle.Info
informer.headline = "Title"
informer.info = "Description"
informer.link = "Any link"
```

<br/>

## Controls

#### Chips

Представляет собой перечень выбранных фильтров, опций или каких-либо
элементов из списка. При нажатии на иконку закрытия элемент удаляется из
списка выбранных. В библиотеке компонент представлен двумя классами
`ChipGroup` и `Chip`.

```xml
<com.admiral.uikit.components.chip.ChipGroup
    android:id="@+id/chips"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">

        <com.admiral.uikit.components.chip.Chip
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:admiralText="@string/tags_chips_chip"
            app:chipIcon="@drawable/admiral_ic_car_solid"
            app:closeIconEnabled="true" />

</com.admiral.uikit.components.chip.ChipGroup>
```

#### Tags

Метка, размечающая и каталогизирующая информацию для облегчения процесса
поиска.

В библиотеке компонент представлен двумя классами `TagGroup` и `Tag`.

```xml
<com.admiral.uikit.components.tag.TagGroup
	android:id="@+id/chips"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">

    <com.admiral.uikit.components.tag.Tag
    	android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:admiralText="@string/tags_chips_emoji" />

</com.admiral.uikit.components.chip.ChipGroup>
```

#### Badges

Обычно дополняет другие компоненты и показывает количественные зачения.
Например, в компоненте Tabs может показывать количество элементов в
закладке. Или показывать количество оповещений в панели нотификации.

Может быть следующих типов: NORMAL, ERROR, ATTENTION, SUCCESS, NEUTRAL,
NEUTRAL.

Компонент также реализован в следующих компонентах:
- IconCellUnit
- LabelCellUnit
- IconBackgroundCellUnit
- Toolbar
- BottomNavigation

```xml
<com.admiral.uikit.components.badge.Badge
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:admiralBadgeStyle="additional"
    app:admiralText="@string/badges_example_start"/>
```

#### Spinner

Компонент служит для демонстрации процесса загрузки, ожидания.
Существует в трех размерах: Small, Medium, Big (см. класс
`SpinnerSize`).
Также компонент возможно сделать контрастным с помощью переменной
**isContrast**

```xml
 <com.admiral.uikit.components.spinner.Spinner
	android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:admiralSpinnerSize="small" />
```

#### Links

Компонент Link используется для навигации. Может применяться отдельно
или внутри текста, с иконкой или без.

#### Page Controls

Используется для навигации и обозначает количество возможных страниц
(шагов). В библиотеке присутствует в двух вариантах:

**CirclePageControl** — чаще используется в центре страницы. Центральный
элемент с иконкой работает как кнопка, при нажатии на которую,
пользователь переходит на новую страницу. Внешний круг - показывает
количество пройденных и предстоящих страниц (шагов) в процентном
соотношении.

**LinerPageControl** — по логике соответствует варианту Circle, при этом
есть дополнительный вариант переключения страниц с помощью свайпа целой
страницы. Дополнительно при использовании такого варианта page control
стоит предусмотреть использование кнопки “Далее”.

<br/>

## Radio, Checkbox, Switch

Компоненты из этого блока - это стандартные компоненты из AppCompat и
Material, только с поддержкой темизации.

#### RadioButton

Представлен в состояниях Deafault, Selected, Disabled, Disabled Selected
и Error.

```kotlin
radioButton.error = true
checkbox.buttonTintColors = ColorState()
```

#### Checkbox

Представлен в состояниях Deafault, Selected, Disabled, Disabled Selected
и Error.

```kotlin
checkbox.error = true
checkbox.buttonTintColors = ColorState()
```

#### Switcher

Представлен в состояниях Default, Active, Disabled и Disabled Active.

```kotlin
switcher.thumbTintColors = ColorState()
```

<br/>

## Tabs

#### StandardTabs / LogoTabs

Компонент используется для переключения между двумя или тремя вкладками.

```xml
<com.admiral.uikit.components.tabs.StandardTabs
	android:id="@+id/tabsState"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="@dimen/module_x4"
    app:checkedId="@+id/defaultTab">

    <com.admiral.uikit.components.tabs.StandardTab
    	android:id="@+id/defaultTab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/tabs_default" />

    <com.admiral.uikit.components.tabs.StandardTab
        android:id="@+id/disabledTab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/tabs_disabled" />

</com.admiral.uikit.components.tabs.StandardTabs>

<com.admiral.uikit.components.tabs.StandardTabs
	android:id="@+id/tabsTwo"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="@dimen/module_x4"
    app:checkedId="@+id/one2">

    <com.admiral.uikit.components.tabs.LogoTab
    	android:id="@+id/one2"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        app:srcCompat="@drawable/test_ic_visa" />

    <com.admiral.uikit.components.tabs.LogoTab
        android:id="@+id/two2"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        app:srcCompat="@drawable/test_ic_master_card" />

</com.admiral.uikit.components.tabs.StandardTabs>
```


#### InformerTabs

Компонент используется для переключения между двумя или тремя вкладками.
Дополнительно элемент имеет компонент раскрытого информера.

```xml
<com.admiral.uikit.components.tabs.InformerTabs
	android:id="@+id/tabsTwo"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="@dimen/module_x4"
    app:checkedId="@+id/one2">

    <com.admiral.uikit.components.tabs.InformerTab
    	android:id="@+id/one2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:admiralSubtitleText="@string/tabs_example_informer_tab_subtitle"
        app:admiralTitleText="@string/tabs_example_informer_tab_title" />

    <com.admiral.uikit.components.tabs.InformerTab
    	android:id="@+id/two2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:admiralSubtitleText="@string/tabs_example_informer_tab_subtitle"
        app:admiralTitleText="@string/tabs_example_informer_tab_title" />

</com.admiral.uikit.components.tabs.InformerTabs>
```

#### OutlineSliderTabs

Компонент используется для переключения между несколькими вкладками. В
библиотеке представлен в одном варианте с уже запланированными отступами
— 16px слева и по 8px сверху и снизу. В слоях есть возможность включать
и отключать вкладки, менять порядок выбранной вкладки.

#### UnderlineSliderTabs

Компонент используется для переключения между несколькими вкладками.

```xml
<com.admiral.uikit.components.tabs.UnderlineSliderTabs
	android:id="@+id/tabsTwo"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:layout_marginTop="@dimen/module_x4"
    app:checkedId="@+id/one2">

    <com.admiral.uikit.components.tabs.UnderlineSliderTab
    	android:id="@+id/one2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:admiralText="@string/tabs_example_one"
        app:admiralUnderlinePadding="2dp" />

    <com.admiral.uikit.components.tabs.UnderlineSliderTab
    	android:id="@+id/two2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:admiralText="@string/tabs_example_two"
        app:admiralUnderlinePadding="2dp" />

</com.admiral.uikit.components.tabs.UnderlineSliderTabs>
```

#### IconTabs

Компонент используется для переключения между двумя или тремя вкладками.

```xml
<com.admiral.uikit.components.tabs.IconTabs
    android:id="@+id/tabsTwo"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
    android:layout_marginTop="@dimen/module_x4"
    app:checkedId="@+id/one2">

    <com.admiral.uikit.components.tabs.IconTab
        android:id="@+id/one2"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        app:admiralIcon="@drawable/admiral_ic_vacation_solid"
        app:admiralText="@string/tabs_one" />

   <com.admiral.uikit.components.tabs.IconTab
        android:id="@+id/two2"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        app:admiralIcon="@drawable/admiral_ic_chat_solid"
        app:admiralText="@string/tabs_two" />

</com.admiral.uikit.components.tabs.IconTabs>
```

<br/>

## System

#### App Bars

Состоит из следующих классов, название которых отображает их назначение

- `AppbarSearch`
- `Appbar`
- `AppbarTitleSubtitle`
- `AppbarStepper`

#### Pin Keyboard

Компонент представляет собой цифровую клавиатуру. Используется вместе с
`PincodeView`.

Дополнительный класс KeyboardNum - отдельная кнопка клавиатуры

```kotlin
pinKeyboard.keyListener = {
		when (it) {
    		KeyboardNum.ADDITIONAL_RIGHT -> todo()
        else -> todo()
		}
}
```

<br/>

## Bottom navigation

Отображают от двух до пяти пунктов назначения в нижней части экрана.
Каждый пункт назначения представлен иконкой и текстовой меткой.

Представлен классом `BottomNavigation`, это тот же
`BottomNavigationView`, только с поддержкой темизации. Работа
`BottomNavigation` ничем не отличается от ``BottomNavigationView``,
кроме методов, предназначенных для работы с [Badges](#badges)

```kotlin
fun setBadge(position: Int, badge: Badge)
и 
fun removeBadge(position: Int)
```

<br/>

## Toolbar

Нижние панели управления отображают от одной до четырех пунктов
управления в нижней части экрана. Каждый пункт управления представлен
иконкой и текстовой меткой.

Для генерации пунктов можно использовать **menu**:

```xml
<com.admiral.uikit.components.toolbar.Toolbar
        app:menu="@menu/menu_toolbar" />
```

Либо добавлять пункты вручную, в случае всем пунктам можно задать разные
цвета.

```kotlin
admiralToolbar.addItem(
	ToolbarItem(
		id = menuIds[index],
		text = menuTitles[index],
		icon = ContextCompat.getDrawable(requireContext(), iconIds[index])
	)
)
```

Также есть возможность добавлять или убирать [Badges](#badges):

```kotlin
fun setBadge(position: Int, badge: Badge)
и 
fun removeBadge(position: Int)
```

<br/>

## Calendar

Календарь — позволяют пользователям выбирать дату или диапазон дат.

```kotlin
calendar.highlightedDates = listOf(
	LocalDate.of(2021, 1, 16),
	LocalDate.of(2021, 2, 16),
)
```

В библиотеке представлен в двух видах: горизонтальный и вертикальный.
Для управления ориентацей используется переменная `var isVertical:
Boolean = true`.

Для настройки количества месяцев, отображающихся на экране, следует
установить первый и последний месяцы: `var firstMonth: YearMonth? =
null` и `var lastMonth: YearMonth? = null` соответственно.

Для того, чтобы отследить изменение выбранных дат, необходимо
использовать переменную `var onDateChooseListener OnDateChooseListener?
= null`

<br/>

## Notifications

#### StaticNotification

Данный элемент предназначен для отображения статичных уведомлений. Может
быть следующих типов:
- Info
- Attention
- Success
- Error

Пример использования в xml:

```xml
  <com.admiral.uikit.components.notifications.fixed.StaticNotification
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginStart="@dimen/module_x4"
                android:layout_marginTop="@dimen/module_x4"
                android:layout_marginEnd="@dimen/module_x4"
                app:admiralLinkText="@string/informers_example_link"
                app:admiralNotificationStyle="info"
                app:admiralText="@string/informers_example_info" />
```

#### ToastNotification

Данный элемент предназначен для отображения одинарного Toast
уведомления. Под капотом используется Snackbar, поэтому в конструктор
необходимо также передавать `rootView` и `context`. Пример
использования:

```kotlin
ToastNotification.Builder(requireContext(), binding.coordinator)
            .setDuration(Snackbar.LENGTH_SHORT)
            .setGravity(Gravity.BOTTOM)
            .setMargins(bottom = 48)
            .setText("Hello, world")
            .setLinkText("I am the link")
            .setIcon(myDrawable)
            .setCloseIconVisible(true)
            .setLinkClickListener {
                println("I am clicked")
            }
            .apply()
            .show()
```

#### ToastMultipleNotification

Данный элемент предназначен для отображения накладывающихся Toast
уведомлений (в случае, если уведомлений несколько). Под капотом
используется Snackbar, поэтому в конструктор необходимо также передавать
`rootView` и `context`.

Пример использования:

```kotlin
 val multipleToastNotificationRoot = ToastMultipleNotification.Builder(requireContext(), binding.coordinator).apply()
 multipleToastNotificationRoot.show()
```

Для отображения уведомления в данный компонент необходимо добавить
`StaticNotification` с помощью метода
`addNotification(staticNotification: StaticNotification)`.

Пример использования:

```kotlin
 binding.btnAddToast.setOnClickListener {
            multipleToastNotificationRoot.addNotification(StaticNotification(requireContext()).apply {
                notificationStyle = StaticNotificationStyle.Success
                isBackgroundColorDefault = true
                setOnIconCloseClickListener {
                    isVisible = false
                }
                notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid."
                linkText = "Link text"
            })
        }
```

При добавлении элементы будут наслаиваться друг на друга, однако по тапу
на компонент список уведомлений раскроется.

#### ActionNotification

Данный элемент предназначен для отображения уведомления, который
содержит в себе таймер закрытия компонента и активную кнопку, поведение
которой можно настроить.

Пример использования:

```kotlin
ActionNotification.Builder(requireContext(), binding.coordinator)
                .setText("Letter deleted for both sides")
                .setDuration(7000)
                .setCancelButtonClickListener {
                    Toast.makeText(requireContext(), "Cancel clicked", Toast.LENGTH_SHORT).show()
                }
                .apply()
                .show()
```

<br/>

## Base elements

#### Shimmers

Элемент, который отображает загрузку строки, элемента или целого блока.

#### Lines

Элемент служит разделителем.

<br/>

## Bottom-sheets

Всплывающие окна отображаются в нижней части экрана, содержат дополнения
к основному экрану и остаются видимыми, пока пользователи
взаимодействуют с основным контентом. Также являются альтернативой
встроенным меню или простым диалоговым окнам на мобильных устройствах и
предоставляют место для дополнительных элементов, более длинных описаний
и значков.

В библиотеке представлен классом `AdmiralBottomSheetDialog`, от которого
следует наследоваться.

```kotlin
class BottomSheetDialogFragment : AdmiralBottomSheetDialog(R.layout.fmt_bottom_sheet_dialog)
```

<br/>

## Alerts

Всплывающие окна поверх контента.

В библиотеке представлен классом `AlertDialog`, от которого следует
наследоваться.

В конструктор необходимо передать layoutResId.

```kotlin
class PopUpDialogFragment : AlertDialog(R.layout.admiral_view_pop_up_dialog)
```

Лайаут `R.layout.admiral_view_pop_up_dialog` так же представлен в
библиотеке для использования.
