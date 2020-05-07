package com.iti.mobile.covid19tracker.model.network

import com.iti.mobile.covid19tracker.model.entities.AllResults
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.entities.CountryHistory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CovidApi{
    @GET("countries")
    suspend fun getCountries(@Query("sort") query: String): List<Country>

    @GET("all")
    suspend fun getFullResults(): AllResults

    @GET("historical/{query}")
    suspend fun getCountryHistory(@Path("query") query: String): CountryHistory

}