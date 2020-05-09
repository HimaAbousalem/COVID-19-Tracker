package com.iti.mobile.covid19tracker.model.entities

import java.lang.Exception

sealed class ResultState<out T>
data class LoadingState<T>(var loading: Boolean):ResultState<T>()
data class SuccessState<T>(var data: T):ResultState<T>()
data class ErrorState<T>(var exception: String):ResultState<T>()