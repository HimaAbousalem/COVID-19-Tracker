package com.iti.mobile.covid19tracker.model.entities

import com.squareup.moshi.Json

data class CountryInfo(

        @field:Json(name ="_id")
        var id: Int?,

        @field:Json(name ="lat")
        var lat: Int? ,

        @field:Json(name ="long")
        var lng: Int? ,

        @field:Json(name ="flag")
        var flag: String?
)