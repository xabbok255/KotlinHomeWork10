data class Chat(val user1: Int, val user2: Int) : Entity<Chat>() {
    override fun self(): Chat {
        return this
    }
}
