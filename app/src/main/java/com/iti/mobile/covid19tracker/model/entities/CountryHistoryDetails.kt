package com.iti.mobile.covid19tracker.model.entities

import com.squareup.moshi.Json

data class CountryHistoryDetails(

    @field:Json(name ="cases")
    var cases: Map<String,Int> ? = HashMap(),

    @field:Json(name ="deaths")
    var deaths: Map<String,Int> ? = HashMap(),

    @field:Json(name ="recovered")
    var recovered: Map<String,Int> ? = HashMap()

    )
