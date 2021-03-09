package ru.startandroid.develop.financemanagment.models

import com.google.gson.annotations.SerializedName

data class OperationResponse (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("operationTypes") val operationTypes : String,
	@SerializedName("operationTypeId") val operationTypeId : Int


)