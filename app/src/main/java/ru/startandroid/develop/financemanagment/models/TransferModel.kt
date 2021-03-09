package ru.startandroid.develop.financemanagment.models

import com.google.gson.annotations.SerializedName

class TransferModel(
    @SerializedName("actionDate")
    val actionDate: String,
    @SerializedName("sum")
    val sum: Int,
    @SerializedName("scoreId")
    val scoreId: Int,
    @SerializedName("score2Id")
    val score2Id: Int,
    @SerializedName("description")
    val description: String
)