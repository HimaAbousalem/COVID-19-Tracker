package com.iti.mobile.covid19tracker.model.entities

import com.squareup.moshi.Json

data class CountryInfo(
        @field:Json(name ="_id")
        var id: Int? = null,

        @field:Json(name ="lat")
        var lat: Int? = null,

        @field:Json(name ="long")
        var long: Int? = null,

        @field:Json(name ="flag")
        var flag: String? = null
)