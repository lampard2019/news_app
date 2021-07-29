package com.vsee.newsapp.service

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit
import com.vsee.newsapp.BuildConfig

object OkInterceptor {
    private var mClient: OkHttpClient? = null
    private var mGsonConverter: GsonConverterFactory? = null
    private const val TIMEOUT_TIME = 20L
    private const val AUTHORIZATION = "Authorization"
    private const val ROUTER_AUTHORIZATION = "Access-Token"
    private const val APPLICATION_JON = "application/json"

    val client: OkHttpClient
        @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
        get() {
            if (mClient == null) {
                val httpBuilder = OkHttpClient.Builder()
                httpBuilder
                    .connectTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
                if (BuildConfig.DEBUG) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    httpBuilder.addInterceptor(interceptor)
                }

                mClient = httpBuilder.build()

            }
            return mClient!!
        }

    val gsonConverter: GsonConverterFactory
        get() {
            if (mGsonConverter == null) {
                mGsonConverter = GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .setLenient()
                            .disableHtmlEscaping()
                            .create()
                    )
            }
            return mGsonConverter!!
        }
}