package com.github.lotashinski.drillapp.app.device.impl

import android.bluetooth.BluetoothAdapter
import android.util.Log
import com.github.lotashinski.drillapp.app.device.ITransmitterConnectionManager
import com.github.lotashinski.drillapp.app.device.ITransmitterConnection

class TransmitterConnectionManager(
    private val btAdapter: BluetoothAdapter
) : ITransmitterConnectionManager {

    override fun refreshConnectorsListAndReturn(): List<ITransmitterConnection> {
        Log.d(this::class.simpleName, "Refresh available device list")
        return btAdapter.bondedDevices.map { btDevice -> TransmitterConnection(btDevice) }
    }

}