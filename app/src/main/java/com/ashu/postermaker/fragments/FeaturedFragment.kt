package com.ashu.postermaker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ashu.postermaker.App
import com.ashu.postermaker.R
import com.ashu.postermaker.interfaces.FeatureListDelegate
import com.ashu.postermaker.model_classes.FeatureModel
import android.content.Intent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ashu.postermaker.activities.FeaturedDetailActivity
import com.ashu.postermaker.network.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FeaturedFragment : Fragment(), FeatureListDelegate {

    var fragment_view: View? = null
    private var recyclerView_feature: androidx.recyclerview.widget.RecyclerView? = null
    private var swipeRefresh: SwipeRefreshLayout? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragment_view = inflater.inflate(com.ashu.postermaker.R.layout.feature_fragment, container, false)

        //getting id's of view items(fragment featured)
        recyclerView_feature = fragment_view!!.findViewById<androidx.recyclerview.widget.RecyclerView>(com.ashu.postermaker.R.id.recyclerview_featured)
        swipeRefresh = fragment_view!!.findViewById(com.ashu.postermaker.R.id.swipeRefresh)
        recyclerView_feature!!.adapter = App.instance.homerSlider
        App.instance.homerSlider.notifyDataSetChanged()
        App.instance.homerSlider.setDelegate(this)

        swipeRefresh?.setOnRefreshListener {
            refresh()                    // refresh your list contents somehow
        }

        return fragment_view
    }


    override fun onItemClick(position: Int, model: FeatureModel?) {
        val intent = Intent(activity, FeaturedDetailActivity::class.java)
        intent.putExtra("id",""+model!!.id);
        intent.putExtra("path",""+model!!.image);
        startActivity(intent)

    }


    private fun refresh(){
        RetrofitFactory.makeRetrofitService().getFeaturedList().enqueue(object : Callback<ArrayList<FeatureModel>> {
            override fun onResponse(call: Call<ArrayList<FeatureModel>>, response: Response<ArrayList<FeatureModel>>) {
                if (!response.isSuccessful) {
                } else {
                    response.body()?.let {
                        if(it.size > 0){
                            App.instance.homerSlider.removeAll()
                            App.instance.homerSlider.addAll(it)
                        }

                        App.instance.homerSlider.notifyDataSetChanged()
                    }

                }
                swipeRefresh?.isRefreshing = false
            }

            override fun onFailure(call: Call<ArrayList<FeatureModel>>, t: Throwable) {
                swipeRefresh?.isRefreshing = false
                t.stackTrace
            }
        })

    }

}