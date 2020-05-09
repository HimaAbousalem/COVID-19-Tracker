package com.iti.mobile.covid19tracker.features.statisticsView

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.iti.mobile.covid19tracker.model.entities.*
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import java.lang.Exception
import javax.inject.Inject

class StatisticsViewModel  @Inject constructor(private val dataRepository: DataRepository): ViewModel(){

    fun getAllHistory () : LiveData<ResultState<CountryHistoryDetails>> = liveData{
        emit(LoadingState<CountryHistoryDetails>(loading = true))
        try{
            val statistics = dataRepository.getAllHistory()
            emit(SuccessState(data =  statistics))
            emit(LoadingState<CountryHistoryDetails>(loading = false))
        }catch (e: Exception){
            emit(ErrorState<CountryHistoryDetails>(exception = "Please Check your internet!"))
            emit(LoadingState<CountryHistoryDetails>(loading = false))
        }
    }
}