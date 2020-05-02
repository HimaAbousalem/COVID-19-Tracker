package com.iti.mobile.covid19tracker.features.all_countries

import androidx.lifecycle.ViewModel
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import com.iti.mobile.covid19tracker.utils.SEARCH_BY_CASES
import javax.inject.Inject

class AllCountriesViewModel @Inject constructor(private val dataRepository: DataRepository): ViewModel(){
    val allCountries = dataRepository.getCountriesData(SEARCH_BY_CASES)
    // val allResults  = dataRepository
}
