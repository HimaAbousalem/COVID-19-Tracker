package com.iti.mobile.covid19tracker.model.network

import com.iti.mobile.covid19tracker.model.entities.AllResults
import com.iti.mobile.covid19tracker.model.entities.Country
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidApi{
    @GET("countries")
    fun getCountries(@Query("sort") query: String): Observable<List<Country>>

    @GET("all")
    fun getFullResults():Observable<AllResults>
}