package com.iti.mobile.covid19tracker.features.statisticsView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.iti.mobile.covid19tracker.model.entities.*
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class StatisticsViewModel  @Inject constructor(private val dataRepository: DataRepository): ViewModel(){

    val statisticsData: MutableLiveData<ResultState<CountryHistoryDetails>> = MutableLiveData()

    suspend fun getAllHistory () = withContext(IO){
        statisticsData.postValue(LoadingState<CountryHistoryDetails>(loading = true))
        try{
            val statistics = dataRepository.getAllHistory()
            statisticsData.postValue(SuccessState(data =  statistics))
            statisticsData.postValue(LoadingState<CountryHistoryDetails>(loading = false))
        }catch (e: Exception){
            statisticsData.postValue(ErrorState<CountryHistoryDetails>(exception = "Please Check your internet!"))
            statisticsData.postValue(LoadingState<CountryHistoryDetails>(loading = false))
        }
    }

    val allCountriesResult: LiveData<AllResults> = liveData {
        emitSource(dataRepository.getAllResultsSharedPreference())
    }
}