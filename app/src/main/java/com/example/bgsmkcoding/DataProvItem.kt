package com.example.bgsmkcoding


import com.google.gson.annotations.SerializedName

data class DataProvItem(
    @SerializedName("attributes")
    val attributes: Attributes
)