package com.vsee.newsapp

import android.app.Application
import androidx.room.Room
import com.vsee.newsapp.dao.AppDatabase

class App : Application() {

    companion object {
        private lateinit var instance: App
        fun shared(): App {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "news-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}