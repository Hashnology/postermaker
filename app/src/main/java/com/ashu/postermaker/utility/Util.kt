package com.ashu.postermaker.utility

import android.content.Intent
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.util.DisplayMetrics
import com.ashu.postermaker.App


class Util {

    companion object{
        fun openPlayStore(context: Activity, appPackageName: String) {
            try {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
            } catch (anfe: android.content.ActivityNotFoundException) {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=$appPackageName")))
            }

        }

        fun convertDpToPixel(dp: Float, context: Context?): Float {
            return if (context != null) {
                val resources = context.resources
                val metrics = resources.displayMetrics
                dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
            } else {
                val metrics = Resources.getSystem().displayMetrics
                dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
            }
        }


        fun convertPixelsToDp(px: Float, context: Context?): Float {
            return if (context != null) {
                val resources = context.resources
                val metrics = resources.displayMetrics
                px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
            } else {
                val metrics = Resources.getSystem().displayMetrics
                px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
            }
        }

    }


}