package com.github.wiljgriff.ethereumwallet.data.ethereum

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by Will on 01/03/2017.
 */
class IcapHexConverterTest {

    val subject = IcapHexConverter()

    @Test
    fun base16ToBase36_returnsExpectedValue() {
        val expectedNumber = "P2J0C65CFU410SM2IXXO687WQO2HMJV".toLowerCase()
        val actualNumber = subject.base16ToBase36("D69F2FF2893C73B5eF4959a2ce85Ab1B1d35CE6B")
        expectedNumber shouldEqual actualNumber
    }

    @Test
    fun base36ToBase16_returnsExpectedValue() {
        val expectedNumber = "D69F2FF2893C73B5eF4959a2ce85Ab1B1d35CE6B".toLowerCase()
        val actualNumber = subject.base36ToBase16("P2J0C65CFU410SM2IXXO687WQO2HMJV")
        expectedNumber shouldEqual actualNumber
    }

    @Test
    fun base16ToBase36_returnsExpectedValue2() {
        val expectedNumber = "38O073KYGTWWZN0F2WZ0R8PX5ZPPZS".toLowerCase()
        val actualNumber = subject.base16ToBase36("c5496aee77c1ba1f0854206a26dda82a81d6d8")
        expectedNumber shouldEqual actualNumber
    }

    @Test
    fun base36ToBase16_returnsExpectedValue2() {
        val expectedNumber = "c5496aee77c1ba1f0854206a26dda82a81d6d8".toLowerCase()
        val actualNumber = subject.base36ToBase16("38O073KYGTWWZN0F2WZ0R8PX5ZPPZS")
        expectedNumber shouldEqual actualNumber
    }

    @Test
    fun createChecksum_returnsCorrectChecksum() {
        val expectedChecksum = 8;
        val actualChecksum = subject.createChecksum("P2J0C65CFU410SM2IXXO687WQO2HMJV")
        expectedChecksum shouldEqual actualChecksum
    }

    @Test
    fun verifyChecksum_withCorrectInputReturnsTrue() {
        val addressIsValid = subject.verifyChecksum("P2J0C65CFU410SM2IXXO687WQO2HMJV", 8)
        addressIsValid shouldBe true
    }

    @Test
    fun verifyChecksum_withIncorrectInputReturnsFalse() {
        val addressIsValid = subject.verifyChecksum("P2J0C65CFU410SM2IXXO687WQO2HMJW", 8)
        addressIsValid shouldBe false
    }

    @Test
    fun formatAsIban_createsCorrectString() {
        
    }

    // 0xD69F2FF2893C73B5eF4959a2ce85Ab1B1d35CE6B should convert to
    // iban:XE08P2J0C65CFU410SM2IXXO687WQO2HMJV

    // XE7338O073KYGTWWZN0F2WZ0R8PX5ZPPZS should convert to
    // 00c5496aee77c1ba1f0854206a26dda82a81d6d8

}