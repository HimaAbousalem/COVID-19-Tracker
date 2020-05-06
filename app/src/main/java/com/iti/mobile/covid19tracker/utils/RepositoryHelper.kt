package com.iti.mobile.covid19tracker.utils

import com.iti.mobile.covid19tracker.model.entities.Country

fun updateApiList(apiList: List<Country>, subscribedList: List<Country>){
    subscribedList.forEach { subscribed->
        apiList.filter { api-> api.country == subscribed.country }
            .forEach { api-> api.subscription = SUBSCRIBED }
    }
}

fun calculateTheDifferences(subscribedData: List<Country>, apiData: List<Country>): MutableList<Country> {
    val changes = mutableListOf<Country>()
    val subscribedCountries = apiData.filter { it.subscription == 1 }
    subscribedData.forEach {subscribed->
        subscribedCountries.filter { api-> api.country == subscribed.country }
            .forEach { api->
                api.cases = if( api.cases - subscribed.cases > 0) api.cases - subscribed.cases else 0
                api.deaths = if( api.deaths - subscribed.deaths > 0 ) api.deaths - subscribed.deaths else  0
                api.recovered = if( api.recovered - subscribed.recovered > 0 ) api.recovered - subscribed.recovered else  0
                changes.add(api)
            }
    }
    return changes
}