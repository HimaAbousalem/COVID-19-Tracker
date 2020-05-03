package com.iti.mobile.covid19tracker.features.base

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.iti.mobile.covid19tracker.model.sync.ChildWorkerFactory
import javax.inject.Inject
import javax.inject.Provider

class WorkerProviderFactory @Inject constructor(
    private val workerFactories: Map<Class<out CoroutineWorker>, @JvmSuppressWildcards Provider<ChildWorkerFactory>>
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val workerFactoryProvider = workerFactories.get(workerClassName)
            return workerFactoryProvider?.get()?.create(appContext, workerParameters)
    }
}