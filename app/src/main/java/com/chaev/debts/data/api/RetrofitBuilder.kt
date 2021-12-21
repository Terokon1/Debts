package com.chaev.debts.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitBuilder {
    //    private const val BASE_URL = "http://10.0.2.2:8000/"
    private const val BASE_URL = "http://193.187.174.73/"
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(
                OkHttpClient()
                    .newBuilder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .apply {
                                level = HttpLoggingInterceptor.Level.BODY
                            })
                    .build()
            )
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }


    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}