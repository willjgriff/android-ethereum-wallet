package com.github.wiljgriff.ethereumwallet.data.ethereum

import org.junit.Assert
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
    private val MOCK_ACTIVE_ACCOUNT_POSITION = 0L

    private lateinit var subject: EthereumAccountManagerKotlin;

    @Mock
    private lateinit var mockAccountManager: AccountManagerDelegate
    @Mock
    private lateinit var mockAccounts: AccountsDelegate
    @Mock
    private lateinit var mockAccount: AccountDelegate

    @Before
    fun setupEthereumAccountManagerKotlinTest() {
        MockitoAnnotations.initMocks(this)
        subject = EthereumAccountManagerKotlin(mockAccountManager, MOCK_ACTIVE_ACCOUNT_POSITION)
    }

    @Test
    fun createAccount_callsCreateAccountOnAccountManager() {
        Mockito.`when`(mockAccountManager.getAccounts()).thenReturn(mockAccounts)

        subject.createAccount(MOCK_PASSWORD)

        Mockito.verify(mockAccountManager).newAccount(MOCK_PASSWORD)
    }

    @Test
    fun getActiveAccount_returnsExpectedAccount() {
        Mockito.`when`(mockAccounts.get(MOCK_ACTIVE_ACCOUNT_POSITION)).thenReturn(mockAccount)
        Mockito.`when`(mockAccounts.size()).thenReturn(1L)
        Mockito.`when`(mockAccountManager.getAccounts()).thenReturn(mockAccounts)

        var actualAccount = subject.getActiveAccount()

        Assert.assertEquals(mockAccount, actualAccount)
    }
}