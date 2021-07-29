package com.vsee.newsapp.main.home.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArticleResponse(
    @SerializedName("status")
    val status: String = "",
    @SerializedName("totalResults")
    val totalResults: Int = 0,
    @SerializedName("articles")
    val articles: List<Article> = listOf()
)

@Entity(tableName = "article")
data class Article(

    @SerializedName("url")
    @PrimaryKey
    @ColumnInfo(name = "url")
    val url: String = "",

    @SerializedName("source")
    @Embedded(prefix = "source_")
    val source: Source = Source(),

    @SerializedName("author")
    @ColumnInfo(name = "author")
    val author: String = "",

    @SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String = "",

    @SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String = "",

    @SerializedName("urlToImage")
    @ColumnInfo(name = "urlToImage")
    val urlToImage: String = "",

    @SerializedName("publishedAt")
    @ColumnInfo(name = "publishedAt")
    val publishedAt: String = "",

    @SerializedName("content")
    @ColumnInfo(name = "content")
    val content: String = ""
): Serializable

data class Source(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = ""
): Serializable
