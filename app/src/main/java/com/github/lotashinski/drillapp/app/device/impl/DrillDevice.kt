package com.github.lotashinski.drillapp.app.device.impl

import android.util.Log
import com.github.lotashinski.drillapp.app.device.IDrillDevice
import com.github.lotashinski.drillapp.app.device.ITransmitter
import com.github.lotashinski.drillapp.app.device.ITransmitterConnection
import com.github.lotashinski.drillapp.app.device.exception.ConnectionLostException
import java.lang.Exception

class DrillDevice(
    connector: ITransmitterConnection
) : IDrillDevice {

    private companion object {
        @JvmStatic
        val COMMAND_BUFFER = "BUFFER"

        @JvmStatic
        val COMMAND_BUFFER_STATUS = "BUFFER_STATUS"

        @JvmStatic
        val COMMAND_BUFFER_CLEAR = "BUFFER_CLEAR"
    }


    private val title: String = connector.title
    private val transmitter: ITransmitter

    private var isClose: Boolean = false

    private val buffer: MutableList<Float>
    private val mcuMaxBufferSize: Int

    init {
        Log.d(this::class.simpleName, "Open transmitter")
        transmitter = connector.connect()

        buffer = mutableListOf()
        val bufferStatus = getBufferStatus()
        mcuMaxBufferSize = parseBufferMaxSize(bufferStatus)
    }

    override fun isClose(): Boolean {
        return isClose
    }

    override fun getTitle(): String {
        return title
    }

    override fun getBuffer(): List<Float> {
        return buffer.toList()
    }

    override fun getMcuMaxBufferSize(): Int {
        return mcuMaxBufferSize
    }

    override fun getMcuBufferSize(): Int {
        val data = getBufferStatus()
        return parseBufferSize(data)
    }

    override fun getCurrentBufferSize(): Int {
        return buffer.size
    }

    override fun loadBuffer(size: Int) {
        var isComplete = false
        var uploaded = 0
        while (!isComplete) {
            val returnedData = loadNextValue()
            val value = returnedData.split(":")[1]
            if (value == "UNDEFINED") {
                isComplete = true
            } else {
                buffer.add(value.toFloat())
                uploaded++
                if (uploaded == size)
                    isComplete = true
            }
        }
    }

    override fun clearMcuBuffer() {
        currentBufferClear()
        mcuBufferClear()
    }

    override fun close() {
        transmitter.close()
        isClose = true
    }

    private fun loadNextValue(): String {
        val command = "$COMMAND_BUFFER ${getCurrentBufferSize()}"
        return sendRequestToMcu(command)
    }

    private fun currentBufferClear() {
        buffer.clear()
    }

    private fun mcuBufferClear() {
        sendRequestToMcu(COMMAND_BUFFER_CLEAR)
    }

    private fun getBufferStatus(): String {
        return sendRequestToMcu(COMMAND_BUFFER_STATUS)
    }

    private fun parseBufferMaxSize(data: String): Int {
        return data.split("/")[1].toInt()
    }

    private fun parseBufferSize(data: String): Int {
        return data.split("/")[0].toInt()
    }

    private fun sendRequestToMcu(data: String): String {
        try {
            transmitter.transmit(data)
            val inputPackage = transmitter.receive()
            return inputPackage.filter { !it.isWhitespace() }
        } catch (e: Exception) {
            Log.e(this::class.simpleName, "Exception on request", e)
            throw ConnectionLostException(e)
        }
    }

}