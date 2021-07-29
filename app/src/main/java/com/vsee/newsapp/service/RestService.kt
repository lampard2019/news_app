package com.vsee.newsapp.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object RestService {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.API_BASE_PATH)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(OkInterceptor.gsonConverter)
            .client(OkInterceptor.client)
            .build()
    }


    fun <T> createService(interfaceClazz: Class<T>): T {
        return retrofit.create(interfaceClazz)
    }


}