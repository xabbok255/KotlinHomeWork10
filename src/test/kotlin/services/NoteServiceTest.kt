package services

import Note
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NoteServiceTest {

    @Before
    fun setUp() {
        NoteService.clear()
    }

    @Test
    fun add() {
        assertEquals(NoteService.add("A", "B", 2, 3), 0)
        assertEquals(NoteService.add("AA", "BB", 3, 4), 1)
    }

    @Test
    fun createComment() {
        NoteService.add("A", "B", 2, 3)
        assertEquals(NoteService.createComment(0, "msg", "guid"), 0)
        assertEquals(NoteService.createComment(0, "msg", "guid"), 1)
    }

    @Test(expected = NoSuchElementException::class)
    fun createCommentException() {
        NoteService.createComment(0, "msg", "guid")
    }

    @Test
    fun delete() {
        NoteService.add("A", "B", 2, 3)
        NoteService.delete(0)
        assert(NoteService.getById(0).deleted)
    }

    @Test(expected = NoSuchElementException::class)
    fun deleteException() {
        NoteService.delete(0)
    }

    @Test
    fun deleteNoteWithComments() {
        NoteService.add("A", "B", 2, 3)
        NoteService.createComment(0, "msg", "guid")
        NoteService.delete(0)
        assertTrue(NoteService.getComments(0).last().deleted)
    }

    @Test
    fun deleteComment() {
        NoteService.add("A", "B", 2, 3)
        NoteService.createComment(0, "msg", "guid")
        NoteService.deleteComment(0)
        assertTrue(NoteService.getComments(0).first().deleted)
    }

    @Test(expected = NoSuchElementException::class)
    fun deleteCommentException() {
        NoteService.deleteComment(0)
    }

    @Test
    fun edit() {
        NoteService.add("A", "B", 2, 3)
        NoteService.edit(0, "11111", "22222", 5, 6)
        assertEquals(NoteService.get(arrayOf(0)).last(), Note("11111", "22222", 5, 6))
    }

    @Test(expected = NoSuchElementException::class)
    fun editException() {
        NoteService.edit(0, "11111", "22222", 5, 6)
    }

    @Test
    fun editComment() {
        NoteService.add("A", "B", 2, 3)
        NoteService.createComment(0, "OLDMSG", "guid")
        NoteService.editComment(0, "NEWMSG")
        assertEquals(NoteService.getComments(0).last().message, "NEWMSG")
    }

    @Test(expected = NoSuchElementException::class)
    fun editCommentException() {
        NoteService.editComment(0, "")
    }

    @Test
    fun get() {
        val note1 = Note("A", "B", 3, 5)
        NoteService.add("A", "B", 3, 5)
        val note2 = NoteService.get(arrayOf(0)).last()
        assertEquals(note1, note2)
    }

    @Test
    fun getException() {
        NoteService.add("1", "2", 1, 2)

        assertThrows(NoSuchElementException::class.java) {
            NoteService.get(noteIds = arrayOf())
        }
    }

    @Test
    fun getCount() {
        NoteService.add("1", "2", 1, 2)
        NoteService.add("11", "22", 1, 2)
        NoteService.add("111", "222", 1, 2)
        NoteService.add("1111", "2222", 1, 2)

        assertArrayEquals(
            NoteService.get(noteIds = arrayOf(1, 2, 3), count = 2u).map { it.id }.toTypedArray(),
            arrayOf(1, 2)
        )
        assertArrayEquals(
            NoteService.get(noteIds = arrayOf(1, 2, 3), count = 0u).map { it.id }.toTypedArray(),
            arrayOf(1, 2, 3)
        )
    }

    @Test
    fun getSorted() {
        NoteService.add("1", "2", 1, 2)
        NoteService.add("11", "22", 1, 2)
        NoteService.add("111", "222", 1, 2)
        NoteService.add("1111", "2222", 1, 2)

        assertArrayEquals(
            NoteService.get(noteIds = arrayOf(1, 2, 3), sort = 0).map { it.id }.toTypedArray(),
            arrayOf(1, 2, 3)
        )
        assertArrayEquals(
            NoteService.get(noteIds = arrayOf(1, 2, 3)).map { it.id }.toTypedArray(),
            arrayOf(1, 2, 3)
        )

        assertArrayEquals(
            NoteService.get(noteIds = arrayOf(1, 2, 3), sort = 1).map { it.id }.toTypedArray(),
            arrayOf(3, 2, 1)
        )
    }

    @Test
    fun getOffset() {
        NoteService.add("1", "2", 1, 2)
        NoteService.add("11", "22", 1, 2)
        NoteService.add("111", "222", 1, 2)
        NoteService.add("1111", "2222", 1, 2)

        assertArrayEquals(
            NoteService.get(noteIds = arrayOf(1, 2, 3), offset = 1u).map { it.id }.toTypedArray(),
            arrayOf(2, 3)
        )

        assertThrows(NoSuchElementException::class.java) {
            NoteService.get(noteIds = arrayOf(1, 2, 3), offset = 10u).map { it.id }.toTypedArray()
        }
    }

    @Test
    fun getById() {
    }

    @Test
    fun getComments() {
        NoteService.add("A", "B", 2, 3)
        NoteService.add("AA", "BB", 2, 3)
        NoteService.createComment(0, "msg1", "guid")
        NoteService.createComment(1, "msg2", "guid")

        assertEquals(NoteService.getComments(0).last().message, "msg1")
    }

    @Test
    fun restoreComment() {
        NoteService.add("A", "B", 2, 3)
        NoteService.createComment(0, "msg", "guid")
        NoteService.deleteComment(0)
        assertTrue(NoteService.getComments(0).first().deleted)
        NoteService.restoreComment(0)
        assertFalse(NoteService.getComments(0).first().deleted)
    }

    @Test(expected = NoSuchElementException::class)
    fun restoreCommentException() {
        NoteService.restoreComment(0)
    }
}