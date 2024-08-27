package com.example.winit_kotlin.workmanagers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SyncDataWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                // Your sync logic here
                syncDeletedLogs()
                syncCPSPromotionHeaderDetails("empNo")
                callSurveySync("empNo")
                loadIncrementalData("empNo")
                Result.success()  // Returning Result.success() within withContext
            } catch (e: Exception) {
                e.printStackTrace()

                // Return failure with optional output data
                Result.failure()
            }
        }

    }

    fun syncDeletedLogs(){

    }
    fun syncCPSPromotionHeaderDetails(empNo : String){

    }
    fun callSurveySync(empNo : String){

    }
    fun loadIncrementalData(empNo : String){

    }
}