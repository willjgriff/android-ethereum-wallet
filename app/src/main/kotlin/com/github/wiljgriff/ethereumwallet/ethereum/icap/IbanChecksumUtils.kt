package com.github.wiljgriff.ethereumwallet.ethereum.icap

import java.math.BigInteger

/**
 * Created by Will on 11/03/2017.
 *
 * Guides: https://usersite.datalab.eu/printclass.aspx?type=wiki&id=91772
 * http://ethereum.stackexchange.com/questions/12016/checksum-calculation-for-icap-address
 *
 * TODO: Consider adding validation
 */
class IbanChecksumUtils(private val baseConverter: BaseConverter) {

    val ICAP_XE_PREFIX = "XE"
    val IBAN_MUTIPLIER = "00"
    val IBAN_CHECK_MOD_VALUE = "97"

    fun createChecksum(base36Value: String): Int {
        if (base36Value.isNullOrBlank()) {
            return -1
        }

        val valueWithXePrefix = base36Value + ICAP_XE_PREFIX
        val valueAsInt = baseConverter.base36ToInteger(valueWithXePrefix)
        val valueTimes100 = valueAsInt + IBAN_MUTIPLIER
        val valueMod97 = BigInteger(valueTimes100).mod(BigInteger(IBAN_CHECK_MOD_VALUE))
        val checksum = 98 - valueMod97.toInt()
        return checksum
    }

    fun verifyChecksum(base36Value: String, checksum: Int): Boolean {
        if (checksum < 1 || checksum > 97) {
            return false
        }

        val checksumString = convertChecksumToDoubleDigitString(checksum)
        val valueWithChecksum = baseConverter.base36ToInteger(base36Value + ICAP_XE_PREFIX) + checksumString
        val bigIntMod = BigInteger(valueWithChecksum).mod(BigInteger(IBAN_CHECK_MOD_VALUE))
        return bigIntMod.toString(10) == "1"
    }

    fun convertChecksumToDoubleDigitString(checksum: Int): String {
        var checksumString = checksum.toString()
        if (checksum < 10) {
            checksumString = "0" + checksumString
        }
        return checksumString
    }

}