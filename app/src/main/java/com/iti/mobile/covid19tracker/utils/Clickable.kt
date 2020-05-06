package com.iti.mobile.covid19tracker.utils

import com.iti.mobile.covid19tracker.model.entities.Country

interface Clickable {
    fun onItemClick(country: Country)
}