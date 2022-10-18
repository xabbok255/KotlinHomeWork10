data class Note(
    val title: String = "",
    val text: String = "",
    val privacy: Int = 0,
    val commentPrivacy: Int = 0,

) : Entity<Note>() {
    override fun self(): Note {
        return this
    }

    override fun toString(): String {
        return "Note(id=$id, deleted=$deleted title='$title', text='$text', privacy=$privacy, commentPrivacy=$commentPrivacy)"
    }
}
