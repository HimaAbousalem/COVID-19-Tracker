package com.iti.mobile.covid19tracker.model.room.daos

import androidx.room.*
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.entities.Subscriptions

@Dao
interface SubscriptionDao {

    @Query("SELECT * FROM countries WHERE country in (Select countryName from subscriptions)")
    suspend fun allCountriesSubscription(): List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscription(subscription: Subscriptions)

    @Query("DELETE FROM subscriptions WHERE countryName = :countryName")
    suspend fun deleteSubscription(countryName: String)

    @Query("DELETE FROM subscriptions")
    suspend fun deleteAllSubscriptions()
}