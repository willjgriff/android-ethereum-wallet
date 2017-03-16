package com.github.wiljgriff.ethereumwallet.ethereum.icap

/**
 * Created by Will on 12/03/2017.
 */
class IbanGenerator(private val baseConverter: BaseConverter,
                    private val ibanChecksumUtils: IbanChecksumUtils) {

    // TODO: Consider adding validation
    fun createIbanFromHexAddress(address: String): String {
        val strippedAddress = stripLeading0x(address)
        val base36Address = baseConverter.base16ToBase36(strippedAddress)
        val checkSum = ibanChecksumUtils.createChecksum(base36Address)
        val doubleDigitCheckSumString = ibanChecksumUtils.convertChecksumToDoubleDigitString(checkSum)
        val iban = "iban:XE" + doubleDigitCheckSumString + base36Address
        return iban.toLowerCase()
    }

    private fun stripLeading0x(address: String): String {
        if (address.substring(0, 2) == "0x") {
            return address.substring(2)
        } else {
            return address
        }
    }

    // TODO: Extend functionality, perhaps with a builder to add params other than just amount.
    fun createIbanFromHexWithAmount(address: String, amount: Double): String {
        var iban = createIbanFromHexAddress(address)
        iban = iban + "?amount=" + amount
        return iban
    }

}