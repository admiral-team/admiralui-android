package com.admiral.uikit.components.dialogs

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.admiral.uikit.R

class AdmiralBottomSheetDialog {

    lateinit var dialog: Dialog

    fun show() {
        dialog.show()
    }

    fun hide() {
        dialog.dismiss()
    }

    class Builder(
        private val context: Context,
        @Deprecated("Use setIsFullScreen() instead") private val isFullScreen: Boolean = false
    ) {
        private var dialog: AdmiralBottomSheetDialog = AdmiralBottomSheetDialog()

        @LayoutRes
        private var layoutResId: Int = 0

        @StyleRes
        private var theme: Int = R.style.AdmiralBottomSheetDialogOverlay

        private var view: View? = null

        private var isFullHeight: Boolean = false

        init {
            // TODO: remove it
            this.isFullHeight = isFullScreen
        }

        private fun setupBehaviour(behaviour: BottomSheetBehavior<View>) {
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            behaviour.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
            behaviour.skipCollapsed = true
        }

        private fun setupFullHeight(bottomSheet: View) {
            val layoutParams = bottomSheet.layoutParams
            if (isFullHeight) {
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            } else {
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            }

            bottomSheet.layoutParams = layoutParams
        }

        /**
         * Set is dialog should be full screen size.
         */
        fun setIsFullScreen(isFullScreen: Boolean): Builder {
            this.isFullHeight = isFullScreen
            return this
        }

        /**
         * Set custom style. Otherwise [R.style.AdmiralBottomSheetDialogOverlay] will be set.
         */
        fun setStyle(@StyleRes theme: Int): Builder {
            this.theme = theme
            return this
        }

        /**
         * Set layout resource as a content to the dialog.
         */
        fun setContent(@LayoutRes layoutResId: Int): Builder {
            this.layoutResId = layoutResId
            return this
        }

        /**
         * Set View as a content to the dialog.
         */
        fun setContent(view: View): Builder {
            this.view = view
            return this
        }

        /**
         * Call this method setup the dialog.
         */
        fun apply(): AdmiralBottomSheetDialog {
            dialog.dialog = BottomSheetDialog(context, theme)
            dialog.dialog.setOnShowListener {
                val bottomSheetDialog = it as BottomSheetDialog
                val parentLayout =
                    bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

                parentLayout?.let { view ->
                    val behaviour = BottomSheetBehavior.from(view)

                    setupBehaviour(behaviour)
                    setupFullHeight(view)
                }
            }

            dialog.dialog.setContentView(layoutResId)
            view?.let { view ->
                dialog.dialog.setContentView(view)
            }

            return dialog
        }
    }
}