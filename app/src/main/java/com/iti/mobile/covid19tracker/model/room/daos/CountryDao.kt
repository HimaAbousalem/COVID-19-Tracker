package com.iti.mobile.covid19tracker.model.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.iti.mobile.covid19tracker.model.entities.Country

@Dao
interface CountryDao {

    @Query("SELECT * FROM countries")
    fun getAllCountries(): LiveData<List<Country>>

    @Query("SELECT * FROM countries where subscription = 1")
    suspend fun getSubscribedCountries():List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: List<Country>)

    @Query("update countries set subscription = 1 where country = :countryName")
    suspend fun subscribeToCountry(countryName :String)

    @Query("DELETE FROM countries WHERE country = :countryName")
    suspend fun deleteCountry(countryName: String)

    @Query("DELETE FROM COUNTRIES")
    suspend fun deleteAllCountries()

    @Update
    suspend fun updateCountry(country : Country)

}