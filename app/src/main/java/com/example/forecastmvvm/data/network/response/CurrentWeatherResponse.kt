package com.example.forecastmvvm.data.network.response

import com.example.forecastmvvm.data.dataBase.entity.CurrentWeatherEntry
import com.example.forecastmvvm.data.dataBase.entity.Location
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    val location: Location,
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry
)