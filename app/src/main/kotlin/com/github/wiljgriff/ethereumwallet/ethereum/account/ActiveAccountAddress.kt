package com.github.wiljgriff.ethereumwallet.ethereum.account

import android.content.SharedPreferences

/**
 * Created by williamgriffiths on 28/02/2017.
 */
class ActiveAccountAddress(private val sharedPreferences: SharedPreferences) {

    private val KEY_ACTIVE_ACCOUNT = ActiveAccountAddress::class.qualifiedName + ";KEY_ACTIVE_ACCOUNT";
    private val DEFAULT_ADDRESS_VALUE = ""

    fun get() = sharedPreferences
            .getString(KEY_ACTIVE_ACCOUNT, DEFAULT_ADDRESS_VALUE)

    fun set(address: String) = sharedPreferences
            .edit()
            .putString(KEY_ACTIVE_ACCOUNT, address)
            .apply()

    fun deleteActiveAddress() {
        set(DEFAULT_ADDRESS_VALUE)
    }

    fun hasActiveAddress(): Boolean {
        return get() != DEFAULT_ADDRESS_VALUE
    }
}
