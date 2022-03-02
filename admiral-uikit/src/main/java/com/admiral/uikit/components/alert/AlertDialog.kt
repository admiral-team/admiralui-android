package com.admiral.uikit.components.alert

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.admiral.uikit.R

@Deprecated("Please, use AlertDialogFragment.")
abstract class AlertDialog(private val layoutResId: Int) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_FRAME, R.style.AdmiralAlertDialog)
        return super.onCreateDialog(savedInstanceState)
    }
}