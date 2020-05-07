package com.iti.mobile.covid19tracker.dagger.component

import com.iti.mobile.covid19tracker.dagger.modules.controller.ControllerModule
import com.iti.mobile.covid19tracker.dagger.modules.controller.ViewModelModule
import com.iti.mobile.covid19tracker.dagger.scopes.ActivityScope
import com.iti.mobile.covid19tracker.features.all_countries.AllCountriesFragment
import com.iti.mobile.covid19tracker.features.graph_test
import com.iti.mobile.covid19tracker.features.main.MainActivity
import com.iti.mobile.covid19tracker.features.subscriptions.SubscriptionsFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ControllerModule::class, ViewModelModule::class])
interface ControllerComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: graph_test)
    fun inject (countriesFragment: AllCountriesFragment)
    fun inject (subscriptionsFragment: SubscriptionsFragment)
}