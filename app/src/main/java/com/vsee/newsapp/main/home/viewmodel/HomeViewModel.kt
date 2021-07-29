package com.vsee.newsapp.main.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vsee.newsapp.main.home.model.Article
import com.vsee.newsapp.repository.ArticleRepository
import com.vsee.newsapp.service.ResponseData
import com.vsee.newsapp.utils.disposedBy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val articleRepository: ArticleRepository) : ViewModel() {
    val compositeDisposable = CompositeDisposable()

    val topNewsList = MutableLiveData<ResponseData<List<Article>>>()

    fun getTopHeadline() {
        topNewsList.postValue(ResponseData.loading())
        articleRepository.getTopHeadline()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                if (data.isNotEmpty()) topNewsList.postValue(ResponseData.success(data))
            }, {
                topNewsList.postValue(ResponseData.error(it.message ?: "Unknown Error"))
            }).disposedBy(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}