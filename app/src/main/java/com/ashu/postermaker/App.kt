package com.ashu.postermaker

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import com.ashu.postermaker.adapters.CategoryAdapter
import com.ashu.postermaker.adapters.FeaturedAdapter
import com.ashu.postermaker.universal.AppController

class App : AppController() {

    lateinit var mainThreadHandler: Handler

    lateinit var homerSlider: FeaturedAdapter
    lateinit var categoryAdapter: CategoryAdapter

    lateinit var editingImageBitmap: Bitmap


    override fun onCreate() {
        super.onCreate()
        instance = this
        mainThreadHandler = Handler()

        homerSlider = FeaturedAdapter()
        categoryAdapter = CategoryAdapter()
    }

    override fun onTerminate() {
        super.onTerminate()
    }


    companion object{
        lateinit var instance: App

        fun getContext(): Context {
            return instance
        }
    }


}
