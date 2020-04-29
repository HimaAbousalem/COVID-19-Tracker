package com.iti.mobile.covid19tracker.model.entities

import com.squareup.moshi.Json

data class CountryInfo(

        @field:Json(name ="_id")
        var _id: Int?,

        @field:Json(name ="lat")
        var lat: Int? ,

        @field:Json(name ="long")
        var long: Int? ,

        @field:Json(name ="flag")
        var flag: String?
)