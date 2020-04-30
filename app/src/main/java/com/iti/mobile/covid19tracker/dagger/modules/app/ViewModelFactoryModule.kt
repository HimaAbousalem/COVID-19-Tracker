package com.iti.mobile.covid19tracker.dagger.modules.app

import androidx.lifecycle.ViewModelProvider
import com.iti.mobile.covid19tracker.features.base.ViewModelProvidersFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ViewModelFactoryModule{
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelProvidersFactory): ViewModelProvider.Factory

//    @Provides
//    fun bindViewModel(factory: ViewModelProvidersFactory):ViewModelProvider.Factory{
//        return factory
//    }
}