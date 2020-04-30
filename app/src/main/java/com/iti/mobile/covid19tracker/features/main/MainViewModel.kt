package com.iti.mobile.covid19tracker.features.main

import androidx.lifecycle.ViewModel
import com.iti.mobile.covid19tracker.model.repositories.DataRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val dataRepository: DataRepository): ViewModel()
