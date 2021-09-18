package com.github.lotashinski.drillapp.ui.fragment.device.current

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.github.lotashinski.drillapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CurrentDeviceFragment : Fragment() {

    private val model: CurrentDeviceViewModel by viewModels()
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(this::class.simpleName, "start")
        rootView = inflater.inflate(R.layout.fragment_current_device, container, false)
        initView()
        return rootView
    }


    @SuppressLint("SetTextI18n")
    private fun initView() {
        val device = model.getDevice()
        rootView.findViewById<TextView>(R.id.text_device_title).text = device.getTitle()

        model.getBuffer().observe(viewLifecycleOwner) {
            rootView.findViewById<TextView>(R.id.text_buffer).text =
                "Buffer: [${device.getCurrentBufferSize()}:${device.getMcuBufferSize()}:${device.getMcuMaxBufferSize()}]"
        }
        rootView.findViewById<FloatingActionButton>(R.id.button_close_connect)
            .setOnClickListener { disconnectDevice() }
    }

    private fun disconnectDevice() {
        model.disconnectDevice()
    }

}