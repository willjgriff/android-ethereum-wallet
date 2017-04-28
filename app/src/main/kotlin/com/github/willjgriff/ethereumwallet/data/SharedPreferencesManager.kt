package com.github.willjgriff.ethereumwallet.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by williamgriffiths on 24/04/2017.
 */
class SharedPreferencesManager(private val sharedPreferences: SharedPreferences, private val gson: Gson) {

    val lock: Any = Any()

    fun <T> writeObjectToPreferences(key: String, value: T) {
        val json = gson.toJson(value)
        sharedPreferences.edit().putString(key, json).apply()
    }

    fun <T> readObjectFromPreferences(key: String, returnType: Class<T>): T? {
        return gson.fromJson(sharedPreferences.getString(key, ""), returnType)
    }

    fun <T> readComplexObjectFromPreferences(key: String, typeToken: TypeToken<T>): T? {
        return gson.fromJson<T>(sharedPreferences.getString(key, ""), typeToken.type)
    }

    fun removeObjectFromPrefs(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}