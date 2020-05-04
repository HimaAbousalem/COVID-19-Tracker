package com.iti.mobile.covid19tracker.utils

import com.iti.mobile.covid19tracker.model.entities.Country
import timber.log.Timber

fun updateApiList(apiList: List<Country>, subscribedList: List<Country>){
    subscribedList.forEach { subscribed->
        apiList.filter { api-> api.country == subscribed.country }
            .forEach { api-> api.subscription = SUBSCRIBED }
    }
}

fun calculateTheDifferences(subscribedData: List<Country>, apiData: List<Country>): MutableList<Country> {
    val changes = mutableListOf<Country>()
    subscribedData.forEach {subscribed->
        apiData.filter { api-> api.country == subscribed.country }
          //  .filter {api -> api.cases - subscribed.cases >= 0 }
            .forEach { api->
                api.cases = api.cases - subscribed.cases
                api.deaths = if( api.deaths-subscribed.deaths > 0 ) api.deaths - subscribed.deaths else  0
                api.cases = if( api.recovered-subscribed.recovered > 0 ) api.recovered - subscribed.recovered else  0
                changes.add(api) }
    }
    Timber.d("#5 filter Data")
    Timber.d("#6 ${changes.size}")
    return changes
}