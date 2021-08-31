package com.github.lotashinski.drillapp.device

import java.io.Closeable

interface TransmitterConnectionInterface: Closeable {
    val title: String
    fun connect(): TransmitterInterface
}