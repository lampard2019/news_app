package com.vsee.newsapp.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.storage.StorageManager
import androidx.annotation.RequiresApi
import com.vsee.newsapp.App
import java.lang.reflect.Method
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


object Utils {
    //Reformat
    fun reformatDate(timeStamp: String): String {
        return try {
            val date1 = timeStamp2Date(timeStamp)
            val df2: DateFormat = SimpleDateFormat("hh:mm aa MMMM dd, yyyy")
            df2.format(date1)
        } catch (e: Exception) {
            e.printStackTrace()
            timeStamp
        }
    }
    //String to date
    fun timeStamp2Date(timeStamp: String): Date? {
        return try {
            val df1: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSX")
            df1.parse(timeStamp)
        } catch (e: Exception) {
            null
        }
    }

    fun isToday(date: Date): Boolean {
        val today = Date()
        val fmt = SimpleDateFormat("yyyyMMdd")
        return fmt.format(date) == fmt.format(today)
    }

    fun myGetExternalStorageDir(): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getPrimaryStorageVolumeForAndroid11AndAbove()
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            getPrimaryStorageVolumeBeforeAndroid11()
        }else{
            Environment.getExternalStorageDirectory().absolutePath
        }
    }

    @TargetApi(Build.VERSION_CODES.R)
    private fun getPrimaryStorageVolumeForAndroid11AndAbove(): String? {
        val myStorageManager = App.shared().getSystemService(Context.STORAGE_SERVICE) as StorageManager
        val mySV = myStorageManager.primaryStorageVolume
        return mySV.getDirectory()?.getPath()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getPrimaryStorageVolumeBeforeAndroid11(): String {
        var volumeRootPath = ""
        val myStorageManager = App.shared().getSystemService(Context.STORAGE_SERVICE) as StorageManager
        val mySV = myStorageManager.primaryStorageVolume
        var storageVolumeClazz: Class<*>? = null
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume")
            val getPath: Method = storageVolumeClazz.getMethod("getPath")
            volumeRootPath = getPath.invoke(mySV) as String
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return volumeRootPath
    }

}