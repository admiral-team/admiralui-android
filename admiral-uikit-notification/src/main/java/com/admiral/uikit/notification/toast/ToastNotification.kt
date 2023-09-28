package com.admiral.uikit.notification.toast

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import com.admiral.uikit.notification.R
import com.admiral.themes.ThemeManager
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.imageview.ImageView
import com.admiral.uikit.textview.TextView
import com.admiral.uikit.core.ext.dpToPx
import com.admiral.uikit.core.ext.drawable
import com.admiral.uikit.core.ext.pixels
import com.admiral.uikit.links.Link
import com.google.android.material.snackbar.Snackbar

class ToastNotification {

    lateinit var snackBarInstance: Snackbar

    fun show() {
        snackBarInstance.show()
    }

    fun hide() {
        snackBarInstance.dismiss()
    }

    class Builder(context: Context, parentRootView: View) {
        private var toastNotification: ToastNotification = ToastNotification()

        private val toastView: View =
            LayoutInflater.from(context).inflate(R.layout.admiral_notification, null)

        private var icon = toastView.findViewById<ImageView>(R.id.admiralIcon)
        private var closeIcon = toastView.findViewById<ImageView>(R.id.admiralCloseIcon)
        private var toastTextView =
            toastView.findViewById<TextView>(R.id.admiralToastTextView).apply {
                isVisible = false
            }
        private var toastLinkTextView = toastView.findViewById<Link>(R.id.admiralLinkTextView)

        /**
         * Listener to detect text changing.
         */
        private var onLinkClickListener: OnLinkClickListener? = null
            set(value) {
                field = value
                toastLinkTextView.setOnClickListener {
                    value?.onLinkClicked(toastNotification)
                }
            }

        private var isWidthMatchParent = false
        private var gravity = Gravity.TOP

        init {
            toastNotification.snackBarInstance =
                Snackbar.make(parentRootView, "", Snackbar.LENGTH_LONG)

            toastNotification.snackBarInstance.let { snackBar ->
                val layout = snackBar.view as Snackbar.SnackbarLayout

                layout.setBackgroundColor(Color.TRANSPARENT)
                layout.setPadding(0, 0, 0, 0)
                layout.addView(toastView, 0)
            }

            closeIcon.setOnClickListener {
                toastNotification.snackBarInstance.dismiss()
            }

            this.setBackgroundColor()
        }

        fun setText(message: CharSequence): Builder {
            toastTextView.text = message
            toastTextView.isVisible = message.isNotEmpty()
            return this
        }

        fun setLinkText(message: String): Builder {
            toastLinkTextView.text = message
            toastLinkTextView.isVisible = message.isNotEmpty()
            return this
        }

        fun setTextColor(colorState: ColorState): Builder {
            toastTextView.textColor = colorState
            return this
        }

        fun setLinkTextColor(colorState: ColorState): Builder {
            toastLinkTextView.textColorState = colorState
            return this
        }

        fun setIcon(iconDrawable: Drawable?): Builder {
            icon.setImageDrawable(iconDrawable)
            return this
        }

        fun setIconTintList(iconTintColorState: ColorState): Builder {
            icon.imageTintColorState = iconTintColorState
            icon.isColored = true
            return this
        }

        fun setCloseIconVisible(isVisible: Boolean): Builder {
            closeIcon.isVisible = isVisible
            return this
        }

        fun setCloseIconTintList(iconTintColorState: ColorState): Builder {
            closeIcon.imageTintColorState = iconTintColorState
            return this
        }

        fun setGravity(gravity: Int = Gravity.TOP): Builder {
            this.gravity = gravity

            return this
        }

        fun <T : ViewGroup> updateGravity(action: (ViewGroup) -> ViewGroup.LayoutParams): Builder {
            val rootViewGroup: ViewGroup = toastNotification.snackBarInstance.view as ViewGroup
            rootViewGroup.layoutParams = action.invoke(rootViewGroup)
            return this
        }

        fun setMargins(
            top: Int = 0, bottom: Int = 0, isDimenRes: Boolean = false
        ): Builder {
            val snackBarView = toastNotification.snackBarInstance.view
            val context = snackBarView.context

            val tomMargin = getMargin(isDimenRes, context, top)
            snackBarView.translationY = tomMargin.toFloat()

            if (bottom != 0) {
                val bottomMargin = getMargin(isDimenRes, context, bottom)
                snackBarView.translationY = -bottomMargin.toFloat()
            }

            return this
        }

        private fun getMargin(isDimenRes: Boolean, context: Context, margin: Int): Int {
            return if (isDimenRes) context.pixels(margin) else margin.dpToPx(context)
        }

