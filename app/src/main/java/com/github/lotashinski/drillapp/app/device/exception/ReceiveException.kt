package com.github.lotashinski.drillapp.app.device.exception

import java.lang.Exception

class ReceiveException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}