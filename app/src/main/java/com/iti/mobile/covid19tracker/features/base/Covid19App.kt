package com.iti.mobile.covid19tracker.features.base

import android.app.Application
import com.iti.mobile.covid19tracker.dagger.component.CovidAppComponent
import com.iti.mobile.covid19tracker.dagger.component.DaggerCovidAppComponent
import com.iti.mobile.covid19tracker.dagger.modules.app.ApplicationModule

class Covid19App : Application(){
    lateinit var appComponent: CovidAppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerCovidAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        //now we get application instance
        appComponent.inject(this)
    }
}