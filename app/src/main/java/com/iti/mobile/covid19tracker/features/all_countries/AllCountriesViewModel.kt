package com.iti.mobile.covid19tracker.features.all_countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import com.iti.mobile.covid19tracker.utils.SEARCH_BY_CASES
import javax.inject.Inject

class AllCountriesViewModel @Inject constructor(private val dataRepository: DataRepository): ViewModel(){
    fun getCountriesData(query: String): LiveData<List<Country>> {
        return liveData {
            emit(dataRepository.getCountries(query))
        }
    }
}
