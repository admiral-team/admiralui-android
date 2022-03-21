package com.admiral.uikit.components.calendar

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.admiral.themes.ColorPaletteEnum
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.datePickerOld.DatePickerOld
import com.admiral.uikit.components.imageview.ImageView
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.next
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.previous
import com.admiral.uikit.view.calendar.InternalCalendarView
import com.admiral.uikit.view.calendar.model.CalendarDay
import com.admiral.uikit.view.calendar.model.CalendarMonth
import com.admiral.uikit.view.calendar.model.DayOwner
import com.admiral.uikit.view.calendar.model.ScrollMode
import com.admiral.uikit.view.calendar.ui.DayBinder
import com.admiral.uikit.view.calendar.ui.MonthHeaderFooterBinder
import com.admiral.uikit.view.calendar.ui.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

@Deprecated("Use HorizontalCalendar and VerticalCalendar instead. This one will be deleted soon.")
class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), ThemeObserver {

    // region Properties
    /**
     * This determines the scroll direction of the the calendar.
     */
    var isVertical: Boolean = true
        set(value) {
            field = value
            invalidateOrientation()
        }

    /**
     * Month shown in the calendar.
     */
    var currentMonth: YearMonth = YearMonth.now()
        set(value) {
            field = value
            internalCalendarView.scrollToMonth(currentMonth)
        }

    /**
     * First month shown in the calendar.
     */
    var firstMonth: YearMonth? = null
        set(value) {
            field = value
            invalidateMonths()
        }

    /**
     * Last month shown in the calendar.
     */
    var lastMonth: YearMonth? = null
        set(value) {
            field = value
            invalidateMonths()
        }

    /**
     * First day of the selected range of dates.
     */
    var startDay: LocalDate? = null
        set(value) {
            notifyDatesChanged(startDay, endDay)
            field = value
            onDateChooseListener?.onDateChoose(startDay, endDay)
            notifyDatesChanged(startDay, endDay)
        }

    /**
     * Last day of the selected range of dates.
     */
    var endDay: LocalDate? = null
        set(value) {
            notifyDatesChanged(startDay, endDay)
            field = value
            onDateChooseListener?.onDateChoose(startDay, endDay)
            notifyDatesChanged(startDay, endDay)
        }

    /**
     * Dates that are highlighted by the cornered border.
     */
    var highlightedDates: List<LocalDate> = listOf()
        set(value) {
            field = value
            internalCalendarView.notifyCalendarChanged()
        }

    /**
     * Determines if dates after the current day are available.
     */
    var datesAfterEnabled: Boolean = true
        set(value) {
            field = value
            internalCalendarView.notifyCalendarChanged()
        }

    /**
     * Determines if dates before the current day are available.
     */
    var datesBeforeEnabled: Boolean = true
        set(value) {
            field = value
            internalCalendarView.notifyCalendarChanged()
        }

    /**
     * Dates that are not clickable.
     */
    var disabledDates: List<LocalDate> = listOf()
        set(value) {
            field = value
            internalCalendarView.notifyCalendarChanged()
        }

    /**
     * Determine whether the user can select multiple dates or a single one.
     */
    var isMultipleSelection: Boolean = true
        set(value) {
            field = value
            if (!value) {
                endDay = null
            }
        }

    /**
     * Color for the month and year element.
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorInt
    var monthYearTextColor: Int? = null
        set(value) {
            field = value
            internalCalendarView.notifyCalendarChanged()
        }

    /**
     * Color for the day legend element.
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorInt
    var dayWeekTextColor: Int? = null
        set(value) {
            field = value
            internalCalendarView.notifyCalendarChanged()
        }

    /**
     * Color for the day element.
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorInt
    var dayTextColor: Int? = null
        set(value) {
            field = value
            internalCalendarView.notifyCalendarChanged()
        }

    /**
     * Color for the day legend element.
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorInt
    var selectedDayTextColor: Int? = null
        set(value) {
            field = value
            internalCalendarView.notifyCalendarChanged()
        }

    /**
     * Color for the disabled day.
     * In case color is null, the selected color theme will be used.
     */
    @ColorInt
    var disabledDayTextColor: Int? = null
        set(value) {
            field = value
            internalCalendarView.notifyCalendarChanged()
        }

