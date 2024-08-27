package com.example.winit_kotlin.common

import android.app.Activity
import android.app.Application
import android.content.Context

class MyApplication : Application() {
    companion object {
        const val APP_DB_LOCK = "Lock"
        const val LOG_LOCK = "LogLock"
        lateinit var mContext: Context
        const val STORE_CHECK_CALCULATION_LOCK = "storecheck"
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        BroadcastReceiverManager.registerReceivers(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        BroadcastReceiverManager.unregisterReceivers(this)
    }
}