package com.iti.mobile.covid19tracker.features.mapView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.mobile.covid19tracker.model.entities.CountryHistory
import com.iti.mobile.covid19tracker.model.entities.CountryHistoryDetails
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import javax.inject.Inject

class MapViewModel  @Inject constructor(private val dataRepository: DataRepository): ViewModel(){

    var historyObserver = MutableLiveData<CountryHistoryDetails>()
    suspend fun getAllHistory () : CountryHistoryDetails {
        return dataRepository.getAllHistory()
    }
}