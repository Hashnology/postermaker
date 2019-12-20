package com.ashu.postermaker.activities

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.ashu.postermaker.App
import com.ashu.postermaker.R
import com.ashu.postermaker.model_classes.Category
import com.ashu.postermaker.model_classes.FeatureModel
import com.ashu.postermaker.network.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Collections.addAll

class SplashActivity : AppCompatActivity() {
    lateinit var splashView: LinearLayout
    lateinit var errorView: LinearLayout
    lateinit var progressBar: ProgressBar
    lateinit var lblError: TextView
    var handler: Handler? = null
    var gotFeatureResponse: Boolean = false
    var gotCategoryResponse: Boolean = false
    var data: ArrayList<Category>? = null


    var redirect: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash2)

        handler = Handler()

        if(redirect){
            App.instance.editingImageBitmap = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.test_image);
            startActivity(Intent(this, EditImageActivity::class.java))
            finish()
        }else{
            fetchDataFromServer()
        }

//        moveNextIfYouCan()
    }

    private fun fetchDataFromServer() {

        RetrofitFactory.makeRetrofitService().getFeaturedList().enqueue(object : Callback<ArrayList<FeatureModel>> {
            override fun onResponse(call: Call<ArrayList<FeatureModel>>, response: Response<ArrayList<FeatureModel>>) {
                gotFeatureResponse = true
                if (!response.isSuccessful) {
                } else {
                    response.body()?.let { App.instance.homerSlider.addAll(it) }
                }
                moveNextIfYouCan()
            }

            override fun onFailure(call: Call<ArrayList<FeatureModel>>, t: Throwable) {
                gotFeatureResponse = true
                t.stackTrace
                moveNextIfYouCan()

            }
        })


        RetrofitFactory.makeRetrofitService().getCategory().enqueue(object : Callback<ArrayList<Category>> {
            override fun onFailure(call: Call<ArrayList<Category>>, t: Throwable) {
                gotCategoryResponse = true
                t.stackTrace
                moveNextIfYouCan()

            }

            override fun onResponse(call: Call<ArrayList<Category>>, response: Response<ArrayList<Category>>) {
                gotCategoryResponse = true
                if (!response.isSuccessful) {
                } else {
                    response.body()?.let {
                        App.instance.categoryAdapter.addAll(it) }
                }
                moveNextIfYouCan()
            }

        })

    }

    private fun moveNextIfYouCan() {
        Log.v("categorySize",""+App.instance.categoryAdapter.list.size);
        if (gotCategoryResponse && gotFeatureResponse) {
            handler!!.post(Runnable {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            })
        }
    }

    var posts: ArrayList<FeatureModel>? = null

    fun newFun() {

        for (i in this.data!!) {
            RetrofitFactory.makeRetrofitService().getFeatured(i.id).enqueue(object : Callback<FeatureModel> {
                override fun onFailure(call: Call<FeatureModel>, t: Throwable) {

                }

                override fun onResponse(call: Call<FeatureModel>, response: Response<FeatureModel>) {
                    if(response.isSuccessful){
                        val posts = response.body()
//                        posts.add()
                    }
                }

            })
        }
    }

}
