package com.admiral.uikit.components.dialogs

import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import com.admiral.uikit.R
import com.admiral.uikit.ext.ADMIRAL_BOTTOM_SHEET_DEFAULT_CORNER_RADIUS
import com.admiral.uikit.ext.setupAdmiralDialog
import com.google.android.material.bottomsheet.BottomSheetDialog

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
    ) {
        private var dialog: AdmiralBottomSheetDialog = AdmiralBottomSheetDialog()

        private var cornerRadius: Float = ADMIRAL_BOTTOM_SHEET_DEFAULT_CORNER_RADIUS

        @StyleRes
        private var theme: Int = R.style.AdmiralBottomSheetDialogOverlay

        @LayoutRes
        private var layoutResId: Int? = null

        private var view: View? = null

        private var isFullHeight: Boolean = false

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
         * Set top corner radius.
         */
        fun setTopCornersRadius(radius: Float): Builder {
            this.cornerRadius = radius
            return this
        }

        /**
         * Call this method setup the dialog.
         */
        fun apply(): AdmiralBottomSheetDialog {
            val bottomSheetDialog = BottomSheetDialog(context, theme)

            dialog.dialog = bottomSheetDialog

            layoutResId?.let { layoutRes ->
                dialog.dialog.setContentView(layoutRes)
            }

            view?.let { view ->
                dialog.dialog.setContentView(view)
            }

            bottomSheetDialog.setupAdmiralDialog(
                cornerRadius = cornerRadius,
                isFullScreen = isFullHeight
            )

            return dialog
        }
    }
}