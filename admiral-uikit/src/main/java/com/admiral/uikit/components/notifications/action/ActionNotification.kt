package com.admiral.uikit.components.notifications.action

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.admiral.themes.ThemeManager
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.imageview.ImageView
import com.admiral.uikit.components.links.Link
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.drawable

class ActionNotification {

    private lateinit var snackBarInstance: Snackbar
    private lateinit var builder: Builder

    fun show() {
        snackBarInstance.show()
        builder.startTimer()
    }

    fun hide() {
        snackBarInstance.dismiss()
        builder.stopTimer()
    }

    class Builder(context: Context, parentRootView: View) {
        private var totalTime: Long = TIME_SHORT_MILLISECONDS
        private var timer: CountDownTimer? = null

        private var actionNotification: ActionNotification = ActionNotification()
        private var parentRootView: View? = null

        private val toastView: View = createViewLayout(context)

        private var cancelButtonImage =
            toastView.findViewById<ImageView>(R.id.admiralNotificationActionImageCanel)
        private var cancelButtonText = toastView.findViewById<Link>(R.id.admiralTextCancel)
        private var actionTextView = toastView.findViewById<TextView>(R.id.admiralActionTextView)
        private var timerView = toastView.findViewById<FrameLayout>(R.id.admiralTimer)
        private var progressTextView = toastView.findViewById<TextView>(R.id.admiralTextTimer)
        private var progressProgressBar =
            toastView.findViewById<ProgressBar>(R.id.admiralProgressTimer)

        /**
         * Listener to detect cancel icon clicked.
         */
        private var onCancelClickListener: View.OnClickListener? = null
            set(value) {
                field = value
                cancelButtonText.setOnClickListener {
                    value?.onClick(it)
                    actionNotification.hide()
                }
                cancelButtonImage.setOnClickListener {
                    value?.onClick(it)
                    actionNotification.hide()
                }
            }

        init {
            actionNotification.snackBarInstance =
                Snackbar.make(parentRootView, "", Snackbar.LENGTH_LONG)
            val layout = actionNotification.snackBarInstance.view as Snackbar.SnackbarLayout

            layout.setBackgroundColor(Color.TRANSPARENT)
            layout.setPadding(0, 0, 0, 0)
            layout.addView(toastView, 0)

            this.setMargins()
            this.setGravity()
            this.setBackgroundColor()
            this.setCloseButtonText()
            this.setCloseButtonColor()
            this.setProgressColorState()
            this.setCloseButtonIcon()

            cancelButtonText.setOnClickListener {
                actionNotification.hide()
            }

            cancelButtonImage.setOnClickListener {
                actionNotification.hide()
            }
        }

        fun setText(message: CharSequence): Builder {
            actionTextView.text = message
            return this
        }

        fun setTextColor(colorState: ColorState): Builder {
            actionTextView.textColor = colorState
            return this
        }

        fun setCloseButtonColor(
            @ColorInt color: Int = ThemeManager.theme.palette.elementAccent
        ): Builder {
            val colorState = ColorState(
                normalEnabled = color,
                normalDisabled = color.withAlpha(),
                pressed = color
            )

            cancelButtonText.textColorState = colorState
            cancelButtonImage.imageTintColorState = colorState
            return this
        }

        fun setCloseButtonText(text: String = "Отмена"): Builder {
            cancelButtonText.text = text
            return this
        }

        fun setCloseTextButtonVisible(isVisible: Boolean = true): Builder {
            cancelButtonText.isVisible = isVisible
            return this
        }

        fun setCloseIconButtonVisible(isVisible: Boolean = true): Builder {
            cancelButtonImage.isVisible = isVisible
            return this
        }

        fun setCloseButtonType(
            closeButtonType: ActionNotificationCloseType = ActionNotificationCloseType.TEXT
        ): Builder {
            when (closeButtonType) {
                ActionNotificationCloseType.ICON -> {
                    cancelButtonText.isVisible = false
                    cancelButtonImage.isVisible = true
                }

                ActionNotificationCloseType.TEXT -> {
                    cancelButtonText.isVisible = true
                    cancelButtonImage.isVisible = false
                }
            }

            return this
        }

