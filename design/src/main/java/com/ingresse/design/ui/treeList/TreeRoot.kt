package com.ingresse.backstage.base.ui.treeList

import androidx.lifecycle.MutableLiveData

class TreeRoot {
    val list: MutableLiveData<List<TreeData>> = MutableLiveData()
    var nodes: MutableMap<String, TreeNode> = mutableMapOf()

    // ----- Quantity
    fun add(count: Int, path: List<String>) {
        if (path.isEmpty()) return
        nodes[path[0]]?.add(count, path.drop(1))
    }

    fun remove(count: Int, path: List<String>) {
        if (path.isEmpty()) return
        nodes[path[0]]?.remove(count, path.drop(1))
    }

    fun fill(path: List<String> = emptyList()) {
        if (path.isEmpty()) return nodes.values.forEach { it.fill() }
        nodes[path[0]]?.fill(path.drop(1))
    }

    fun empty(path: List<String> = emptyList()) {
        if (path.isEmpty()) return nodes.values.forEach { it.empty() }
        nodes[path[0]]?.empty(path.drop(1))
    }

    // ----- Folding
    fun openNode(path: List<String>) {
        if (path.isEmpty()) return
        nodes[path[0]]?.openNode(path.drop(1))
    }

    fun closeNode(path: List<String>) {
        if (path.isEmpty()) return
        nodes[path[0]]?.closeNode(path.drop(1))
    }

    fun closeAll() { nodes.values.forEach { it.closeAll() } }

    // ----- Selection
    fun selectNode(path: List<String>, value: Boolean) {
        if (path.isEmpty()) return
        nodes[path[0]]?.selectNode(path.drop(1), value)
    }

    fun selectChildren(path: List<String>, value: Boolean) {
        if (path.isEmpty()) return
        nodes[path[0]]?.selectChildren(path.drop(1), value)
    }

    // Structure
    fun insertNode(child: TreeObject, replace: Boolean = true) {
        if (child.level() != 0) {
            nodes[child.path()[0]]?.insertChild(child)
            return
        }

        if (!replace && nodes.containsKey(child.id())) return
        val node = TreeNode(child)
        nodes[child.id()] = node
    }

    fun insertNodes(children: List<TreeObject>) = children.forEach { insertNode(it) }
    fun removeAllNodes() = nodes.clear()

    fun getData(path: List<String>): TreeObject? {
        if (path.isEmpty()) return null
        return nodes[path[0]]?.getData(path.drop(1))
    }

    fun update() = list.postValue(flat())
    private fun flat(): List<TreeData> {
        return nodes.values
                .sortedBy { it.data.sortKey() }
                .fold(listOf()) { acc, node -> acc.plus(node.flat()) }
    }
}