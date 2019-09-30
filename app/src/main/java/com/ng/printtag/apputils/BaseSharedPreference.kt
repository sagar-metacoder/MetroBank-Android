package com.ng.printtag.apputils

import android.annotation.SuppressLint
import android.content.Context
import com.ng.printtag.R

/**
 * This class used for data store and receive from local device
 */
open class BaseSharedPreference private constructor(context: Context) {

    private val sharedPref: android.content.SharedPreferences
    private val mEdit: android.content.SharedPreferences.Editor

    companion object {
        private var INSTANCE: BaseSharedPreference? = null
        fun getInstance(context: Context): BaseSharedPreference {
            if (INSTANCE == null)
                INSTANCE = BaseSharedPreference(context)
            return INSTANCE as BaseSharedPreference
        }
    }

    init {
        val suffix = "_preferences"
        sharedPref = context.getSharedPreferences(context.packageName + suffix, Context.MODE_PRIVATE)
        mEdit = sharedPref.edit()
        mEdit.apply()
    }

    @SuppressLint
    fun clear(key: String) {
        mEdit.remove(key).apply()
    }


    @SuppressLint
    fun clearAll() {
        mEdit.clear().apply()
    }

    fun putValue(key: String, value: Any) {
        val editor = sharedPref.edit()
        when (value) {
            is String -> editor.putString(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Int -> editor.putInt(key, value)
            is Float -> editor.putFloat(key, value)
        }
        editor.apply()


    }

    fun getPrefValue(value: String): String? {
        return sharedPref.getString(value, "")
    }

    fun getLanguage(key: String): String {
        return sharedPref.getString(key, "en")!!
    }
    fun getPrefBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    @SuppressLint
    fun getPrefInt(key: String): Int {
        return sharedPref.getInt(key, 0)
    }

    @SuppressLint
    fun getPrefFloat(key: String): Float {
        return sharedPref.getFloat(key, 0f)
    }

    /*fun getProsperaId(context: Context): String {
        return sharedPref.getString(context.getString(R.string.pref_user_northgate_id), "")
    }

    fun putProsperaId(context: Context, northGateId: String) {
        putValue(context.getString(R.string.pref_user_northgate_id), northGateId)
    }

    fun getAssociateId(context: Context): String {
        return sharedPref.getString(context.getString(R.string.pref_associate_id), Constant.ASSOCIATE_ID)
    }

    fun putAssociateId(context: Context, associateId: String) {
        putValue(context.getString(R.string.pref_associate_id), associateId)
    }*/

}