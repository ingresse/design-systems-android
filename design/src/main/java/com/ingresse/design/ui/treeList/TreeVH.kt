package com.ingresse.backstage.base.ui.treeList

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class TreeVH(view: View): RecyclerView.ViewHolder(view) {
    abstract fun getData(): TreeData
    abstract fun loadItem(data: TreeData)
}

data class VHInfo(val open: Boolean, val count: Int): RecyclerView.ItemAnimator.ItemHolderInfo()