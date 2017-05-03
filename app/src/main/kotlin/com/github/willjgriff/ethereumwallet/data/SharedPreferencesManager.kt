package com.github.willjgriff.ethereumwallet.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by williamgriffiths on 24/04/2017.
 */
class SharedPreferencesManager(private val sharedPreferences: SharedPreferences, private val gson: Gson) {

    fun <T> writeObjectToPreferences(key: String, value: T) {
        synchronized(this, {
            val json = gson.toJson(value)
            sharedPreferences.edit().putString(key, json).apply()
        })
    }

    fun <T> readObjectFromPreferences(key: String, returnType: Class<T>): T? {
        return synchronized(this, {
            val readObject = sharedPreferences.getString(key, "")
            gson.fromJson(readObject, returnType)
        })
    }

    fun <T> readComplexObjectFromPreferences(key: String, typeToken: TypeToken<T>): T? {
        return synchronized(this, {
            val readObject = sharedPreferences.getString(key, "")
            gson.fromJson<T>(readObject, typeToken.type)
        })
    }

    fun removeObjectFromPrefs(key: String) {
        synchronized(this, { sharedPreferences.edit().remove(key).apply() })
    }
}