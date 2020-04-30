package com.iti.mobile.covid19tracker.model.repositories

import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.network.CovidApi
import com.iti.mobile.covid19tracker.model.room.LocalDataSource
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DataRepository @Inject constructor(
     val localDataSource: LocalDataSource,
     val covidApi: CovidApi) {

     fun getCountriesData(query: String): Observable<List<Country>>{
          return getCountries(query)
               .publish {network ->
                    Observable.merge(
                         network,
                         localDataSource.allCountries().takeUntil(network)
                    )
               }
     }

     fun getCountries(query: String): Observable<List<Country>>{
          return covidApi.getCountries(query = query).delay(10, TimeUnit.SECONDS)
     }
}
