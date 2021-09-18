package com.github.lotashinski.drillapp.app.device

import java.io.Closeable

interface IDrillDevice : Closeable {

    fun isClose(): Boolean

    fun getTitle(): String

    fun getBuffer(): List<Float>
    fun getMcuMaxBufferSize(): Int
    fun getMcuBufferSize(): Int
    fun getCurrentBufferSize(): Int
    fun loadBuffer(size: Int)

    fun clearMcuBuffer()
}