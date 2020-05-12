package com.iti.mobile.covid19tracker.model.shared_prefrence

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iti.mobile.covid19tracker.dagger.scopes.ApplicationScope
import com.iti.mobile.covid19tracker.model.entities.AllResults
import com.iti.mobile.covid19tracker.utils.*
import javax.inject.Inject


@ApplicationScope
class SharedPreferenceHandler @Inject constructor(private val sharedPref: SharedPreferences) {
    private lateinit var editor: SharedPreferences.Editor
    private val allResultsLiveData = MutableLiveData<AllResults>()
    private val updateNotificationLiveData = MutableLiveData<Long>()
    private val prefChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
          if(key == UPDATE_KEY){
              updateNotificationLiveData.postValue(postNotificationHour(sharedPreferences))
          } else{
              allResultsLiveData.postValue(getAllCountriesResults(sharedPreferences))
          }

        }

    init {
        sharedPref.registerOnSharedPreferenceChangeListener(prefChangeListener)
    }
//All results
    fun saveAllCountriesResult(allResults: AllResults) {
        editor = sharedPref.edit()
        editor.putInt(ALL_CASES, allResults.cases)
        editor.putInt(ALL_DEATHS, allResults.deaths)
        editor.putInt(ALL_RECOVERED, allResults.recovered)
        editor.putInt(TODAY_CASES, allResults.todayCases)
        editor.putInt(TODAY_DEATHS, allResults.todayDeaths)
        editor.putInt(CRITICAL, allResults.critical)
        editor.putLong(UPDATED, allResults.updated)
        editor.apply()
    }
    fun getAllCountriesResultLiveData(): LiveData<AllResults>{
        if( allResultsLiveData.value == null) allResultsLiveData.postValue(getAllCountriesResults(sharedPref))
        return allResultsLiveData
    }

    private fun getAllCountriesResults(sharedPref: SharedPreferences): AllResults {
        return AllResults(updated = sharedPref.getLong(UPDATED, 0)
            ,cases = sharedPref.getInt(ALL_CASES,0), todayCases = sharedPref.getInt(TODAY_CASES, 0)
            ,deaths = sharedPref.getInt(ALL_DEATHS,0), todayDeaths = sharedPref.getInt(TODAY_DEATHS,0)
            , critical = sharedPref.getInt(CRITICAL, 0), recovered = sharedPref.getInt(ALL_RECOVERED,0))
    }
   //notification update hour
   fun  saveSettingNotificationUpdate(updateTime:Long, isEnabled : Boolean){
           editor = sharedPref.edit()
           editor.putLong(UPDATE_KEY, updateTime)
           editor.putBoolean(ENABLE_UPDATE_KEY,isEnabled)
           editor.apply()
   }
    fun getNotificationUpdateHour (): LiveData<Long> {
        if (updateNotificationLiveData.value == null) updateNotificationLiveData.postValue(postNotificationHour(sharedPref))
        return updateNotificationLiveData
    }
    private fun postNotificationHour (sharedPref: SharedPreferences) :Long {
       if (sharedPref.getBoolean(ENABLE_UPDATE_KEY,true)){
           return sharedPref.getLong(UPDATE_KEY, DEFAULT_UPDATE_TIME)
       }
        //if notifications isn't enabled
        return 0
    }



    fun clearPref() {
        editor = sharedPref.edit()
        editor.clear().apply()
    }
}
