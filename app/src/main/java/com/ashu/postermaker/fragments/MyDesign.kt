package com.ashu.postermaker.fragments

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.AppCompatImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ashu.postermaker.Constants

import com.ashu.postermaker.R
import com.ashu.postermaker.utility.ImageHandler


class MyDesign : Fragment(), AdapterView.OnItemClickListener {

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Toast.makeText(context, "Click",Toast.LENGTH_SHORT).show()
    }

    private var fragment_view: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragment_view = inflater.inflate(R.layout.mydesign_fragment, container, false)


        val list= ImageHandler.loadAllImagesFromAppStorage(Constants.USER_EDIT_IMAGE_DIR)
        val gird  = fragment_view!!.findViewById<GridView>(com.ashu.postermaker.R.id.subCategoryGrid)
        val progressbar  = fragment_view!!.findViewById<ProgressBar>(com.ashu.postermaker.R.id.progressbar)
//        progressbar.visibility = View.GONE

        val text  = fragment_view!!.findViewById<TextView>(com.ashu.postermaker.R.id.lblError)

        val adapter = MyDesignGridAdapter(list)
        gird.adapter= adapter
        if (list.size<= 0){
            text.text = "No design found!"
        }
        return fragment_view
    }


    private inner class MyDesignGridAdapter(val list: MutableList<Bitmap>) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View?
            val vh: ListRowHolder
            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.view_sub_category_grid_list, parent, false)
                vh = ListRowHolder(view)
                view.tag = vh
            } else {
                view = convertView
                vh = view.tag as ListRowHolder
            }

            vh.txt_layer.setImageBitmap(list[position])
            return view!!
        }

        override fun getItem(p0: Int): Any {
            return  list[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return list.size
        }


    }

    private class ListRowHolder(row: View?) {
        val txt_layer: AppCompatImageView

        init {
            this.txt_layer = row?.findViewById(R.id.txt_layer) as AppCompatImageView
        }
    }

}
