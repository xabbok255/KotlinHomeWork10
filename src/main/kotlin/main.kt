import attachments.*
import comments.Comment
import posts.Comments
import posts.Likes
import posts.Post
import posts.PostType

fun main() {
    WallService.add(Post(ownerId = 1, text = "12345"))
    //Thread.sleep(1000)
    WallService.add(Post(ownerId = 1, text = "67890"))
    WallService.add(Post(ownerId = 2, text = "200000"))
    WallService.add(Post(ownerId = 2, text = "300000", postType = PostType.REPLY))

    WallService.add(Post(ownerId = 3, text = "800000", likes = Likes(canLike = true)))

    WallService.update(Post(id = 2, text = "replaced", comments = Comments(canClose = true)))
    WallService.update(Post(id = 1, text = "new text", comments = Comments(canOpen = true), canPin = false))

    WallService.add(Post(attachments = arrayOf(AudioAttachment(), EventAttachment(), NoteAttachment(), PostedPhotoAttachment(),
    GraffitiAttachment())))

    WallService.createComment(1, Comment())
    WallService.reportComment(10, 1, 5)

    println(WallService)
}