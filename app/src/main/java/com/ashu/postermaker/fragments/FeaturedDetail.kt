package com.ashu.postermaker.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ashu.postermaker.App
import com.ashu.postermaker.R

class FeaturedDetail : Fragment(){

    var fragment_view:View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragment_view = inflater.inflate(R.layout.feature_fragment, container, false)


        return fragment_view;
    }
}