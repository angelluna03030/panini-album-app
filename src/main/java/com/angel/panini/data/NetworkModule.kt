package com.angel.panini.data

import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

object NetworkModule {
    private const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/3/"

    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val theSportsDbApi: TheSportsDbApi = retrofit.create(TheSportsDbApi::class.java)
}