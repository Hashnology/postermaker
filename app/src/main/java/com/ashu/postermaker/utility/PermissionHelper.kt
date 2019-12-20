package com.ashu.postermaker.utility

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import android.util.Log

import java.util.ArrayList
import java.util.Arrays

object PermissionHelper {

    fun isPermissionGranted(context: Context, permissionName: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        val res = context.checkSelfPermission(permissionName)
        return res == PackageManager.PERMISSION_GRANTED
    }

    fun isAllPermissionGranted(context: Context, list: List<String>): Boolean {
        for (i in list.indices) {
            if (!isPermissionGranted(context, list[i])) {
                return false
            }
        }
        return true
    }

    fun isAllPermissionGranted(context: Context, permissions: Array<String>): Boolean {
        val list = ArrayList(Arrays.asList(*permissions)) //new ArrayList is only needed if you absolutely need an ArrayList
        return isAllPermissionGranted(context, list)
    }


    fun getListOfDeniedPermission(context: Context, list: ArrayList<String>): ArrayList<String> {
        val newList = ArrayList<String>()
        for (i in list.indices) {
            if (!isPermissionGranted(context, list[i])) {
                newList.add(list[i])
            }
        }
        return newList
    }

    fun requestThesePermission(activity: Activity, permission: String, permissionTag: Int) {
        requestThesePermission(activity, arrayOf(permission), permissionTag)
    }

    fun requestThesePermission(activity: Activity, permissions: Array<String>, permissionTag: Int) {
        ActivityCompat.requestPermissions(activity, permissions, permissionTag)
    }

    fun canAskAgainThis(activity: Activity, permission: String): Boolean {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            return false
        }
        return true
    }

    fun canAnyAskAgain(activity: Activity, permission: Array<String>): Boolean {
        for (i in permission.indices) {
            if (canAskAgainThis(activity, permission[i])) {
                Log.v("permission_message", "can take this again = " + permission[i])
                return true
            }
        }
        Log.v("permission_message", "false")
        return false
    }

    fun permissionRelatedToStorage(permission: String?): Boolean {
        if (permission == null || permission.isEmpty()){
            return false;
        }
        if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) || permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)){
            return true;
        }
        return false;
    }
}
