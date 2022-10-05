import exceptions.PostNotFoundException
import posts.Comment
import posts.Post
import java.lang.Exception

object WallService {
    private var posts: Array<Post> = emptyArray()
    private var comments: Array<Comment> = emptyArray()

    fun createComment(postId: Int, comment: Comment) : Comment {
        val post: Post = posts.find { post ->  post.id == postId} ?: throw PostNotFoundException("No post with id $postId")
        comments += comment
        return comment
    }

    override fun toString(): String {
        return "Posts: ${posts.contentToString()} Comments: ${comments.contentToString()}"
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