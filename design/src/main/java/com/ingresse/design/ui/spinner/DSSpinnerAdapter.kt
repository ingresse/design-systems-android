package com.ingresse.design.ui.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ingresse.design.R

class DSSpinnerAdapter(context: Context): ArrayAdapter<String>(context, R.layout.item_custom_spinner, R.id.txt_item) {
    var items: List<String> = emptyList()
    var shortItems: List<String> = emptyList()

    override fun getCount() = items.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup) =
            createView(parent, getViewItem(position))

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup) =
            createView(parent, items[position])

    private fun getViewItem(position: Int): String {
        if (position > items.size) return ""
        return if (shortItems.size != items.size) items[position] else shortItems[position]
    }

    private fun createView(parent: ViewGroup, text: String): View {
        val inflater = LayoutInflater.from(context)
        val row = inflater.inflate(R.layout.item_custom_spinner, parent, false)
        val txt = row.findViewById<TextView>(R.id.txt_item)
        txt.text = text

        return row
    }
}