package ru.startandroid.develop.util

import com.google.gson.GsonBuilder
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OAuthInterceptor(private val tokenType: String, private val acceessToken: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", "$tokenType $acceessToken").build()

        return chain.proceed(request)
    }
}

