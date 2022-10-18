data class Comment(
    val noteId: Int = 0,
    val replyTo: String = "",
    val message: String = "",
    val guid: String = ""
) : Entity<Comment>() {
    override fun self(): Comment {
        return this
    }

    override fun toString(): String {
        return "Comment(id=$id, noteId=$noteId, deleted=$deleted, replyTo='$replyTo', message='$message', guid='$guid')"
    }
}

