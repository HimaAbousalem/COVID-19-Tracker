package com.iti.mobile.covid19tracker.model.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.entities.CountryHistory
import com.iti.mobile.covid19tracker.model.entities.CountryHistoryDetails
import com.iti.mobile.covid19tracker.model.entities.ResultState
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

    suspend fun updateDataBase(): List<String>? = withContext(Dispatchers.IO){
        var changesList: MutableList<String>? = null
        //getAllResults
        val allResults = covidApi.getFullResults()
        val apiData = covidApi.getCountries("cases")
        sharedPreference.saveAllCountriesResult(allResults)
        val subscribedData = localDatabaseSource.getSubscribedCountries()
        //update ApiData
        if(subscribedData.isNotEmpty()) {
            updateApiList(apiData, subscribedData)
            if (subscribedData.isNotEmpty()) {
               changesList = calculateTheDifferences(subscribedData, apiData)
            }
        }
        localDatabaseSource.insert(apiData)
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

    suspend fun getCountryHistory (country:String) : CountryHistory {
       return covidApi.getCountryHistory(country)
    }

    suspend fun getAllHistory () : CountryHistoryDetails {
        return covidApi.getAllHistory()
    }

    suspend fun pullToRefreshLogic(): String = withContext(Dispatchers.IO){
        try {
            val allResults = covidApi.getFullResults()
            val apiData = covidApi.getCountries("cases")
            sharedPreference.saveAllCountriesResult(allResults)
            val subscribedData = localDatabaseSource.getSubscribedCountries()
            //update ApiData
            if (subscribedData.isNotEmpty()) {
                updateApiList(apiData, subscribedData)
            }
            localDatabaseSource.insert(apiData)
            return@withContext "data updated"
        }catch (e: Exception){
            return@withContext "Something went wrong"
        }
    }
}
