package ru.startandroid.develop.financemanagment.retrofit

import android.os.Build
import android.preference.Preference
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


object RetrofitBuilder {
    private var authToken: String = ""
    val baseURL = "https://testrepobackend.herokuapp.com/"
    private val logger: HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val retrofitInstance: ApiService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        retrofit().create(
            ApiService::class.java
        )
    }
    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url
            .newBuilder()
            .build()


        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    fun getInstance() = retrofitInstance
    private val client =
        OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logger)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    private fun retrofit() =
        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    fun setToken(token: String) {
        this.authToken = token
    }

    fun getToken(): String = "Bearer $authToken"
}
