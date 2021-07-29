package com.vsee.newsapp.dao

import androidx.room.*
import com.vsee.newsapp.main.home.model.Article
import io.reactivex.Single

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
    fun getAllArticles(): Single<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<Article>): Single<List<Long>>

    @Query("DELETE FROM article")
    fun deleteAll()

}


