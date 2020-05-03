package com.iti.mobile.covid19tracker.features.base

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.iti.mobile.covid19tracker.BuildConfig
import com.iti.mobile.covid19tracker.dagger.component.CovidAppComponent
import com.iti.mobile.covid19tracker.dagger.component.DaggerCovidAppComponent
import com.iti.mobile.covid19tracker.dagger.modules.app.ApplicationModule
import timber.log.Timber
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
    }
}