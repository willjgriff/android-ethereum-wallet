package com.github.wiljgriff.ethereumwallet.data.ethereum.icap

import org.ethereum.geth.BigInt
import java.math.BigInteger

/**
 * Created by Will on 11/03/2017.
 */
class IbanChecksumUtils {

    val ICAP_XE_PREFIX = "XE"
    val IBAN_MUTIPLIER = "00"
    val IBAN_MOD_VALUE = "97"

    fun createChecksum(base36Value: String): Int {
        val valueWithXePrefix = base36Value + ICAP_XE_PREFIX
        val valueAsInt = convertBase36ToInteger(valueWithXePrefix)
        val valueTimes100 = valueAsInt + IBAN_MUTIPLIER
        val valueMod97 = BigInteger(valueTimes100).mod(BigInteger(IBAN_MOD_VALUE))
        val checksum = 98 - valueMod97.toInt()
        return checksum
    }

    fun verifyChecksum(base36Value: String, checksum: Int): Boolean {
        if (checksum < 1 || checksum > 97) {
            return false
        }

        val checksumString = convertChecksumToString(checksum)
        val valueWithChecksum = convertBase36ToInteger(base36Value + ICAP_XE_PREFIX) + checksumString
        val bigIntMod = BigInteger(valueWithChecksum).mod(BigInteger(IBAN_MOD_VALUE))
        return bigIntMod.toString(10) == "1"
    }

    private fun convertChecksumToString(checksum: Int): String {
        var checksumString = checksum.toString()
        if (checksum < 10) {
            checksumString = "0" + checksumString
        }
        return checksumString
    }

    private fun convertBase36ToInteger(base36Value: String): String {
        var integerString = ""
        for (char: Char in base36Value) {
            integerString = integerString + BigInteger(char.toString(), 36)
        }
        return integerString
    }

}