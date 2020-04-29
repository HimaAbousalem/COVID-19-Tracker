package com.iti.mobile.covid19tracker.model.entities

import androidx.room.Ignore
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




object CountryInfo {
        @SerializedName("_id")
        @Expose
        var id: Int? = null

        @SerializedName("iso2")
        @Ignore
        var iso2: String? = null

        @SerializedName("iso3")
        @Ignore
        var iso3: String? = null

        @SerializedName("lat")
        @Expose
        var lat: Int? = null

        @SerializedName("long")
        @Expose
        var long: Int? = null

        @SerializedName("flag")
        @Expose
        var flag: String? = null
}