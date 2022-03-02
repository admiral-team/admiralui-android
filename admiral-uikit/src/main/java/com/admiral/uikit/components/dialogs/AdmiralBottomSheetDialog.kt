package com.admiral.uikit.components.dialogs

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
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

    class Builder(context: Context, private val isFullScreen: Boolean = false) {
        private var dialog: AdmiralBottomSheetDialog = AdmiralBottomSheetDialog()

        init {
            dialog.dialog = BottomSheetDialog(context, R.style.AdmiralBottomSheetDialog)
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
        }

        private fun setupBehaviour(behaviour: BottomSheetBehavior<View>) {
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            behaviour.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
            behaviour.skipCollapsed = true
        }

        private fun setupFullHeight(bottomSheet: View) {
            val layoutParams = bottomSheet.layoutParams
            if (isFullScreen) {
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            } else {
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            }

            bottomSheet.layoutParams = layoutParams
        }

        fun setContent(layoutResId: Int): Builder {
            dialog.dialog.setContentView(layoutResId)
            return this
        }

        fun setContent(view: View): Builder {
            dialog.dialog.setContentView(view)
            return this
        }

        fun apply(): AdmiralBottomSheetDialog {
            return dialog
        }
    }
}