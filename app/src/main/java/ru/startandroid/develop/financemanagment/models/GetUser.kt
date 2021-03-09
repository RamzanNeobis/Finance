package ru.startandroid.develop.financemanagment.models

import com.google.gson.annotations.SerializedName

class GetUser(

    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("name")
    val name: String
)