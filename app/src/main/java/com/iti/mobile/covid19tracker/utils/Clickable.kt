package com.iti.mobile.covid19tracker.utils

import com.iti.mobile.covid19tracker.databinding.DetailsCountryCardLayoutBinding
import com.iti.mobile.covid19tracker.model.entities.Country
import com.iti.mobile.covid19tracker.model.entities.CountryHistory

interface Clickable {
    fun onItemClick(country: Country)
    fun updateNotificationTime (time : Long , isEnabled : Boolean)
}