package com.iti.mobile.covid19tracker.model.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriptions")
class Subscriptions {
    @NonNull
    @PrimaryKey
    var countryName : String = ""
}