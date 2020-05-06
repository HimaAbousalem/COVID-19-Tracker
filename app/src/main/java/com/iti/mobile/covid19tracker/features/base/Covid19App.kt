package com.iti.mobile.covid19tracker.features.base

import android.app.Application
import androidx.work.*
import com.iti.mobile.covid19tracker.BuildConfig
import com.iti.mobile.covid19tracker.dagger.component.CovidAppComponent
import com.iti.mobile.covid19tracker.dagger.component.DaggerCovidAppComponent
import com.iti.mobile.covid19tracker.dagger.modules.app.ApplicationModule
import com.iti.mobile.covid19tracker.model.sync.SyncWork
import com.iti.mobile.covid19tracker.utils.WORK_MANAGER_KEY
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Covid19App : Application(){
    lateinit var appComponent: CovidAppComponent
    @Inject
    lateinit var myWorkerFactory: WorkerProviderFactory

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        appComponent = DaggerCovidAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        appComponent.inject(this)
        WorkManager.initialize(this, Configuration.Builder()
                .setWorkerFactory(myWorkerFactory)
                .build())
        scheduleWork()
    }

    private fun scheduleWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = PeriodicWorkRequestBuilder<SyncWork>(15, TimeUnit.MINUTES)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 2, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(WORK_MANAGER_KEY, ExistingPeriodicWorkPolicy.KEEP, request)
    }
}