package com.ingresse.design.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ingresse.design.R

open class DSBottomFragmentDialog(@LayoutRes val layout: Int,
                                  @StyleRes val style: Int = R.style.SheetDialog,
                                  private val isFullScreen: Boolean = true): BottomSheetDialogFragment() {

    override fun getTheme() = style

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layout, container, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return bottomSheetDialog.apply { setOnShowListener {
            if (isFullScreen) setFullScreen(it as BottomSheetDialog) } }
    }

    private fun setFullScreen(dialog: BottomSheetDialog) {
        val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
    }
}