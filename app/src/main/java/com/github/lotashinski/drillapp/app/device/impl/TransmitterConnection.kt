package com.github.lotashinski.drillapp.app.device.impl

import android.bluetooth.BluetoothDevice
import android.util.Log
import com.github.lotashinski.drillapp.app.device.ITransmitter
import com.github.lotashinski.drillapp.app.device.ITransmitterConnection

internal class TransmitterConnection(
    private val btDevice: BluetoothDevice
) : ITransmitterConnection {

    override val title: String = btDevice.name
    override val macAddress: String = btDevice.address


    override fun connect(): ITransmitter {
        Log.d(this::class.simpleName, "Connect to ${btDevice.address}")
        return Transmitter(btDevice)
    }

}