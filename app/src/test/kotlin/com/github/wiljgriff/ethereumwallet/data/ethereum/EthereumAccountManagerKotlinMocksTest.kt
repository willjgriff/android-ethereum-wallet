package com.github.wiljgriff.ethereumwallet.data.ethereum

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Will on 07/02/2017.
 */
class EthereumAccountManagerKotlinMocksTest {

    private val MOCK_PASSWORD = "password"
    private val MOCK_ACTIVE_ACCOUNT_POSITION = 0L

    private lateinit var subject: EthereumAccountManagerKotlin;

    private var mockAccounts: AccountsDelegate = mock()
    private var mockAccount: AccountDelegate = mock()
    private var mockAccountManager: AccountManagerDelegate = mock() {
        on { accounts } doReturn mockAccounts
    }

    @Before
    fun setupEthereumAccountManagerKotlinTest() {
        subject = EthereumAccountManagerKotlin(mockAccountManager, MOCK_ACTIVE_ACCOUNT_POSITION)
    }

    @Test
    fun createAccount_callsCreateAccountOnAccountManager() {
        Mockito.`when`(mockAccountManager.accounts).thenReturn(mockAccounts)

        subject.createAccount(MOCK_PASSWORD)

        Mockito.verify(mockAccountManager).newAccount(MOCK_PASSWORD)
    }

    @Test
    fun getActiveAccount_returnsExpectedAccount() {
        Mockito.`when`(mockAccounts.get(MOCK_ACTIVE_ACCOUNT_POSITION)).thenReturn(mockAccount)
        Mockito.`when`(mockAccounts.size()).thenReturn(1L)
        Mockito.`when`(mockAccountManager.accounts).thenReturn(mockAccounts)

        var actualAccount = subject.getActiveAccount()

        Assert.assertEquals(mockAccount, actualAccount)
    }
}