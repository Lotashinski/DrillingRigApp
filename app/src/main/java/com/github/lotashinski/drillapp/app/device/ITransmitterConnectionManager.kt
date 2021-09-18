package com.github.lotashinski.drillapp.app.device

interface ITransmitterConnectionManager{
    fun refreshConnectorsListAndReturn(): List<ITransmitterConnection>
}