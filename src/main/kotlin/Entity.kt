abstract class Entity<T : Entity<T>>(
    id: Int = 0,
    deleted: Boolean = false
) {
    var deleted = deleted
        get() = field
        set(value) {
            field = value
        }

    var id = id
        get() = field
        set(value) {
            field = value
        }

    fun changeId(id: Int): T {
        this.id = id
        return self()
    }

    fun delete() : T {
        if (this.deleted) {
            throw NoSuchElementException("Element with id ${this.id} already deleted")
        }
        this.deleted = true
        return self()
    }

    fun recover() : T {
        if (!this.deleted) {
            throw NoSuchElementException("Element with id ${this.id} is not deleted")
        }
        this.deleted = false
        return self()
    }

    /*fun getId() : Int {
        return id
    }*/

    protected abstract fun self(): T
}
