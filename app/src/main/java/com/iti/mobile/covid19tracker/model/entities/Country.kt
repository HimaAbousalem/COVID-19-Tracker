package com.iti.mobile.covid19tracker.model.entities

import androidx.annotation.NonNull
import androidx.room.*
import com.iti.mobile.covid19tracker.utils.UN_SUBSCRIBED
import com.squareup.moshi.Json

@Entity(tableName = "countries" , indices = arrayOf(Index(value = ["country"])))
data class Country (

    var subscribtion : Int = UN_SUBSCRIBED ,

    @field:Json (name = "updates")
    var updated : Long = 0,

    @NonNull
    @PrimaryKey
    @field:Json (name = "country")
    var country : String = "" ,

    @Embedded
    @field:Json(name = "countryInfo")
    var countryInfo: CountryInfo = CountryInfo(),

    @field:Json(name ="cases")
    var cases: Int = 0,

    @field:Json(name ="todayCases")
    var todayCases: Int = 0,

    @field:Json(name ="deaths")
    var deaths: Int = 0,

    @field:Json(name ="todayDeaths")
    var todayDeaths: Int = 0,

    @field:Json(name ="recovered")
    var recovered: Int = 0,

    //can be changed
    @field:Json(name ="active")
    @Ignore
    var active: Int = 0,

    @field:Json(name ="critical")
    @Ignore
    var critical: Int = 0,

    @field:Json(name ="casesPerOneMillion")
    @Ignore
    var casesPerOneMillion: Int = 0,

    @field:Json(name ="deathsPerOneMillion")
    @Ignore
    var deathsPerOneMillion: Int = 0,

    @field:Json(name ="tests")
    @Ignore
    var tests: Int = 0,

    @field:Json(name ="testsPerOneMillion")
    @Ignore
    var testsPerOneMillion: Int = 0,

    @field:Json(name ="continent")
    @Ignore
    var continent: String = ""
)

