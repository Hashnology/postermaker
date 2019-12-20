package com.ashu.postermaker.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.ashu.postermaker.R
import com.ashu.postermaker.fragments.CreateLogoFragment.LogoGridAdapter as LogoGridAdapter1


class CreateLogoFragment : Fragment(), AdapterView.OnItemClickListener {

    private lateinit var fragment_view: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragment_view = inflater.inflate(R.layout.createlogo_fragment, container, false)

        var lblTitleLogo = fragment_view.findViewById<TextView>(R.id.lblTitleLogo)
        lblTitleLogo.text = "Logo"

        var adapter = LogoGridAdapter()

        var gridView = fragment_view.findViewById<GridView>(R.id.logoGridView)
        gridView.adapter = adapter

        gridView.onItemClickListener = this

        return fragment_view
    }

    override fun onItemClick(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val map:HashMap<String, Any> = adapter!!.getItemAtPosition(position) as HashMap<String, Any>

        var numberOfPixel:String = map["numberOfPixel"] as String

        numberOfPixel = numberOfPixel.replace("*","")

        Toast.makeText(context,"Create logo of "+numberOfPixel,Toast.LENGTH_SHORT).show()

    }

    private inner class LogoGridAdapter : BaseAdapter() {

        private var mapArrayList: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
        private val colorCodeList: ArrayList<Int> = ArrayList<Int>()
        init {
            colorCodeList.add(Color.GRAY)
            colorCodeList.add(Color.RED)
            colorCodeList.add(R.color.light_blue)
            colorCodeList.add(R.color.dark_blue)
            colorCodeList.add(R.color.purple)
            loadMap()
        }

        var numberOfPixel:String = "numberOfPixel"
        var name:String = "name"
        fun loadMap(){
            var map:HashMap<String, Any> = HashMap<String, Any>()
            map["name"] = "Logo"
            map["numberOfPixel"] = "1080"
            mapArrayList.add(map)

            map["numberOfPixel"] = "250"
            mapArrayList.add(map)
            map["numberOfPixel"] = "200"
            mapArrayList.add(map)
            map["numberOfPixel"] = "400"
            mapArrayList.add(map)
            map["numberOfPixel"] = "300"
            mapArrayList.add(map)
        }


        override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
            var vi = view
            var holder: ViewHolder? = null
            if (vi == null) {
                holder = ViewHolder()
                vi = layoutInflater.inflate(R.layout.view_logo_list, null)
                holder.backgroundView = vi.findViewById(R.id.view);
                holder.title = vi.findViewById(R.id.logoTitle);
                holder.logoPixel = vi.findViewById(R.id.logoPixel);

                vi.tag = holder
            } else {
                holder = vi.tag as ViewHolder?
            }

            val map = mapArrayList[position]

            holder!!.backgroundView!!.setBackgroundColor(colorCodeList[position])
            holder!!.title!!.text= map[name] as CharSequence?
            val numberOfPixel:CharSequence = map[numberOfPixel] as CharSequence
            holder!!.logoPixel!!.text= "$numberOfPixel*"
            return vi!!
        }

        override fun getItem(position: Int): Any {
           return mapArrayList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
           return mapArrayList.size
        }


        inner class ViewHolder {
            var backgroundView:View? = null
            var title:TextView? = null
            var logoPixel:TextView? = null
        }

    }

}
