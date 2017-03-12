package com.github.wiljgriff.ethereumwallet.data.ethereum.icap

import org.junit.Test

/**
 * Created by Will on 12/03/2017.
 */
class SendPaymentGeneratorTest {

    private val subject = SendPaymentGenerator()

    @Test
    fun getSendPaymentFromIban_returnsSendPaymentWithCorrectFields() {
        subject.getSendPaymentFromIban("IBAN:XE73ha98u3434r98u?amount:0.23")
    }
}