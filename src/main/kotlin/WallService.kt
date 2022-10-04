import posts.Post

object WallService {
    private var posts: Array<Post> = emptyArray()

    override fun toString(): String {
        return posts.contentToString()
    }

    fun add(post: Post): Post {
        posts += post.copy(id = posts.size + 1, date = (System.currentTimeMillis() / 1000).toULong())
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index, value) in posts.withIndex()) {
            if (value.id == post.id) {
                posts[index] = post.copy(ownerId = value.ownerId, date = value.date)
                return true
            }
        }

        return false
    }

    fun clear() {
        posts = emptyArray()
    }
}