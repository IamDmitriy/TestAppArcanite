package com.easyapps.testapparcanite.api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseUrl = "https://jsonplaceholder.typicode.com"

class Client {
    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val api: API =
        retrofit.create(
            API::class.java
        )
}