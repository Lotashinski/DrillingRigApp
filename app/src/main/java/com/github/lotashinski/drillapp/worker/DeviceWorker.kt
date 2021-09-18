package com.github.lotashinski.drillapp.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.github.lotashinski.drillapp.app.App
import com.github.lotashinski.drillapp.app.service.IDeviceService
import java.util.concurrent.TimeUnit

class DeviceWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    companion object {
        const val WORKER_TAG = "DEVICE_WORKER"
    }


    override fun doWork(): Result {
        Log.d(this::class.simpleName, "start work")
        doUpdateUntilDeviceClosed()
        return Result.success()
    }


    private fun doUpdateUntilDeviceClosed() {
        val service = getDeviceService()
        val currentDevice = service.currentDevice()
        while (!currentDevice.isClose()) {
            service.updateCurrentDevice()
            TimeUnit.MILLISECONDS.sleep(250)
        }
    }

    private fun getDeviceService(): IDeviceService {
        val app = getApp()
        return app.getDeviceService()
    }

    private fun getApp(): App {
        return context as App
    }

}