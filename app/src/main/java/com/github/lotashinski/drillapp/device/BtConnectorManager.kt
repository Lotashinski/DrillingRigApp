package com.github.lotashinski.drillapp.device

import java.io.Closeable

interface BtConnectorManager: Closeable {
    fun refreshAndGetConnectors(): List<TransmitterConnectionInterface>
}