package com.iti.mobile.covid19tracker.dagger.modules.app

import android.app.Application
import com.iti.mobile.covid19tracker.dagger.scopes.ApplicationScope
import com.iti.mobile.covid19tracker.model.room.LocalDatabase
import com.iti.mobile.covid19tracker.model.room.daos.CountryDao
import com.iti.mobile.covid19tracker.model.room.daos.SubscriptionDao
import com.iti.mobile.covid19tracker.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides

@Module(includes = [ApplicationModule::class])
class RoomModule {
    @ApplicationScope
    @Provides
    fun provideLocalDatabase (mApplication: Application) : LocalDatabase{
        return androidx.room.Room
            .databaseBuilder(mApplication,LocalDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @ApplicationScope
    @Provides
    fun provideCountryDao (localDatabase: LocalDatabase) : CountryDao{
        return  localDatabase.countryDao
    }

    @ApplicationScope
    @Provides
    fun provideSubscriptionDao (localDatabase: LocalDatabase) : SubscriptionDao{
        return  localDatabase.subscriptionDao
    }
}