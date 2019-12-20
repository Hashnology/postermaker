package com.ashu.postermaker.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ashu.postermaker.App
import com.ashu.postermaker.R
import com.ashu.postermaker.activities.SubCategoryActivity
import com.ashu.postermaker.interfaces.CategoryListener
import com.ashu.postermaker.model_classes.Category
import com.ashu.postermaker.model_classes.SubCategory

class CategoryFragment : Fragment(), CategoryListener {

    private var fragment_view: View? = null

    private var categoryList: androidx.recyclerview.widget.RecyclerView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragment_view = inflater.inflate(R.layout.categories_fragment, container, false)

        categoryList = fragment_view!!.findViewById(R.id.categoryList)
        categoryList!!.adapter = App.instance.categoryAdapter

        val size:Int = App.instance.categoryAdapter.list.size
        App.instance.categoryAdapter.setDelegate(this)
        App.instance.categoryAdapter.notifyDataSetChanged()

        Log.v("categorySize",""+size)
        return fragment_view
    }




    override fun onItemClick(position: Int, model: SubCategory) {

        val bundle = Bundle()
//        bundle.putString("path", subPath+model.image)
        bundle.putString("path", model.image)
        val fragInfo = ImageViewFragment()
        fragInfo.arguments = bundle

//        val fragmentTransaction = mFragmentManager.beginTransaction()
//        fragmentTransaction.addToBackStack("ImageViewFragment");

        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragInfo)
                .commit()

    }

    override fun onSeeAllClick(position: Int, model: Category) {
        val intent = Intent(context, SubCategoryActivity::class.java)
        intent.putExtra("categoryID", model.id)
        intent.putExtra("title", model.name)
        startActivity(intent)
    }

}
