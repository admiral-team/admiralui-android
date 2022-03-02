package com.admiral.uikit.components.dialogs

import android.app.Dialog
import android.content.Context
import android.view.View

class AlertDialog {

    lateinit var dialog: Dialog

    fun show() {
        dialog.show()
    }

    fun hide() {
        dialog.dismiss()
    }

    class Builder(context: Context) {
        private var dialog: AlertDialog = AlertDialog()

        init {
            dialog.dialog = Dialog(context, com.admiral.uikit.R.style.AdmiralAlertDialog)
        }

        fun setContent(layoutResId: Int): Builder {
            dialog.dialog.setContentView(layoutResId)
            return this
        }

        fun setContent(view: View): Builder {
            dialog.dialog.setContentView(view)
            return this
        }

        fun apply(): AlertDialog {
            return dialog
        }
    }
}