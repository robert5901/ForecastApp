package com.example.forecastmvvm.data.network

import com.example.forecastmvvm.data.network.response.CurrentWeatherResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "fe463042b0984d3dbc8194957202711"

//https://api.weatherapi.com/v1/current.json?key=fe463042b0984d3dbc8194957202711&q=London&lang=en

interface ApiuxWeatherApiService {
    @GET("current.json")
   suspend fun getCurrentWeatherAsync(
        @Query("q") location: String,
        @Query("lang") language: String = "en"
    ): CurrentWeatherResponse

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApiuxWeatherApiService {
            val requestInterceptor = Interceptor{ chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key",
                        API_KEY
                    )
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiuxWeatherApiService::class.java)
        }
    }
}