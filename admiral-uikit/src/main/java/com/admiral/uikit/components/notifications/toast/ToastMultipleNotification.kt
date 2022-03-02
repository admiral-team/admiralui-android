package com.admiral.uikit.components.notifications.toast

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.admiral.uikit.R
import com.admiral.uikit.components.notifications.fixed.StaticNotification
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.setMargins
import com.admiral.uikit.layout.LinearLayout

class ToastMultipleNotification {

    private lateinit var snackBarInstance: Snackbar
    private lateinit var builder: Builder

    fun show() {
        snackBarInstance.show()
    }

    fun hide() {
        snackBarInstance.dismiss()
        builder.removeNotifications()
    }

    fun addNotification(staticNotification: StaticNotification) {
        builder.addNotification(staticNotification)
    }

    fun collapse() {
        builder.collapse()
    }

    fun open() {
        builder.open()
    }

    class Builder(context: Context, parentRootView: View) {
        private var isCollapsed = true
        private var toastNotification: ToastMultipleNotification = ToastMultipleNotification()

        private val toastsContainerOpened: ViewGroup = LinearLayout(context).apply {
            showDividers = LinearLayoutCompat.SHOW_DIVIDER_MIDDLE
            dividerDrawable = ContextCompat.getDrawable(context, R.drawable.admiral_devider_space_vertical_12dp)

            orientation = LinearLayoutCompat.VERTICAL
            isVisible = false
        }

        private val toastsContainerCollapsed: ViewGroup = FrameLayout(context).apply {
            setOnClickListener {
                open()
            }
        }

        /**
         * Listener to detect cancel icon clicked.
         */
        private var onLinkClickListener: View.OnClickListener? = null

        init {
            toastNotification.snackBarInstance =
                Snackbar.make(parentRootView, "", Snackbar.LENGTH_LONG)
            val layout = toastNotification.snackBarInstance.view as Snackbar.SnackbarLayout

            layout.setBackgroundColor(Color.TRANSPARENT)
            layout.setPadding(0, 0, 0, 0)
            layout.addView(toastsContainerOpened, 0)
            layout.addView(toastsContainerCollapsed, 0)

            toastNotification.snackBarInstance.setCallback(object : Snackbar.Callback() {
                override fun onShown(snackbar: Snackbar) {
                    super.onShown(snackbar)
                    snackbar.view.visibility = View.VISIBLE
                }
            })

            this.setGravity()
            this.setMargins()
            this.setDuration()
        }

        fun setGravity(gravity: Int = Gravity.TOP): Builder {
            val view: View = toastNotification.snackBarInstance.view
            val params = view.layoutParams as FrameLayout.LayoutParams
            params.gravity = gravity
            view.layoutParams = params
            return this
        }

        fun setMargins(top: Int = 0, bottom: Int = 0): Builder {
            val snackBarView = toastNotification.snackBarInstance.view
            snackBarView.translationY = top.dpToPx(toastNotification.snackBarInstance.view.context).toFloat()

            if (bottom != 0) {
                snackBarView.translationY =
                    -bottom.dpToPx(toastNotification.snackBarInstance.view.context).toFloat()
            }

            return this
        }

        fun setDuration(duration: Int = Snackbar.LENGTH_LONG): Builder {
            toastNotification.snackBarInstance.duration = duration
            return this
        }

        fun setLinkClickListener(onClickListener: View.OnClickListener): Builder {
            onLinkClickListener = onClickListener
            return this
        }

        fun apply(): ToastMultipleNotification {
            toastNotification.builder = this
            return toastNotification
        }

        fun addNotification(staticNotification: StaticNotification) {
            val context = staticNotification.context

            if (isCollapsed) {
                toastsContainerCollapsed.addView(staticNotification)

                val toastList = toastsContainerCollapsed.children.toList().reversed()
                if (toastList.size > 1) {
                    val top = (MARGIN_COLLAPSED).dpToPx(context)

                    toastList[1].setMargins(top = top, left = top, right = top)
                }

                if (toastList.size > 2) {
                    toastList[2].isVisible = false
                }
            } else {
                toastsContainerOpened.addView(staticNotification, 0)
            }

            toastNotification.snackBarInstance.show()
        }

        fun removeNotifications() {
            toastsContainerOpened.removeAllViews()
            toastsContainerCollapsed.removeAllViews()
        }

        fun collapse() {
            isCollapsed = true

            val views = toastsContainerCollapsed.children.toList().reversed()
            toastsContainerCollapsed.removeAllViews()
            toastsContainerCollapsed.isVisible = false
            toastsContainerOpened.isVisible = true

            views.forEach {
                toastsContainerCollapsed.addView(it)

                val toastList = toastsContainerCollapsed.children.toList().reversed()
                if (toastList.size > 1) {
                    val top = (MARGIN_COLLAPSED).dpToPx(it.context)

                    toastList[1].setMargins(top = top, left = top, right = top)
                }

                if (toastList.size > 2) {
                    toastList[2].isVisible = false
                }
            }
        }

        fun open() {
            isCollapsed = false
            val views = toastsContainerCollapsed.children.toList().reversed()
            toastsContainerCollapsed.removeAllViews()
            toastsContainerCollapsed.isVisible = false
            toastsContainerOpened.isVisible = true

            views.forEach {
                it.isVisible = true
                it as StaticNotification
                it.backgroundColors = it.backgroundColors
                it.layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                it.setMargins(0, 0, 0, 0)
                toastsContainerOpened.addView(it)
            }
        }

        private companion object {
            const val MARGIN_COLLAPSED = 4
        }
    }
}