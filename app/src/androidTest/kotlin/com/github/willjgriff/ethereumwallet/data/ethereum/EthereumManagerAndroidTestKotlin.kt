package com.github.willjgriff.ethereumwallet.data.ethereum

import com.github.wiljgriff.ethereumwallet.data.ethereum.EthereumAccountConfig
import com.github.wiljgriff.ethereumwallet.data.ethereum.EthereumAccountManagerKotlin
import com.github.wiljgriff.ethereumwallet.data.ethereum.LightEthereumAccountConfig
import org.ethereum.geth.Geth
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Will on 06/02/2017.
 */
class EthereumManagerAndroidTestKotlin {

    private val MOCK_PASSWORD = "password"
    private val MOCK_FILE_PATH = "/mockPath"

    private lateinit var subject: EthereumAccountManagerKotlin

    @Mock
    private lateinit var mockAccountConfig: EthereumAccountConfig

    @Before
    fun setupEthereumManagerAndroidTestKotlin() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(mockAccountConfig.getFilePath()).then { MOCK_FILE_PATH }
        Mockito.`when`(mockAccountConfig.getCryptoScryptN()).then { Geth.LightScryptN }
        Mockito.`when`(mockAccountConfig.getCryptoScryptP()).then { Geth.LightScryptP }

        subject = EthereumAccountManagerKotlin(mockAccountConfig)
    }

    @Test
    fun getAccountWithNoAccount_returnsNull() {
        val account = subject.getAccount()
        Assert.assertNull(account)
    }
}