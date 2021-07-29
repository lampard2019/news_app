package com.vsee.newsapp.dao

import androidx.room.*
import com.vsee.newsapp.main.home.model.Article

@Database(entities = arrayOf(Article::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}


