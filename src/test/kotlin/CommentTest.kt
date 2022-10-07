import attachments.AudioAttachment
import comments.Comment
import comments.Donut
import comments.Thread
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class CommentTest {
    private val comment: Comment = Comment(
        id = 1,
        fromId = 5,
        date = 10u,
        text = "asd",
        donut = Donut(),
        replyToUser = 5,
        replyToComment = 6,
        attachments = emptyArray(),
        parentStack = emptyArray(),
        thread = Thread()
    )

    @Test
    fun testEqualsAndHashCodeWithoutIdComparing() {
        val c1 = comment.copy(id = 10)
        val c2 = comment.copy(id = 11)

        assertEquals(c1, c2)
        assertEquals(c1.hashCode(), c2.hashCode())

        assertNotEquals(comment, Comment().copy(id = 1))
        assertNotEquals(comment.hashCode(), Comment().copy(id = 1).hashCode())
    }

    @Test
    fun testEquals() {
        val c1 = comment.copy(id = 10)
        assertEquals(c1, c1)
        assertNotEquals(c1, "another class")
        assertNotEquals(c1, c1.copy(date = 100500u))
        assertNotEquals(c1, c1.copy(text = "100500"))
        assertNotEquals(c1, c1.copy(replyToUser = 100500))
        assertNotEquals(c1, c1.copy(replyToComment = 100500))
        assertNotEquals(c1, c1.copy(replyToComment = 100500))
        assertNotEquals(c1, c1.copy(thread = Thread(1)))
        assertNotEquals(c1, c1.copy(parentStack = arrayOf(1, 2, 3, 4, 5)))
        assertNotEquals(c1, c1.copy(attachments = arrayOf(AudioAttachment())))
        assertNotEquals(c1, c1.copy(donut = Donut(true)))

    }
}