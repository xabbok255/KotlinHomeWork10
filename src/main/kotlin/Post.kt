data class Post(
    val id: Int = 0,
    val ownerId: Int = 0,
    val date: ULong = 0u,
    val signer_id: Int = 0,
    val text: String = "",
    val canPin: Boolean = true,
    val canEdit: Boolean = true,
    val canDelete: Boolean = true,
    val comments: Comments = Comments(),
    val likes: Likes = Likes()
)
