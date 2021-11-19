package com.chaev.debts.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    //    private const val BASE_URL = "http://10.0.2.2:8000/"
    private const val BASE_URL = "http://192.168.31.71:8000/"
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
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
    val a = Response::class.java.newInstance()
    val field = Response::class.java.getField("data")

    init {
        field.setInt(a, 1)
    }
    val b = Response(1)
}

data class Response(
    val data: Int
)
/*
{"data":null}
 */