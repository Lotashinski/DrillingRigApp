package com.github.lotashinski.drillapp.app

import android.app.Application
import com.github.lotashinski.drillapp.app.service.IDatabaseService
import com.github.lotashinski.drillapp.app.service.IDeviceService
import com.github.lotashinski.drillapp.app.service.impl.DatabaseService
import com.github.lotashinski.drillapp.app.service.impl.DeviceService


class App : Application() {

    private var dbService: IDatabaseService? = null
    private var deviceService: IDeviceService? = null


    fun getDataBaseService(): IDatabaseService {
        if (dbService == null)
            dbService = DatabaseService(this)
        return dbService as IDatabaseService
    }

    fun getDeviceService(): IDeviceService {
        if (deviceService == null)
            deviceService = DeviceService(this)
        return deviceService as IDeviceService
    }

}