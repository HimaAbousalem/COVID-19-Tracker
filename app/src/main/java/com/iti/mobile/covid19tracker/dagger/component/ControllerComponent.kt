package com.iti.mobile.covid19tracker.dagger.component

import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.dagger.modules.controller.ViewModelModule
import com.iti.mobile.covid19tracker.dagger.scopes.ActivityScope
import com.iti.mobile.covid19tracker.features.all_countries.AllCountriesFragment
import com.iti.mobile.covid19tracker.features.main.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ControllerModule::class, ViewModelModule::class])
interface ControllerComponent {
    fun inject(activity: MainActivity)
    fun inject (countriesFragment: AllCountriesFragment)
}