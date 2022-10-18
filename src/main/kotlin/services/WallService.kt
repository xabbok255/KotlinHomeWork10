package services

import Report
import comments.Comment
import exceptions.CommentNotFoundException
import exceptions.PostNotFoundException
import exceptions.ReportReasonNotFoundException
import posts.Post

private const val REASON_MAX = 8

object WallService {
    private var posts: Array<Post> = emptyArray()
    private var comments: Array<Comment> = emptyArray()
    private var reports: Array<Report> = emptyArray()

    fun createComment(postId: Int, comment: Comment): Comment {
        posts.find { post -> post.id == postId } ?: throw PostNotFoundException("No post with id $postId")
        val commentWithFixedId = comment.copy(id = comments.count() + 1)
        comments += commentWithFixedId
        return commentWithFixedId
    }

    fun reportComment(ownerId: Int, commentId: Int, reason: Int): Report {
        if (reason !in 0..REASON_MAX) throw ReportReasonNotFoundException("Reason $reason must be in range [0..$REASON_MAX]")
        comments.find { comment -> comment.id == commentId }
            ?: throw CommentNotFoundException("No comment with id $commentId")
        val report = Report(ownerId, commentId, reason)
        reports += report
        return report
    }

    override fun toString(): String {
        return "Posts: ${posts.contentToString()} Comments: ${comments.contentToString()} Reports: ${reports.contentToString()}"
    }

    fun add(post: Post): Post {
        val postWithModifiedId = post.copy(id = posts.size + 1, date = (System.currentTimeMillis() / 1000).toULong())
        posts += postWithModifiedId
            return postWithModifiedId
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
        comments = emptyArray()
        reports = emptyArray()
    }
}