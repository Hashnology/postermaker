package com.ashu.postermaker.interfaces

import com.ashu.postermaker.model_classes.Category
import com.ashu.postermaker.model_classes.FeatureModel
import com.ashu.postermaker.model_classes.SubCategory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitListener {

    @GET("home/screen/slider")
    fun getHomeScreenPosters(): Call<ArrayList<FeatureModel>>

    @GET("featured/post")
    fun getFeaturedList(): Call<ArrayList<FeatureModel>>

    @GET("featured/post/{id}")
    fun getFeatured(@Path("id") id: Int): Call<FeatureModel>


    @GET("categories/")
    fun getCategory(): Call<ArrayList<Category>>

    @GET("categories/post/{id}")
    fun getCategoryDetails(@Path("id") id: Int): Call<ArrayList<SubCategory>>

}