package com.admiral.uikit.view.calendar.ui

import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.core.view.MarginLayoutParamsCompat.getMarginEnd
import androidx.core.view.MarginLayoutParamsCompat.getMarginStart
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import com.admiral.uikit.ext.inflate
import com.admiral.uikit.view.calendar.model.CalendarDay
import com.admiral.uikit.view.calendar.model.Size

internal data class DayConfig(
    val size: Size,
    @LayoutRes val dayViewRes: Int,
    val viewBinder: DayBinder<ViewContainer>
)

internal class DayHolder(private val config: DayConfig) {

    private lateinit var dateView: View
    private lateinit var viewContainer: ViewContainer
    private var day: CalendarDay? = null

    fun inflateDayView(parent: LinearLayout): View {
        dateView = parent.inflate(config.dayViewRes).apply {
            // This will be placed in the WeekLayout(A LinearLayout) hence we
            // use LinearLayout.LayoutParams and set the weight appropriately.
            // The parent's wightSum is already set to 7 to accommodate seven week days.
            updateLayoutParams<LinearLayout.LayoutParams> {
                width = config.size.width - getMarginStart(this) - getMarginEnd(this)
                height = config.size.height - marginTop - marginBottom
                weight = 1f
            }
        }
        return dateView
    }

    fun bindDayView(currentDay: CalendarDay?) {
        this.day = currentDay
        if (!::viewContainer.isInitialized) {
            viewContainer = config.viewBinder.create(dateView)
        }

        val dayHash = currentDay?.date.hashCode()
        if (viewContainer.view.tag != dayHash) {
            viewContainer.view.tag = dayHash
        }

        if (currentDay != null) {
            if (!viewContainer.view.isVisible) {
                viewContainer.view.isVisible = true
            }
            config.viewBinder.bind(viewContainer, currentDay)
        } else if (!viewContainer.view.isGone) {
            viewContainer.view.isGone = true
        }
    }

    fun reloadViewIfNecessary(day: CalendarDay): Boolean {
        return if (day == this.day) {
            bindDayView(this.day)
            true
        } else {
            false
        }
    }
}
