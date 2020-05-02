package com.iti.mobile.covid19tracker.model.shared_prefrence

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iti.mobile.covid19tracker.dagger.scopes.ApplicationScope
import com.iti.mobile.covid19tracker.model.entities.AllResults
import com.iti.mobile.covid19tracker.utils.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@ApplicationScope
class SharedPreferenceHandler @Inject constructor(private val sharedPref: SharedPreferences) {
    private lateinit var editor: SharedPreferences.Editor
    private val liveData = MutableLiveData<AllResults>()
    private val prefChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, _ ->
            liveData.postValue(getAllCountriesResults(sharedPreferences))
        }

    init {
        sharedPref.registerOnSharedPreferenceChangeListener(prefChangeListener)
    }

    fun saveAllCountriesResult(allResults: AllResults) {
        editor = sharedPref.edit()
        editor.putInt(ALL_CASES, allResults.cases)
        editor.putInt(ALL_DEATHS, allResults.deaths)
        editor.putInt(ALL_RECOVERED, allResults.recovered)
        editor.putInt(TODAY_CASES, allResults.todayCases)
        editor.putInt(TODAY_DEATHS, allResults.todayDeaths)
        editor.putLong(UPDATED, allResults.updated)
        editor.apply()
    }

    fun getAllCountriesResultLiveData() = liveData

    private fun getAllCountriesResults(sharedPref: SharedPreferences): AllResults {
        return AllResults(sharedPref.getLong(UPDATED, 0)
            ,sharedPref.getInt(ALL_CASES,0),sharedPref.getInt(TODAY_CASES, 0)
            ,sharedPref.getInt(ALL_DEATHS,0), sharedPref.getInt(TODAY_DEATHS,0)
            , sharedPref.getInt(ALL_RECOVERED,0))
    }

    fun clearPref() {
        editor = sharedPref.edit()
        editor.clear().apply()
    }
}
