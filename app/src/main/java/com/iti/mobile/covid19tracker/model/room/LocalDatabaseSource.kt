package com.iti.mobile.covid19tracker.model.room

import com.iti.mobile.covid19tracker.dagger.scopes.ApplicationScope
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.room.daos.CountryDao
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

@ApplicationScope
class LocalDataSource @Inject constructor(localDatabase: LocalDatabase) {
    private  val countryDao: CountryDao

    init {
        countryDao = localDatabase.countryDao
    }
    fun allCountries(): Observable<List<Country>> {
        return countryDao.allCountries
    }

    fun insert(country: Country) {
        countryDao.insertCountry(country)
    }

    fun delete(countryName : String) {
        countryDao.deleteCountry(countryName)
    }

    fun update(country: Country) {
        countryDao.updateCountry(country)
    }


}