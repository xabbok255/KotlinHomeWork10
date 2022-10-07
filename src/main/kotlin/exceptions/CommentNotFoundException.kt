package exceptions

import java.lang.RuntimeException

class CommentNotFoundException(override val message: String) : RuntimeException(message)