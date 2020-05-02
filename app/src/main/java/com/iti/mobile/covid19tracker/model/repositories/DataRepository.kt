package com.iti.mobile.covid19tracker.model.repositories

import androidx.lifecycle.LiveData
import com.iti.mobile.covid19tracker.model.entities.AllResults
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.network.CovidApi
import com.iti.mobile.covid19tracker.model.room.LocalDataSource
import com.iti.mobile.covid19tracker.model.shared_prefrence.SharedPreferenceHandler
import com.iti.mobile.covid19tracker.utils.SUBSCRIBED
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val covidApi: CovidApi, private  val sharedPreference: SharedPreferenceHandler) {

    suspend fun updateDataBase() = withContext(Dispatchers.IO){
        val apiData = covidApi.getCountries("cases")
        val subscribedData = localDataSource.getSubscribedCountries()
        //update ApiData
        subscribedData.forEach { subscribed->
            apiData.filter { api-> api.country == subscribed.country }
                .forEach { api-> api.subscription = SUBSCRIBED }
        }
        localDataSource.insert(apiData)
        //getAllResults
        val allResults = covidApi.getFullResults()
        sharedPreference.saveAllCountriesResult(allResults)
    }

    fun getCountriesData(): LiveData<List<Country>> {
          return localDataSource.allCountries()
    }

    fun getAllResultsSharedPreference() = sharedPreference.getAllCountriesResultLiveData()

     suspend fun getCountries(query: String): List<Country> {
          return covidApi.getCountries(query = query)
     }

}
