package services

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class ChatServiceTest {

    @Before
    fun setUp() {
        ChatService.clear()
    }

    @Test
    fun getChatId() {
        ChatService.chatToUser(1, 2, "")
        val chatId = ChatService.chatToUser(2, 3, "")
        assertEquals(ChatService.getChat(2, 3)?.id, chatId)
        assertEquals(ChatService.getChat(3, 2)?.id, chatId)

        assertNull(ChatService.getChat(10, 10)?.id)
        assertNull(ChatService.getChat(3, 10)?.id)
        assertNull(ChatService.getChat(10, 3)?.id)
    }

    @Test
    fun chatToUser() {
        assertEquals(ChatService.chatToUser(2, 3, ""), 0)
        assertEquals(ChatService.chatToUser(3, 2, ""), 0)
        assertEquals(ChatService.chatToUser(2, 3, ""), 0)
        assertEquals(ChatService.chatToUser(3, 2, ""), 0)
        assertEquals(ChatService.chatToUser(20, 30, ""), 1)
        assertEquals(ChatService.chatToUser(30, 20, ""), 1)
    }

    @Test
    fun getUnreadChatsCountSwap() {
        ChatService.chatToUser(2, 3, "1")
        ChatService.chatToUser(2, 3, "2")
        ChatService.chatToUser(3, 2, "3")

        ChatService.chatToUser(3, 5, "3")

        assertEquals(ChatService.getUnreadChatsCount(2), 1)
        assertEquals(ChatService.getUnreadChatsCount(3), 2)
        assertEquals(ChatService.getUnreadChatsCount(2, ChatService.MessageDirection.ANY), 1)
        assertEquals(ChatService.getUnreadChatsCount(3, ChatService.MessageDirection.ANY), 2)

        assertEquals(ChatService.getUnreadChatsCount(3, ChatService.MessageDirection.INCOMING), 1)
        assertEquals(ChatService.getUnreadChatsCount(3, ChatService.MessageDirection.OUTGOING), 2)
    }

    @Test
    fun getChatsWithUnreadMessages() {
        ChatService.chatToUser(2, 3, "1")
        ChatService.chatToUser(2, 4, "2")
        ChatService.chatToUser(2, 5, "3")
        ChatService.chatToUser(4, 3, "3")

        ChatService.chatToUser(3, 2, "11")
        ChatService.chatToUser(3, 4, "22")

        assertEquals(ChatService.getChatsWithUnreadMessages(3).count(), 2)
        assertEquals(ChatService.getChatsWithUnreadMessages(2).count(), 1)
        assertEquals(ChatService.getChatsWithUnreadMessages(4).count(), 2)
        assertEquals(ChatService.getChatsWithUnreadMessages(5).count(), 1)
    }

    @Test
    fun getChatsWithUnreadMessagesByDirection() {
        ChatService.chatToUser(2, 3, "1")
        ChatService.chatToUser(2, 4, "2")
        ChatService.chatToUser(2, 5, "3")
        ChatService.chatToUser(4, 3, "3")

        ChatService.chatToUser(3, 2, "11")
        ChatService.chatToUser(3, 4, "22")

        assertEquals(ChatService.getChatsWithUnreadMessagesByDirection(3).count(), 2)
        assertEquals(ChatService.getChatsWithUnreadMessagesByDirection(2).count(), 3)
        assertEquals(ChatService.getChatsWithUnreadMessagesByDirection(4).count(), 2)
        assertEquals(ChatService.getChatsWithUnreadMessagesByDirection(5).count(), 1)

        assertEquals(
            ChatService.getChatsWithUnreadMessagesByDirection(
                2, ChatService.MessageDirection.INCOMING
            ).count(), 1
        )
        assertEquals(
            ChatService.getChatsWithUnreadMessagesByDirection(
                3, ChatService.MessageDirection.INCOMING
            ).count(), 2
        )
        assertEquals(
            ChatService.getChatsWithUnreadMessagesByDirection(
                4, ChatService.MessageDirection.INCOMING
            ).count(), 2
        )
        assertEquals(
            ChatService.getChatsWithUnreadMessagesByDirection(
                5, ChatService.MessageDirection.INCOMING
            ).count(), 1
        )

        assertEquals(
            ChatService.getChatsWithUnreadMessagesByDirection(
                2, ChatService.MessageDirection.OUTGOING
            ).count(), 3
        )
        assertEquals(
            ChatService.getChatsWithUnreadMessagesByDirection(
                3, ChatService.MessageDirection.OUTGOING
            ).count(), 2
        )
        assertEquals(
            ChatService.getChatsWithUnreadMessagesByDirection(
                4, ChatService.MessageDirection.OUTGOING
            ).count(), 1
        )
        assertEquals(
            ChatService.getChatsWithUnreadMessagesByDirection(
                5, ChatService.MessageDirection.OUTGOING
            ).count(), 0
        )
    }

    @Test
    fun getUnreadChatsCount() {
        assertEquals(ChatService.getUnreadChatsCount(3), 0)

        ChatService.chatToUser(2, 3, "")
        ChatService.chatToUser(2, 3, "")
        assertEquals(ChatService.getUnreadChatsCount(3), 1)

        ChatService.chatToUser(5, 3, "")
        ChatService.chatToUser(5, 3, "")
        assertEquals(ChatService.getUnreadChatsCount(3), 2)

        ChatService.getMessagesFromChat(3, 1, 2, 1)
        assertEquals(ChatService.getUnreadChatsCount(3), 2)
        ChatService.getMessagesFromChat(3, 1, 3, 1)
        assertEquals(ChatService.getUnreadChatsCount(3), 1)

        ChatService.deleteChat(1)
        ChatService.deleteChat(0)
        assertEquals(ChatService.getUnreadChatsCount(3), 0)
    }

    @Test
    fun getChats() {
        assertEquals(ChatService.getChats().count(), 0)

        ChatService.chatToUser(2, 3, "")
        assertEquals(ChatService.getChats().count(), 1)

        ChatService.deleteChat(0)
        assertEquals(ChatService.getChats().count(), 0)
    }

    @Test
    fun getChatsByUser() {
        assertEquals(ChatService.getChatsByUser(1).count(), 0)

        ChatService.chatToUser(1, 2, "1")
        assertEquals(ChatService.getChatsByUser(1).count(), 1)

        ChatService.chatToUser(1, 2, "2")
        assertEquals(ChatService.getChatsByUser(1).count(), 1)

        ChatService.chatToUser(2, 1, "3")
        assertEquals(ChatService.getChatsByUser(1).count(), 1)

        ChatService.chatToUser(1, 30, "30")
        assertEquals(ChatService.getChatsByUser(1).count(), 2)

        ChatService.deleteChat(1)
        assertEquals(ChatService.getChatsByUser(1).count(), 1)
    }

    @Test
    fun getMessagesFromChat() {
        ChatService.chatToUser(2, 1, "A")
        ChatService.chatToUser(2, 1, "S")
        ChatService.chatToUser(2, 1, "D")

        assertEquals(ChatService.getMessagesFromChat(1, 0, 0, 1).count(), 1)
        assertEquals(ChatService.getMessagesFromChat(1, 0, 1, 2).count(), 2)

        assertEquals(ChatService.getMessagesFromChat(1, 0, 0, 1).first().message, "A")
        assertArrayEquals(
            ChatService.getMessagesFromChat(1, 0, 1, 2).map { it.message }.toTypedArray(), arrayOf("S", "D")
        )
    }

    @Test
    fun deleteMessage() {
        ChatService.chatToUser(2, 1, "A")
        ChatService.chatToUser(2, 1, "S")
        ChatService.chatToUser(2, 1, "D")

        assertEquals(ChatService.getMessagesFromChat(1, 0, 0, 100).count(), 3)
        ChatService.deleteMessage(2, 1)

        assertEquals(ChatService.getMessagesFromChat(1, 0, 0, 100).count(), 2)

        ChatService.deleteMessage(2, 2)
        ChatService.deleteMessage(2, 0)
        assertEquals(ChatService.getMessagesFromChat(1, 0, 0, 100).count(), 0)

    }

    @Test(expected = NoSuchElementException::class)
    fun deleteMessageException() {
        ChatService.deleteMessage(1, 0)
    }

    @Test
    fun deleteChat() {
        ChatService.chatToUser(2, 1, "A")
        ChatService.chatToUser(2, 1, "S")
        ChatService.chatToUser(2, 1, "D")

        ChatService.chatToUser(3, 5, "AA")
        ChatService.chatToUser(3, 5, "SS")
        ChatService.chatToUser(3, 5, "DD")

        assertEquals(ChatService.getMessagesFromChat(5, 1, 0, 100).count(), 3)
        ChatService.deleteChat(1)
        assertEquals(ChatService.getMessagesFromChat(5, 1, 0, 100).count(), 0)
    }
}