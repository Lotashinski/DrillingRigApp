package com.github.lotashinski.drillapp.ui.fragment.device.available

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.lotashinski.drillapp.R
import com.github.lotashinski.drillapp.app.device.ITransmitterConnection


class AvailableDeviceAdapter(
    private val availableDevices: List<ITransmitterConnection>,
    private val selectDeviceCallback: (ITransmitterConnection) -> Unit
) : RecyclerView.Adapter<AvailableDeviceAdapter.ViewHolder>() {

    class ViewHolder(
        view: View,
        private val selectDeviceCallback: (ITransmitterConnection) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val deviceTitle = view.findViewById<TextView>(R.id.text_device_title)
        private val deviceMac = view.findViewById<TextView>(R.id.text_device_mac)
        private val connectButton = view.findViewById<ImageButton>(R.id.button_device_connect)

        fun setTransmitterConnection(c: ITransmitterConnection) {
            deviceTitle.text = c.title
            deviceMac.text = c.macAddress
            connectButton.setOnClickListener { selectDeviceCallback(c) }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item_available_device, parent, false)
        return ViewHolder(view, selectDeviceCallback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setTransmitterConnection(availableDevices[position])
    }

    override fun getItemCount(): Int {
        return availableDevices.size
    }
}