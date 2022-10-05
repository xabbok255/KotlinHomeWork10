import exceptions.PostNotFoundException
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import posts.Comment
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
    fun updateNonExistingId() {
        assertFalse(WallService.update(Post(id = 1)))
    }

    @Test
    fun createCommentSuccessfully() {
        WallService.add(Post())
        val comment: Comment = Comment(text = "test")
        assertTrue(WallService.createComment(1, comment) === comment)
    }

    @Test(expected = PostNotFoundException::class)
    fun createCommentException() {
        WallService.add(Post())
        WallService.createComment(100500, Comment())
    }
}