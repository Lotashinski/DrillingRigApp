package com.github.lotashinski.drillapp.app.service.impl

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.github.lotashinski.drillapp.app.service.IDeviceService
import com.github.lotashinski.drillapp.app.service.exception.DeviceNotConnectedException
import com.github.lotashinski.drillapp.app.device.IDrillDevice
import com.github.lotashinski.drillapp.app.device.ITransmitterConnection
import com.github.lotashinski.drillapp.app.device.ITransmitterConnectionManager
import com.github.lotashinski.drillapp.app.device.impl.SynchronizedDrillDevice
import com.github.lotashinski.drillapp.app.device.impl.TransmitterConnectionManager
import com.github.lotashinski.drillapp.util.extensions.calculateLeadTime
import com.github.lotashinski.drillapp.worker.DeviceWorker
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject


class DeviceService(
    private val context: Context
) : IDeviceService {

    private var device: IDrillDevice? = null
    private val transmitterConnectionManager: ITransmitterConnectionManager =
        TransmitterConnectionManager(BluetoothAdapter.getDefaultAdapter())

    private val isConnectSubject: Subject<Boolean> = PublishSubject.create()
    private val deviceConnectSubject: Subject<IDrillDevice> = PublishSubject.create()
    private val deviceUpdateSubject: Subject<IDrillDevice> = PublishSubject.create()


    override val isConnectObservable: Observable<Boolean> = isConnectSubject
    override val onConnectDeviceObservable: Observable<IDrillDevice> = deviceConnectSubject
    override val onDeviceUpdateObservable: Observable<IDrillDevice> = deviceUpdateSubject


    init {
        isConnectSubject.onNext(false)
    }


    override fun isConnect(): Boolean {
        return device != null
    }

    override fun currentDevice(): IDrillDevice {
        return device ?: throw DeviceNotConnectedException()
    }

    override fun refreshAndReturnAvailableDevices(): List<ITransmitterConnection> {
        return transmitterConnectionManager.refreshConnectorsListAndReturn()
    }

    override fun connectDevice(connector: ITransmitterConnection) {
        if (device != null)
            disconnect()

        val candidate = SynchronizedDrillDevice(connector)
        device = candidate

        deviceConnectSubject.onNext(candidate)
        isConnectSubject.onNext(true)

        startAutoUpdate()
    }

    override fun updateCurrentDevice() {
        val current = currentDevice()
        update(current)
    }

    override fun startAutoUpdate() {
        WorkManager
            .getInstance(context)
            .cancelAllWorkByTag(DeviceWorker.WORKER_TAG);
        val request = OneTimeWorkRequest.Builder(DeviceWorker::class.java)
            .addTag(DeviceWorker.WORKER_TAG)
            .build()
        WorkManager.getInstance(context)
            .enqueue(request)
    }

    override fun disconnectCurrentDevice() {
        if (device == null)
            throw DeviceNotConnectedException()
        disconnect()
        isConnectSubject.onNext(false)
    }

    override fun close() {
        if (device != null)
            disconnectCurrentDevice()

        deviceUpdateSubject.onComplete()
        deviceUpdateSubject.onComplete()
        isConnectSubject.onComplete()
    }


    private fun disconnect() {
        val currentDevice = currentDevice()
        currentDevice.close()
        device = null
    }

    private fun update(device: IDrillDevice) {
        val leadTime = calculateLeadTime {
            val currentIndex = device.getCurrentBufferSize()
            val availableToLoad = device.getMcuBufferSize()
            for (i in currentIndex..availableToLoad) {
                device.loadBuffer(1)
                deviceUpdateSubject.onNext(device)
            }
        }
        Log.d(this::class.simpleName, "Device update. Lead time: ${leadTime}ms")
    }

}