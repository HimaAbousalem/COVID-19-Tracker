package com.iti.mobile.covid19tracker.dagger.modules.app

import android.app.Application
import com.iti.mobile.covid19tracker.dagger.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule (private var mApplication: Application) {

    @ApplicationScope
    @Provides
    fun provideApplication() = mApplication
}