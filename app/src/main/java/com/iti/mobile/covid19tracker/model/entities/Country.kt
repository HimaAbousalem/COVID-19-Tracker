package com.iti.mobile.covid19tracker.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.squareup.moshi.Json

@Entity(tableName = "countries")
data class Country (

    @ColumnInfo(name = "updated")
    var updated : Long? = null,

    @ColumnInfo(name = "country")
    var country : String? = null,

    @field:Json(name = "countryInfo")
    private val countryInfo: CountryInfo? = null,

    @field:Json(name ="cases")
    private val cases: Int? = null,

    @field:Json(name ="todayCases")
    private val todayCases: Int? = null,

    @field:Json(name ="deaths")
    private val deaths: Int? = null,

    @field:Json(name ="todayDeaths")
    private val todayDeaths: Int? = null,

    @field:Json(name ="recovered")
    private val recovered: Int? = null,

    //can be changed
    @field:Json(name ="active")
    @Ignore
    private val active: Int? = null,

    @field:Json(name ="critical")
    @Ignore
    private val critical: Int? = null,

    @field:Json(name ="casesPerOneMillion")
    @Ignore
    private val casesPerOneMillion: Int? = null,

    @field:Json(name ="deathsPerOneMillion")
    @Ignore
    private val deathsPerOneMillion: Int? = null,

    @field:Json(name ="tests")
    @Ignore
    private val tests: Int? = null,

    @field:Json(name ="testsPerOneMillion")
    @Ignore
    private val testsPerOneMillion: Int? = null,

    @field:Json(name ="continent")
    private val continent: String? = null
)

