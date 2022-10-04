package attachments

data class NoteAttachment(
    val id: Int = 0,
    val ownerId: Int = 0,
    val title: String = "",
    val text: String = "",
    val date: UInt = 0u,
    val comments: Int = 0,
    val readComments: Int = 0,
    val viewUrl: String = ""
) : Attachment(AttachmentType.NOTE)
