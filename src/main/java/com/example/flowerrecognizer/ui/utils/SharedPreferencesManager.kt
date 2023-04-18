package com.example.flowerrecognizer.ui.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by AralBenli on 18.04.2023.
 */
class SharedPreferencesManager {

    companion object {
        private var sp: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null

        private fun getInstance(context: Context): SharedPreferences {
            synchronized(this) {
                sp = context.getSharedPreferences("SharedPreferencesFile", Context.MODE_PRIVATE)
                return sp!!
            }
        }

        fun putBoolean(context: Context, key:String, value :Boolean){
            editor = getInstance(context).edit()
            editor!!.putBoolean(key, value)
            editor!!.apply()

        }

        fun getBoolean(context: Context, key: String, default: Boolean): Boolean {
            return getInstance(context).getBoolean(key, default)
        }


        fun putLong(context: Context, key: String, value: Long) {
            editor = getInstance(context).edit()
            editor!!.putLong(key, value)
            editor!!.apply()
        }

        fun getLong(context: Context, key: String, default: Long): Long {
            return getInstance(context).getLong(key, default)
        }

        fun putString(context: Context, key: String, value: String) {
            editor = getInstance(context).edit()
            editor!!.putString(key, value)
            editor!!.apply()
        }

        fun getString(context: Context, key: String, default: String): String {
            return getInstance(context).getString(key, default)!!
        }

        fun putInt(context: Context, key: String, value: Int) {
            editor = getInstance(context).edit()
            editor!!.putInt(key, value)
            editor!!.apply()
        }

        fun getInt(context: Context, key: String, default: Int): Int {
            return getInstance(context).getInt(key, default)
        }
    }

}