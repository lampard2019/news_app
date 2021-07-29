package com.vsee.newsapp

import com.vsee.newsapp.main.home.viewmodel.HomeViewModel
import com.vsee.newsapp.repository.ArticleRepository
import com.vsee.newsapp.service.ArticleService
import com.vsee.newsapp.service.RestService

object Injection {

    fun provideHomeViewModel(): HomeViewModel {
        val service = RestService.createService(ArticleService::class.java)
        val dao = App.shared().appDatabase.articleDao()
        val repository = ArticleRepository(service, dao)
        return HomeViewModel(repository)
    }


}