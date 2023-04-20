package com.example.birdRecognizer.app

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by AralBenli on 20.04.2023.
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
        fun putBoolean(context: Context, key: String, value: Boolean) {
            editor = getInstance(context).edit()
            editor!!.putBoolean(key, value)
            editor!!.apply()
        }

        fun getBoolean(context: Context, key: String, default: Boolean): Boolean {
            return getInstance(context).getBoolean(key, default)
        }
    }
}