package com.vsee.newsapp.repository

import android.util.Log
import com.vsee.newsapp.dao.ArticleDao
import com.vsee.newsapp.main.home.model.Article
import com.vsee.newsapp.service.ArticleService
import com.vsee.newsapp.service.Constants
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class ArticleRepository(
    private val articleService: ArticleService,
    private val articleDao: ArticleDao
) {


    fun getTopHeadline(): Single<List<Article>> {
        return Single.zip(
            getArticleFromApi(),
            getArticleFromDb(),
            BiFunction { apiList: List<Article>, cachedList: List<Article> ->
                val result = arrayListOf<Article>()
                if (apiList.isNotEmpty()) {
                    result.addAll(apiList)
                    Log.d("VSEE", "Get data from api ${apiList.size}")
                } else if (cachedList.isNotEmpty()) {
                    result.addAll(cachedList)
                    Log.d("VSEE", "Get data from cached ${cachedList.size}")
                }
                return@BiFunction result
            })
    }

    private fun getArticleFromDb(): Single<List<Article>> {
        return articleDao.getAllArticles()
            .onErrorResumeNext { Single.just(listOf()) }
    }

    private fun getArticleFromApi(): Single<List<Article>> {
        val map = mutableMapOf<String, String>()
        map["sources"] = "bbc-sport"
        map["apiKey"] = Constants.API_KEY
        return articleService
            .getTopHeadline(map)
            .map { it.articles }
            .doOnSuccess { saveArticleInDb(it) }
            .onErrorResumeNext { Single.just(listOf()) }
    }

    private fun saveArticleInDb(articles: List<Article>) {

        if (articles.isEmpty()) return
        articleDao.deleteAll()

        articleDao.insertAll(articles)
            .onErrorResumeNext { Single.just(listOf()) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe { data ->
                Log.d("VSEE", "Inserted ${articles.size} articles from API in DB...")
            }
    }

}