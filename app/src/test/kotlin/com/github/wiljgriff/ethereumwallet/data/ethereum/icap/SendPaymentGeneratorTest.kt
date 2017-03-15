package com.github.wiljgriff.ethereumwallet.data.ethereum.icap

import com.github.wiljgriff.ethereumwallet.data.ethereum.SendPayment
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

/**
 * Created by Will on 12/03/2017.
 */
class SendPaymentGeneratorTest {

    private val mockBaseConverter: BaseConverter = mock()
    private val subject = SendPaymentGenerator(mockBaseConverter)

    @Before
    fun setupSendPaymentGeneratorTest() {
        whenever(mockBaseConverter.base36ToBase16("P2J0C65CFU410SM2IXXO687WQO2HMJV"))
                .then { "d69f2ff2893c73b5ef4959a2ce85ab1b1d35ce6b" }
    }

    @Test
    fun getSendPaymentFromIban_returnsDefaultSendPaymentWithIncorrectFormat() {
        val expectedPayment = SendPayment()
        val actualPayment = subject.getSendPaymentFromIban("iba:XE08P2J0C65CFU410SM2IXXO687WQO2HMJV?amount=0.023")
        actualPayment shouldEqual expectedPayment
    }

    @Test
    fun getSendPaymentFromIban_returnsDefaultSendPaymentWithTooShortIban() {
        val expectedPayment = SendPayment()
        val actualPayment = subject.getSendPaymentFromIban("iban:XE08P2J0C65CFU410SM2IXXO687WQO2HMJ")
        actualPayment shouldEqual expectedPayment
    }

    @Test
    fun getSendPaymentFromIban_returnsSendPaymentWithDefaultAmountAndLabel() {
        val expectedPayment = SendPayment("0xD69F2FF2893C73B5eF4959a2ce85Ab1B1d35CE6B".toLowerCase())
        val actualPayment = subject.getSendPaymentFromIban("iban:XE08P2J0C65CFU410SM2IXXO687WQO2HMJV")
        actualPayment shouldEqual expectedPayment
    }

    @Test
    fun getSendPaymentFromIban_returnsSendPaymentWithCorrectAmount() {
        val expectedPayment = SendPayment("0xD69F2FF2893C73B5eF4959a2ce85Ab1B1d35CE6B".toLowerCase(), 0.023)
        val actualPayment = subject.getSendPaymentFromIban("iban:XE08P2J0C65CFU410SM2IXXO687WQO2HMJV?amount=0.023")
        actualPayment shouldEqual expectedPayment
    }

    @Test
    fun getSendPaymentFromIban_returnsSendPaymentWithCorrectAmountAndLabel() {
        val expectedPayment = SendPayment("0xD69F2FF2893C73B5eF4959a2ce85Ab1B1d35CE6B".toLowerCase(), 0.023, "quepasa")
        val actualPayment = subject.getSendPaymentFromIban("iban:XE08P2J0C65CFU410SM2IXXO687WQO2HMJV?amount=0.023&label=quepasa")
        actualPayment shouldEqual expectedPayment
    }
}