package ru.startandroid.develop.financemanagment.models

import com.google.gson.annotations.SerializedName

class EditTransactionModel(
    @SerializedName("id") val id : Int,
    @SerializedName("actionDate") val transactionDate : String,
    @SerializedName("sum") val sum : Int,
    @SerializedName("operationId") val operationId : Int,
    @SerializedName("projectId") val projectId : Int,
    @SerializedName("scoreId") val scoreId : Int,
    @SerializedName("counterPartyId") val counterPartyId : Int,
    @SerializedName("description") val description : String
)