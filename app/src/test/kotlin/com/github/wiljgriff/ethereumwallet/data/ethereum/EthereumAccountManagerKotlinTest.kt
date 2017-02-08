package com.github.wiljgriff.ethereumwallet.data.ethereum

import org.ethereum.geth.Accounts
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Will on 07/02/2017.
 */
class EthereumAccountManagerKotlinTest {

    private val MOCK_PASSWORD = "password"

    private lateinit var subject: EthereumAccountManagerKotlin;

    @Mock
    private lateinit var mAccountManagerDelegate: AccountManagerDelegate

    @Before
    fun setupEthereumAccountManagerKotlinTest() {
        MockitoAnnotations.initMocks(this)
        subject = EthereumAccountManagerKotlin(mAccountManagerDelegate, 0)
    }

    @Test
    fun createAccount_callsCreateAccountOnAccountManager() {
        Mockito.`when`(mAccountManagerDelegate.accounts).thenReturn(Mockito.mock(AccountsDelegate::class.java))
        subject.createAccount(MOCK_PASSWORD)
        Mockito.verify(mAccountManagerDelegate).newAccount(MOCK_PASSWORD)
    }
}