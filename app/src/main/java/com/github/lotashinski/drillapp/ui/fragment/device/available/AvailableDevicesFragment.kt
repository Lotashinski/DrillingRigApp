package com.github.lotashinski.drillapp.ui.fragment.device.available

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.lotashinski.drillapp.R
import com.github.lotashinski.drillapp.app.device.ITransmitterConnection


class AvailableDevicesFragment
    : Fragment() {

    private lateinit var rootView: View
    private lateinit var listAvailableView: RecyclerView
    private val model by viewModels<AvailableDeviceViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(this::class.simpleName, "create View")
        rootView = inflater.inflate(R.layout.fragment_available_devices, container, false)
        listAvailableView = rootView.findViewById(R.id.list_available)
        initObservers()
        return rootView
    }


    private fun initObservers() {
        val available = model.getAvailableDevices()
        available.observe(viewLifecycleOwner, {
            createList(it)
        })
    }

    private fun createList(available: List<ITransmitterConnection>) {
        Log.d(this::class.simpleName, "Create listView [count: ${available.size}]")
        listAvailableView.layoutManager = LinearLayoutManager(context)
        listAvailableView.adapter = AvailableDeviceAdapter(available) {
            Log.d(this::class.simpleName, "Connect ${it.title}")
            connectDevice(it)
        }
    }

    private fun connectDevice(connection: ITransmitterConnection) {
        model.selectDevice(connection)
    }

}