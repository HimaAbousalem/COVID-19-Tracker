package com.iti.mobile.covid19tracker.dagger.modules.controller

import androidx.appcompat.app.AppCompatActivity
import com.iti.mobile.covid19tracker.dagger.scopes.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ControllerModule(private val mActivity: AppCompatActivity){

    @ActivityScope
    @Provides
    fun provideContext() = mActivity
}
