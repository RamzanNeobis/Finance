package ru.startandroid.develop.financemanagment.models

import com.google.gson.annotations.SerializedName

data class Project(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val nameProject: String,
    @SerializedName("income")
    val sumIncome: Int,
    @SerializedName("expense")
    val sumExpense: Int,
    @SerializedName("profit")
    val profit: Int


)