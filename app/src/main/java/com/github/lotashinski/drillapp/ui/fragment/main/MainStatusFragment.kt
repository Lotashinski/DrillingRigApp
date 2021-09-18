package com.github.lotashinski.drillapp.ui.fragment.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import com.github.lotashinski.drillapp.R
import com.github.lotashinski.drillapp.ui.fragment.device.available.AvailableDevicesFragment
import com.github.lotashinski.drillapp.ui.fragment.device.current.CurrentDeviceFragment


class MainStatusFragment
    : Fragment() {

    private val model by viewModels<MainStatusViewModel>()
    private lateinit var rootView: View
    private lateinit var deviceFragment: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(this::class.simpleName, "Create view")
        rootView = inflater.inflate(R.layout.fragment_main_status, container, false)
        deviceFragment = rootView.findViewById(R.id.frame_device)

        setObservers()
        return rootView
    }

    private fun setObservers() {
        val isConnect = model.isDeviceConnect()
        isConnect
            .observe(viewLifecycleOwner, { setDevice(it) })
    }

    private fun setDevice(isConnect: Boolean) {
        when (isConnect) {
            true -> showCurrentDeviceFragment()
            false -> showAvailableDeviceFragment()
        }
    }

    private fun showCurrentDeviceFragment() {
        Log.d(this::class.simpleName, "Show Current Device")
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_device, CurrentDeviceFragment())
            .commit()

    }

    private fun showAvailableDeviceFragment() {
        Log.d(this::class.simpleName, "Show Available Device")

        childFragmentManager.beginTransaction()
            .replace(R.id.frame_device, AvailableDevicesFragment())
            .commit()
    }


}