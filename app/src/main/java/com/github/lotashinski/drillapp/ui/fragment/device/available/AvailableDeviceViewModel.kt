package com.github.lotashinski.drillapp.ui.fragment.device.available

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.lotashinski.drillapp.app.device.ITransmitterConnection
import com.github.lotashinski.drillapp.util.extensions.getDeviceService


class AvailableDeviceViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val availableDevicesLiveData: MutableLiveData<List<ITransmitterConnection>> by lazy {
        MutableLiveData<List<ITransmitterConnection>>(loadAvailableDevices())
    }


    fun getAvailableDevices(): LiveData<List<ITransmitterConnection>> {
        return availableDevicesLiveData
    }

    fun selectDevice(d: ITransmitterConnection){
        getDeviceService().connectDevice(d)
    }

    private fun loadAvailableDevices(): List<ITransmitterConnection> {
        return getDeviceService().refreshAndReturnAvailableDevices()
    }

}