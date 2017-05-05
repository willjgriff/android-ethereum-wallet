package com.github.willjgriff.ethereumwallet.ethereum.common.model

import com.github.willjgriff.ethereumwallet.ethereum.common.model.Denomination.*
import org.amshove.kluent.shouldEqual
import org.junit.Test
import java.math.BigDecimal

/**
 * Created by williamgriffiths on 05/05/2017.
 */
class EtherAmountTest {

    @Test
    fun convertWeiToWei() {
        val expectedWeiAmount = EtherAmount(BigDecimal("123"), WEI)

        val actualWeiAmount = expectedWeiAmount.to(WEI)

        actualWeiAmount shouldEqual expectedWeiAmount
    }

    @Test
    fun convertKweiToKwei() {
        val expectedKweiAmount = EtherAmount(BigDecimal("123"), KWEI)

        val actualKweiAmount = expectedKweiAmount.to(KWEI)

        actualKweiAmount shouldEqual expectedKweiAmount
    }

    @Test
    fun convertWeiToKwei() {
        val weiAmount = EtherAmount(BigDecimal("1001"), WEI)
        val expectedKweiAmount = EtherAmount(BigDecimal("1.001"), KWEI)

        val actualKweiAmount = weiAmount.to(KWEI)

        actualKweiAmount shouldEqual expectedKweiAmount
    }

    @Test
    fun convertKweiToWei() {
        val kweiAmount = EtherAmount(BigDecimal("1001"), KWEI)
        val expectedWeiAmount = EtherAmount(BigDecimal("1001000"), WEI)

        val actualWeiAmount = kweiAmount.to(WEI)

        actualWeiAmount shouldEqual expectedWeiAmount
    }

    @Test
    fun convertWeiToEther() {
        val weiAmount = EtherAmount(BigDecimal("1000000000"), WEI)
        val expectedEtherAmount = EtherAmount(BigDecimal("0.000000001"), ETHER)

        val actualEtherAmount = weiAmount.to(ETHER)

        actualEtherAmount shouldEqual expectedEtherAmount
    }

    @Test
    fun convertEtherToWei() {
        val etherAmount = EtherAmount(BigDecimal("0.0001234"), ETHER)
        val expectedWeiAmount = EtherAmount(BigDecimal("123400000000000"), WEI)

        val weiAmount = etherAmount.to(WEI)

        weiAmount shouldEqual expectedWeiAmount
    }
}