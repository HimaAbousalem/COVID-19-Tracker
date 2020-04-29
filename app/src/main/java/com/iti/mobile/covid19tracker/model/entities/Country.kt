package com.iti.mobile.covid19tracker.model.entities

import androidx.annotation.NonNull
import androidx.room.*
import com.iti.mobile.covid19tracker.utils.UN_SUBSCRIBED
import com.squareup.moshi.Json

@Entity(tableName = "countries" , indices = [Index(value = ["country"])])
data class Country (
    @Transient
    var subscribtion : Int = UN_SUBSCRIBED ,
    @field:Json (name = "updates")
    var updated : Long? = null,

    @PrimaryKey
    @NonNull
    @field:Json (name = "country")
    var country : String,

    @Embedded(prefix = "countryInfo_")
    @field:Json(name = "countryInfo")
    var countryInfo: CountryInfo,

    @field:Json(name ="cases")
    var cases: Int?= null,

    @field:Json(name ="todayCases")
    var todayCases: Int?= null,

    @field:Json(name ="deaths")
    var deaths: Int? = null,

    @field:Json(name ="todayDeaths")
    var todayDeaths: Int?= null,

    @field:Json(name ="recovered")
    var recovered: Int?= null,

    //can be changed
    @field:Json(name ="active")
    @Ignore
    var active: Int? = null,

    @field:Json(name ="critical")
    @Ignore
    var critical: Int? = null,

    @field:Json(name ="casesPerOneMillion")
    @Ignore
    var casesPerOneMillion: Int? = null,

    @field:Json(name ="deathsPerOneMillion")
    @Ignore
    var deathsPerOneMillion: Int? = null,

    @field:Json(name ="tests")
    @Ignore
    var tests: Int? = null,

    @field:Json(name ="testsPerOneMillion")
    @Ignore
    var testsPerOneMillion: Int? = null,

    @field:Json(name ="continent")
    @Ignore
    var continent: String? = null
)

