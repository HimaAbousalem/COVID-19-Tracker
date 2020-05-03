package com.iti.mobile.covid19tracker.model.room

import androidx.lifecycle.LiveData
import com.iti.mobile.covid19tracker.dagger.scopes.ApplicationScope
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.room.daos.CountryDao
import javax.inject.Inject

@ApplicationScope
class LocalDataSource @Inject constructor(private val countryDao: CountryDao) {

    fun allCountries(): LiveData<List<Country>> {
        return countryDao.getAllCountries()
    }

    suspend fun getSubscribedCountries():List<Country>{
        return countryDao.getSubscribedCountries()
    }

    suspend fun insert(country: List<Country>) {
        countryDao.insertCountry(country)
    }

    suspend fun delete(countryName : String) {
        countryDao.deleteCountry(countryName)
    }

    suspend fun update(country: Country) {
        countryDao.updateCountry(country)
    }

    suspend fun subscribeToCountry(name: String) {
        countryDao.subscribeToCountry(name)
    }
}