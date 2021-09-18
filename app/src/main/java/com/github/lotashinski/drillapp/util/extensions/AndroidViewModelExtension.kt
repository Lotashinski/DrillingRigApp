package com.github.lotashinski.drillapp.util.extensions

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.github.lotashinski.drillapp.app.App
import com.github.lotashinski.drillapp.app.service.IDatabaseService
import com.github.lotashinski.drillapp.app.service.IDeviceService


fun AndroidViewModel.getDatabaseService(): IDatabaseService {
    val app = getAppContextAsApp()
    return app.getDataBaseService()
}

fun AndroidViewModel.getDeviceService(): IDeviceService {
    val app = getAppContextAsApp()
    return app.getDeviceService()
}

fun AndroidViewModel.getAppContextAsApp(): App {
    return getAppContext() as App
}

fun AndroidViewModel.getAppContext(): Context {
    return getApplication<Application>().applicationContext
}

