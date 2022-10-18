package services

import Note
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CrudTest() {
    private val crud: Crud<Note> = Crud()

    @Test
    fun add() {
        val note1 = Note("A", "B", 3, 5)
        crud.add(Note())
        crud.add(Note("A", "B", 3, 5))
        val note2 = crud.get(arrayOf(1)).last()
        assertEquals(note1, note2)
    }

    @Test
    fun delete() {
        crud.add(Note())
        assertEquals(crud.count(), 1)
        crud.delete(0)
        assertEquals(crud.count(), 1)
        assertTrue(crud.get(arrayOf(0)).last().deleted)
    }

    @Test
    fun deleteException() {
        crud.add(Note())

        assertThrows(NoSuchElementException::class.java) {
            crud.delete(5)
        }

        assertThrows(NoSuchElementException::class.java) {
            crud.delete(0)
            crud.delete(0)
        }
    }

    @Test
    fun restore() {
        crud.add(Note())

        assertFalse(crud.get(arrayOf(0)).last().deleted)
        crud.delete(0)
        assertTrue(crud.get(arrayOf(0)).last().deleted)
        crud.restore(0)
        assertFalse(crud.get(arrayOf(0)).last().deleted)
    }

    @Test
    fun restoreException() {


        assertThrows(NoSuchElementException::class.java) {
            crud.add(Note())
            crud.restore(0)
        }

        assertThrows(NoSuchElementException::class.java) {
            crud.restore(5)
        }

    }

    @Test
    fun edit() {
        crud.add(Note())

        assertThrows(NoSuchElementException::class.java) {
            crud.edit(5, Note())
        }

        assertThrows(NoSuchElementException::class.java) {
            crud.delete(5)
        }

        assertThrows(NoSuchElementException::class.java) {
            crud.delete(0)
            crud.edit(0, Note())
        }


    }

    @Test
    fun editNormal() {
        val note1 = Note("A", "B", 3, 5)
        crud.add(Note())
        crud.edit(0, note1)
        assertEquals(crud.getById(0), note1)
    }

    @Test
    fun getAllIds() {
        assertArrayEquals(crud.getAllIds(), arrayOf())
        crud.add(Note())
        crud.add(Note())
        crud.add(Note())
        assertArrayEquals(crud.getAllIds(), arrayOf(0, 1, 2))
    }

    @Test
    fun get() {
        val note1 = Note("A", "B", 3, 5)
        crud.add(Note("A", "B", 3, 5))
        val note2 = crud.get(arrayOf(0)).last()
        assertEquals(note1, note2)
    }

    @Test
    fun getException() {
        crud.add(Note())

        assertThrows(NoSuchElementException::class.java) {
            crud.get(getIds = arrayOf())
        }
    }

    @Test
    fun getCount() {
        crud.add(Note())
        crud.add(Note())
        crud.add(Note())
        crud.add(Note())
        assertArrayEquals(
            crud.get(getIds = arrayOf(1, 2, 3), count = 2u).map { it.id }.toTypedArray(),
            arrayOf(1, 2)
        )
        assertArrayEquals(
            crud.get(getIds = arrayOf(1, 2, 3), count = 0u).map { it.id }.toTypedArray(),
            arrayOf(1, 2, 3)
        )
    }

    @Test
    fun getSorted() {
        crud.add(Note())
        crud.add(Note())
        crud.add(Note())
        crud.add(Note())
        assertArrayEquals(
            crud.get(getIds = arrayOf(1, 2, 3), sort = 0).map { it.id }.toTypedArray(),
            arrayOf(1, 2, 3)
        )
        assertArrayEquals(
            crud.get(getIds = arrayOf(1, 2, 3)).map { it.id }.toTypedArray(),
            arrayOf(1, 2, 3)
        )

        assertArrayEquals(
            crud.get(getIds = arrayOf(1, 2, 3), sort = 1).map { it.id }.toTypedArray(),
            arrayOf(3, 2, 1)
        )
    }

    @Test
    fun getOffset() {
        crud.add(Note())
        crud.add(Note())
        crud.add(Note())
        crud.add(Note())

        assertArrayEquals(
            crud.get(getIds = arrayOf(1, 2, 3), offset = 1u).map { it.id }.toTypedArray(),
            arrayOf(2, 3)
        )

        assertThrows(NoSuchElementException::class.java) {
            crud.get(getIds = arrayOf(1, 2, 3), offset = 10u).map { it.id }.toTypedArray()
        }
    }

    @Test
    fun getById() {
        val note1 = Note("A", "B", 3, 5)
        crud.add(Note())
        crud.add(Note("A", "B", 3, 5))
        val note2 = crud.getById(1)
        assertEquals(note1, note2)

        assertThrows(NoSuchElementException::class.java) {
            crud.getById(10)
        }
    }

    @Before
    fun setUp() {
        crud.clear()
    }
}