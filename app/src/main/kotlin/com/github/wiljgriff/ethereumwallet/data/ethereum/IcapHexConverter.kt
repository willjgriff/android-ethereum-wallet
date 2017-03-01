package com.github.wiljgriff.ethereumwallet.data.ethereum

import java.math.BigInteger

/**
 * Created by Will on 01/03/2017.
 */
class IcapHexConverter {

    fun base16ToBase36(base16: String): String {
        val base16BigInt = BigInteger(base16, 16)
        return base16BigInt.toString(36).toLowerCase()
    }

    fun base36ToBase16(base36: String): String {
        val base36BigInt = BigInteger(base36, 36)
        return base36BigInt.toString(16).toLowerCase()
    }

    
}