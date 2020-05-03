package com.iti.mobile.covid19tracker.model.entities

import com.squareup.moshi.Json

data class AllResults(

    @field:Json(name = "updated")
    var updated : Long = 0,

    @field:Json(name ="cases")
    var cases: Int=0,

    @field:Json(name ="todayCases")
    var todayCases: Int=0,

    @field:Json(name ="deaths")
    var deaths: Int=0,

    @field:Json(name ="todayDeaths")
    var todayDeaths: Int=0,
    @field:Json(name ="critical")
    var critical: Int=0,
    @field:Json(name ="recovered")
    var recovered: Int=0
)