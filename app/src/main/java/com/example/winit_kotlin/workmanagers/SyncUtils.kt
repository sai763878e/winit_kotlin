package com.example.winit_kotlin.workmanagers

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.winit_kotlin.viewmodel.SyncViewModel

object SyncUtils {

    fun scheduleDataSync(context: Context, syncViewModel: SyncViewModel) {
        val syncDataWork = OneTimeWorkRequestBuilder<SyncDataWorker>().build()

        WorkManager.getInstance(context).enqueue(syncDataWork)

        WorkManager.getInstance(context).getWorkInfoByIdLiveData(syncDataWork.id)
            .observeForever { workInfo ->
                if (workInfo.state == WorkInfo.State.FAILED) {
                    val errorMessage = workInfo.outputData.getString("ERROR_MESSAGE_KEY")
                    syncViewModel.setSyncError(errorMessage ?: "Unknown Error")
                }
            }
    }
}