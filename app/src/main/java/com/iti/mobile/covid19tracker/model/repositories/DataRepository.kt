package com.iti.mobile.covid19tracker.model.repositories

import com.iti.mobile.covid19tracker.model.network.CovidApi
import com.iti.mobile.covid19tracker.model.room.LocalDataSource
import javax.inject.Inject

class DataRepository @Inject constructor(
     val localDataSource: LocalDataSource,
     val covidApi: CovidApi
) {

}
