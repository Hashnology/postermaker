package com.ashu.postermaker.utility

import android.text.method.TextKeyListener.clear
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE
import android.provider.SyncStateContract
import com.ashu.postermaker.App




object PreferenceHelper {

    private val SHARED_PREFERENCES_NAME = "Flyer_Preference"
    private var sharedPreferences: SharedPreferences = App.instance.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)


    private fun getEditor(): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    fun save(key: String, value: Any?) {// use "saveDoubleVal" method of util class for saving value of double datatype instead of this
        val editor = getEditor()
        if (value is Boolean) {
            editor.putBoolean(key, value as Boolean)
        } else if (value is Int) {
            editor.putInt(key, value)
        } else if (value is Float) {
            editor.putFloat(key, value as Float)
        } else if (value is Long) {
            editor.putLong(key, value as Long)
        } else if (value is String) {
            editor.putString(key, value as String?)
        } else if (value is Enum<*>) {
            editor.putString(key, value.toString())
        } else if (value != null) {
            throw RuntimeException("Attempting to save non-supported preference")
        }

        editor.commit()
    }

    operator fun <T> get(key: String): T {
        return sharedPreferences.getAll().get(key) as T
    }

    operator fun <T> get(key: String, defValue: T): T { // use "getDoubleValFromPreference" method of util class for getting value of double datatype
        val returnValue = sharedPreferences.getAll().get(key) as T
        return returnValue ?: defValue
    }


    fun delete(key: String) {
        if (sharedPreferences.contains(key)) {
            getEditor().remove(key).commit()
        }
    }

    fun has(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    fun clearAllData() {
        val editor = getEditor()
        editor.clear().commit()
    }


}