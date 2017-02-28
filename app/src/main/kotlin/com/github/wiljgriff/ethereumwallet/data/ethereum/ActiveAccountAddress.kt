package com.github.wiljgriff.ethereumwallet.data.ethereum

import android.content.SharedPreferences

/**
 * Created by williamgriffiths on 28/02/2017.
 */
class ActiveAccountAddress(val sharedPreferences: SharedPreferences) {

    val KEY_ACTIVE_ACCOUNT = javaClass.canonicalName + ";KEY_ACTIVE_ACCOUNT";

    fun get(): String = sharedPreferences.getString(KEY_ACTIVE_ACCOUNT, "")
    fun set(address: String) = sharedPreferences.edit().putString(KEY_ACTIVE_ACCOUNT, address).apply()
}
