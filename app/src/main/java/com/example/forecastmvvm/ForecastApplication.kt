package com.example.forecastmvvm

import android.app.Application
import com.example.forecastmvvm.data.dataBase.ForecastDataBase
import com.example.forecastmvvm.data.network.*
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.data.repository.ForecastRepositoryImpl
import com.example.forecastmvvm.ui.weather.current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication: Application(), KodeinAware{
    override val kodein = Kodein.lazy {   //обьявление зависимостей
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDataBase(instance()) }
        bind() from singleton { instance<ForecastDataBase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiuxWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

}