package com.admiral.uikit.components.actionbar

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import kotlin.math.abs

/**
 * TouchListener to simplify the swipe handling
 */
@SuppressLint("ClickableViewAccessibility")
open class ActionBarOnSwipeTouchListener(
    private val swipeableView: View,
    private val actionBar: View
) : View.OnTouchListener {
    private var gestureDetector = GestureDetector(swipeableView.context, GestureListener())

    private var isHorizontalScrolling = false
    private var isVerticalScrolling = false
    private var diffX = 0f

    private val maxOffset: Float by lazy(LazyThreadSafetyMode.NONE) {
        abs(actionBar.x - swipeableView.width)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.action == ACTION_UP) {
            resetScrollingIndicators()
            finishWithAnimation()
        }

        return gestureDetector.onTouchEvent(event)
    }

    open fun onSingleTapConfirmed(): Boolean = false

    open fun onLongPress() {}

    private inner class GestureListener : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return this@ActionBarOnSwipeTouchListener.onSingleTapConfirmed()
        }

        override fun onLongPress(e: MotionEvent) {
            this@ActionBarOnSwipeTouchListener.onLongPress()
        }

        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            val diffY = e2.y - e1.y
            diffX = e2.x - e1.x

            return if (isHorizontalScrolling) {
                onHorizontalScroll(swipeableView.x + diffX)
                true
            } else {
                if (!isVerticalScrolling && abs(diffX) > abs(diffY)) {
                    isHorizontalScrolling = true
                    onHorizontalScroll(swipeableView.x + diffX)
                    true
                } else {
                    isVerticalScrolling = true
                    false
                }
            }
        }
    }

    private fun onHorizontalScroll(delta: Float) {
        swipeableView.x = when {
            delta > 0 -> 0f
            delta < -maxOffset -> -maxOffset
            else -> delta
        }
    }

    private fun finishWithAnimation() {
        if (diffX < 0) {
            animateLeftSwipe()
        } else {
            animateRightSwipe()
        }
    }

    private fun animateLeftSwipe() = animateXTranslation(-maxOffset)

    private fun animateRightSwipe() = animateXTranslation(0f)

    private fun animateXTranslation(x: Float) {
        swipeableView.animate()
            .x(x)
            .setDuration(ANIMATION_DURATION)
            .setInterpolator(ANIMATION_INTERPOLATOR)
            .start()
    }

    private fun resetScrollingIndicators() {
        isHorizontalScrolling = false
        isVerticalScrolling = false
    }

    private companion object {
        const val ANIMATION_DURATION = 400L
        val ANIMATION_INTERPOLATOR = AccelerateDecelerateInterpolator()
    }
}