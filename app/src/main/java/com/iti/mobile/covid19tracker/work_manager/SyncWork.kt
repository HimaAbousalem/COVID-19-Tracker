package com.iti.mobile.covid19tracker.work_manager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import com.iti.mobile.covid19tracker.model.shared_prefrence.SharedPreferenceHandler
import com.iti.mobile.covid19tracker.utils.makeStatusNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


class SyncWork @Inject constructor(
     private val repo: DataRepository,
     private val prefs: SharedPreferenceHandler,
     appContext: Context,
     workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val job = repo.updateDataBase()
            if(prefs.getNotificationStatus()) {
                if (job != null) {
                    if (job.isNotEmpty()) {
                        var notificationText = "There is a changes happens in ("
                        for (data in job.indices) {
                            notificationText += if (data == 0)
                                job[data]
                            else
                                ", ${job[data]}"
                        }
                        notificationText += ")"
                        makeStatusNotification(applicationContext, notificationText)
                    }
                }
            }
            Result.success()
        }catch(e: Exception){
            Timber.d("Error: ${e.message}")
            Result.failure()
        }
    }


    class Factory @Inject constructor(
        private val myRepository: DataRepository,
        private val prefs: SharedPreferenceHandler
    ): ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): CoroutineWorker {
            return SyncWork(
                myRepository,
                prefs,
                appContext,
                params
            )
        }
    }
}
interface ChildWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): ListenableWorker
}

