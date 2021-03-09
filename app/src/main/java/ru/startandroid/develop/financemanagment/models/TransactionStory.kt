package ru.startandroid.develop.financemanagment.models

import com.google.gson.annotations.SerializedName


data class TransactionStory(

    @SerializedName("pageNumber") val pageNumber : Int,
    @SerializedName("pageSize") val pageSize : Int,
    @SerializedName("totalPages") val totalPages : Int,
    @SerializedName("totalRecords") val totalRecords : Int,
    @SerializedName("data") val data : List<Data>
)