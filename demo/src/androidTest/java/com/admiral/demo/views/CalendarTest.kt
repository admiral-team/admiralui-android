package com.admiral.demo.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.databinding.FmtTestBinding
import com.admiral.demo.ext.waitUntilShown
import com.admiral.uikit.components.calendar.common.CalendarState
import com.admiral.uikit.components.calendar.common.DisabledDaysInfo
import com.admiral.uikit.components.calendar.common.Selection
import com.admiral.uikit.components.calendar.common.SelectionMode
import com.admiral.uikit.components.calendar.horisontal.HorizontalCalendar
import com.admiral.uikit.components.calendar.vertical.VerticalCalendar
import com.karumi.shot.FragmentScenarioUtils.waitForFragment
import com.karumi.shot.ScreenshotTest
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneOffset

class CalendarTest : ScreenshotTest {
    private val fixedClock = Clock.fixed(
        Instant.parse("2022-01-26T12:00:00Z"),
        ZoneOffset.UTC
    )

    private val state = CalendarState(
        initialYearMonth = YearMonth.of(2022, 1),
        selectionMode = SelectionMode.IntervalSelection,
        selection = Selection.IntervalSelection(
            startDate = LocalDate.of(2022, 1, 5),
            endDate = LocalDate.of(2022, 1, 15)
        ),
        disabledDaysInfo = DisabledDaysInfo(
            disabledDays = listOf(
                LocalDate.of(2022, 1, 21),
                LocalDate.of(2022, 1, 22),
                LocalDate.of(2022, 1, 23),
                LocalDate.of(2022, 1, 24),
            )
        ),
        markedDays = listOf(
            LocalDate.of(2022, 1, 2),
            LocalDate.of(2022, 1, 28)
        )
    )

    @Test
    fun checkProgrammaticallyVerticalCalendar() {
        val scenario = launchFragmentInContainer() {
            CalendarTestFragment(
                fixedClock = fixedClock,
                isVertical = true,
                state = state
            )
        }

        val fragment = scenario.waitForFragment()
        onView(isRoot()).perform(waitUntilShown(R.id.calendarMonth, VIEW_WAITING_LIMIT_IN_MILLS))
        compareScreenshot(fragment)
    }

    @Test
    fun checkProgrammaticallyHorizontalCalendar() {
        val scenario = launchFragmentInContainer() {
            CalendarTestFragment(
                fixedClock = fixedClock,
                isVertical = false,
                state = state
            )
        }

        val fragment = scenario.waitForFragment()
        onView(isRoot()).perform(waitUntilShown(R.id.calendarMonth, VIEW_WAITING_LIMIT_IN_MILLS))
        waitForAnimationsToFinish()
        compareScreenshot(fragment)
    }

    private companion object {
        const val VIEW_WAITING_LIMIT_IN_MILLS = 5000L
    }
}

class CalendarTestFragment(
    private val fixedClock: Clock,
    private val isVertical: Boolean,
    private val state: CalendarState
) : Fragment(R.layout.fmt_test) {
    private val binding by viewBinding(FmtTestBinding::bind)
    private val vmStoreOwner = ViewModelStoreOwner { ViewModelStore() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCalendarView()
    }

    private fun addCalendarView() {
        val calendar = if (isVertical) {
            VerticalCalendar(requireContext()).apply {
                ViewTreeViewModelStoreOwner.set(this, vmStoreOwner)
                this.calendarState = state
                this.clock = fixedClock
            }
        } else {
            HorizontalCalendar(requireContext()).apply {
                ViewTreeViewModelStoreOwner.set(this, vmStoreOwner)
                this.calendarState = state
                this.clock = fixedClock
                this.isDebounceEnabled = false
            }
        }

        binding.container.addView(calendar)
    }
}