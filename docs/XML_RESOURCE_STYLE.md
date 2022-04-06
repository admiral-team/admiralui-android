## Xml Resource Style

### 1. Общие правила именования ресурсов

Имя ресурса должно состоять из следующих блоков:

- `<module>` - модуль, к которому относится ресурс
- `<what>` - тип ресурса
- `<where>` - принадлежность ресурса к конкретному экрану/фиче
- `<description>` - дополнительное описание ресурса
- `<size>` - размер ресурса

Во избежании проблем при сборке, обязательно наличие имени модуля, к которому относится ресурс.

Наличие остальных блоков необязательно.

#### 1.1. Layouts

`<module>_<what>_<where>_<description>.xml`

Типы layout:

- `act` - Activity
- `fmt` - Fragment
- `view` - View
- `item` - RecyclerView
- `layout` - layout для переиспользования

Примеры:

- `profile_act_edit.xml`
- `chat_item_point.xml`

#### 1.2. Drawables

`<module>_<what>_<where>_<description>_<size>.xml`

Типы drawables:

- `ic` - иконка
- `bg` - бэкграунд
- `btn` - кнопка
- `divider` - разделитель
- `img` - простая картинка

Примеры:

- `login_bg_btn_enter.xml`
- `ui_ic_plus_48.xml`

#### 1.3. Strings

`<module>_<where>_<description>.xml`

- Строки не должны использоваться повторно.
- Следует использовать `Resources#getString(int id, Object… formatArgs)` для форматирования строк и не рекомендуется делать конкатенацию через оператор `+`
- Следует использовать `Resources#getQuantityString(int id, int quantity)` для количественных строк
- Необходимо использовать функцию `toUpperCase()`, вместо написания строки заглавными буквами в `strings.xml`

Примеры:

- `login_name_hint`
- `settings_dialog_title` 

#### 1.4. IDs

`<description><What>`

- Все идентификаторы должны быть в стиле **upperCamelCase**.
- Не позволяются сокращения.
- Добавляем postfix.

Примеры:

- `secondNameTextView`
- `phoneEditText`
- `refreshLayout`

#### 1. Dimens

`<module>_<what>_<where>_<description>_<size>`

Типы dimens:

- `height` - высота
- `width` - ширина
- `margin` - отступ
- `radius` - радиус

Примеры:

- `profile_width_alert_block`
- `uikit_height_toolbar`

#### 1.6. Themes & Styles

`<ModuleName><Where><Description>`

- Темы и стили должны быть названы с использованием UpperCamelCase.

Примеры:

- `AppTheme.NoActionBar`
- `UiChipTabLayout`
- `TemplatesSumNormal`

### 2. Форматирование

Форматирование можно выполнить с помощью функции форматирования в Android Studio.

**macOS**: Command + Option + L

**Windows / Linu**x: Control + Alt + L
<br/><br/> 

Когда View или Layout не являются родительскими, то есть не содержат в себе другие View или Layout, следует использовать self-closing теги.

```xml
<ImageView
    android:id="@+id/star"
    android:layout_width="40dp"
    android:layout_height="38dp"
    android:src="@drawable/ic_star_empty" />
```

Следует соблюдать следующий порядок атрибутов:

- View Id;
- Style;
- Layout width и layout height;
- Остальные layout_ атрибуты;
- Оставшиеся атрибуты.

```xml
<EditText
    android:id="@+id/name"
    style="@style/AppTheme.Widget.RegularEditText"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:scrollbarStyle="outsideInset"
    android:scrollbars="vertical"
    android:textColor="#7a7a7a"
    android:textSize="14sp"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent"/>
```