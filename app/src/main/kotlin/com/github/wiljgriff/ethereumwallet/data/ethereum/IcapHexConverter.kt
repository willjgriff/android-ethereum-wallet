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

    fun createChecksum(base36Value: String): Int {
        val bigInt = BigInteger(base36Value, 36)
        val baseMod97 = bigInt.mod(BigInteger("97", 10))
        val checkSum = 98 - baseMod97.toInt()
        return checkSum
    }

    fun verifyChecksum(base36Value: String, checkSum: Int): Boolean {
        if (checkSum < 1 || checkSum > 97) {
            return false
        }
        val checkSumString = convertCheckSumToString(checkSum)
        val bigInt = BigInteger(base36Value, 36)
        // Append checkSumString (base 10) to bigInt (base 36)
        return false
    }

    private fun convertCheckSumToString(checkSum: Int): String {
        var checkSumString = checkSum.toString()
        if (checkSum < 10) {
            checkSumString = "0" + checkSumString
        }
        return checkSumString
    }


}