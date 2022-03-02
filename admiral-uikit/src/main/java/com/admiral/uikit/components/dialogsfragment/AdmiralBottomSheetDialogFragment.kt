package com.admiral.uikit.components.dialogsfragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.admiral.uikit.R

abstract class AdmiralBottomSheetDialogFragment(
    private val layoutResId: Int,
    private val isFullScreen: Boolean = false
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutResId, null, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), R.style.AdmiralBottomSheetDialog)

        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            parentLayout?.let { view ->
                val behaviour = BottomSheetBehavior.from(view)

                setupBehaviour(behaviour)
                setupFullHeight(view)
            }
        }

        return dialog
    }

    protected open fun setupBehaviour(behaviour: BottomSheetBehavior<View>) {
        behaviour.state = BottomSheetBehavior.STATE_EXPANDED
        behaviour.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
        behaviour.skipCollapsed = true
    }

    protected open fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        if (isFullScreen) {
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        } else {
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        }

        bottomSheet.layoutParams = layoutParams
    }
}