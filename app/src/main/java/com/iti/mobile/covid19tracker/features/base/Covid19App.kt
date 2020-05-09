package com.iti.mobile.covid19tracker.features.base

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.*
import com.iti.mobile.covid19tracker.BuildConfig
import com.iti.mobile.covid19tracker.dagger.component.CovidAppComponent
import com.iti.mobile.covid19tracker.dagger.component.DaggerCovidAppComponent
import com.iti.mobile.covid19tracker.dagger.modules.app.ApplicationModule
import com.iti.mobile.covid19tracker.utils.APP_REQUEST
import com.iti.mobile.covid19tracker.utils.DEFAULT_UPDATE_TIME
import com.iti.mobile.covid19tracker.utils.SETTINGS_REQUEST
import com.iti.mobile.covid19tracker.work_manager.SyncWork
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
        scheduleWork(DEFAULT_UPDATE_TIME,this, APP_REQUEST)
    }


}
fun scheduleWork(timeInHour : Long , context: Context, requestType : Int) {
    Log.i("worker",timeInHour.toString())
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
    val request = PeriodicWorkRequestBuilder<SyncWork>(timeInHour, TimeUnit.HOURS)
        .setBackoffCriteria(BackoffPolicy.LINEAR, 2, TimeUnit.MINUTES)
        .setConstraints(constraints)
        .build()
  if (requestType == APP_REQUEST) {
      WorkManager.getInstance(context)
          .enqueueUniquePeriodicWork(WORK_MANAGER_KEY, ExistingPeriodicWorkPolicy.KEEP, request)
  }else if (requestType == SETTINGS_REQUEST){
      WorkManager.getInstance(context)
          .enqueueUniquePeriodicWork(WORK_MANAGER_KEY, ExistingPeriodicWorkPolicy.REPLACE, request)
  }

}