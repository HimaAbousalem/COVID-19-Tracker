package com.iti.mobile.covid19tracker.model.repositories

import androidx.lifecycle.LiveData
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.network.CovidApi
import com.iti.mobile.covid19tracker.model.room.LocalDataSource
import com.iti.mobile.covid19tracker.model.shared_prefrence.SharedPreferenceHandler
import com.iti.mobile.covid19tracker.utils.SUBSCRIBED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val covidApi: CovidApi, private  val sharedPreference: SharedPreferenceHandler) {

    suspend fun updateDataBase() = withContext(Dispatchers.IO){
        //getAllResults
        val allResults = covidApi.getFullResults()
        Timber.d("#1 ${allResults.cases}")
        val apiData = covidApi.getCountries("cases")
        Timber.d("#2 ${apiData[0].cases}")

        sharedPreference.saveAllCountriesResult(allResults)
        localDataSource.insert(apiData)
        localDataSource.subscribeToCountry("Egypt")
        localDataSource.subscribeToCountry("USA")
        localDataSource.subscribeToCountry("Italy")
        val subscribedData = localDataSource.getSubscribedCountries()

        Timber.d("#3 #4 ${subscribedData[0].cases}")
        subscribedData[0].cases = 10000000
        subscribedData[0].cases = 5000000
        //update ApiData
        subscribedData.forEach { subscribed->
            apiData.filter { api-> api.country == subscribed.country }
                .forEach { api-> api.subscription = SUBSCRIBED }
        }
        localDataSource.insert(apiData)
        Timber.d("#5 save to database")

        if(subscribedData.isNotEmpty()){
            calculateTheDifferences(subscribedData, apiData)
        }
    }

    private fun calculateTheDifferences(subscribedData: List<Country>, apiData: List<Country>) {
        val changes = mutableListOf<Country>()
        subscribedData.forEach {subscribed->
            apiData.filter { api-> api.country == subscribed.country }
                .filter {api -> api.cases - subscribed.cases > 0 }
                .forEach { api->
                    api.cases = api.cases - subscribed.cases
                    api.deaths = if( api.deaths-subscribed.deaths > 0 ) api.deaths - subscribed.deaths else  0
                    api.cases = if( api.recovered-subscribed.recovered > 0 ) api.recovered - subscribed.recovered else  0
                    changes.add(api) }
        }
        Timber.d("#6 filter Data")
        Timber.d("#7 ${changes[0]}")
        Timber.d("#8 ${changes.size}")
        //show notification with these changes.
    }

    fun getCountriesData(): LiveData<List<Country>> {
          return localDataSource.allCountries()
    }

    fun getAllResultsSharedPreference() = sharedPreference.getAllCountriesResultLiveData()

}
