package com.iti.mobile.covid19tracker.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val dataRepository: DataRepository): ViewModel(){

//    val countriesData: LiveData<List<Country>> = liveData {
//        emit(dataRepository.getCountries())
//    }

    fun getCountriesDat(query: String):LiveData<List<Country>>{
        return liveData {
            emit(dataRepository.getCountries(query))
        }
    }
}
