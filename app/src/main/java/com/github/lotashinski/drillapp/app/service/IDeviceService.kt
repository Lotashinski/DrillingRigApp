package com.github.lotashinski.drillapp.app.service

import com.github.lotashinski.drillapp.app.device.IDrillDevice
import com.github.lotashinski.drillapp.app.device.ITransmitterConnection
import io.reactivex.rxjava3.core.Observable
import java.io.Closeable

interface IDeviceService: Closeable {

    val isConnectObservable: Observable<Boolean>
    val onConnectDeviceObservable: Observable<IDrillDevice>
    val onDeviceUpdateObservable: Observable<IDrillDevice>

    fun isConnect(): Boolean
    fun currentDevice(): IDrillDevice

    fun refreshAndReturnAvailableDevices(): List<ITransmitterConnection>
    fun connectDevice(connector: ITransmitterConnection)
    fun updateCurrentDevice()
    fun startAutoUpdate()
    fun disconnectCurrentDevice()

}