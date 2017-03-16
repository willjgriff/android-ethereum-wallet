package com.github.wiljgriff.ethereumwallet.ethereum.icap

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.junit.Test

/**
 * Created by Will on 11/03/2017.
 */
class IbanChecksumUtilsTest {

    private val mockBaseConverter = mock<BaseConverter>()
    private val subject = IbanChecksumUtils(mockBaseConverter)

    @Test
    fun createChecksum_returnsCorrectChecksum() {
        val expectedChecksum = 8
        setupMockBaseConverterForTest1()
        val actualChecksum = subject.createChecksum("P2J0C65CFU410SM2IXXO687WQO2HMJV")
        actualChecksum shouldEqual expectedChecksum
    }

    @Test
    fun createChecksum_returnsCorrectChecksum2() {
        val expectedChecksum = 73
        setupMockBaseConverterForTest2()
        val actualChecksum = subject.createChecksum("38O073KYGTWWZN0F2WZ0R8PX5ZPPZS")
        actualChecksum shouldEqual expectedChecksum
    }

    @Test
    fun verifyChecksum_withCorrectInputReturnsTrue() {
        setupMockBaseConverterForTest1()
        val addressIsValid = subject.verifyChecksum("P2J0C65CFU410SM2IXXO687WQO2HMJV", 8)
        addressIsValid shouldBe true
    }

    @Test
    fun verifyChecksum_withCorrectInputReturnsTrue2() {
        setupMockBaseConverterForTest2()
        val addressIsValid = subject.verifyChecksum("38O073KYGTWWZN0F2WZ0R8PX5ZPPZS", 73)
        addressIsValid shouldBe true
    }

    private fun setupMockBaseConverterForTest1() {
        whenever(mockBaseConverter.base36ToInteger("P2J0C65CFU410SM2IXXO687WQO2HMJVXE"))
                .then { "252190126512153041028222183333246873226242172219313314" }
    }

    private fun setupMockBaseConverterForTest2() {
        whenever(mockBaseConverter.base36ToInteger("38O073KYGTWWZN0F2WZ0R8PX5ZPPZSXE"))
                .then { "382407320341629323235230152323502782533535252535283314" }
    }

    @Test
    fun verifyChecksum_withIncorrectInputReturnsFalse() {
        whenever(mockBaseConverter.base36ToInteger("P2J0C65CFU410SM2IXXO687WQO2HMJWXE"))
                .then { "252190126512153041028222183333246873226242172219323314" }
        val addressIsValid = subject.verifyChecksum("P2J0C65CFU410SM2IXXO687WQO2HMJW", 8)
        addressIsValid shouldBe false
    }

    @Test
    fun verifyChecksum_withCorrectInputReturnsFalse2() {
        whenever(mockBaseConverter.base36ToInteger("48O073KYGTWWZN0F2WZ0R8PX5ZPPZSXE"))
                .then { "482407320341629323235230152323502782533535252535283314" }
        val addressIsValid = subject.verifyChecksum("48O073KYGTWWZN0F2WZ0R8PX5ZPPZS", 73)
        addressIsValid shouldBe false
    }

    @Test
    fun convertCheckSumToDoubleDigitString_convertsToString() {
        val expectedChecksum = "73"
        val actualChecksum = subject.convertChecksumToDoubleDigitString(73)
        actualChecksum shouldEqual expectedChecksum
    }

    @Test
    fun convertCheckSumToDoubleDigitString_addsLeading0() {
        val expectedChecksum = "08"
        val actualChecksum = subject.convertChecksumToDoubleDigitString(8)
        actualChecksum shouldEqual expectedChecksum
    }
}