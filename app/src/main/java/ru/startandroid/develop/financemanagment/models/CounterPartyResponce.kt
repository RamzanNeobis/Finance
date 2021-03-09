package ru.startandroid.develop.financemanagment.models

import com.google.gson.annotations.SerializedName

data class CounterPartyResponce(

    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String
)
