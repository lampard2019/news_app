package com.vsee.newsapp.service

import com.vsee.newsapp.main.home.model.ArticleResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ArticleService {

    @GET("top-headlines")
    fun getTopHeadline(@QueryMap params: Map<String, String>): Single<ArticleResponse>

}