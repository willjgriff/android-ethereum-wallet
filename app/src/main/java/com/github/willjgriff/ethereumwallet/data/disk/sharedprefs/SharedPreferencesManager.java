package com.github.willjgriff.ethereumwallet.data.disk.sharedprefs;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Will on 03/04/2016.
 */
public class SharedPreferencesManager {

    private SharedPreferences mSharedPreferences;
    private Gson mGson;

    public SharedPreferencesManager(SharedPreferences sharedPreferences, Gson gson) {
        mSharedPreferences = sharedPreferences;
        mGson = gson;
    }

    public <T> void writeObjectToPreferences(String key, T value) {
        String json = mGson.toJson(value);
        mSharedPreferences.edit().putString(key, json).apply();
    }

    public <T> T readObjectFromPreferences(String key, Class<T> returnType) {
        return mGson.fromJson(mSharedPreferences.getString(key, ""), returnType);
    }

    public <T> T readComplexObjectFromPreferences(String key, TypeToken<T> typeToken) {
        return mGson.fromJson(mSharedPreferences.getString(key, ""), typeToken.getType());
    }

    public void removeObjectFromPreferences(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }
}
