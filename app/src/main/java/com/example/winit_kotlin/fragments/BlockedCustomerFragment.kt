package com.example.winit_kotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.winit_kotlin.R
import com.example.winit_kotlin.interfaces.OnDialogListeners


class BlockedCustomerFragment : Fragment(),OnDialogListeners {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blocked_customer, container, false)
    }

    companion object {

    }

    override fun onButtonYesClick(from: String) {

    }

    override fun onButtonYesClick(from: String, any: Any) {

    }

    override fun onButtonNoClick(from: String) {

    }

    override fun onButtonNoClick(from: String, any: Any) {

    }
}