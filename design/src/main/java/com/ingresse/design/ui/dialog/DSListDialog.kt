package com.ingresse.design.ui.dialog

import android.content.Context
import com.ingresse.design.R
import com.ingresse.design.ui.adapters.BaseAdapter
import kotlinx.android.synthetic.main.ds_list_dialog.*

class DSListDialog(context: Context): DSBaseDialog(R.layout.ds_list_dialog, context) {
    /**
     * Function to set dialog properties
     *
     * @param title - Title to show on top of dialog.
     * @param adapter - Adapter to control list inside dialog.
     */
    fun <T>setDialog(title: String, adapter: BaseAdapter<T>) {
        lbl_dialog_title.text = title
        recycler.adapter = adapter
    }

    /**
     * Function to set dialog recycler minimum height
     *
     * @param height - Height to set as minimum.
     */
    fun setRecyclerMinHeight(height: Int) { recycler.minimumHeight = height }
}