package com.ashu.postermaker.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.ashu.postermaker.R
import com.ashu.postermaker.model_classes.FeatureModel
import com.ashu.postermaker.network.ImageHandler
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception
import android.util.Log
import com.ashu.postermaker.App


class FeaturedDetailActivity : AppCompatActivity(), Target {

    //internal var baseURL = "http://najamchaudhry.com/poster/public/images/poster/home_screen/"

    lateinit var progressBar: ProgressBar
    lateinit var image: ImageView
    lateinit var featureModel: FeatureModel

    private var lookUI: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.ashu.postermaker.R.layout.activity_featured_detail)

        val postID = intent.extras.get("id") as String
        val path = intent.extras.get("path") as String

        progressBar = findViewById(com.ashu.postermaker.R.id.progressBar)
        image = findViewById(com.ashu.postermaker.R.id.temp_view)


        lookUI = true


//        ImageHandler.getHandler(this).load(baseURL +path).placeholder( com.ashu.postermaker.R.drawable.progress_animation )
//                . into(image, object: Callback{
//                    override fun onSuccess() {
//                        lookUI = false
//                    }
//
//                    override fun onError(e: Exception?) {
//                        lookUI = true
//                    }
//
//                });

        val mTarget: Target = this
        ImageHandler.getHandler(this).load(path).placeholder(R.drawable.progress_animation)
                .into(mTarget)
//        ImageHandler.getHandler(this).load(baseURL +path).placeholder( R.drawable.progress_animation )
//                . into(mTarget)

    }


    public fun onEditClick(v: View) {
        if (lookUI) {
            return
        }
        startActivity(Intent(this, EditImage::class.java))
    }


    public fun onCancelClick(v: View) {
        finish()
    }


    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        lookUI = true
        e?.printStackTrace()
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        lookUI = false

        image.setImageBitmap(bitmap)
        if (bitmap != null) {
            App.instance.editingImageBitmap = bitmap!!
        }

    }


}
