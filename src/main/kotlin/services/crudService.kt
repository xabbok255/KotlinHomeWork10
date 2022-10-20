package services

import Entity

class Crud<T : Entity<T>> : Iterable<T> {
    private val list: MutableList<T> = mutableListOf()

    fun count(): Int {
        return list.count()
    }

    fun add(item: T): T {
        val newId: Int = list.count()
        val newItem = item.changeId(newId)
        list.add(newItem)
        return newItem
    }

    fun delete(idToDelete: Int) {
        list.getOrNull(idToDelete)
            ?.delete()
            ?: throw NoSuchElementException("No item with id $idToDelete")
    }

    fun restore(idToRestore: Int) {
        list.getOrNull(idToRestore)
            ?.recover()
            ?: throw NoSuchElementException("No item with id $idToRestore")
    }

    fun edit(idToEdit: Int, newItem: T) {
        try {
            if (list[idToEdit].deleted) throw NoSuchElementException("Item with id $idToEdit is deleted")

            newItem.id = idToEdit
            list[idToEdit] = newItem
        } catch (e: IndexOutOfBoundsException) {
            throw NoSuchElementException("No item with id $idToEdit")
        }


    }

    fun getAllIds(): Array<Int> {
        return list
            .map { it.id }
            .toTypedArray()
    }

    fun get(getIds: Array<Int>, count: UInt = 0u, offset: UInt = 0u, sort: Int = 0): Collection<T> {
        if (getIds.isEmpty()) throw NoSuchElementException("List of Ids is empty")
        getIds.forEach { getById(it) }

        val returnList = list
            .asSequence()
            .filterIndexed { index, entity -> getIds.contains(index) }
            .sortedWith(compareBy { if (sort == 0) it.id else -it.id })
            .drop(offset.toInt())
            .take(if (count > 0u) count.toInt() else list.count())
            .toList()

        if (returnList.isEmpty()) throw NoSuchElementException(
            "No items with filter ids [${getIds.joinToString()}] with " +
                    "offset $offset and count limit $count in list [${list.joinToString()}]"
        )
        return returnList
    }

    fun getById(idToGet: Int): T {
        return list.getOrNull(idToGet) ?: throw NoSuchElementException("No item with id $idToGet")
    }

    override fun iterator(): Iterator<T> {
        return list.iterator()
    }

    override fun toString(): String {
        return "Crud(list=$list)"
    }

    fun clear() {
        list.clear()
    }

}