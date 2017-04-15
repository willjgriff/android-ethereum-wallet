package com.github.wiljgriff.ethereumwallet.ethereum.account

import com.github.wiljgriff.ethereumwallet.ethereum.account.delegates.AccountDelegate
import com.github.wiljgriff.ethereumwallet.ethereum.account.delegates.AccountManagerDelegate
import com.github.wiljgriff.ethereumwallet.ethereum.account.delegates.AccountsDelegate
import com.nhaarman.mockito_kotlin.*
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

/**
 * Created by Will on 07/02/2017.
 */
class WalletAccountManagerTest {

    private val MOCK_ACCOUNTS_SIZE = 1L

    private lateinit var subject: WalletAccountManager;

    private var mockAccount: AccountDelegate = mock()
    private var mockAccounts: AccountsDelegate = mock {
        on { get(any()) } doReturn mockAccount
    }
    private var mockAccountManager: AccountManagerDelegate = mock {
        on { getAccounts() } doReturn mockAccounts
    }
    private var mockActiveAddress: ActiveAccountAddress = mock()

    @Before
    fun setupEthereumAccountManagerKotlinTest() {
        subject = WalletAccountManager(mockAccountManager, mockActiveAddress)
    }

    @Test
    fun createAccount_callsNewAccountOnAccountManager() {
        val MOCK_PASSWORD = "password"

        subject.createActiveAccount(MOCK_PASSWORD)

        verify(mockAccountManager).newAccount(MOCK_PASSWORD)
        verify(mockActiveAddress).set(any())
    }

    @Test
    fun getActiveAccount_returnsExpectedAccount() {
        whenever(mockAccounts.size()).then { MOCK_ACCOUNTS_SIZE }

        val actualAccount = subject.getActiveAccount()

        mockAccount shouldEqual actualAccount
    }
}