package com.iti.mobile.covid19tracker.utils

import com.iti.mobile.covid19tracker.model.entities.Country

fun updateApiList(apiList: List<Country>, subscribedList: List<Country>){
    subscribedList.forEach { subscribed->
        apiList.filter { api-> api.country == subscribed.country }
            .forEach { api-> api.subscription = SUBSCRIBED }
    }
}

fun calculateTheDifferences(subscribedData: List<Country>, apiData: List<Country>): MutableList<String> {
    val changes = mutableListOf<String>()
    val subscribedCountries = apiData.filter {
        it.subscription == 1
    }
    subscribedData.forEach {subscribed->
        subscribedCountries.filter { api-> api.country == subscribed.country }
            .forEach { api->
                if( api.cases > subscribed.cases || api.deaths > subscribed.deaths || api.recovered > subscribed.recovered) {
                    changes.add(api.country)
                }
            }
    }

//    if(changes.isNotEmpty()){
//        Timber.d("changes is : ${changes[0]}")
//        Timber.d("changes is : ${changes.size}")
//    }

    return changes
}