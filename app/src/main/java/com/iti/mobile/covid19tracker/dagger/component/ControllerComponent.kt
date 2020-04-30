package com.iti.mobile.covid19tracker.dagger.component

import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.dagger.modules.controller.ViewModelModule
import com.iti.mobile.covid19tracker.dagger.scopes.ActivityScope
import com.iti.mobile.covid19tracker.features.base.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ControllerModule::class, ViewModelModule::class])
interface ControllerComponent {
    fun inject(activity: MainActivity)
}