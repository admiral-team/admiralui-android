package com.admiral.uikit.components.dialogsfragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import com.admiral.uikit.R
import com.admiral.uikit.ext.ADMIRAL_BOTTOM_SHEET_DEFAULT_CORNER_RADIUS
import com.admiral.uikit.ext.setupAdmiralDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class AdmiralBottomSheetDialogFragment(
    @LayoutRes
    private val layoutResId: Int,
    private val cornerRadius: Float = ADMIRAL_BOTTOM_SHEET_DEFAULT_CORNER_RADIUS,
    private val isFullScreen: Boolean = false,
    @StyleRes
    private val theme: Int? = null
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutResId, null, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =
            BottomSheetDialog(requireContext(), theme ?: R.style.AdmiralBottomSheetDialogOverlay)

        dialog.setupAdmiralDialog(
            cornerRadius = cornerRadius,
            isFullScreen = isFullScreen,
        )

        return dialog
    }
}
