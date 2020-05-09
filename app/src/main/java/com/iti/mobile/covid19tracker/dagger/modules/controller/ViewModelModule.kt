package com.iti.mobile.covid19tracker.dagger.modules.controller

import androidx.lifecycle.ViewModel
import com.iti.mobile.covid19tracker.dagger.scopes.ViewModelKey
import com.iti.mobile.covid19tracker.features.all_countries.AllCountriesViewModel
import com.iti.mobile.covid19tracker.features.main.MainViewModel
import com.iti.mobile.covid19tracker.features.mapView.MapViewModel
import com.iti.mobile.covid19tracker.features.subscriptions.SubscriptionsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AllCountriesViewModel::class)
    internal abstract fun allCountriesViewModel(viewModel: AllCountriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SubscriptionsViewModel::class)
    internal abstract fun subscriptionsViewModel(viewModel: SubscriptionsViewModel): ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    internal abstract fun mapViewModel(viewModel: MapViewModel): ViewModel
}