package com.admiral.demo.ext

import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers

@Suppress("MatchingDeclarationName")
class BetterScrollTo(scrollToAction: ViewAction = ViewActions.scrollTo()) : ViewAction by scrollToAction {
    override fun getConstraints(): Matcher<View> {
        return Matchers.allOf(
            ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
            ViewMatchers.isDescendantOfA(
                Matchers.anyOf(
                    ViewMatchers.isAssignableFrom(NestedScrollView::class.java),
                    ViewMatchers.isAssignableFrom(ScrollView::class.java),
                    ViewMatchers.isAssignableFrom(HorizontalScrollView::class.java),
                    ViewMatchers.isAssignableFrom(ListView::class.java)
                )
            )
        )
    }
}

/**
 * Use this method for measuring view [View.MeasureSpec.UNSPECIFIED]. It means view can be whatever size it wants.
 */
fun View.measureUnspecified() {
    val spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    measure(spec, spec)
}

/**
 * Use this method for measuring view with [View.MeasureSpec.UNSPECIFIED] height.
 * It means view height can be whatever size it wants.
 * @param widthInPixels width in pixels which this view will have after measuring.
 * Default value 1080 (width of the emulator for screenshot tests)
 */
fun View.measureUnspecifiedHeight(widthInPixels: Int = 1080) {
    measure(
        View.MeasureSpec.makeMeasureSpec(widthInPixels, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
}