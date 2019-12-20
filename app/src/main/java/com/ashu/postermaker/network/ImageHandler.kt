package com.ashu.postermaker.network

import android.content.Context
import com.ashu.postermaker.interfaces.RetrofitListener
import com.squareup.picasso.Cache
import com.squareup.picasso.Picasso
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.Executors

object ImageHandler {

    fun getHandler(context: Context): Picasso {
        return  Picasso.Builder(context).executor(Executors.newSingleThreadExecutor()).indicatorsEnabled(true).build()
    }
}