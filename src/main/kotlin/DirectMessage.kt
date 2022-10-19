data class DirectMessage(
    val fromId: Int,
    val toId: Int,
    val chatId: Int,
    val message: String,
    var seen: Boolean = false
) : Entity<DirectMessage>() {

    override fun self(): DirectMessage {
        return this
    }

    override fun toString(): String {
        return "DirectMessage(id=$id, fromId=$fromId, toId=$toId, message='$message', seen=$seen, chatId=$chatId)"
    }
}
