package com.ashu.postermaker.adapters

import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ashu.postermaker.App
import com.ashu.postermaker.R
import com.ashu.postermaker.model_classes.Category
import com.ashu.postermaker.adapters.SubCategoryAdapter as SubCategoryAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import com.ashu.postermaker.interfaces.CategoryListener
import com.ashu.postermaker.model_classes.SubCategory
import com.ashu.postermaker.network.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoryAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var list: MutableList<Category> = ArrayList()
    private var delegate: CategoryListener? = null
    private var downloadList:Boolean = false

    fun setDelegate(delegate: CategoryListener) {
        if (this.delegate != null) {
            this.delegate = null
        }
        this.delegate = delegate
    }

    fun add(category: Category) {
        list.add(category)
        App.instance.mainThreadHandler.post { notifyDataSetChanged() }
    }

    fun addAll(categories: ArrayList<Category>) {
        list.addAll(categories)
        App.instance.mainThreadHandler.post { notifyDataSetChanged() }
    }

    fun removeAll() {
        val size = list.size
        list.clear()
        App.instance.mainThreadHandler.post { notifyItemRangeRemoved(0, size) }
    }

    fun removeAt(index: Int) {
        if (index < 0 || index >= list.size) {
            return
        }
        list.removeAt(index)
        App.instance.mainThreadHandler.post {
            notifyItemRemoved(index)
            notifyItemRangeChanged(index, list.size)
        }
    }

    fun addAllCategories(categoryModel: Collection<Category>) {
        list.addAll(categoryModel)
        App.instance.mainThreadHandler.post { notifyDataSetChanged() }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_category_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {

        val model = list[i]

        holder.txt_see_all.setOnClickListener {
            delegate?.onSeeAllClick(i, model)
        }

        holder.txt_category_name.text = model.name
        holder.recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(App.getContext(), androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
        RetrofitFactory.makeRetrofitService().getCategoryDetails(model.id).enqueue(object : Callback<ArrayList<SubCategory>> {
            override fun onFailure(call: Call<ArrayList<SubCategory>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ArrayList<SubCategory>>, response: Response<ArrayList<SubCategory>>) {
                if(response.isSuccessful){
                    val list: ArrayList<SubCategory> = response.body()!!
                    Log.v("subCategory","size = "+list.size);
                    downloadList = true
                    holder.recycler.adapter = SubCategoryAdapter(list, delegate)
                }
            }

        })

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        lateinit var txt_category_name: AppCompatTextView
        lateinit var txt_see_all: AppCompatTextView
        lateinit var recycler: androidx.recyclerview.widget.RecyclerView

        init {
            init(itemView)
        }

        private fun init(v: View) {
            txt_see_all = v.findViewById(R.id.txt_see_all)
            recycler = v.findViewById(R.id.recycler)
            txt_category_name = v.findViewById(R.id.txt_category_name)
        }
    }
}
