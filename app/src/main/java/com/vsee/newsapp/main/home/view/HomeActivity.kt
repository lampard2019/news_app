package com.vsee.newsapp.main.home.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsee.newsapp.Injection
import com.vsee.newsapp.R
import com.vsee.newsapp.main.detail.DetailActivity
import com.vsee.newsapp.service.Status
import com.vsee.newsapp.utils.Utils
import kotlinx.android.synthetic.main.activity_home.*
import listener.UpdateDownloadListener
import model.UpdateConfig
import update.UpdateAppUtils

class HomeActivity : AppCompatActivity() {

    private var adapter = ArticleAdapter()
    private val viewModel by lazy { Injection.provideHomeViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        swipe_refresh.setOnRefreshListener {
            Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()
            viewModel.getTopHeadline()
        }

        adapter.onClickListener = { position, item ->
            startActivity(DetailActivity.newInstance(this, item))
        }

        recycler_view.layoutManager =  LinearLayoutManager(this)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)

        viewModel.topNewsList.observe(this, Observer { responseData ->
            when (responseData.status) {
                Status.SUCCESS -> {
                    loading_progressbar.visibility = View.GONE
                    responseData.data?.let { list ->
                        if(list.isNotEmpty()){
                            recycler_view.visibility = View.VISIBLE
                            txt_no_data.visibility = View.GONE
                            adapter.updateData(list)
                        }else{
                            recycler_view.visibility = View.GONE
                            txt_no_data.visibility = View.VISIBLE
                        }
                    }
                    if (swipe_refresh.isRefreshing) swipe_refresh.isRefreshing = false
                }
                Status.LOADING -> {
                    if (!swipe_refresh.isRefreshing) {
                        loading_progressbar.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(this, responseData.message, Toast.LENGTH_SHORT).show()
                    loading_progressbar.visibility = View.GONE
                    if (swipe_refresh.isRefreshing) swipe_refresh.isRefreshing = false
                }
            }
        })

        viewModel.getTopHeadline()
//        showUpdate()
    }

    private fun showUpdate() {

        val appDownUrl = "https://github.com/lampard-2020/news_app/raw/master/app-v10.apk"
        val updateConfig = UpdateConfig().apply {
            force = false
            isShowNotification = true
            notifyImgRes = R.drawable.ic_update_logo
            apkSavePath = Utils.myGetExternalStorageDir() ?: ""
            apkSaveName = "c66_app"
        }

        UpdateAppUtils
            .getInstance()
            .apkUrl(appDownUrl)
            .updateTitle("Update App")
            .updateContent("Content")
            .updateConfig(updateConfig)
            .setUpdateDownloadListener(object : UpdateDownloadListener {
                // do something
                override fun onDownload(progress: Int) {
                    Log.d("Lampard", "progress " + progress)
                }

                override fun onError(e: Throwable) {
                    Log.d("Lampard", "onError e " + e.message)
                }

                override fun onFinish() {
                    Log.d("Lampard", "onFinish ")

                }

                override fun onStart() {
                    Log.d("Lampard", "onStart ")
                }
            })
            .update()

    }
}