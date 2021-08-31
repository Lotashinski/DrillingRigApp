package com.github.lotashinski.drillapp.device

import java.io.Closeable

interface TransmitterInterface: Closeable {
    fun transmit() : String
}