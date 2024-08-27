package com.example.winit_kotlin.interfaces

import com.google.android.gms.common.api.Status

interface VersionChangeListner {
    fun onVersionChanged(status: Int)
}