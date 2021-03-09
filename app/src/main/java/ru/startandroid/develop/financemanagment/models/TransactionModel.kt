package ru.startandroid.develop.financemanagment.models

import com.google.gson.annotations.SerializedName

data class TransactionModel(

    @SerializedName("actionDate")
    var transactionDate : String,
    @SerializedName("sum")
    var sum : Int,
    @SerializedName("operationId")
    var operationId : Int,
    @SerializedName("projectId")
    var projectId : Int,
    @SerializedName("counterPartyId")
    var counterPartyId : Int,
    @SerializedName("scoreId")
    var scoreId : Int,
    @SerializedName("description")
    var description : String





)