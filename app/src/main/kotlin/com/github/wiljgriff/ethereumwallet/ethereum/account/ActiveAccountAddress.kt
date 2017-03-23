package com.github.wiljgriff.ethereumwallet.ethereum.account

import android.content.SharedPreferences

/**
 * Created by williamgriffiths on 28/02/2017.
 */
class ActiveAccountAddress(private val sharedPreferences: SharedPreferences) {

    val KEY_ACTIVE_ACCOUNT = javaClass.canonicalName + ";KEY_ACTIVE_ACCOUNT";

    fun get() = sharedPreferences
            .getString(KEY_ACTIVE_ACCOUNT, "")

    fun set(address: String) = sharedPreferences
            .edit()
            .putString(KEY_ACTIVE_ACCOUNT, address)
            .apply()
}
