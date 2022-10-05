package exceptions

import java.lang.RuntimeException

class PostNotFoundException(override val message: String) : RuntimeException(message)