package com.iti.mobile.covid19tracker.dagger.component

import com.iti.mobile.covid19tracker.dagger.modules.app.ApiServicesModule
import com.iti.mobile.covid19tracker.dagger.modules.app.RoomModule
import com.iti.mobile.covid19tracker.dagger.scopes.ApplicationScope
import com.iti.mobile.covid19tracker.features.base.Covid19App
import dagger.Component

@ApplicationScope
@Component(modules = [ApiServicesModule::class, RoomModule::class])
interface CovidAppComponent{
    fun inject(app: Covid19App)
}