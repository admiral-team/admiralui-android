package com.admiral.uikit.ext

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat.setBackground
import com.admiral.themes.ThemeManager
import com.admiral.uikit.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

internal fun BottomSheetDialog.setupAdmiralDialog(
    cornerRadius: Float,
    isFullScreen: Boolean,
) {
    setOnShowListener {
        val bottomSheetDialog = it as BottomSheetDialog
        val parentLayout =
            bottomSheetDialog.findViewById<ViewGroup>(com.google.android.material.R.id.design_bottom_sheet)

        val childLayout = parentLayout?.getChildAt(0)
        childLayout?.roundTopCorners(cornerRadius = cornerRadius)

        parentLayout?.let { bottomSheetRootView ->
            // set behavior and height via layout params
            val layoutParams = getLayoutParamsWithHeight(
                bottomSheetView = bottomSheetRootView,
                isFullHeight = isFullScreen
            )
            layoutParams.behavior = setupBehaviour(bottomSheetDialog)

            // set corners
            val newMaterialShapeDrawable: MaterialShapeDrawable =
                bottomSheetRootView.createMaterialShapeDrawableWithRadius(cornerRadius = cornerRadius)
            setBackground(bottomSheetRootView, newMaterialShapeDrawable)
        }
    }
}

private fun setupBehaviour(bottomSheetDialog: BottomSheetDialog): BottomSheetDialogBehaviour<ViewGroup> {
    val behavior = BottomSheetDialogBehaviour<ViewGroup>()

    behavior.state = BottomSheetBehavior.STATE_EXPANDED
    behavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
    behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheetDialog.cancel()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}

    })
    behavior.skipCollapsed = true
    behavior.isHideable = true

    return behavior
}

private class BottomSheetDialogBehaviour<V : View> :
    BottomSheetBehavior<V>()

private fun getLayoutParamsWithHeight(
    bottomSheetView: View,
    isFullHeight: Boolean
): CoordinatorLayout.LayoutParams {
    val layoutParams = bottomSheetView.layoutParams as CoordinatorLayout.LayoutParams
    if (isFullHeight) {
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
    } else {
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    return layoutParams
}

private fun ViewGroup.createMaterialShapeDrawableWithRadius(cornerRadius: Float): MaterialShapeDrawable {
    val shapeAppearanceModel: ShapeAppearanceModel =
        //Create a ShapeAppearanceModel with the same shapeAppearanceOverlay used in the style
        ShapeAppearanceModel.builder(
            this.context,
            0,
            R.style.AdmiralBottomSheetDialogOverlay
        )
            .setTopLeftCornerSize(cornerRadius)
            .setTopRightCornerSize(cornerRadius)
            .build()

    //Create a new MaterialShapeDrawable (you can't use the original MaterialShapeDrawable in the BottomSheet)
    val background: Drawable? = this.getChildAt(0)?.background
    val color = if (background is ColorDrawable) background.color else null

    val newMaterialShapeDrawable = MaterialShapeDrawable((shapeAppearanceModel))
    if (color == null) {
        newMaterialShapeDrawable.setTint(ThemeManager.theme.palette.backgroundBasic)
    } else {
        newMaterialShapeDrawable.setTint(color)
    }
    newMaterialShapeDrawable.initializeElevationOverlay(this.context)

    return newMaterialShapeDrawable
}

internal const val ADMIRAL_BOTTOM_SHEET_DEFAULT_CORNER_RADIUS = 16f
