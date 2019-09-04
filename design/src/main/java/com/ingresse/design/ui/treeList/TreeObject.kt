package com.ingresse.design.ui.treeList

interface TreeObject {
    fun path(): List<String>
    fun id(): String
    fun sortKey(): String
    fun level(): Int

    // Selection
    fun isSelected(): Boolean
    fun select() {}
    fun deselect() {}

    // Quantity
    fun count(): Int
    fun add(count: Int) {}
    fun remove(count: Int) {}
    fun empty() {}

    // Folding
    fun isOpen(): Boolean
    fun open() {}
    fun close() {}
}