    /**
     * Color for the backgroundEndStart drawable tint.
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorInt
    var backgroundEndStartTintColor: Int? = null
        set(value) {
            field = value
            internalCalendarView.notifyCalendarChanged()
        }

    /**
     * Color for the backgroundMiddle drawable tint.
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorInt
    var backgroundMiddleTintColor: Int? = null
        set(value) {
            field = value
            internalCalendarView.notifyCalendarChanged()
        }

    /**
     * Color for the backgroundHighlighted drawable tint.
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorInt
    var backgroundHighlightedTintColor: Int? = null
        set(value) {
            field = value
            internalCalendarView.notifyCalendarChanged()
        }

    /**
     * Listener to detect and updates of the picked dates.
     */
    var onDateChooseListener: OnDateChooseListener? = null

    /**
     * Listener to detect if [DatePickerOld] was closed or opened.
     */
    var onPickerInteractListener: OnPickerInteractListener? = null

    /**
     * Defines the date that will be shown in the [DatePickerOld]
     */
    var datePickerYearMonth: YearMonth = YearMonth.now()
        set(value) {
            field = value

            val displayName = value.month.monthToHuman(resources)
            val monthText = "$displayName  ${value.year}".replaceFirstChar {
                if (it.isLowerCase()) {
                    it.titlecase(Locale.getDefault())
                } else {
                    it.toString()
                }
            }

            datePickerHorizontalMonthYearTextView.text = monthText
            horizontalMonthYearTextView.text = monthText
        }

    /**
     * Defines first value of the years range that [DatePickerOld] will contain
     */
    var datePickerStartYear: Int? = null

    /**
     * Defines last value of the years range that [DatePickerOld] will contain
     */
    var datePickerEndYear: Int? = null

    /**
     * Dates that are marked by a dot below the text.
     */
    var markedDays: MutableList<LocalDate> = mutableListOf()
        set(value) {
            field = value
            internalCalendarView.notifyCalendarChanged()
        }

    /**
     * Color of the markers that are placed for the [markedDays].
     */
    var markersColorFromPaletteEnum: ColorPaletteEnum = ColorPaletteEnum.ELEMENT_ACCENT
        set(value) {
            field = value
            markedDays.forEach {
                internalCalendarView.notifyDateChanged(it)
            }
        }

    private val internalCalendarView: InternalCalendarView by lazy { findViewById(R.id.calendarView) }
    private val datePickerView: View = LayoutInflater.from(context)
        .inflate(R.layout.admiral_calendar_date_picker, null).also { it.isVisible = false }

    private val locale = Locale("ru", "RU")
    private val firstDayOfWeek: DayOfWeek = WeekFields.of(locale).firstDayOfWeek

    private val horizontalHeaderMonth: LinearLayout by lazy { findViewById(R.id.admiralHorizontalHeaderMonth) }
    private val horizontalHeaderDayOfWeek: LinearLayout by lazy { findViewById(R.id.admiralHorizontalHeaderDayOfWeek) }

    private val datePickerHorizontalMonthYearTextView: TextView by lazy {
        datePickerView.findViewById(R.id.monthYearTextView)
    }
    private val horizontalMonthYearTextView: TextView by lazy { findViewById(R.id.monthYearTextView) }
    private val iconLeft: ImageView by lazy { findViewById(R.id.ivLeft) }
    private val iconRight: ImageView by lazy { findViewById(R.id.ivRight) }
    // endregion

    init {
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.admiral_view_calendar, this)
        internalCalendarView.overScrollMode = OVER_SCROLL_NEVER
        addView(datePickerView)

        invalidateOrientation()
        invalidateMonths()

        parseAttrs(attrs, R.styleable.CalendarView).use {
            isMultipleSelection = it.getBoolean(R.styleable.CalendarView_isMultipleSelection, true)

            monthYearTextColor = it.getColorOrNull(R.styleable.CalendarView_admiralMonthYearTextColor)
            dayWeekTextColor = it.getColorOrNull(R.styleable.CalendarView_admiralDayLegendTextColor)
            dayTextColor = it.getColorOrNull(R.styleable.CalendarView_admiralDayTextColor)
            selectedDayTextColor = it.getColorOrNull(R.styleable.CalendarView_admiralSelectedDayTextColor)

            backgroundEndStartTintColor =
                it.getColorOrNull(R.styleable.CalendarView_admiralBackgroundEndStartTintColor)
            backgroundMiddleTintColor =
                it.getColorOrNull(R.styleable.CalendarView_admiralBackgroundMiddleTintColor)
            backgroundHighlightedTintColor =
                it.getColorOrNull(R.styleable.CalendarView_admiralBackgroundHighlightedTintColor)

            datesAfterEnabled = it.getBoolean(R.styleable.CalendarView_admiralDatesAfterEnabled, true)
            datesBeforeEnabled = it.getBoolean(R.styleable.CalendarView_admiralDatesBeforeEnabled, true)
        }