        fun setIsWidthMatchParent(isMatchParent: Boolean): Builder {
            isWidthMatchParent = isMatchParent

            return this
        }

        fun setBackgroundColor(color: Int = ThemeManager.theme.palette.backgroundAdditionalOne): Builder {
            toastView.backgroundTintList = ColorStateList.valueOf(color)
            return this
        }

        fun setDuration(duration: Int = Snackbar.LENGTH_LONG): Builder {
            toastNotification.snackBarInstance.duration = duration
            return this
        }

        fun setLinkClickListener(onClickListener: OnLinkClickListener): Builder {
            onLinkClickListener = onClickListener
            return this
        }

        fun apply(): ToastNotification {
            // apply width of the toast.
            val viewInner: View =
                (toastNotification.snackBarInstance.view as ViewGroup).getChildAt(0)
            val paramsInner = viewInner.layoutParams as FrameLayout.LayoutParams
            paramsInner.width = if (isWidthMatchParent) {
                CoordinatorLayout.LayoutParams.MATCH_PARENT
            } else {
                CoordinatorLayout.LayoutParams.WRAP_CONTENT
            }

            // change inner gravity depended on toasts' width.
            if (isWidthMatchParent) {
                paramsInner.gravity = Gravity.START
            } else {
                paramsInner.gravity = Gravity.CENTER
            }
            viewInner.layoutParams = paramsInner

            // apply gravity to the outer view.
            val view: View = toastNotification.snackBarInstance.view
            if (view.layoutParams is CoordinatorLayout.LayoutParams) {
                val params = view.layoutParams as CoordinatorLayout.LayoutParams

                params.gravity = gravity or Gravity.CENTER_HORIZONTAL

                view.layoutParams = params
            } else {
                val params = view.layoutParams as FrameLayout.LayoutParams

                params.gravity = gravity or Gravity.CENTER_HORIZONTAL

                view.layoutParams = params
            }

            return toastNotification
        }

        private fun createViewLayout(context: Context) =
            LinearLayout(context).apply {
                val params =
                    LinearLayout.LayoutParams(
                        CONTAINER_WIDTH.dpToPx(context),
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                        .apply {
                            gravity = Gravity.CENTER
                        }
                layoutParams = params

                background = context.drawable(R.drawable.admiral_bg_rectangle_8dp)
                backgroundTintList =
                    ColorStateList.valueOf(ThemeManager.theme.palette.backgroundAdditionalOne)
                setPadding(
                    CONTAINER_PADDING_HORIZONTAL.dpToPx(context),
                    CONTAINER_PADDING_VERTICAL.dpToPx(context),
                    CONTAINER_PADDING_HORIZONTAL.dpToPx(context),
                    CONTAINER_PADDING_VERTICAL.dpToPx(context)
                )

                addView(createIconStart(context))
                addView(createTextsCenter(context))
                addView(createIconClose(context))
            }

        private fun createIconStart(context: Context) = ImageView(context).apply {
            id = R.id.admiralIcon
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, ICONS_MARGIN.dpToPx(context), 0)
            }
            imageTintList = null
            isColored = false
        }

        private fun createTextsCenter(context: Context) =
            LinearLayout(context).apply {
                this.layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
                orientation = LinearLayout.VERTICAL

                addView(TextView(context).apply {
                    id = R.id.admiralToastTextView

                    textStyle = ThemeManager.theme.typography.body2
                })

                addView(Link(context).apply {
                    id = R.id.admiralLinkTextView
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        gravity = Gravity.START
                    }

                    setMargins(LINK_MARGIN_TOP, 0)
                    isVisible = false
                })
            }

        private fun createIconClose(context: Context) = ImageView(context).apply {
            id = R.id.admiralCloseIcon
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(ICONS_MARGIN.dpToPx(context), 0, 0, 0)
            }

            setImageDrawable(drawable(R.drawable.admiral_ic_close_outline))
            imageTintColorState =
                ColorState(normalEnabled = ThemeManager.theme.palette.elementPrimary)
            isFocusable = true
            isClickable = true
            isVisible = false
        }
    }

    fun interface OnLinkClickListener {
        fun onLinkClicked(toastNotification: ToastNotification)
    }

    private companion object {
        const val CONTAINER_WIDTH = 328

        const val CONTAINER_PADDING_VERTICAL = 12
        const val CONTAINER_PADDING_HORIZONTAL = 16

        const val ICONS_MARGIN = 12
        const val LINK_MARGIN_TOP = 8
    }
}