package com.ashu.postermaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.ashu.postermaker.R

class LayersAdapter: RecyclerView.Adapter<LayersAdapter.ViewHolder>() {



    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(p0.context)
                .inflate(R.layout.view_layer_list, p0, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var layer_img: ImageView
        lateinit var layer_txt: TextView

        init {
            init(itemView)
        }

        private fun init(v: View) {
            layer_txt = v.findViewById(R.id.layer_txt)
            layer_img = v.findViewById(R.id.layer_img)
        }
    }



}