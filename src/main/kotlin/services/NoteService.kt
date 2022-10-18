package services

import Comment
import Note

object NoteService {
    private var notes: Crud<Note> = Crud()
    private val comments: Crud<Comment> = Crud()

    /* not using privacy_view and privacy_comment */
    fun add(title: String, text: String, privacy: Int, commentPrivacy: Int): Int {
        return notes.add(
            Note(
                title = title,
                text = text,
                privacy = privacy,
                commentPrivacy = commentPrivacy
            )
        ).id
    }

    /* not using ownerId and replyTo */
    fun createComment(noteId: Int, message: String, guid: String): Int {
        getById(noteId)
        return comments.add(Comment(noteId = noteId, message = message, guid = guid)).id
    }

    fun delete(noteId: Int) {
        //если есть комментарии у удаляемого note, помечаем их удаленными тоже
        try {
            getComments(noteId).forEach {
                deleteComment(it.id)
            }
        }
        catch (_: NoSuchElementException) {

        }
        finally {
            notes.delete(noteId)
        }
    }

    /* not using owner_id */
    fun deleteComment(commentId: Int) {
        comments.delete(commentId)
    }

    fun edit(noteId: Int, title: String, text: String, privacy: Int, commentPrivacy: Int) {
        notes.edit(
            noteId, notes.getById(noteId).copy(
                title = title,
                text = text,
                privacy = privacy,
                commentPrivacy = commentPrivacy
            )
        )
    }

    /* not using owner_id */
    fun editComment(commentId: Int, message: String) {
        comments.edit(commentId, comments.getById(commentId).copy(message = message))
    }


    /* not using user_id */
    fun get(noteIds: Array<Int>, count: UInt = 0u, offset: UInt = 0u, sort: Int = 0): Collection<Note> {
        return notes.get(getIds = noteIds, count = count, offset = offset, sort = sort)
    }

    /* not using owner_id and need_wiki */
    fun getById(id: Int): Note {
        return notes.getById(id)
    }

    fun getComments(noteId: Int, count: UInt = 0u, offset: UInt = 0u, sort: Int = 0): Collection<Comment> {
        val allComments = comments.getAllIds()
        val commentIds = comments.get(allComments)
            .filter { it.noteId == noteId }
            .toTypedArray()
            .map { it.id }
            .toTypedArray()

        return comments.get(getIds = commentIds, count = count, offset = offset, sort = sort)
    }

    /* not using owner_id */
    fun restoreComment(commentId: Int) {
        comments.restore(commentId)
    }

    fun clear() {
        notes.clear()
        comments.clear()
    }
}