package com.iti.mobile.covid19tracker.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "countries")
object Country {
    @Expose
    @ColumnInfo(name = "updated")
    var updated : Long? = null

    @Expose
    @ColumnInfo(name = "country")
    var country : String? = null

    @SerializedName("countryInfo")
    @Expose
    private val countryInfo: CountryInfo? = null

    @SerializedName("cases")
    @Expose
    private val cases: Int? = null

    @SerializedName("todayCases")
    @Expose
    private val todayCases: Int? = null

    @SerializedName("deaths")
    @Expose
    private val deaths: Int? = null

    @SerializedName("todayDeaths")
    @Expose
    private val todayDeaths: Int? = null

    @SerializedName("recovered")
    @Expose
    private val recovered: Int? = null
//can be changed
    @SerializedName("active")
    @Ignore
    private val active: Int? = null

    @SerializedName("critical")
    @Ignore
    private val critical: Int? = null

    @SerializedName("casesPerOneMillion")
    @Ignore
    private val casesPerOneMillion: Int? = null

    @SerializedName("deathsPerOneMillion")
    @Ignore
    private val deathsPerOneMillion: Int? = null

    @SerializedName("tests")
    @Ignore
    private val tests: Int? = null

    @SerializedName("testsPerOneMillion")
    @Ignore
    private val testsPerOneMillion: Int? = null

    @SerializedName("continent")
    @Ignore
    private val continent: String? = null
}

