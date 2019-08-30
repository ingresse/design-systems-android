package com.ingresse.design.ui.treeList

data class TreeData(val path: List<String> = emptyList(),
               val id: String = "",
               val sortKey: String = "",
               val level: Int = 0,
               val isOpen: Boolean = false,
               val isSelected: Boolean = false,
               var count: Int = 0,
               val obj: TreeObject
)

fun TreeObject.toData() = TreeData(path(), id(), sortKey(), level(), isOpen(), isSelected(), count(), this)