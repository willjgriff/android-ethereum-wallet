package com.github.wiljgriff.ethereumwallet.ethereum.payment

import com.github.wiljgriff.ethereumwallet.data.ethereum.SendPayment
import com.github.wiljgriff.ethereumwallet.ethereum.icap.BaseConverter
import com.github.wiljgriff.ethereumwallet.ethereum.icap.IbanParam

/**
 * Created by Will on 12/03/2017.
 */
class SendPaymentGenerator(private val baseConverter: BaseConverter) {

    val IBAN_PREFIX_LOWERCASE = "iban:"
    val IBAN_PREFIX_XE_LOWERCASE = "xe"
    val IBAN_PREFIX_LENGTH_INCLUDING_CHECKSUM = 9
    val IBAN_MAX_LENGTH = 35
    val IBAN_PARAMS_START_DELIMITER = "?"
    val IBAN_PARAM_DELIMITER = "&"
    val IBAN_PARAM_KEY_VALUE_DELIMITER = "="
    val HEX_PREFIX = "0x"

    fun getSendPaymentFromIban(iban: String): SendPayment {
        return if (validEthereumIban(iban)) {
            val hexAddress = getHexAddressFromIban(iban)
            val params = getParamsFromIban(iban)
            SendPayment(hexAddress,
                    params.get(IbanParam.AMOUNT)?.toDouble() ?: 0.0,
                    params.get(IbanParam.LABEL) ?: "")
        } else {
            SendPayment()
        }
    }

    private fun validEthereumIban(iban: String) = iban.length >= IBAN_MAX_LENGTH + IBAN_PREFIX_LOWERCASE.length
            && iban.substring(0, 7).toLowerCase() == IBAN_PREFIX_LOWERCASE + IBAN_PREFIX_XE_LOWERCASE

    // TODO: Add some extra checks, eg there could be "=" in the params
    private fun getParamsFromIban(iban: String): HashMap<IbanParam, String> {
        return iban
                .substringAfter(IBAN_PARAMS_START_DELIMITER, "")
                .split(IBAN_PARAM_DELIMITER)
                .filter { it.contains(IBAN_PARAM_KEY_VALUE_DELIMITER) }
                .map { it.split(IBAN_PARAM_KEY_VALUE_DELIMITER, limit = 2) }
                .fold(HashMap<IbanParam, String>()) { acc, next ->
                    acc.put(IbanParam.fromString(next.get(0)), next.get(1))
                    acc
                }
    }

    private fun getHexAddressFromIban(iban: String): String {
        val base36Value = iban
                .substring(IBAN_PREFIX_LENGTH_INCLUDING_CHECKSUM)
                .substringBefore(IBAN_PARAMS_START_DELIMITER)
        return HEX_PREFIX + baseConverter.base36ToBase16(base36Value)
    }
}