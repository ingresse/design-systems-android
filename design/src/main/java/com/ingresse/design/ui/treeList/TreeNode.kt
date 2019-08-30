package com.ingresse.design.ui.treeList

class TreeNode(var data: TreeObject) {
    var children: MutableMap<String, TreeNode> = mutableMapOf()
    private fun setOpen(open: Boolean) = if (open) data.open() else data.close()
    private fun setSelected(value: Boolean) = if (value) data.select() else data.deselect()

    fun insertChild(child: TreeObject, replace: Boolean = true) {
        val childLevel = data.level() + 1
        if (child.level() != childLevel) {
            children[child.path()[childLevel]]?.insertChild(child)
            return
        }

        val id = child.id()
        if (!replace && children.containsKey(id)) return
        val node = TreeNode(child)
        children[id] = node
    }

    // --- Quantity
    fun add(count: Int, path: List<String>) {
        data.add(count)
        if (path.isEmpty()) return
        children[path[0]]?.add(count, path.drop(1))
    }

    fun remove(count: Int, path: List<String>) {
        data.remove(count)
        if (path.isEmpty()) return
        children[path[0]]?.remove(count, path.drop(1))
    }

    fun empty(path: List<String> = emptyList()) {
        if (path.isEmpty()) return emptyChildren()
        children[path[0]]?.empty(path.drop(1))
    }

    private fun emptyChildren() {
        data.empty()
        children.values.forEach { it.emptyChildren() }
    }

    fun fill(path: List<String> = emptyList()) {
        if (path.isEmpty()) return fillChildren()
        children[path[0]]?.empty(path.drop(1))
    }

    private fun getChildrenCount(): Int {
        return children.values.fold(0) { acc, node ->
            return acc + 1 + node.getChildrenCount()
        }
    }

    private fun fillChildren() {
        val total = getChildrenCount()
        data.add(total)
        children.values.forEach { it.fillChildren() }
    }

    // --- Folding
    fun openNode(path: List<String>) {
        if (path.isEmpty()) return setOpen(true)
        children[path[0]]?.openNode(path.drop(1))
    }

    fun openAll() {
        setOpen(true)
        children.values.forEach { it.openAll() }
    }

    fun closeNode(path: List<String>) {
        if (path.isEmpty()) return setOpen(false)
        children[path[0]]?.closeNode(path.drop(1))
    }

    fun closeAll() {
        setOpen(false)
        children.values.forEach { it.closeAll() }
    }

    // --- Selection
    fun selectNode(path: List<String>, value: Boolean) {
        if (path.isEmpty()) return setSelected(value)
        children[path[0]]?.selectNode(path.drop(1), value)
    }

    fun selectChildren(path: List<String>, value: Boolean) {
        if (path.isEmpty()) return selectAllChildren(value)
        children[path[0]]?.selectChildren(path.drop(1), value)
    }

    private fun selectAllChildren(value: Boolean) {
        setSelected(value)
        children.values.forEach { it.selectAllChildren(value) }
    }

    // --- Data
    fun getData(path: List<String>): TreeObject? {
        if (path.isEmpty()) return data
        return children[path[0]]?.getData(path.drop(1))
    }

    fun flat(): List<TreeData> {
        if (!data.isOpen()) {
            return listOf(data.toData())
        }

        return children.values
                .sortedBy { it.data.sortKey() }
                .fold (listOf(data.toData())) { acc, node -> acc.plus(node.flat()) }
    }
}