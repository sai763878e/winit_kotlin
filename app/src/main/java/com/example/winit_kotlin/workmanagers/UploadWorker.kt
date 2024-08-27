package com.example.winit_kotlin.workmanagers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.winit_kotlin.utils.CalendarUtils
import com.example.winit_kotlin.utils.isNetworkConnectionAvailable

class UploadWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        return try {
            // Check network availability
            if (isNetworkConnectionAvailable(applicationContext)) {
                // Upload data
//                UploadData(applicationContext, null, CalendarUtils.getOrderPostDate())
                Result.success()
            } else {
                Result.retry() // Retry if network is unavailable
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}