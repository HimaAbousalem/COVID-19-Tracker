package com.iti.mobile.covid19tracker.model.entities

import com.squareup.moshi.Json

data class AllResults(

    @field:Json(name = "updated")
    var updated : Long,

    @field:Json(name ="cases")
    var cases: Int,

    @field:Json(name ="todayCases")
    var todayCases: Int,

    @field:Json(name ="deaths")
    var deaths: Int,

    @field:Json(name ="todayDeaths")
    var todayDeaths: Int,

    @field:Json(name ="recovered")
    var recovered: Int
)