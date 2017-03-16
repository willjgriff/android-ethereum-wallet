package com.github.wiljgriff.ethereumwallet.ethereum.icap

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.shouldEqual
import org.junit.Test

/**
 * Created by Will on 12/03/2017.
 */
class IbanGeneratorTest {

    private val mockBaseConverter = mock<BaseConverter>()
    private val mockIbanChecksumUtils = mock<IbanChecksumUtils>()
    private val subject = IbanGenerator(mockBaseConverter, mockIbanChecksumUtils)

    @Test
    fun createIbanFromAddress_returnsValidIban() {
        val expectedIban = "iban:XE08P2J0C65CFU410SM2IXXO687WQO2HMJV".toLowerCase()
        setupMocksForTest1()
        val actualIban = subject.createIbanFromHexAddress("0xD69F2FF2893C73B5eF4959a2ce85Ab1B1d35CE6B")
        actualIban shouldEqual expectedIban
    }

    // TODO: Test some transactions with leading zeros in the address.
    @Test
    fun createIbanFromAddress_returnsValidIban2() {
        val expectedIban = "iban:XE7338O073KYGTWWZN0F2WZ0R8PX5ZPPZS".toLowerCase()
        whenever(mockBaseConverter.base16ToBase36("00c5496aee77c1ba1f0854206a26dda82a81d6d8"))
                .then { "38o073kygtwwzn0f2wz0r8px5zppzs" }
        whenever(mockIbanChecksumUtils.createChecksum("38o073kygtwwzn0f2wz0r8px5zppzs"))
                .then { 73 }
        whenever(mockIbanChecksumUtils.convertChecksumToDoubleDigitString(73))
                .then { "73" }

        val actualIban = subject.createIbanFromHexAddress("0x00c5496aee77c1ba1f0854206a26dda82a81d6d8")

        actualIban shouldEqual expectedIban
    }

    @Test
    fun createIbanWithAmount_returnsValidIbanWithAmount() {
        val expectedIban = "iban:XE08P2J0C65CFU410SM2IXXO687WQO2HMJV?amount=0.023".toLowerCase()
        setupMocksForTest1()
        val actualIban = subject.createIbanFromHexWithAmount("0xD69F2FF2893C73B5eF4959a2ce85Ab1B1d35CE6B", 0.023)
        actualIban shouldEqual expectedIban
    }

    private fun setupMocksForTest1() {
        whenever(mockBaseConverter.base16ToBase36("D69F2FF2893C73B5eF4959a2ce85Ab1B1d35CE6B"))
                .then { "p2j0c65cfu410sm2ixxo687wqo2hmjv" }
        whenever(mockIbanChecksumUtils.createChecksum("p2j0c65cfu410sm2ixxo687wqo2hmjv"))
                .then { 8 }
        whenever(mockIbanChecksumUtils.convertChecksumToDoubleDigitString(8))
                .then { "08" }
    }
}