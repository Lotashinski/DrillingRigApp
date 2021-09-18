package com.github.lotashinski.drillapp.app.device.impl

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import com.github.lotashinski.drillapp.app.device.ITransmitter
import com.github.lotashinski.drillapp.app.device.exception.ReceiveException
import com.github.lotashinski.drillapp.app.device.exception.TransmitException
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception

internal class Transmitter(
    btDevice: BluetoothDevice
) : ITransmitter {

    private val mmSocket: BluetoothSocket
    private val mmInputStream: InputStream
    private val mmOutputStream: OutputStream


    init {
        Log.d(this::class.simpleName, "Init new transmitter")
        val btUid = btDevice.uuids.first()
        Log.d(this::class.simpleName, "Init BluetoothSocket")
        mmSocket = btDevice.createRfcommSocketToServiceRecord(btUid.uuid)
        if (!mmSocket.isConnected){
            mmSocket.connect()
        }
        Log.d(this::class.simpleName, "Init InputStream")
        mmInputStream = mmSocket.inputStream
        Log.d(this::class.simpleName, "Init OutputStream")
        mmOutputStream = mmSocket.outputStream
        Log.d(this::class.simpleName, "Init complete")
    }


    override fun transmit(data: String) {
        try {
            transmitToMcu(data)
        } catch (e: Exception) {
            Log.w(this::class.simpleName, "Error on transmit", e)
            try {
                transmitToMcu(data)
            } catch (fe: Exception) {
                Log.e(this::class.simpleName, "Fatal error on transmit", fe)
                throw TransmitException("Fatal error on transmit", fe)
            }
        }
    }

    override fun receive(): String {
        return try {
            receiveFromMcu()
        } catch (e: Exception) {
            Log.w(this::class.simpleName, "Error on receive", e)
            return try {
                receiveFromMcu()
            } catch (fe: Exception) {
                Log.e(this::class.simpleName, "Fatal error on receive", fe)
                throw ReceiveException("Fatal error on receive", fe)
            }
        }
    }

    override fun close() {
        try {
            Log.d(this::class.simpleName, "Call close()")
            mmInputStream.close()
            mmOutputStream.close()
            mmSocket.close()
        } catch (e: IOException) {
            Log.e(this::class.simpleName, "Could not close the socket", e)
        }
    }


    private fun transmitToMcu(data: String) {
        Log.d(this::class.simpleName, "Transmit data $data")
        clearBuffer()
        val checkedData = checkAndSetEol(data)
        sendData(checkedData)
    }

    private fun receiveFromMcu(): String {
        val buffer = ByteArray(1)
        val receivedData = mutableListOf<Byte>()
        var isComplete = false

        while (!isComplete) {
            val bytesCount = mmInputStream.read(buffer)
            if (bytesCount == 0 || buffer[0] == '\r'.code.toByte()) {
                isComplete = true
            } else {
                receivedData.add(buffer[0])
            }
        }

        val receivedString = receivedData.toByteArray().toString(Charsets.US_ASCII)
        Log.d(this::class.simpleName, "Receive data $receivedString")
        return receivedString
    }

    private fun clearBuffer() {
        while (mmInputStream.available() > 0)
            mmInputStream.read()
    }

    private fun checkAndSetEol(data: String): String {
        return when (data.last() in arrayOf('\n', '\r')) {
            true -> data
            false -> "${data}\n"
        }
    }

    private fun sendData(data: String) {
        mmOutputStream.write(data.toByteArray())
    }

}