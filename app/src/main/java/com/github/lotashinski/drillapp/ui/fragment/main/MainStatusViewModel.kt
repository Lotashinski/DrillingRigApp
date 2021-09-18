package com.github.lotashinski.drillapp.ui.fragment.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.lotashinski.drillapp.util.extensions.getDeviceService
import io.reactivex.rxjava3.disposables.Disposable

class MainStatusViewModel(app: Application) : AndroidViewModel(app) {

    private var disposeIsConnect: Disposable? = null
    private val isDeviceConnectLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData(loadIsDeviceConnect()).apply {
            disposeIsConnect = getDeviceService()
                .isConnectObservable
                .subscribe { this.value = it }
        }
    }


    override fun onCleared() {
        super.onCleared()
        disposeIsConnect?.dispose()
    }

    fun isDeviceConnect(): LiveData<Boolean> {
        return isDeviceConnectLiveData
    }


    private fun loadIsDeviceConnect(): Boolean {
        return getDeviceService().isConnect()
    }

}