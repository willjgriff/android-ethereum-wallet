package com.github.willjgriff.ethereumwallet.ethereum.common.model

import com.github.willjgriff.ethereumwallet.ethereum.common.fromDenominationToWei
import com.github.willjgriff.ethereumwallet.ethereum.common.fromWeiTo
import org.amshove.kluent.shouldEqual
import org.junit.Test
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Created by williamgriffiths on 05/05/2017.
 */
class UnitConverterExtensionsTest {

    @Test
    fun convertWeiStringToEther() {
        val weiAmount = BigInteger("1000000000")

        val etherAmount = weiAmount.fromWeiTo(Denomination.ETHER)

        etherAmount shouldEqual BigDecimal("0.000000001")
    }

    @Test
    fun convertEtherStringToWei() {
        val etherAmount = BigDecimal("0.0001234")

        val weiAmount = etherAmount.fromDenominationToWei(Denomination.ETHER)

        weiAmount shouldEqual BigInteger("123400000000000")
    }

    @Test
    fun convertEtherStringToWei2() {
        val etherValue = BigDecimal("0.0000123")

        val weiAmount = etherValue.fromDenominationToWei(Denomination.ETHER)

        weiAmount shouldEqual BigInteger("12300000000000")
    }

    @Test
    fun convertWeiToEtherString2() {
        val weiAmount = BigInteger("12300000000000")

        val etherString = weiAmount.fromWeiTo(Denomination.ETHER)

        etherString shouldEqual BigDecimal("0.0000123")
    }
}