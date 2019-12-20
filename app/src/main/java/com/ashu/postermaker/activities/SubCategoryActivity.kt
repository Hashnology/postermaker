package com.ashu.postermaker.activities

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import android.util.Log
import android.view.View
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView
import com.ashu.postermaker.R
import com.ashu.postermaker.adapters.SubCategoryGridAdapter
import com.ashu.postermaker.fragments.ImageViewFragment
import com.ashu.postermaker.interfaces.CategoryListener
import com.ashu.postermaker.model_classes.Category
import com.ashu.postermaker.model_classes.SubCategory
import com.ashu.postermaker.network.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubCategoryActivity : BaseActivity(), CategoryListener {
    override fun getClassName(): String {
        return  "SubCategoryActivity"
    }

    private lateinit var mFragmentManager: FragmentManager
    private lateinit var subCategoryGrid:GridView
    lateinit var progressbar:ProgressBar
    lateinit var lblError:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        showBackButton()
        showSettingButton()
        var title:String = intent.getStringExtra("title")
        title = title ?: ""
        val categoryID:Int = intent.getIntExtra("categoryID",-1)
        setMyTitle(title)
        subCategoryGrid = findViewById(R.id.subCategoryGrid)
        progressbar = findViewById(R.id.progressbar)
        lblError = findViewById(R.id.lblError)


        mFragmentManager = this.supportFragmentManager

        downloadSubCategory(categoryID)


    }

    private fun downloadSubCategory(id: Int) {
        progressbar.visibility = View.VISIBLE

        RetrofitFactory.makeRetrofitService().getCategoryDetails(id).enqueue(object : Callback<ArrayList<SubCategory>> {
            override fun onFailure(call: Call<ArrayList<SubCategory>>, t: Throwable) {
                t.printStackTrace()
                progressbar.visibility = View.GONE
                lblError.visibility = View.VISIBLE
                lblError.text = "unable to connect"
            }

            override fun onResponse(call: Call<ArrayList<SubCategory>>, response: Response<ArrayList<SubCategory>>) {
                if(response.isSuccessful){
                    val list: ArrayList<SubCategory> = response.body()!!
                    Log.v("subCategory","size = "+list.size);

                    progressbar.visibility = View.GONE
                    val adapter = SubCategoryGridAdapter(list, this@SubCategoryActivity)
                    subCategoryGrid.adapter = adapter
                    adapter.notifyDataSetChanged()

                    if (list.size <= 0){
                        lblError.visibility = View.VISIBLE
                        lblError.text = "No data found"

                    }
                }
            }

        })
    }


    override fun onItemClick(position: Int,  model: SubCategory) {

        val bundle = Bundle()
//        bundle.putString("path", subPath+model.image)
        bundle.putString("path", model.image)
        val fragInfo = ImageViewFragment()
        fragInfo.arguments = bundle

        val fragmentTransaction = mFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack("ImageViewFragment");
        fragmentTransaction.add(R.id.frameView, fragInfo)
        fragmentTransaction.commit()
        mFragmentManager.executePendingTransactions();
    }

    override fun onSeeAllClick(position: Int, model: Category) {

    }


    override fun onBackPressed() {
        if (mFragmentManager.backStackEntryCount > 0)
            mFragmentManager.popBackStackImmediate()
        else
            super.onBackPressed()
    }
}
