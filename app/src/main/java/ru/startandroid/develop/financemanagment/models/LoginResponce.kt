package ru.startandroid.develop.financemanagment.models

import com.google.gson.annotations.SerializedName

data class LoginResponce (
    @SerializedName("username")
    val username: String,
    @SerializedName("token")
    var token: String
)