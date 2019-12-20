package com.ashu.postermaker.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.ashu.postermaker.App

import com.ashu.postermaker.adapters.SettingAdapter
import com.ashu.postermaker.utility.AppUtility
import com.ashu.postermaker.utility.Util
import android.net.Uri
import android.util.Log
import com.ashu.postermaker.R
import com.ashu.postermaker.interfaces.SettingListListener
import kotlinx.android.synthetic.main.settings_fragment.*


class SettingsActivity : BaseActivity(), SettingListListener {
    override fun getClassName(): String {
        return  "SettingsActivity"
    }

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_fragment)
        setMyTitle("Settings")
        showBackButton()
        recyclerView = findViewById(R.id.settingList)
        recyclerView.addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(recyclerView.getContext(), androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL));
        val  adapter = SettingAdapter()
        adapter.setDelegate(this)
        recyclerView.adapter = adapter
//        adapter.notifyDataSetChanged()

    }


    private fun openPlayStore(){
        Util.openPlayStore(this, App.getContext().packageName)
    }

    private fun feedback(){
        val subject =  getString(com.ashu.postermaker.R.string.app_name)+" feedback"+"\n"+"Version "+AppUtility.getAppVersion()

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "azamiqbal888@gmail.com"))
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(""))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, "")
        startActivity(intent)
    }

    private fun shareApp(){
        try {

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Hi Friends, ")
            var shareMessage = "I am finding the "+getString(com.ashu.postermaker.R.string.app_name)+" App to make Flyer. Why don’t you try it out on your phone  "
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + App.getContext().packageName + "\n"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            //e.toString();
        }

    }

    override fun ontItemClick(pos: Int, title: String) {
        when (pos) {
            0 -> openPlayStore()
            1 -> feedback()
            2 -> shareApp()
        }

    }

}
