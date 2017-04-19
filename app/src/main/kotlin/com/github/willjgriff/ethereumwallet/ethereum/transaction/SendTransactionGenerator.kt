package com.github.willjgriff.ethereumwallet.ethereum.transaction

import com.github.willjgriff.ethereumwallet.data.model.SendTransaction
import com.github.willjgriff.ethereumwallet.ethereum.icap.BaseConverter
import com.github.willjgriff.ethereumwallet.ethereum.icap.IbanParam

/**
 * Created by Will on 12/03/2017.
 */
class SendTransactionGenerator(private val baseConverter: BaseConverter) {

    private val IBAN_PREFIX_LOWERCASE = "iban:"
    private val IBAN_PREFIX_XE_LOWERCASE = "xe"
    private val IBAN_PREFIX_LENGTH_INCLUDING_CHECKSUM = 9
    private val IBAN_MAX_LENGTH = 35
    private val IBAN_PARAMS_START_DELIMITER = "?"
    private val IBAN_PARAM_DELIMITER = "&"
    private val IBAN_PARAM_KEY_VALUE_DELIMITER = "="
    private val HEX_PREFIX = "0x"

    fun getSendPaymentFromIban(iban: String): SendTransaction {
        return if (validEthereumIban(iban)) {
            val hexAddress = getHexAddressFromIban(iban)
            val params = getParamsFromIban(iban)
            SendTransaction(hexAddress,
                    params.get(IbanParam.AMOUNT)?.toLong() ?: 0L,
                    params.get(IbanParam.LABEL) ?: "")
        } else {
            SendTransaction()
        }
    }

    private fun validEthereumIban(iban: String): Boolean = iban.length >= IBAN_MAX_LENGTH + IBAN_PREFIX_LOWERCASE.length
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