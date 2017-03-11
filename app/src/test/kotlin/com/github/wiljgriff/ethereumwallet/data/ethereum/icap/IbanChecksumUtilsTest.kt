package com.github.wiljgriff.ethereumwallet.data.ethereum.icap

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.junit.Test

/**
 * Created by Will on 11/03/2017.
 */
class IbanChecksumUtilsTest {

    val subject = IbanChecksumUtils()

    @Test
    fun createChecksum_returnsCorrectChecksum() {
        val expectedChecksum = 8
        val actualChecksum = subject.createChecksum("P2J0C65CFU410SM2IXXO687WQO2HMJV")
        actualChecksum shouldEqual expectedChecksum
    }

    @Test
    fun createChecksum_returnsCorrectChecksum2() {
        val expectedChecksum = 73
        val actualChecksum = subject.createChecksum("38O073KYGTWWZN0F2WZ0R8PX5ZPPZS")
        actualChecksum shouldEqual expectedChecksum
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
    fun verifyChecksum_withCorrectInputReturnsTrue2() {
        val addressIsValid = subject.verifyChecksum("38O073KYGTWWZN0F2WZ0R8PX5ZPPZS", 73)
        addressIsValid shouldBe true
    }

    @Test
    fun verifyChecksum_withCorrectInputReturnsFalse2() {
        val addressIsValid = subject.verifyChecksum("48O073KYGTWWZN0F2WZ0R8PX5ZPPZS", 73)
        addressIsValid shouldBe false
    }
}