package com.ashu.postermaker.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ashu.postermaker.App
import com.ashu.postermaker.R
import com.ashu.postermaker.interfaces.CategoryListener
import com.ashu.postermaker.model_classes.SubCategory
import com.ashu.postermaker.network.ImageHandler
import com.squareup.picasso.Picasso
import java.lang.Exception


class SubCategoryAdapter(val itemList: ArrayList<SubCategory>, val delegate: CategoryListener?) : androidx.recyclerview.widget.RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>() {


    val IMAGE_BASE_URL:String = "http://najamchaudhry.com/poster/public/images/poster/category/"
    var list: MutableList<SubCategory> = ArrayList()


    fun add(category: SubCategory) {
        list.add(category)
        App.instance.mainThreadHandler.post { notifyDataSetChanged() }
    }

    fun addAll(categories: ArrayList<SubCategory>) {
        list.addAll(categories)
        App.instance.mainThreadHandler.post { notifyDataSetChanged() }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(p0.context)
                .inflate(R.layout.view_sub_category_list, p0, false)
        return ViewHolder(itemView)    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {

        val category = itemList[p1]
//        val path = IMAGE_BASE_URL + category.image
        val path = category.image
        val view = holder.txt_layer

        var lockUI = true

        Log.v("path1","path = "+path);
        ImageHandler.getHandler(App.getContext()).load(path).placeholder( R.drawable.image_place_holder )
                .into(object: com.squareup.picasso.Target {
                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

                        e?.printStackTrace()

                    }

                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                        lockUI = false
                        view.setImageBitmap(bitmap)
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable) {
                        lockUI = true
                    }
                })


        view.setOnClickListener{
            if (lockUI){
                return@setOnClickListener
            }
            delegate?.onItemClick(p1, itemList[p1] )
        }
    }


    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        lateinit var txt_layer: AppCompatImageView

        init {
            init(itemView)
        }

        private fun init(v: View) {
            txt_layer = v.findViewById(R.id.txt_layer)
        }
    }



}