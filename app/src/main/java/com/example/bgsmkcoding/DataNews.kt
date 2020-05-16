package com.example.bgsmkcoding


import com.google.gson.annotations.SerializedName

data class DataNews(
    @SerializedName("articles")
    val articles: Article,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)