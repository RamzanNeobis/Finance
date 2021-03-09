package ru.startandroid.develop.financemanagment.models

import com.google.gson.annotations.SerializedName

data class Account (
    @SerializedName("id")
    val id: Int,
    @SerializedName("code")
    val code: Int,
    @SerializedName("paymentType")
    val paymentType: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("sum")
    val sum: Int
)  {
    
}