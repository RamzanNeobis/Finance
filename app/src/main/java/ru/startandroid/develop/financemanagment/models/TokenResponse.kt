package ru.startandroid.develop.financemanagment.models

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("expiration")
    val expiration: String
)