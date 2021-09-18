package com.github.lotashinski.drillapp.app.device.impl

import android.util.Log
import com.github.lotashinski.drillapp.app.device.IDrillDevice
import com.github.lotashinski.drillapp.app.device.ITransmitterConnection
import java.lang.Exception
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class SynchronizedDrillDevice(
    connection: ITransmitterConnection
) : IDrillDevice {

    private val lock: Lock = ReentrantLock()
    private val realDevice: DrillDevice = DrillDevice(connection)


    override fun getTitle(): String {
        return runInMutex { realDevice.getTitle() }
    }

    override fun isClose(): Boolean {
        return runInMutex { realDevice.isClose() }
    }

    override fun getBuffer(): List<Float> {
        return runInMutex { realDevice.getBuffer() }
    }

    override fun getMcuMaxBufferSize(): Int {
        return runInMutex { realDevice.getMcuMaxBufferSize() }
    }

    override fun getMcuBufferSize(): Int {
        return runInMutex { realDevice.getMcuBufferSize() }
    }

    override fun getCurrentBufferSize(): Int {
        return runInMutex { realDevice.getCurrentBufferSize() }
    }

    override fun loadBuffer(size: Int) {
        return runInMutex { realDevice.loadBuffer(size) }
    }

    override fun clearMcuBuffer() {
        runInMutex { realDevice.clearMcuBuffer() }
    }

    override fun close() {
        runInMutex { realDevice.close() }
    }


    private fun <T> runInMutex(concurrentFunc: () -> T): T {
        try {
            lock.lock()
            return concurrentFunc()
        } catch (e: Exception) {
            Log.e(this::class.simpleName, "Exception on concurrent section", e)
            throw e
        } finally {
            lock.unlock()
        }
    }

}