package com.ingresse.backstage.base.ui.treeList

data class TreeData(val path: List<String> = emptyList(),
               val id: String = "",
               val sortKey: String = "",
               val level: Int = 0,
               val isOpen: Boolean = false,
               val isSelected: Boolean = false,
               val count: Int = 0,
               val obj: TreeObject)

fun TreeObject.toData() = TreeData(path(), id(), sortKey(), level(), isOpen(), isSelected(), count(), this)