package com.github.wiljgriff.ethereumwallet.data.ethereum.icap

import com.github.wiljgriff.ethereumwallet.data.ethereum.SendPayment

/**
 * Created by Will on 12/03/2017.
 */
class SendPaymentGenerator(val baseConverter: BaseConverter) {

    val IBAN_PREFIX_LOWERCASE = "iban:xe"
    val IBAN_PREFIX_LENGTH_INCLUDING_CHECKSUM = 9
    val IBAN_PARAMS_START_DELIMITER = "?"
    val IBAN_PARAM_DELIMITER = "&"
    val IBAN_PARAM_KEY_VALUE_DELIMITER = "="
    val HEX_PREFIX = "0x"

    fun getSendPaymentFromIban(iban: String): SendPayment {
        if (validEthereumIban(iban)) {
            val hexAddress = getHexAddressFromIban(iban)
            val params = getParamsFromIban(iban)
            return SendPayment(hexAddress,
                    params.get(IbanParam.AMOUNT)?.toDouble() ?: 0.0,
                    params.get(IbanParam.LABEL) ?: "")
        } else {
            return SendPayment()
        }
    }

    private fun validEthereumIban(iban: String) = iban.length >= 35
            && iban.substring(0, 7).toLowerCase() == IBAN_PREFIX_LOWERCASE

    // TODO: Add some extra checks, eg there could be "=" in the params
    private fun getParamsFromIban(iban: String): HashMap<IbanParam, String> {
        return iban
                .substringAfter(IBAN_PARAMS_START_DELIMITER, "")
                .split(IBAN_PARAM_DELIMITER)
                .filter { it.contains(IBAN_PARAM_KEY_VALUE_DELIMITER) }
                .map { it.split(IBAN_PARAM_KEY_VALUE_DELIMITER, limit = 2) }
                .fold(HashMap<IbanParam, String>()) { acc, next ->
                    acc.put(IbanParam.fromString(next.get(0)), next.get(1))
                    return acc
                }
    }

    private fun getHexAddressFromIban(iban: String): String {
        val base36Value = iban
                .substring(IBAN_PREFIX_LENGTH_INCLUDING_CHECKSUM)
                .substringBefore(IBAN_PARAMS_START_DELIMITER)
        return HEX_PREFIX + baseConverter.base36ToBase16(base36Value)
    }
}