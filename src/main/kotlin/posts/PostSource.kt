package posts

data class PostSource(
    val type: PostSourceType = PostSourceType.VK,
    val platform: PostSourcePlatform = PostSourcePlatform.ANDROID,
    val data: PostSourceData? = null,
    val url: String = ""
)
