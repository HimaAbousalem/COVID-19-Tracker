package com.iti.mobile.covid19tracker.model.entities

import com.squareup.moshi.Json

 data class CountryInfo(

        @field:Json(name ="_id")
        var id: Int = 0,

        @field:Json(name ="lat")
        var lat: Int = 0,

        @field:Json(name ="long")
        var long: Int = 0,

        @field:Json(name ="flag")
        var flag: String = ""
)