package comments

import attachments.Attachment

data class Comment(
    val id: Int = 0,
    val fromId: Int = 0,
    val date: UInt = 0u,
    val text: String = "",
    val donut: Donut = Donut(),
    val replyToUser: Int = 0,
    val replyToComment: Int = 0,
    val attachments: Array<Attachment> = emptyArray<Attachment>(),
    val parentStack: Array<Int> = emptyArray<Int>(),
    val thread: Thread = Thread()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Comment) return false

        if (fromId != other.fromId) return false
        if (date != other.date) return false
        if (text != other.text) return false
        if (donut != other.donut) return false
        if (replyToUser != other.replyToUser) return false
        if (replyToComment != other.replyToComment) return false
        if (!attachments.contentEquals(other.attachments)) return false
        if (!parentStack.contentEquals(other.parentStack)) return false
        if (thread != other.thread) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fromId
        result = 31 * result + date.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + donut.hashCode()
        result = 31 * result + replyToUser
        result = 31 * result + replyToComment
        result = 31 * result + attachments.contentHashCode()
        result = 31 * result + parentStack.contentHashCode()
        result = 31 * result + thread.hashCode()
        return result
    }

}
