package com.iti.mobile.covid19tracker.dagger.modules

import android.app.Application
import com.iti.mobile.covid19tracker.dagger.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
public class ApplicationModule constructor(var mApplication: Application) {
    @ApplicationScope
    @Provides
    fun provideApplication() : Application {
        return mApplication ;
    }
}