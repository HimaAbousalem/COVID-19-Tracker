package com.iti.mobile.covid19tracker.features.subscriptions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.entities.CountryHistory
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import javax.inject.Inject

class SubscriptionsViewModel @Inject constructor(private val repository: DataRepository) : ViewModel(){

    val subscribedCountriesData: LiveData<List<Country>> = liveData {
        emitSource(repository.getSubscribedCountries())
    }
    suspend fun updateCountry(country: Country){
        repository.updateCountrySubscription(country)
    }
}