package com.github.lotashinski.drillapp.app.service.exception

import java.lang.Exception

class DeviceNotConnectedException : Exception {
    constructor() : super()
    constructor(e: Exception) : super(e)
}