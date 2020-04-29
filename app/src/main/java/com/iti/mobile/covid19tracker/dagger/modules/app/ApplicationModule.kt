package com.iti.mobile.covid19tracker.dagger.modules.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.iti.mobile.covid19tracker.dagger.scopes.ApplicationScope
import com.iti.mobile.covid19tracker.utils.SHARED_PREFERENCE_NAME
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule (private var mApplication: Application) {

    @ApplicationScope
    @Provides
    fun provideApplication() = mApplication

    @ApplicationScope
    @Provides
    fun provideSharedPreference() = mApplication.getSharedPreferences(
            SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )

}