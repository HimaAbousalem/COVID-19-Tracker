package com.iti.mobile.covid19tracker.model.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncWork(appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

     override suspend fun doWork(): Result {
         //getDataFromApi
         //then getDataFromRoom
         //then compare Subscription Data From Api and room db
         // then send Notification to the user.
         return Result.success()
    }

}