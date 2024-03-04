package com.admiral.demo.ext

import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
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

fun withIndex(matcher: Matcher<View?>, index: Int): Matcher<View?> {
    return object : TypeSafeMatcher<View?>() {
        var currentIndex = 0
        override fun describeTo(description: Description) {
            description.appendText("with index: ")
            description.appendValue(index)
            matcher.describeTo(description)
        }

        override fun matchesSafely(view: View?): Boolean {
            return matcher.matches(view) && currentIndex++ == index
        }
    }
}