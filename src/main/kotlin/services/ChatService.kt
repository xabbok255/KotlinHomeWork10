package services

import Chat
import DirectMessage


object ChatService {
    enum class MessageDirection { INCOMING, OUTGOING, ANY }

    private var directMessages = Crud<DirectMessage>()
    private var chats = Crud<Chat>()

    /**
     * Получает объект чата по id пользователей в любом порядке
     * @param userId1 id первого пользователя
     * @param userId2 id второго пользователя
     */
    fun getChat(userId1: Int, userId2: Int): Chat? {
        return chats.firstOrNull { ((it.user1 == userId1) && (it.user2 == userId2)) || ((it.user1 == userId2) && (it.user2 == userId1)) }
    }

    /**
     * Пишет сообщение от пользователя fromId пользователю toId
     * @param fromId отправитель
     * @param toId получатель
     * @param message сообщение
     */
    fun chatToUser(fromId: Int, toId: Int, message: String): Int {
        return directMessages.add(
            DirectMessage(
                fromId,
                toId,
                getChat(fromId, toId)?.id ?: chats.add(Chat(fromId, toId)).id,
                message
            )
        ).chatId
    }

    /** Подсчитывает количество чатов с хотя бы одним непрочитанным сообщением с выбором направления
     * @param userId id пользователя
     * @param direction направление сообщения: входящее, исходящее или любое
     **/
    fun getUnreadChatsCount(userId: Int, direction: MessageDirection = MessageDirection.ANY): Int {
        return getChatsWithUnreadMessagesByDirection(userId, direction).count()
    }

    /** Получает список чатов с хотя бы одним непрочитанным сообщением с выбором направления
     * @param userId id пользователя
     * @param direction направление сообщения: входящее, исходящее или любое
     **/
    fun getChatsWithUnreadMessagesByDirection(userId: Int, direction: MessageDirection = MessageDirection.ANY) : Collection<Int> {
        return directMessages
            .asSequence()
            .filter {
                ((it.toId == userId) && (direction == MessageDirection.INCOMING))
                        || ((it.fromId == userId) && (direction == MessageDirection.OUTGOING))
                        || ((it.toId == userId || it.fromId == userId) && (direction == MessageDirection.ANY))
            }
            .filter { !it.deleted }
            .filter { !it.seen }
            .groupBy { it.chatId }
            .keys
    }

    /**
     * Получает список чатов
     */
    fun getChats(): Collection<Int> {
        return directMessages
            .asSequence()
            .filter { !it.deleted }
            .groupBy { it.chatId }
            .keys
    }

    /** Получает список чатов для пользователя userId, в которых есть непрочитанные ВХОДЯЩИЕ сообщения
     * @param userId id пользователя
     */
    fun getChatsWithUnreadMessages(userId: Int): Collection<Int> {
        return getChatsWithUnreadMessagesByDirection(userId, MessageDirection.INCOMING)
    }

    /**
     * Получает список чатов по id пользователя (operator)
     * @param userId id пользователя
     */
    operator fun get(userId: Int) : Collection<Int> {
        return getChatsByUser(userId)
    }

    /**
     * Получает список чатов по id пользователя
     * @param userId id пользователя
     */
    fun getChatsByUser(userId: Int): Collection<Int> {
        return directMessages
            .asSequence()
            .filter { !it.deleted }
            .filter { (it.toId == userId) || (it.fromId == userId) }
            .groupBy { it.chatId }
            .keys
    }

    /**
     * Возвращает список ВХОДЯЩИХ сообщений для пользователя userId и чата chatId, автоматически помечая их прочитанными
     * @param fromMessageId начиная с этого номера сообщения возвращается результат
     * @param count количество возвращаемых сообщений
     * @return список сообщений в виде коллекции строк
     */

    fun getMessagesFromChat(userId: Int, chatId: Int, fromMessageId: Int, count: Int): Collection<DirectMessage> {
        return directMessages
            .asSequence()
            .filter { !it.deleted }
            .filter { it.chatId == chatId }
            .filter { it.toId == userId }
            .filter { it.id >= fromMessageId }
            .take(count)
            .onEach { it.seen = true }
            .toList()
    }

    /**
     * Удаляет сообщение messageId у пользователя userId
     * @param userId id пользователя
     * @param messageId id сообщения
     */
    fun deleteMessage(userId: Int, messageId: Int) {
        val chatId: Int = directMessages.firstOrNull { it.id == messageId }?.chatId ?: throw NoSuchElementException("No chat with messageId $messageId")

        directMessages
            .filter { (it.fromId == userId) && (it.id == messageId)}
            .onEach { directMessages.delete(it.id); println(it.id)}

        if (directMessages.none { !it.deleted }) {
            deleteChat(chatId)
        }
    }

    /**
     * Удаляет чат вместе со всеми сообщениями в нем
     * @param chatId id чата
     */
    fun deleteChat(chatId: Int) {
        directMessages
            .asSequence()
            .filter { it.chatId == chatId }
            .filter { !it.deleted }
            .toList()
            .onEach { directMessages.delete(it.id) }

        chats.delete(chatId)
    }

    fun clear() {
        chats.clear()
        directMessages.clear()
    }
}