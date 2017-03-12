package com.github.wiljgriff.ethereumwallet.data.ethereum.icap

import com.github.wiljgriff.ethereumwallet.data.ethereum.SendPayment
import org.amshove.kluent.shouldEqual
import org.junit.Test

/**
 * Created by Will on 12/03/2017.
 */
class SendPaymentGeneratorTest {

    //TODO: mock base converter
    private val subject = SendPaymentGenerator(BaseConverter())

    @Test
    fun getSendPaymentFromIban_returnsSendPaymentWithCorrectFields() {
        val expectedPayment = SendPayment("0xD69F2FF2893C73B5eF4959a2ce85Ab1B1d35CE6B".toLowerCase(), 0.023)
        val actualPayment = subject.getSendPaymentFromIban("iban:XE08P2J0C65CFU410SM2IXXO687WQO2HMJV?amount=0.023")
        actualPayment shouldEqual expectedPayment
    }

    // 0xD69F2FF2893C73B5eF4959a2ce85Ab1B1d35CE6B should convert to
    // iban:XE08P2J0C65CFU410SM2IXXO687WQO2HMJV

    // XE7338O073KYGTWWZN0F2WZ0R8PX5ZPPZS should convert to
    // 00c5496aee77c1ba1f0854206a26dda82a81d6d8
}