package com.github.lotashinski.drillapp.app.device

interface ITransmitterConnection {

    val title: String
    val macAddress: String

    fun connect(): ITransmitter

}