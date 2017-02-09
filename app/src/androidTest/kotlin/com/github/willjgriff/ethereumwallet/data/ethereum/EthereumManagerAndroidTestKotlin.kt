package com.github.willjgriff.ethereumwallet.data.ethereum

import android.support.test.InstrumentationRegistry
import com.github.wiljgriff.ethereumwallet.data.ethereum.EthereumAccountConfig
import com.github.wiljgriff.ethereumwallet.data.ethereum.EthereumAccountManagerKotlin
import com.github.wiljgriff.ethereumwallet.data.ethereum.LightEthereumAccountConfig
import org.ethereum.geth.AccountManager
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

/**
 * Created by Will on 06/02/2017.
 */
class EthereumManagerAndroidTestKotlin {

    private val MOCK_PASSWORD = "password"

    private lateinit var subject: EthereumAccountManagerKotlin

    private val appContext = InstrumentationRegistry.getTargetContext()
    private val accountConfig: EthereumAccountConfig = LightEthereumAccountConfig(appContext)

    @Before
    fun setupEthereumManagerAndroidTestKotlin() {
        MockitoAnnotations.initMocks(this)

        val ethereumAccountManager = AccountManager(accountConfig.getFilePath(), accountConfig.getCryptoScryptN(), accountConfig.getCryptoScryptP())
//        subject = EthereumAccountManagerKotlin(ethereumAccountManager, 0);
    }

    @Test
    fun createAccount_createsANewAccount() {
        subject.createAccount(MOCK_PASSWORD)
        subject.getActiveAccount()
    }

    @Test
    fun getActiveAccount_returnsAccountAtGivenPosition() {
        val account = subject.getActiveAccount()
    }

}