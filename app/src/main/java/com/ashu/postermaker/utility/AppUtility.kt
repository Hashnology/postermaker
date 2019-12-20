package com.ashu.postermaker.utility

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.ashu.postermaker.App


class AppUtility {

    companion object{
        fun getAppVersion():String{
            var version = ""
            try {
                val pInfo = App.getContext().getPackageManager().getPackageInfo(App.getContext().getPackageName(), 0)
                 version = pInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return  version
        }
    }
}