        fun setCloseButtonIcon(icon: Drawable? = null): Builder {
            val context = actionNotification.snackBarInstance.view.context
            val iconSet = icon ?: ContextCompat.getDrawable(
                context, R.drawable.admiral_ic_back_outline
            )
            cancelButtonImage.setImageDrawable(iconSet)

            return this
        }

        fun setCancelButtonClickListener(onClickListener: View.OnClickListener): Builder {
            onCancelClickListener = onClickListener
            return this
        }

        fun setProgressColorState(
            progressColorState: ColorState = ColorState(
                normalEnabled = ThemeManager.theme.palette.elementAccent
            )
        ): Builder {
            progressProgressBar.progressTintList = parentRootView?.colorStateList(
                enabled = progressColorState.normalEnabled
                    ?: ThemeManager.theme.palette.elementAccent,
                disabled = progressColorState.normalDisabled
                    ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                pressed = progressColorState.pressed
                    ?: ThemeManager.theme.palette.elementAccent
            )
            progressTextView.textColor = progressColorState
            return this
        }

        fun setGravity(gravity: Int = Gravity.BOTTOM): Builder {
            val view: View = actionNotification.snackBarInstance.view
            return if (view.layoutParams is CoordinatorLayout.LayoutParams) {
                val params = view.layoutParams as CoordinatorLayout.LayoutParams

                params.width = CoordinatorLayout.LayoutParams.WRAP_CONTENT
                params.gravity = gravity or Gravity.CENTER_HORIZONTAL

                view.layoutParams = params
                this
            } else {
                val params = view.layoutParams as FrameLayout.LayoutParams

                params.width = FrameLayout.LayoutParams.WRAP_CONTENT
                params.gravity = gravity or Gravity.CENTER_HORIZONTAL

                view.layoutParams = params
                this
            }
        }

        fun <T : ViewGroup> updateGravity(action: (ViewGroup) -> ViewGroup.LayoutParams): Builder {
            val rootViewGroup: ViewGroup = actionNotification.snackBarInstance.view as ViewGroup
            rootViewGroup.layoutParams = action.invoke(rootViewGroup)
            return this
        }

        fun setMargins(top: Int = 0, bottom: Int = DEFAULT_MARGIN_BOTTOM): Builder {
            val snackBarView = actionNotification.snackBarInstance.view
            snackBarView.translationY = top
                .dpToPx(actionNotification.snackBarInstance.view.context)
                .toFloat()
            snackBarView.translationY = -bottom
                .dpToPx(actionNotification.snackBarInstance.view.context)
                .toFloat()

            return this
        }

        fun setBackgroundColor(
            color: Int = ThemeManager.theme.palette.backgroundAdditionalOne
        ): Builder {
            toastView.backgroundTintList = ColorStateList.valueOf(color)
            return this
        }

        fun setTimerVisibility(isVisible: Boolean): Builder {
            timerView.isVisible = isVisible

            return this
        }

        fun setDuration(duration: Int = Snackbar.LENGTH_LONG): Builder {
            actionNotification.snackBarInstance.duration = duration

            totalTime = when (duration) {
                Snackbar.LENGTH_SHORT -> {
                    TIME_SHORT_MILLISECONDS
                }
                Snackbar.LENGTH_LONG -> {
                    TIME_LONG_MILLISECONDS
                }
                else -> duration.toLong()
            }
            return this
        }

        fun apply(): ActionNotification {
            actionNotification.builder = this
            return actionNotification
        }

        fun startTimer() {
            timer = object : CountDownTimer(totalTime, COUNT_DOWN_INTERVAL) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsToShow =
                        ((millisUntilFinished / MILLISECONDS_TO_SECONDS) + 1).toInt()
                    progressTextView.text = "$secondsToShow"

                    val progressBarValue =
                        (PROGRESS_BAR_TOTAL * (millisUntilFinished.toFloat() / totalTime)).toInt()

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        progressProgressBar.setProgress(progressBarValue, true)
                    } else {
                        progressProgressBar.progress = progressBarValue
                    }
                }

