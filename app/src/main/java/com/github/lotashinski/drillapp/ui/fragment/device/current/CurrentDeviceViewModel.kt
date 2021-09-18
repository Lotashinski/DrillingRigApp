package com.github.lotashinski.drillapp.ui.fragment.device.current

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.lotashinski.drillapp.app.device.IDrillDevice
import com.github.lotashinski.drillapp.util.extensions.getDeviceService
import io.reactivex.rxjava3.disposables.Disposable


class CurrentDeviceViewModel(
    application: Application
) : AndroidViewModel(application) {

    private var deviceBufferDisposable: Disposable? = null

    private val deviceBufferLiveData: MutableLiveData<List<Float>> by lazy {
        MutableLiveData(loadBuffer()).apply {
            deviceBufferDisposable = getDeviceService()
                .onDeviceUpdateObservable
                .subscribe { setValue(it) }
        }
    }


    override fun onCleared() {
        super.onCleared()
        deviceBufferDisposable?.dispose()
    }


    fun getBuffer(): LiveData<List<Float>> {
        return deviceBufferLiveData
    }

    fun getDevice(): IDrillDevice {
        return getDeviceService()
            .currentDevice()
    }

    fun disconnectDevice() {
        getDeviceService().disconnectCurrentDevice()
    }

    private fun loadBuffer(): List<Float> {
        return getDeviceService()
            .currentDevice()
            .getBuffer()
    }

    private fun setValue(d: IDrillDevice) {
        deviceBufferLiveData.postValue(d.getBuffer())
    }

}