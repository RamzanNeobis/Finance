package ru.startandroid.develop.financemanagment.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import ru.startandroid.develop.financemanagment.models.*

interface ApiService {
    @Headers("Content-Type:application/json")
    @GET("counterParty/index")
    fun getCounterParty(): Call<List<CounterPartyResponce>>

    @Headers("Content-Type:application/json")
    @GET("operation/index")
    fun getOperations(): Call<List<OperationResponse>>

    @Headers("Content-Type:application/json")
    @GET("project")
    fun getProject(): Call<List<Project>>

    @Headers("Content-Type:application/json")
    @GET("score/index")
    fun getScores(): Call<List<Account>>

    @GET("User/GetUser")
    fun getUser(): Call<GetUser>

    @Headers("Content-Type:application/json")
    @POST("authenticate/register")
    fun register(@Body info: User) : retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("authenticate/login")
    fun signIn(@Body info: LoginRequest) : retrofit2.Call<TokenResponse>

    @Headers("Content-Type:application/json")
    @GET("financeactions/index")
    fun getTransaction(): Call<TransactionStory>


    @POST("transaction/create")
    fun createTransaction(@Body transaction: TransactionModel,@Header("Authorization") token: String) : retrofit2.Call<ResponseBody>


    @POST("remittance/create")
    fun createTransfer(@Body transfer: TransferModel, @Header("Authorization") token: String) : retrofit2.Call<ResponseBody>

    @PUT("transaction/edit")
    fun editTransaction(@Body transaction: EditTransactionModel,@Header("Authorization") token: String) : retrofit2.Call<ResponseBody>




}


//interface Api {
//
//    @Headers("Content-Type:application/json")
//    @POST(Constants.LOGIN_URL)
//
//    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
//
//
//    @Headers("Content-Type:application/json")
//    @POST(Constants.REGISTER_URL)
//
//    suspend fun register(@Body request: user): Response<ResponseBody>
//}