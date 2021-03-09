package ru.startandroid.develop.financemanagment.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BasicAuthClient<T> {
    private val client =  OkHttpClient.Builder()
        .addInterceptor(OAuthInterceptor("Bearer", "---ACCESS---TOKEN---"))
        .build()

    val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.example.com")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun create(service: Class<T>): T {
        return retrofit.create(service)
    }
}