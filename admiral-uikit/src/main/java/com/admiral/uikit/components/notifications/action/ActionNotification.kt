package com.admiral.uikit.components.notifications.action

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.google.android.material.snackbar.Snackbar
import com.admiral.themes.ThemeManager
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.databinding.AdmiralNotificationActionBinding
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.dpToPx

class ActionNotification {

    lateinit var snackBarInstance: Snackbar
    private lateinit var builder: Builder

    /**
     * Show action notification
     */
    fun show(isTimerEnabled: Boolean = true) {
        snackBarInstance.show()
        builder.startTimer(isTimerEnabled)
    }

    /**
     * Hide action notification
     */
    fun hide() {
        snackBarInstance.dismiss()
        builder.stopTimer()
    }

    /**
     * Builder for [ActionNotification]
     */
    class Builder(context: Context, private val parentRootView: View) {
        private var totalTime: Long = TIME_SHORT_MILLISECONDS
        private var timer: CountDownTimer? = null

        private var actionNotification: ActionNotification = ActionNotification()

        private val layoutInflater = LayoutInflater.from(context)
        private val binding = AdmiralNotificationActionBinding.inflate(layoutInflater)

        /**
         * Listener to detect cancel icon clicked.
         */
        private var onCancelClickListener: View.OnClickListener? = null
            set(value) {
                field = value
                binding.cancelText.setOnClickListener {
                    value?.onClick(it)
                    actionNotification.hide()
                }
                binding.cancelImage.setOnClickListener {
                    value?.onClick(it)
                    actionNotification.hide()
                }
            }

        init {
            actionNotification.snackBarInstance = Snackbar.make(
                parentRootView,
                "",
                Snackbar.LENGTH_LONG
            )

            val layout = actionNotification.snackBarInstance.view as Snackbar.SnackbarLayout

            layout.setBackgroundColor(Color.TRANSPARENT)
            layout.setPadding(0, 0, 0, 0)
            layout.addView(binding.root, 0)

            binding.root.updateLayoutParams {
                width = CONTAINER_WIDTH.dpToPx(context)
            }

            this.setMargins()
            this.setGravity()
            this.setBackgroundColor()
            this.setCloseButtonText()
            this.setCloseButtonColor()
            this.setProgressColorState()
            this.setCloseButtonIcon()

            onCancelClickListener = null
        }

        /**
         * Set text
         */
        fun setText(message: CharSequence): Builder {
            binding.message.text = message
            return this
        }

        /**
         * Set text color
         */
        fun setTextColor(colorState: ColorState): Builder {
            binding.message.textColor = colorState
            return this
        }

        /**
         * Set close button color
         */
        fun setCloseButtonColor(
            @ColorInt color: Int = ThemeManager.theme.palette.elementAccent
        ): Builder {
            val colorState = ColorState(
                normalEnabled = color,
                normalDisabled = color.withAlpha(),
                pressed = color
            )

            binding.cancelText.textColorState = colorState
            binding.cancelImage.imageTintColorState = colorState
            return this
        }

        /**
         * Set close button text
         */
        fun setCloseButtonText(text: String = "Отмена"): Builder {
            binding.cancelText.text = text
            return this
        }

        /**
         * Set close button visibility
         */
        fun setCloseTextButtonVisible(isVisible: Boolean = true): Builder {
            binding.cancelText.isVisible = isVisible
            return this
        }

        /**
         * Set close icon visibility
         */
        fun setCloseIconButtonVisible(isVisible: Boolean = true): Builder {
            binding.cancelImage.isVisible = isVisible
            return this
        }

        /**
         * Set close button type [ActionNotificationCloseType]
         */
        fun setCloseButtonType(
            closeButtonType: ActionNotificationCloseType = ActionNotificationCloseType.TEXT
        ): Builder {
            when (closeButtonType) {
                ActionNotificationCloseType.ICON -> {
                    binding.cancelText.isVisible = false
                    binding.cancelImage.isVisible = true
                }

                ActionNotificationCloseType.TEXT -> {
                    binding.cancelText.isVisible = true
                    binding.cancelImage.isVisible = false
                }
            }
            return this
        }

