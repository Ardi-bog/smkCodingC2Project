package com.example.bgsmkcoding

import retrofit2.Retrofit

object ApiService{
    private val TAG = "--ApiService"

    fun loginApiCall() = Retrofit.Builder()
        .baseUrl("https://bogelardi.000webhostapp.com/")
        .addConverterFactory(ApiWorker.gsonConverter)
        .client(ApiWorker.client)
        .build()
        .create(ApiList::class.java)
}