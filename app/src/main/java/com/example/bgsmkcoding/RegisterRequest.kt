package com.example.bgsmkcoding

import com.google.gson.annotations.SerializedName


class RegisterRequest(@SerializedName("username") var username: String,
                      @SerializedName("password") var password: String)