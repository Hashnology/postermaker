package com.ashu.postermaker.adapters

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ashu.postermaker.R
import com.ashu.postermaker.interfaces.SettingListListener
import java.text.FieldPosition

class SettingAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<SettingAdapter.ViewHolder>(){

    lateinit var listener : SettingListListener

    private var titleList: MutableList<String> = arrayListOf()
    private var iconList: MutableList<Int> = arrayListOf()
    init {
        addListItemsAndIcon()
    }

    fun setDelegate(listener : SettingListListener){
        this.listener = listener
        Log.v("adapter_setting","setDelegate, size = "+(titleList.size))
    }

    private fun addListItemsAndIcon() {
        titleList.add("Rate Us")
        titleList.add("Feedback")
        titleList.add("Share App")
        iconList.add(R.drawable.ic_star);
        iconList.add(R.drawable.ic_feedback);
        iconList.add(R.drawable.ic_share);
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_setting_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        Log.v("adapter_setting","size = "+(titleList.size))

        return titleList.size

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val title = titleList[position]
        viewHolder?.containerView?.setOnClickListener { onItemClick(title, position) }

        viewHolder.title.text = title;
        viewHolder.icon.setImageResource(iconList.get(position));


    }

    private fun onItemClick(title: String, position: Int) {
        listener?.ontItemClick(position, title)
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        lateinit var icon: ImageView
        lateinit var title: TextView
        lateinit var containerView: LinearLayout

        init {
            init(itemView)
        }

        private fun init(v: View) {
            icon = v.findViewById(R.id.icon)
            title = v.findViewById(R.id.title)
            containerView = v.findViewById(R.id.containerView)
        }
    }


}