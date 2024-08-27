package com.example.winit_kotlin.common

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

object BroadcastReceiverManager {
    private var isRegistered = false

    private val logoutReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            (context as? Activity)?.finish()
        }
    }


    fun registerReceivers(context: Context) {
        if (!isRegistered) {
            val logoutFilter = IntentFilter(AppConstants.ACTION_LOGOUT)
            context.registerReceiver(logoutReceiver, logoutFilter)

            isRegistered = true
        }
    }

    fun unregisterReceivers(context: Context) {
        if (isRegistered) {
            context.unregisterReceiver(logoutReceiver)
            isRegistered = false
        }
    }
}