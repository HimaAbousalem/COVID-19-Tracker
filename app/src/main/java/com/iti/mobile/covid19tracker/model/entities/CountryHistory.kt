package com.iti.mobile.covid19tracker.model.entities


import com.squareup.moshi.Json

data class CountryHistory (

    @field:Json(name = "country")
    var country : String = "",
    @field:Json(name = "timeline")
    var timeLine : CountryHistoryDetails? = null
)