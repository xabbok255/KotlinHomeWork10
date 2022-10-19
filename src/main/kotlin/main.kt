import services.ChatService

fun main() {
    /*services.WallService.add(Post(ownerId = 1, text = "12345"))
    //Thread.sleep(1000)
    services.WallService.add(Post(ownerId = 1, text = "67890"))
    services.WallService.add(Post(ownerId = 2, text = "200000"))
    services.WallService.add(Post(ownerId = 2, text = "300000", postType = PostType.REPLY))

    services.WallService.add(Post(ownerId = 3, text = "800000", likes = Likes(canLike = true)))

    services.WallService.update(Post(id = 2, text = "replaced", comments = Comments(canClose = true)))
    services.WallService.update(Post(id = 1, text = "new text", comments = Comments(canOpen = true), canPin = false))

    services.WallService.add(Post(attachments = arrayOf(AudioAttachment(), EventAttachment(), NoteAttachment(), PostedPhotoAttachment(),
    GraffitiAttachment())))

    services.WallService.createComment(1, Comment())
    services.WallService.reportComment(10, 1, 5)

    println(services.WallService)*/

    /*NoteService.add(title = "11111", commentPrivacy = 1, text = "ttttt", privacy = 2)
    NoteService.add(title = "22222", commentPrivacy = 2, text = "fffff", privacy = 3)
    NoteService.add(title = "33333", commentPrivacy = 3, text = "ggggg", privacy = 4)

    NoteService.createComment(0, message = "comment11", "guid11")
    NoteService.createComment(0, message = "comment12", "guid12")
    NoteService.createComment(1, message = "comment21", "guid21")
    NoteService.createComment(1, message = "comment22", "guid22")

    NoteService.delete(0)
    NoteService.deleteComment(2)

    NoteService.edit(1, "EDIT1", "TEXTEDIT1", 5, 6)
    NoteService.editComment(1, "NEW COMMENT EDIT")

    println(NoteService.get(noteIds = arrayOf(0, 1, 2), sort = 0, offset = 0u, count = 10u))
    println(NoteService.getComments(1, sort = 1, offset = 0u, count = 10u))
    println(NoteService.getComments(0, sort = 0, offset = 0u, count = 10u))

    */

    //println(services.NoteService.getComments(2))

    ChatService.chatToUser(1, 2, "Hello mr 2")
    ChatService.chatToUser(2, 1, "Nice to meet you 1!")
    ChatService.chatToUser(1, 2, "How are you ?")
    ChatService.chatToUser(2, 1, "Fine !")


    ChatService.chatToUser(3, 1, "3to1")
    ChatService.chatToUser(4, 1, "4to1")
    ChatService.chatToUser(5, 1, "5to1")

    println(ChatService.getUnreadChatsCount(1))
    println(ChatService.getUnreadChatsCount(2))

    println(ChatService.getChats())

    println(ChatService.getMessagesFromChat(2, 0, 0, 100))
    println(ChatService.getMessagesFromChat(1, 0, 0, 100))
    //ChatService.deleteMessage(1, 1)
    //ChatService.deleteMessage(2, 2)
    //ChatService.deleteMessage(1, 0)
    //ChatService.deleteChat(3)
    //println(ChatService.getMessagesFromChat(1, 0, 0, 100))

    println(ChatService.getChatsByUser(1))

}