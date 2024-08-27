package com.example.winit_kotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SyncViewModel: ViewModel() {
    private val _syncError = MutableLiveData<String?>()
    val syncError: LiveData<String?> get() = _syncError

    fun setSyncError(error: String) {
        _syncError.postValue(error)
    }

    fun clearSyncError() {
        _syncError.postValue(null)
    }
}