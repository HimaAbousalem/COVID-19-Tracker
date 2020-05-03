package com.iti.mobile.covid19tracker.model.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SyncWork @Inject constructor(
     private val repo: DataRepository,
     appContext: Context,
     workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        var job = repo.updateDataBase()
        //notification 3lshan el context.
        Result.success()
    }


    class Factory @Inject constructor(
        private val myRepository: DataRepository
    ): ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): CoroutineWorker {
            return SyncWork(myRepository, appContext, params)
        }
    }
}
interface ChildWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): ListenableWorker
}

