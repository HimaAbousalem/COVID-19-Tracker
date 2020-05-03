package com.iti.mobile.covid19tracker.model.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import javax.inject.Inject


class SyncWork @AssistedInject constructor(@Assisted private val repo: DataRepository, @Assisted private val appContext: Context, @Assisted private val workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {

        //getDataFromApi
        //then getDataFromRoom
        //then compare Subscription Data From Api and room db
        // then send Notification to the user.
        return Result.success()
    }

    class WorkerFactory @Inject constructor(private val repository: DataRepository):ChildWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): CoroutineWorker {
            return SyncWork(repository, appContext, params)
        }
    }
}

interface ChildWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): CoroutineWorker
}