        internalCalendarView.dayBinder = getDayBinder(context)
        internalCalendarView.monthHeaderBinder = getMonthHeaderFooterBinder()

        invalidateHorizontalHeader()
        invalidateBackground()
        invalidateWeekDaysColor()
    }

    private fun invalidateBackground() {
        background = ContextCompat.getDrawable(context, R.drawable.admiral_bg_rectangle_12dp)
        backgroundTintList = ColorStateList.valueOf(ThemeManager.theme.palette.backgroundBasic)
    }

    private fun invalidateHorizontalHeader() {
        internalCalendarView.monthScrollListener = { calendarMonth ->
            datePickerYearMonth = calendarMonth.yearMonth
        }

        horizontalMonthYearTextView.apply {
            setOnClickListener {
                showDatePicker()
            }
        }

        iconLeft.setOnClickListener {
            internalCalendarView.findFirstVisibleMonth()?.let {
                internalCalendarView.smoothScrollToMonth(it.yearMonth.previous)
            }
        }

        iconRight.setOnClickListener {
            internalCalendarView.findFirstVisibleMonth()?.let {
                internalCalendarView.smoothScrollToMonth(it.yearMonth.next)
            }
        }

        datePickerView.findViewById<TextView>(R.id.linkChose).apply {
            setOnClickListener {
                val datePicker = datePickerView.findViewById<DatePickerOld>(R.id.datePicker)
                internalCalendarView.scrollToMonth(datePicker.getChosenYearMonth())
                showCalendar()
            }
        }

        invalidateHorizontalHeaderColors()
    }

    private fun invalidateHorizontalHeaderColors() {
        val normalEnabled = monthYearTextColor ?: ThemeManager.theme.palette.textAccent
        val pressedColor = monthYearTextColor ?: ThemeManager.theme.palette.textAccentPressed
        val iconColorStateLit = colorStateList(
            enabled = ThemeManager.theme.palette.elementAccent,
            disabled = ThemeManager.theme.palette.elementAccent.withAlpha(),
            pressed = ThemeManager.theme.palette.elementAccentPressed
        )

        horizontalMonthYearTextView.textColor = ColorState(
            normalEnabled = normalEnabled,
            pressed = pressedColor
        )
        datePickerHorizontalMonthYearTextView.textColor = ColorState(
            normalEnabled = normalEnabled,
            pressed = pressedColor
        )

        iconLeft.imageTintList = iconColorStateLit
        iconRight.imageTintList = iconColorStateLit

        setCompoundDrawableForHorizontalMonthYearTextView()
    }

    private fun invalidateWeekDaysColor() {
        horizontalHeaderDayOfWeek.children.map { it as TextView }
            .forEach { textViewWeek ->
                textViewWeek.textColor = ColorState(
                    normalEnabled = dayWeekTextColor ?: ThemeManager.theme.palette.textSecondary
                )
            }
    }

    private fun setCompoundDrawableForHorizontalMonthYearTextView() {
        // NB: it's strange but when we try to change tint for existing CompoundDrawables
        // a crash occurs. That's why we change drawable
        val drawableEnd = drawable(R.drawable.admiral_ic_chevron_down_outline)
        drawableEnd?.colored(
            colorStateList(
                enabled = monthYearTextColor ?: ThemeManager.theme.palette.elementAccent,
                disabled = monthYearTextColor ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                pressed = monthYearTextColor ?: ThemeManager.theme.palette.elementAccentPressed
            )
        )
        horizontalMonthYearTextView
            .setCompoundDrawablesWithIntrinsicBounds(null, null, drawableEnd, null)
        datePickerHorizontalMonthYearTextView
            .setCompoundDrawablesWithIntrinsicBounds(null, null, drawableEnd, null)

        horizontalMonthYearTextView.apply {
            compoundDrawablesNormalEnabledPalette = ColorPaletteEnum.ELEMENT_ACCENT
            compoundDrawablesPressedPalette = ColorPaletteEnum.ELEMENT_ACCENT_PRESSED
        }
        datePickerHorizontalMonthYearTextView.apply {
            compoundDrawablesNormalEnabledPalette = ColorPaletteEnum.ELEMENT_ACCENT
            compoundDrawablesPressedPalette = ColorPaletteEnum.ELEMENT_ACCENT_PRESSED
        }
    }

    private fun getDayBinder(context: Context) = object : DayBinder<DayViewContainer> {
        // Called only when a new container is needed.
        override fun create(view: View) = DayViewContainer(view)

        // Called every time we need to reuse a container.
        override fun bind(container: DayViewContainer, day: CalendarDay) {
            val backgroundEndStart = ContextCompat.getDrawable(context, R.drawable.admiral_bg_rectangle_4dp)
                ?.colored(backgroundEndStartTintColor ?: ThemeManager.theme.palette.backgroundAccent)
            val backgroundMiddle = ContextCompat.getDrawable(context, R.drawable.admiral_bg_rectangle_4dp)
                ?.colored(backgroundMiddleTintColor ?: ThemeManager.theme.palette.backgroundSelected)
            val backgroundHighlighted = ContextCompat.getDrawable(context, R.drawable.admiral_bg_stroke_4dp)
                ?.colored(backgroundHighlightedTintColor ?: ThemeManager.theme.palette.textPrimary)

            container.day = day
            val dayTextView = container.dayTextView

            dayTextView.text = null
            dayTextView.background = null

            if (highlightedDates.contains(day.date) && day.owner == DayOwner.THIS_MONTH) {
                dayTextView.background = backgroundHighlighted
            }

            if (markedDays.contains(day.date) && day.owner == DayOwner.THIS_MONTH) {
                container.mark.isVisible = true
                container.mark.imageColorNormalEnabledPalette = markersColorFromPaletteEnum
            } else {
                container.mark.isVisible = false
            }

            if (day.owner == DayOwner.THIS_MONTH) {
                dayTextView.text = day.day.toString()
                val isBeforeDisabled = !datesBeforeEnabled && day.date.isBefore(LocalDate.now())
                val isAfterDisabled = !datesAfterEnabled && day.date.isAfter(LocalDate.now())
                when {
                    // // We have start and end days - change middle
                    startDay != null && endDay != null && (day.date > startDay && day.date < endDay) -> {
                        dayTextView.textColor =
                            ColorState(normalEnabled = dayTextColor ?: ThemeManager.theme.palette.textPrimary)
                        dayTextView.background = backgroundMiddle
                    }

                    // We have an end day
                    day.date == endDay -> {
                        dayTextView.textColor = ColorState(
                            normalEnabled =
                            selectedDayTextColor ?: ThemeManager.theme.palette.textStaticWhite
                        )
                        dayTextView.background = backgroundEndStart
                    }

                    // We have a start day
                    day.date == startDay -> {
                        dayTextView.textColor = ColorState(
                            normalEnabled =
                            selectedDayTextColor ?: ThemeManager.theme.palette.textStaticWhite
                        )
                        dayTextView.background = backgroundEndStart
                    }

                    disabledDates.contains(day.date) or isBeforeDisabled or isAfterDisabled -> {
                        dayTextView.textColor = ColorState(
                            normalEnabled =
                            disabledDayTextColor ?: ThemeManager.theme.palette.textSecondary,
                            pressed = disabledDayTextColor ?: ThemeManager.theme.palette.textSecondary
                        )
                        dayTextView.background = null
                    }

                    else -> {
                        dayTextView.textColor =
                            ColorState(normalEnabled = dayTextColor ?: ThemeManager.theme.palette.textPrimary)
                    }
                }
            }
        }
    }

    private fun getMonthHeaderFooterBinder() = object : MonthHeaderFooterBinder<MonthHeaderFooterViewContainer> {
        override fun create(view: View) = MonthHeaderFooterViewContainer(view)
        override fun bind(container: MonthHeaderFooterViewContainer, month: CalendarMonth) {
            // Setup each header day text if we have not done that already.
            if (container.legendLayout.tag == null) {
                container.legendLayout.tag = month.yearMonth
                container.legendLayout.children.map { it as TextView }
                    .forEachIndexed { index, textViewWeek ->
                        val dayOfWeek = daysOfWeekFromLocale()[index]
                        val displayName = dayOfWeek.weekToHuman(resources)
                        textViewWeek.text = displayName

                        textViewWeek.textColor =
                            ColorState(normalEnabled = dayWeekTextColor ?: ThemeManager.theme.palette.textSecondary)
                    }
            }

            val displayName = month.yearMonth.month.monthToHuman(resources)
            val monthText =
                "$displayName  ${month.year}".replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            container.monthYearTextView.text = monthText

            container.monthYearTextView.textColor =
                ColorState(normalEnabled = monthYearTextColor ?: ThemeManager.theme.palette.textPrimary)
        }
    }

    /**
     * Set visible months.
     */
    private fun invalidateMonths() {
        if (isVertical) {
            internalCalendarView.setup(
                firstMonth ?: YearMonth.now(),
                lastMonth ?: YearMonth.now().plusMonths(MONTHS_TO_SHOW),
                firstDayOfWeek
            )
        } else {
            internalCalendarView.setup(
                firstMonth ?: YearMonth.now().minusMonths(MONTHS_TO_SHOW_HORIZONTAL),
                lastMonth ?: YearMonth.now().plusMonths(MONTHS_TO_SHOW_HORIZONTAL),
                firstDayOfWeek
            )
        }
    }

    private fun invalidateOrientation() {
        if (isVertical) {
            internalCalendarView.monthHeaderResource = R.layout.admiral_item_calendar_header
            horizontalHeaderMonth.isVisible = false
            horizontalHeaderDayOfWeek.isVisible = false

            internalCalendarView.orientation = RecyclerView.VERTICAL
            internalCalendarView.scrollMode = ScrollMode.CONTINUOUS
        } else {
            internalCalendarView.monthHeaderResource = 0
            horizontalHeaderMonth.isVisible = true
            horizontalHeaderDayOfWeek.isVisible = true

            internalCalendarView.orientation = RecyclerView.HORIZONTAL
            internalCalendarView.scrollMode = ScrollMode.PAGED
        }

        invalidateMonths()
    }

    private fun showDatePicker() {
        datePickerHorizontalMonthYearTextView.apply {
            setOnClickListener { showCalendar() }
        }

        datePickerView.findViewById<DatePickerOld>(R.id.datePicker).apply {
            startYear = datePickerStartYear ?: (firstMonth ?: YearMonth.now()
                .minusMonths(MONTHS_TO_SHOW_HORIZONTAL / 2))
                .year
            endYear = datePickerEndYear ?: (lastMonth ?: YearMonth.now()
                .plusMonths(MONTHS_TO_SHOW_HORIZONTAL / 2))
                .year
            setDate(datePickerYearMonth)
        }

        horizontalHeaderMonth.isVisible = false
        horizontalHeaderDayOfWeek.isVisible = false
        internalCalendarView.isVisible = false
        datePickerView.isVisible = true
        onPickerInteractListener?.onPickerInteract(true)
    }

    private fun showCalendar() {
        horizontalHeaderMonth.isVisible = true
        horizontalHeaderDayOfWeek.isVisible = true
        internalCalendarView.isVisible = true
        datePickerView.isVisible = false
        onPickerInteractListener?.onPickerInteract(false)
    }

    /**
     * Subscribe for theme change.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.subscribe(this)
    }

    /**
     * Unsubscribe for theme change.
     */
    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateBackground()
        internalCalendarView.notifyCalendarChanged()
        invalidateHorizontalHeaderColors()
        invalidateWeekDaysColor()
    }

    /**
     * Get list of dates that are in the selected range of dates.
     */
    fun getSelectedDays(): List<LocalDate> {
        return when {
            startDay != null && endDay != null -> {
                val days = mutableListOf<LocalDate>()
                var startDay = this.startDay!!
                val endDay = this.endDay!!
                while (startDay != endDay) {
                    days.add(startDay)
                    startDay = startDay.plusDays(1)
                }
                days.add(endDay)
                days
            }
            startDay != null && endDay == null -> {
                listOf(startDay!!)
            }
            else -> emptyList()
        }
    }

    private fun daysOfWeekFromLocale(): Array<DayOfWeek> {
        var daysOfWeek = DayOfWeek.values()

        // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
        if (firstDayOfWeek != DayOfWeek.MONDAY) {
            val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
            val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
            daysOfWeek = rhs + lhs
        }
        return daysOfWeek
    }

    private fun Month.monthToHuman(resources: Resources): String = when (this) {
        Month.JANUARY -> resources.getString(R.string.month_january)
        Month.FEBRUARY -> resources.getString(R.string.month_february)
        Month.MARCH -> resources.getString(R.string.month_march)
        Month.APRIL -> resources.getString(R.string.month_april)
        Month.MAY -> resources.getString(R.string.month_may)
        Month.JUNE -> resources.getString(R.string.month_june)
        Month.JULY -> resources.getString(R.string.month_july)
        Month.AUGUST -> resources.getString(R.string.month_august)
        Month.SEPTEMBER -> resources.getString(R.string.month_september)
        Month.OCTOBER -> resources.getString(R.string.month_october)
        Month.NOVEMBER -> resources.getString(R.string.month_november)
        Month.DECEMBER -> resources.getString(R.string.month_december)
    }

    private fun DayOfWeek.weekToHuman(resources: Resources): String = when (this) {
        DayOfWeek.SUNDAY -> resources.getString(R.string.day_of_week_sunday)
        DayOfWeek.MONDAY -> resources.getString(R.string.day_of_week_monday)
        DayOfWeek.TUESDAY -> resources.getString(R.string.day_of_week_tuesday)
        DayOfWeek.WEDNESDAY -> resources.getString(R.string.day_of_week_wednesday)
        DayOfWeek.THURSDAY -> resources.getString(R.string.day_of_week_thursday)
        DayOfWeek.FRIDAY -> resources.getString(R.string.day_of_week_friday)
        DayOfWeek.SATURDAY -> resources.getString(R.string.day_of_week_saturday)
    }

    interface OnDateChooseListener {
        fun onDateChoose(startDate: LocalDate?, endDate: LocalDate?)
    }

    interface OnPickerInteractListener {
        fun onPickerInteract(isOpened: Boolean)
    }

    private inner class DayViewContainer(view: View) : ViewContainer(view) {
        var day: CalendarDay? = null // Will be set when this container is bound.
        val dayTextView: TextView = view.findViewById(R.id.calendarDayText)
        val mark: ImageView = view.findViewById(R.id.admiralCalendarMark)

        init {
            view.setOnClickListener {
                if (day?.owner != DayOwner.THIS_MONTH) {
                    return@setOnClickListener
                }
                val isAfterEnabled = if (!datesAfterEnabled) {
                    day?.date?.isBefore(LocalDate.now().plusDays(1)) == true
                } else true

                val isBeforeEnabled = if (!datesBeforeEnabled) {
                    day?.date?.isAfter(LocalDate.now().minusDays(1)) == true
                } else true

                if (!disabledDates.contains(day?.date) && isAfterEnabled && isBeforeEnabled
                ) {
                    val date = day!!.date

                    if (isMultipleSelection) {
                        if (startDay == null && endDay == null) {
                            // when we don't have end day - we should pick only start day

                            startDay = date
                        } else if (startDay != null && endDay == null) {
                            // when we have a start day and don't have an end day – pick the end day if the chosen day is after the start day.
                            // otherwise reselect the start day

                            if (date.isAfter(startDay)) {
                                endDay = date
                            } else {
                                endDay = startDay
                                startDay = date
                            }
                        } else if (startDay != null && endDay != null) {
                            // when both dates are chosen - start expanding the range or selecting a new range of dates
                            when {
                                date < startDay -> {
                                    startDay = date
                                }
                                date > endDay -> {
                                    endDay = date
                                }
                                else -> {
                                    val oldDateStart = startDay
                                    val oldDateEnd = endDay
                                    notifyDatesChanged(oldDateStart, oldDateEnd)

                                    startDay = date
                                    endDay = null
                                }
                            }
                        }
                    } else {
                        startDay?.let {
                            notifyDatesChanged(startDay, null)
                        }
                        startDay = date
                    }
                }
            }
        }
    }

    private fun notifyDatesChanged(startDate: LocalDate?, endDate: LocalDate?) {
        // notify date in the range from new start till new end dates.
        var startDateRange = startDate
        if (startDateRange != null && endDate != null) {
            while (startDateRange != endDate.plusDays(1)) {
                internalCalendarView.notifyDateChanged(startDateRange!!)
                startDateRange = startDateRange.plusDays(1)
            }
        } else if (startDateRange != null) {
            internalCalendarView.notifyDateChanged(startDateRange)
        }
    }

    private companion object {
        const val MONTHS_TO_SHOW = 3L
        const val MONTHS_TO_SHOW_HORIZONTAL = 24L
    }
}