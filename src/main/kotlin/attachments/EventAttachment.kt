package attachments

data class EventAttachment(
    val id: Int = 0,
    val time: UInt = 0u,
    val memberStatus: Int = 0,
    val isFavorite: Boolean = false,
    val address: String = "",
    val text: String = "",
    val buttonText: String = "",
    val friends: Array<Int>? = null
) : Attachment(AttachmentType.EVENT)
