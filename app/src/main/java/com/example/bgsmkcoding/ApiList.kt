package com.example.bgsmkcoding

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiList{

    @POST("register.php")
    fun doRegister(
        @Body registerRequest : RegisterRequest
    ): Call<RegisterResponse>
}