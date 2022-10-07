import comments.Comment
import exceptions.CommentNotFoundException
import exceptions.PostNotFoundException
import exceptions.ReportReasonNotFoundException
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import posts.Post

class WallServiceTest {

    @Before
    fun setUp() {
        WallService.clear()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun add() {
        assertTrue(WallService.add(Post()).id > 0)
    }

    @Test
    fun updateExistingId() {
        WallService.add(Post())
        assertTrue(WallService.update(Post(id = 1)))
    }


    @Test
    fun updateAllId() {
        WallService.add(Post())
        assertTrue(WallService.update(Post(id = 1)))
        assertFalse(WallService.update(Post(id = 2)))
    }

    @Test
    fun updateNonExistingPostId() {
        assertFalse(WallService.update(Post(id = 1)))
    }

    @Test
    fun createCommentSuccessfully() {
        WallService.add(Post())
        val comment = Comment(text = "test")
        assertTrue(WallService.createComment(1, comment) == comment)
        assertEquals(WallService.createComment(1, Comment()).id, 2)
    }

    @Test(expected = PostNotFoundException::class)
    fun createCommentWithException() {
        WallService.add(Post())
        WallService.createComment(100500, Comment())
    }

    @Test(expected = ReportReasonNotFoundException::class)
    fun reportCommentReasonExceptionPositive() {
        WallService.add(Post())
        WallService.createComment(1, Comment())
        WallService.reportComment(2, 1, 100500)
    }

    @Test(expected = ReportReasonNotFoundException::class)
    fun reportCommentReasonExceptionNegative() {
        WallService.add(Post())
        WallService.createComment(1, Comment())
        WallService.reportComment(2, 1, -100500)
    }

    @Test(expected = CommentNotFoundException::class)
    fun reportCommentTestNoCommentException() {
        WallService.add(Post())
        WallService.createComment(1, Comment())
        WallService.reportComment(2, 100500, 1)
    }

    @Test
    fun reportCommentTestReasonSuccess() {
        WallService.add(Post())
        WallService.createComment(1, Comment())
        WallService.createComment(1, Comment())
        assertEquals(WallService.reportComment(1, 2, 5).reason, 5)
    }
}