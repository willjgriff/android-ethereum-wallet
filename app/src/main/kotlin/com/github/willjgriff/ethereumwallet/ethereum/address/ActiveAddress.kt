package com.github.willjgriff.ethereumwallet.ethereum.address

import android.content.SharedPreferences

/**
 * Created by williamgriffiths on 28/02/2017.
 */
class ActiveAddress(private val sharedPreferences: SharedPreferences) {

    private val KEY_ACTIVE_ACCOUNT = ActiveAddress::class.qualifiedName + ";KEY_ACTIVE_ACCOUNT";
    private val DEFAULT_ADDRESS_VALUE = ""

    fun getHex(): String = sharedPreferences
            .getString(KEY_ACTIVE_ACCOUNT, DEFAULT_ADDRESS_VALUE)

    fun setHex(address: String) = sharedPreferences
            .edit()
            .putString(KEY_ACTIVE_ACCOUNT, address)
            .apply()

    fun deleteActiveAddress() = setHex(DEFAULT_ADDRESS_VALUE)

    fun hasActiveAddress(): Boolean = getHex() != DEFAULT_ADDRESS_VALUE
}