        /**
         * Set close button icon
         * @param icon icon [Drawable]
         */
        fun setCloseButtonIcon(icon: Drawable? = null): Builder {
            val context = actionNotification.snackBarInstance.view.context
            val iconSet = icon ?: ContextCompat.getDrawable(
                context, R.drawable.admiral_ic_back_outline
            )
            binding.cancelImage.setImageDrawable(iconSet)
            return this
        }

        /**
         * Set close button click listener
         * @param onClickListener click listener [View.OnClickListener]
         */
        fun setCancelButtonClickListener(onClickListener: View.OnClickListener): Builder {
            onCancelClickListener = onClickListener
            return this
        }

        /**
         * Set progress color state
         * @param progressColorState progress color state [ColorState]
         */
        fun setProgressColorState(
            progressColorState: ColorState = ColorState(
                normalEnabled = ThemeManager.theme.palette.elementAccent
            )
        ): Builder {
            binding.timerProgressBar.progressTintList = parentRootView.colorStateList(
                enabled = progressColorState.normalEnabled
                    ?: ThemeManager.theme.palette.elementAccent,
                disabled = progressColorState.normalDisabled
                    ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                pressed = progressColorState.pressed
                    ?: ThemeManager.theme.palette.elementAccent
            )
            binding.timerText.textColor = progressColorState
            return this
        }

        /**
         * Set gravity
         * @param gravity [Gravity]
         */
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

        /**
         * Update gravity value
         */
        fun <T : ViewGroup> updateGravity(action: (ViewGroup) -> ViewGroup.LayoutParams): Builder {
            val rootViewGroup: ViewGroup = actionNotification.snackBarInstance.view as ViewGroup
            rootViewGroup.layoutParams = action.invoke(rootViewGroup)
            return this
        }

        /**
         * Set top and bottom margins
         * @param top top margin in Dp
         * @param bottom bottom margin in Dp
         */
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

        /**
         * Set background color
         * @param color - color [Int] value
         */
        fun setBackgroundColor(
            @ColorInt color: Int = ThemeManager.theme.palette.backgroundAdditionalOne
        ): Builder {
            binding.root.backgroundTintList = ColorStateList.valueOf(color)
            return this
        }

        /**
         * Set timer visibility
         * @param isVisible - if true timer will be visible
         */
        fun setTimerVisibility(isVisible: Boolean): Builder {
            binding.timerContainer.isVisible = isVisible
            return this
        }

        /**
         * Set timer duration
         * @param duration - in milliseconds
         */
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

        /**
         * Build [ActionNotification]
         */
        @Deprecated("Use `build()` instead", ReplaceWith("build()"))
        fun apply(): ActionNotification = build()

        /**
         * Build [ActionNotification]
         */
        fun build(): ActionNotification {
            actionNotification.builder = this
            return actionNotification
        }

        /**
         * Start timer
         */
        fun startTimer(isTimerEnabled: Boolean) {
            if (isTimerEnabled.not()) return

            timer = object : CountDownTimer(totalTime, COUNT_DOWN_INTERVAL) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsToShow =
                        ((millisUntilFinished / MILLISECONDS_TO_SECONDS) + 1).toInt()
                    binding.timerText.text = "$secondsToShow"

                    val progressBarValue =
                        (PROGRESS_BAR_TOTAL * (millisUntilFinished.toFloat() / totalTime)).toInt()

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        binding.timerProgressBar.setProgress(progressBarValue, true)
                    } else {
                        binding.timerProgressBar.progress = progressBarValue
                    }
                }

                override fun onFinish() {}
            }
            timer?.start()
        }

        /**
         * Stop timer
         */
        fun stopTimer() {
            timer?.cancel()
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
    }
}