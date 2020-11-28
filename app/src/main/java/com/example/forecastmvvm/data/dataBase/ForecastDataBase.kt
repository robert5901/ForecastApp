package com.example.forecastmvvm.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.forecastmvvm.data.dataBase.entity.CurrentWeatherEntry

@Database(
    entities = [CurrentWeatherEntry::class],
    version = 1
)
abstract class ForecastDataBase : RoomDatabase() { //сновной класс по работе с базой данных. наследует RoomDatabase
    abstract fun currentWeatherDao(): CurrentWeatherDao

    companion object{
       @Volatile private var instance: ForecastDataBase? = null //@Volotile означает, что записи в это поле немедленно становятся видимыми для других потоков
        private val LOCK = Any() //чтобы быть увереным, что два потока не делают одно и тоже в одно время

        operator fun invoke(contex: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDataBase(contex).also { instance = it }
        }

        private fun buildDataBase(contex: Context) =
            Room.databaseBuilder(contex.applicationContext,
                ForecastDataBase::class.java, "forecast.db")
                .build()
    }
}