package com.ashu.postermaker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.ashu.postermaker.R
import kotlinx.android.synthetic.main.custom_toolbar.*

abstract class BaseActivity : AppCompatActivity() {

    abstract fun getClassName(): String

    private lateinit var lblTitle: TextView
    private lateinit var  btnBack: AppCompatImageView
    private lateinit var btnSetting: AppCompatImageView
    private lateinit var editImageViews: RelativeLayout

    lateinit var btnUndo: AppCompatImageView
    lateinit var btnRedo: AppCompatImageView
    lateinit var btnVisible: AppCompatImageView
    lateinit var btnLock: AppCompatImageView
    lateinit var btnExport: AppCompatImageView


    fun setMyTitle(title: String) {
        if (! ::lblTitle.isInitialized) {
            lblTitle = findViewById(R.id.lblTitle)
        }

//        if (lblTitle == null) {
//            lblTitle = findViewById(R.id.lblTitle)
//        }

        lblTitle!!.text = title
    }

    fun setMyTitle(titleId: Int) {
        if (! ::lblTitle.isInitialized) {
            lblTitle = findViewById(R.id.lblTitle)
        }

//        if (lblTitle == null) {
//            lblTitle = findViewById(R.id.lblTitle)
//        }
        lblTitle!!.setText(titleId)
    }

    fun showBackButton() {
        if (! ::btnBack.isInitialized) {
            btnBack = findViewById(R.id.btnBack)
        }

        btnBack!!.visibility = View.VISIBLE
        btnBack!!.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    fun showSettingButton() {
        if (! ::btnSetting.isInitialized) {
            btnSetting = findViewById(R.id.btnSetting)
        }

        btnSetting!!.visibility = View.VISIBLE
        btnSetting!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        })

    }

    fun hideTitle() {
        if (! ::lblTitle.isInitialized) {
            lblTitle = findViewById(R.id.lblTitle)
        }

        lblTitle!!.visibility = View.GONE
    }

    fun hideSetting() {
        if (! ::btnSetting.isInitialized) {
            btnSetting = findViewById(R.id.btnSetting)
        }

        btnSetting!!.visibility = View.GONE
    }

    fun showEditImageViews() {

        if (! ::editImageViews.isInitialized) {
            editImageViews = findViewById(R.id.editImageViews)
        }

        editImageViews!!.visibility = View.VISIBLE

        showVisibleIcon()
        showLockIcon()
        showUndoIcon()
        showRedoIcon()
        showExportImageIcon()
    }


    fun showVisibleIcon() {
        if (! ::btnVisible.isInitialized) {
            btnVisible = findViewById(R.id.btnVisible)
        }

        btnVisible!!.visibility = View.VISIBLE
    }

    fun showLockIcon() {
        if (! ::btnLock.isInitialized) {
            btnLock = findViewById(R.id.btnLock)
        }
        btnLock!!.visibility = View.VISIBLE
    }

    fun showUndoIcon() {
        if (! ::btnUndo.isInitialized) {
            btnUndo = findViewById(R.id.btnUndo)
        }
        btnUndo!!.visibility = View.VISIBLE
    }

    fun showRedoIcon() {
        if (! ::btnRedo.isInitialized) {
            btnRedo = findViewById(R.id.btnRedo)
        }
        btnRedo!!.visibility = View.VISIBLE
    }

    fun showExportImageIcon() {
        if (! ::btnExport.isInitialized) {
            btnExport = findViewById(R.id.btnExport)
        }
        btnExport!!.visibility = View.VISIBLE
    }



}