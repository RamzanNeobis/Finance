package ru.startandroid.develop.financemanagment.models

import com.google.gson.annotations.SerializedName

class Data (

    @SerializedName("id") val id : Int,
    @SerializedName("actionDate") val transactionDate : String,
    @SerializedName("sum") val sum : Int,
    @SerializedName("operationName") val operationName : String,
    @SerializedName("transactionType") val transactionType : String,
    @SerializedName("projectName") val projectName : String,
    @SerializedName("score") val score : String,
    @SerializedName("targetEntity") val counterPartyName : String

)