package com.iti.mobile.covid19tracker.model.repositories

import androidx.lifecycle.LiveData
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.entities.CountryHistory
import com.iti.mobile.covid19tracker.model.entities.CountryHistoryDetails
import com.iti.mobile.covid19tracker.model.network.CovidApi
import com.iti.mobile.covid19tracker.model.room.LocalDatabaseSource
import com.iti.mobile.covid19tracker.model.shared_prefrence.SharedPreferenceHandler
import com.iti.mobile.covid19tracker.utils.calculateTheDifferences
import com.iti.mobile.covid19tracker.utils.updateApiList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val localDatabaseSource: LocalDatabaseSource,
    private val covidApi: CovidApi, private  val sharedPreference: SharedPreferenceHandler) {

    suspend fun updateDataBase(): List<Country>? = withContext(Dispatchers.IO){
        var changesList: MutableList<Country>? = null
        //getAllResults
        val allResults = covidApi.getFullResults()
        //Timber.d("#1 $allResults")
        val apiData = covidApi.getCountries("cases")
     //   Timber.d("#2 ${apiData[0].cases}")

        sharedPreference.saveAllCountriesResult(allResults)
//        localDatabaseSource.insert(apiData)
//        localDatabaseSource.subscribeToCountry("Egypt")
//        localDatabaseSource.subscribeToCountry("USA")
//        localDatabaseSource.subscribeToCountry("Italy")
        val subscribedData = localDatabaseSource.getSubscribedCountries()

//        Timber.d("#3 #4 ${subscribedData[0].cases}")
//        subscribedData[0].cases = 10000000
//        subscribedData[1].cases = 5000000

        //update ApiData
        if(subscribedData.isNotEmpty()) {
            updateApiList(apiData, subscribedData)
            if (subscribedData.isNotEmpty()) {
               changesList = calculateTheDifferences(subscribedData, apiData)
            }
        }
        localDatabaseSource.insert(apiData)
    //    Timber.d("#7 save to database")
        return@withContext changesList
    }

    fun getCountriesData(): LiveData<List<Country>> {
          return localDatabaseSource.getAllCountries()
    }

    fun getAllResultsSharedPreference() = sharedPreference.getAllCountriesResultLiveData()

    fun getSubscribedCountries() = localDatabaseSource.getAllSubscriptions()

    suspend fun updateCountrySubscription(country: Country) {
        localDatabaseSource.update(country)
    }

    fun getCountryHistory (country:String) : CountryHistory {
       return covidApi.getCountryHistory()
    }
}
