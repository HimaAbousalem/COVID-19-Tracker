package com.iti.mobile.covid19tracker.model.entities

import androidx.annotation.NonNull
import androidx.room.*
import com.squareup.moshi.Json

@Entity(tableName = "countries" , indices = [Index(value = ["country"])])
data class Country (

    @field:Json (name = "updated")
    var updated : Long = 0,
    @NonNull
    @PrimaryKey
    @field:Json (name = "country")
    var country : String = "",

    @Embedded(prefix = "countryInfo_")
    @field:Json(name = "countryInfo")
    var countryInfo: CountryInfo? = null,

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
    var active: Int = 0,

    @field:Json(name ="critical")
    var critical: Int = 0,

    @field:Json(name ="casesPerOneMillion")
    var casesPerOneMillion: Int = 0,

    @field:Json(name ="deathsPerOneMillion")
    var deathsPerOneMillion: Int = 0,

    @field:Json(name ="tests")
    var tests: Int = 0,

    @field:Json(name ="testsPerOneMillion")
    var testsPerOneMillion: Int = 0,

    @field:Json(name ="continent")
    var continent: String = ""
)

