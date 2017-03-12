package com.github.wiljgriff.ethereumwallet.data.ethereum.icap

import org.amshove.kluent.shouldEqual
import org.junit.Test

/**
 * Created by Will on 01/03/2017.
 */
class BaseConverterTest {

    private val subject = BaseConverter()

    @Test
    fun base16ToBase36_returnsExpectedValue() {
        val expectedNumber = "P2J0C65CFU410SM2IXXO687WQO2HMJV".toLowerCase()
        val actualNumber = subject.base16ToBase36("D69F2FF2893C73B5eF4959a2ce85Ab1B1d35CE6B")
        actualNumber shouldEqual expectedNumber
    }

    @Test
    fun base16ToBase36_returnsExpectedValue2() {
        val expectedNumber = "38O073KYGTWWZN0F2WZ0R8PX5ZPPZS".toLowerCase()
        val actualNumber = subject.base16ToBase36("c5496aee77c1ba1f0854206a26dda82a81d6d8")
        actualNumber shouldEqual expectedNumber
    }

    @Test
    fun base36ToBase16_returnsExpectedValue() {
        val expectedNumber = "D69F2FF2893C73B5eF4959a2ce85Ab1B1d35CE6B".toLowerCase()
        val actualNumber = subject.base36ToBase16("P2J0C65CFU410SM2IXXO687WQO2HMJV")
        actualNumber shouldEqual expectedNumber
    }

    @Test
    fun base36ToBase16_returnsExpectedValue2() {
        val expectedNumber = "c5496aee77c1ba1f0854206a26dda82a81d6d8".toLowerCase()
        val actualNumber = subject.base36ToBase16("38O073KYGTWWZN0F2WZ0R8PX5ZPPZS")
        actualNumber shouldEqual expectedNumber
    }

    @Test
    fun base36ToInteger_returnsExpectedIntegerString() {
        val expectedInteger = "25219012651215304102822218333324687322624217221931"
        val actualInteger = subject.base36ToInteger("P2J0C65CFU410SM2IXXO687WQO2HMJV")
        actualInteger shouldEqual expectedInteger
    }

    @Test
    fun base36ToInteger_returnsExpectedIntegerString2() {
        val expectedInteger = "38240732034162932323523015232350278253353525253528"
        val actualInteger = subject.base36ToInteger("38O073KYGTWWZN0F2WZ0R8PX5ZPPZS")
        actualInteger shouldEqual expectedInteger
    }

}