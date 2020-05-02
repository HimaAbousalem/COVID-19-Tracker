package com.iti.mobile.covid19tracker.model.repositories

import com.iti.mobile.covid19tracker.model.entities.AllResults
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.network.CovidApi
import com.iti.mobile.covid19tracker.model.room.LocalDataSource
import com.iti.mobile.covid19tracker.model.shared_prefrence.SharedPreference
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DataRepository @Inject constructor(
     val localDataSource: LocalDataSource,
     val covidApi: CovidApi , sharedPreference: SharedPreference ) {

     fun getCountriesData(query: String): Observable<List<Country>>{
          return getCountries(query)
               .publish {network ->
                    Observable.merge(
                         network,
                         localDataSource.allCountries().takeUntil(network)
                    )
               }
     }

     private fun getCountries(query: String): Observable<List<Country>>{
          return covidApi.getCountries(query = query).delay(5, TimeUnit.SECONDS)
     }

     fun getAllResultsData () : Observable<AllResults> {
          return covidApi.getFullResults()
     }
}
