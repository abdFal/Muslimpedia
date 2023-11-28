package com.example.muslimpedia.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiClient {

    private val BASE_URL = "https://newsapi.org/"
    private val API_KEY= "8aff45ed4b0b4bba98c642383af211fe"

    fun provideApiService(): ApiService{
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("X-api-key", API_KEY).build()
                chain.proceed(newRequest)

            }
            .readTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)
    }
}