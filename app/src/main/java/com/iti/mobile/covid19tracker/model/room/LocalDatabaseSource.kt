package com.iti.mobile.covid19tracker.model.room

import com.iti.mobile.covid19tracker.dagger.scopes.ApplicationScope
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.room.daos.CountryDao
import com.iti.mobile.covid19tracker.model.room.daos.SubscriptionDao
import javax.inject.Inject

@ApplicationScope
class LocalDataSource @Inject constructor(private val countryDao: CountryDao, private val subscriptionDao: SubscriptionDao) {

    suspend fun allCountries(): List<Country> {
        return countryDao.getAllCountries()
    }

    suspend fun insert(country: Country) {
        countryDao.insertCountry(country)
    }

    suspend fun delete(countryName : String) {
        countryDao.deleteCountry(countryName)
    }

    suspend fun update(country: Country) {
        countryDao.updateCountry(country)
    }
}