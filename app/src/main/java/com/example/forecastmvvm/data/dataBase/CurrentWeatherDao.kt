package com.example.forecastmvvm.data.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forecastmvvm.data.dataBase.entity.CURRENT_WEATHER_ID
import com.example.forecastmvvm.data.dataBase.entity.CurrentWeatherEntry
import com.example.forecastmvvm.data.dataBase.unitlocalized.ImperialCurrentWeatherEntry
import com.example.forecastmvvm.data.dataBase.unitlocalized.MetricCurrentWeatherEntry
import kotlinx.coroutines.selects.select

@Dao //сдесь описываются методы для работы с БД
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //если при помещении новых данных, там уже находятся другие данны, то заменяем их
    fun upsert(weatherEntry: CurrentWeatherEntry) //метод для обновления или помещения новых данных

    @Query("SELECT * FROM current_weather WHERE id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry> //получаем данные из класса с метрическими данными

    @Query("SELECT * FROM current_weather WHERE id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry> //получаем данные из класса с империческими данными

}