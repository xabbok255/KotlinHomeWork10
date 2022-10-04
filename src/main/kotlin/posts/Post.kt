package posts

import attachments.Attachment

data class Post(
    val id: Int = 0,
    val ownerId: Int = 0,
    val fromId: Int = 0,
    val createdBy: Int = 0,
    val date: ULong = 0u,
    val text: String = "",
    val replyOwnerId: Int = 0,
    val replyPostId: Int = 0,
    val friendsOnly: Boolean = false,
    val comments: Comments? = Comments(),
    val copyright: String = "",
    val likes: Likes? = Likes(),
    val reposts: Reposts? = Reposts(),
    val views: Views? = Views(),
    val postType: PostType = PostType.POST,
    val postSource: PostSource = PostSource(),
    val attachments: Array<Attachment>? = null,
    val geo: Geo? = Geo(),
    val signerId: Int = 0,
    val copyHistory: Array<Post>? = null,
    val canPin: Boolean = true,
    val canDelete: Boolean = true,
    val canEdit: Boolean = true,
    val isPinned: Boolean = false,
    val markedAsAds: Boolean = false,
    val isFavorite: Boolean = false,
    val postponedId: Int = 0
)
