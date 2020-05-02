package com.iti.mobile.covid19tracker.model.repositories

import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.network.CovidApi
import com.iti.mobile.covid19tracker.model.room.LocalDataSource
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val covidApi: CovidApi) {

     suspend fun getCountriesData(): List<Country> {
          return localDataSource.allCountries()
     }

     suspend fun getCountries(query: String): List<Country>{
          return covidApi.getCountries(query = query)
     }
}
