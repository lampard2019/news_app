package com.vsee.newsapp.main.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.vsee.newsapp.R
import com.vsee.newsapp.main.home.model.Article
import kotlinx.android.synthetic.main.activity_detail.*


@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val article = intent.getSerializableExtra(EXTRA_DATA) as Article

        supportActionBar?.apply {
            setTitle(article.title)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        webview_content.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

        webview_content.settings.setAppCacheEnabled(true)
        webview_content.webViewClient = WebViewClient()
        webview_content.webChromeClient = object:WebChromeClient(){
            override fun onProgressChanged(view: WebView?, progress: Int) {
                super.onProgressChanged(view, progress)
                if (progress < 80) {
                    loading_progressbar.visibility = View.VISIBLE
                }
                if (progress >= 80) {
                    loading_progressbar.visibility = View.GONE
                }
            }
        }
        webview_content.loadUrl(article.url)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"

        fun newInstance(
            context: Context,
            article: Article
        ): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_DATA, article)
            return intent
        }
    }
}