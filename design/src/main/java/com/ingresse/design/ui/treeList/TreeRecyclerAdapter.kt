package com.ingresse.design.ui.treeList

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class TreeRecyclerAdapter(val viewHolder: (level: Int, parent: ViewGroup) -> TreeVH): RecyclerView.Adapter<TreeVH>() {
    private var items: MutableList<TreeData> = mutableListOf()

    fun updateItems(list: List<TreeData>) {
        val callback = TreeDiffCallback(items, list)
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)

        items = mutableListOf()
        items.addAll(list)
    }

    fun add(count: Int, path: List<String>) {
        val position = items.indexOfFirst { path.dropLast(1) == it.path }
        items[position].count += count
        notifyDataSetChanged()
    }

    fun remove(count: Int, path: List<String>) {
        val position = items.indexOfFirst { path.dropLast(1) == it.path }
        items[position].count -= count
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = viewHolder(viewType, parent)
    override fun onBindViewHolder(holder: TreeVH, position: Int) = holder.loadItem(items[position])
    override fun getItemViewType(position: Int) = items[position].level
}
