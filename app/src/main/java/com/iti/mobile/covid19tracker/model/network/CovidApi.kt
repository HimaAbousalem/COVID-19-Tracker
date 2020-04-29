package com.iti.mobile.covid19tracker.model.network

import com.iti.mobile.covid19tracker.model.entities.Country
import io.reactivex.Observable
import retrofit2.http.GET

interface CovidApi{
    @GET("countries")
    fun getCountries(): Observable<List<Country>>
}