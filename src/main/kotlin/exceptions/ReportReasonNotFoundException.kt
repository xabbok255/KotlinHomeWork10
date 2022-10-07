package exceptions

import java.lang.RuntimeException

class ReportReasonNotFoundException(override val message: String) : RuntimeException(message)