package com.iti.mobile.covid19tracker.model.room.daos

import androidx.room.*
import com.iti.mobile.covid19tracker.model.entities.Country
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface CountryDao {

    @get:Query("SELECT * FROM countries")
    val allCountries: Observable<List<Country>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountry(country: Country)

    @Query("DELETE FROM countries WHERE country = :countryName")
    fun deleteCountry(countryName: String)

    @Query("DELETE FROM COUNTRIES")
    fun deleteAllCountries()

    @Update
    fun updateCountry(country : Country)

}