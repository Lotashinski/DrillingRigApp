package com.github.lotashinski.drillapp.app.device

import java.io.Closeable

interface ITransmitter: Closeable {

    fun transmit(data: String)
    fun receive(): String

}