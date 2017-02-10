package com.github.wiljgriff.ethereumwallet.data.ethereum

import com.github.wiljgriff.ethereumwallet.data.ethereum.delegates.AccountDelegate
import com.github.wiljgriff.ethereumwallet.data.ethereum.delegates.AccountManagerDelegate
import com.github.wiljgriff.ethereumwallet.data.ethereum.delegates.AccountsDelegate
import com.nhaarman.mockito_kotlin.*
import org.amshove.kluent.shouldEqual
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Will on 07/02/2017.
 */
class EthereumAccountManagerKotlinMocksTest {

    private val MOCK_ACTIVE_ACCOUNT_POSITION = 0L
    private val MOCK_ACCOUNTS_SIZE = 1L

    private lateinit var subject: EthereumAccountManagerKotlin;

    private var mockAccount: AccountDelegate = mock()
    private var mockAccounts: AccountsDelegate = mock {
        on { get(any()) } doReturn mockAccount
    }
    private var mockAccountManager: AccountManagerDelegate = mock {
        on { getAccounts() } doReturn mockAccounts
    }

    @Before
    fun setupEthereumAccountManagerKotlinTest() {
        subject = EthereumAccountManagerKotlin(mockAccountManager, MOCK_ACTIVE_ACCOUNT_POSITION)
    }

    @Test
    fun createAccount_callsNewAccountOnAccountManager() {
        val MOCK_PASSWORD = "password"

        subject.createAccount(MOCK_PASSWORD)

        verify(mockAccountManager).newAccount(MOCK_PASSWORD)
    }

    @Test
    fun getActiveAccount_returnsExpectedAccount() {
        whenever(mockAccounts.size()).then { MOCK_ACCOUNTS_SIZE }

        val actualAccount = subject.getActiveAccount()

        mockAccount shouldEqual actualAccount
    }
}