                override fun onFinish() {}
            }

            timer?.start()
        }

        fun stopTimer() {
            timer?.cancel()
        }

        private fun createViewLayout(context: Context) =
            LinearLayout(context).apply {
                val params =
                    LinearLayout.LayoutParams(
                        CONTAINER_WIDTH.dpToPx(context),
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                layoutParams = params

                background = context.drawable(R.drawable.admiral_bg_rectangle_8dp)
                backgroundTintList = ColorStateList.valueOf(
                    ThemeManager.theme.palette.backgroundAdditionalOne
                )
                setPadding(
                    CONTAINER_PADDING_HORIZONTAL.dpToPx(context),
                    CONTAINER_PADDING_VERTICAL.dpToPx(context),
                    CONTAINER_PADDING_HORIZONTAL.dpToPx(context),
                    CONTAINER_PADDING_VERTICAL.dpToPx(context)
                )

                addView(createTimer(context))
                addView(createTextsCenter(context))
                addView(createIconClose(context))
                addView(createTextClose(context))
            }

        private fun createTimer(context: Context) =
            FrameLayout(context).apply {
                id = R.id.admiralTimer
                layoutParams = LinearLayout.LayoutParams(
                    TIMER_VIEW_SIZE.dpToPx(context),
                    TIMER_VIEW_SIZE.dpToPx(context)
                )

                addView(
                    ProgressBar(
                        context,
                        null,
                        0,
                        android.R.style.Widget_Material_ProgressBar_Horizontal
                    ).apply {
                        id = R.id.admiralProgressTimer
                        isIndeterminate = false
                        max = PROGRESS_MAX_VALUE
                        progressDrawable = drawable(R.drawable.admiral_progress_bar)
                        scaleX = -1f
                        progressDrawable.colorFilter = BlendModeColorFilterCompat
                            .createBlendModeColorFilterCompat(
                                ThemeManager.theme.palette.elementAccent,
                                BlendModeCompat.SRC_ATOP
                            )
                    })

                addView(TextView(context).apply {
                    gravity = Gravity.CENTER
                    id = R.id.admiralTextTimer
                    textAlignment = TEXT_ALIGNMENT_CENTER
                    textStyle = ThemeManager.theme.typography.subhead3
                })
            }

        private fun createTextsCenter(context: Context) =
            TextView(context).apply {
                id = R.id.admiralActionTextView
                this.layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                ).apply {
                    setMargins(TEXT_MARGIN_START.dpToPx(context), 0, 0, 0)
                    gravity = Gravity.CENTER_VERTICAL
                }
                textStyle = ThemeManager.theme.typography.body2
            }

        private fun createIconClose(context: Context) =
            ImageView(context).apply {
                id = R.id.admiralNotificationActionImageCanel
                layoutParams = LinearLayout.LayoutParams(
                    IMAGE_VIEW_SIZE.dpToPx(context),
                    IMAGE_VIEW_SIZE.dpToPx(context)
                )
                imageTintColorState = ColorState(
                    normalEnabled = ThemeManager.theme.palette.elementPrimary
                )
                isFocusable = true
                isClickable = true
                isVisible = false
            }

        private fun createTextClose(context: Context) =
            Link(context).apply {
                id = R.id.admiralTextCancel
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER_VERTICAL
                }
                text = "Отмена"
                textStyle = ThemeManager.theme.typography.body2

                isFocusable = true
                isClickable = true
                isVisible = false
            }
    }

    private companion object {
        const val COUNT_DOWN_INTERVAL = 50L
        const val TIME_SHORT_MILLISECONDS = 2000L
        const val TIME_LONG_MILLISECONDS = 3500L
        const val DEFAULT_MARGIN_BOTTOM = 104
        const val PROGRESS_BAR_TOTAL = 100
        const val MILLISECONDS_TO_SECONDS = 1000

        const val CONTAINER_WIDTH = 328
        const val CONTAINER_PADDING_VERTICAL = 12
        const val CONTAINER_PADDING_HORIZONTAL = 16

        const val IMAGE_VIEW_SIZE = 28
        const val TIMER_VIEW_SIZE = 28
        const val TEXT_MARGIN_START = 16

        const val PROGRESS_MAX_VALUE = 100
    }
}