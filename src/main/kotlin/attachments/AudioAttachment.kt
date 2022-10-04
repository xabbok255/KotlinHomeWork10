package attachments

data class AudioAttachment(
    val id: Int = 0,
    val ownerId: Int = 0,
    val artist: String = "",
    val title: String = "",
    val duration: Int = 0,
    val url: String = "",
    val lyricsId: Int? = null,
    val albumId: Int? = null,
    val genreId: Int = 0,
    val date: UInt = 0u,
    val noSearch: Boolean? = null,
    val isHq: Boolean = false
) : Attachment(AttachmentType.AUDIO)