package com.iti.mobile.covid19tracker.features.all_countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.iti.mobile.covid19tracker.model.entities.AllResults
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.entities.CountryHistory
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import javax.inject.Inject

class AllCountriesViewModel @Inject constructor(private val dataRepository: DataRepository): ViewModel(){
    suspend fun updateDatabase() {
        dataRepository.updateDataBase()
    }

    val countriesData: LiveData<List<Country>> = liveData {
        emitSource(dataRepository.getCountriesData())
    }

    val allCountriesResult: LiveData<AllResults> = liveData {
        emitSource(dataRepository.getAllResultsSharedPreference())
    }

    suspend fun updateCountry(country: Country){
        dataRepository.updateCountrySubscription(country)
    }
    suspend fun pullToRefreshLogic(): String {
        return dataRepository.pullToRefreshLogic()
    }
   val getNotificationSettings : Long = dataRepository.getNotificationSettings()


    fun  updateNotificationSettings (time:Long,isEnabled:Boolean){
        dataRepository.updateNotificationSetting(time,isEnabled)
    }



}
