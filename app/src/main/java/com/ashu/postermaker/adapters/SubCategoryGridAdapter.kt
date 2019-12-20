package com.ashu.postermaker.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.ashu.postermaker.App
import com.ashu.postermaker.R
import com.ashu.postermaker.interfaces.CategoryListener
import com.ashu.postermaker.model_classes.SubCategory
import com.ashu.postermaker.network.ImageHandler
import com.squareup.picasso.Picasso
import java.lang.Exception

class SubCategoryGridAdapter(val itemList:ArrayList<SubCategory>, val delegate: CategoryListener? ): BaseAdapter() {
    private val mInflator: LayoutInflater = LayoutInflater.from(App.getContext())

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View?
        val vh: ListRowHolder
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.view_sub_category_grid_list, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        val category = itemList[position]
        //val path = IMAGE_BASE_URL + category.image
        val path = category.image

        val imageView = vh.txt_layer
        var lockUI = true

        Log.v("path2","path = "+path);

        ImageHandler.getHandler(App.getContext()).load(path).placeholder( R.drawable.image_place_holder )
                .into(object: com.squareup.picasso.Target {
                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

                        e?.printStackTrace()

                    }

                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                        lockUI = false
                        imageView.setImageBitmap(bitmap)
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable) {
                    }
                })

        imageView.setOnClickListener {
            if (lockUI){
                return@setOnClickListener
            }

            delegate?.onItemClick(position, itemList[position])
        }

        return view!!
    }

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }

    private class ListRowHolder(row: View?) {
         val txt_layer: AppCompatImageView

        init {
            this.txt_layer = row?.findViewById(R.id.txt_layer) as AppCompatImageView
        }
    }

}

