package com.ashu.postermaker.fragments

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.ashu.postermaker.App
import com.ashu.postermaker.R
import com.ashu.postermaker.network.ImageHandler
import com.squareup.picasso.Picasso
import java.lang.Exception

class ImageViewFragment: Fragment() {
    lateinit var progressBar: ProgressBar
    lateinit var image: ImageView
    lateinit var btnCancel: ImageView
    private var lockUI:Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_image_view, container, false)

        val path = this.arguments!!.getString("path")

        progressBar = view.findViewById(R.id.progressBar)
        image = view.findViewById(R.id.temp_view)
        btnCancel = view.findViewById(R.id.btnCancel)

        btnCancel.setOnClickListener {
            if (lockUI){
                return@setOnClickListener
            }
            activity?.supportFragmentManager?.popBackStack();
        }



        ImageHandler.getHandler(App.getContext()).load(path).placeholder( R.drawable.image_place_holder )
                .into(object: com.squareup.picasso.Target {
                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                        lockUI = true
                        e?.printStackTrace()
                    }

                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                        lockUI = false
                        image.setImageBitmap(bitmap)
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable) {
                        lockUI = true
                    }
                })


        lockUI = true


        return  view
    }


    public fun onEditClick(v:View){

    }
}
