package com.github.wiljgriff.ethereumwallet.ethereum.account

import com.github.wiljgriff.ethereumwallet.data.model.DomainAccount
import com.github.wiljgriff.ethereumwallet.data.model.DomainAddress
import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsAdapter
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

/**
 * Created by Will on 07/02/2017.
 */
class AccountsManagerTest {

    private lateinit var mSubject: AccountsManager;

    private var MOCK_HEX_ADDRESS = "0x349j8w983"
    private var mockAddress: DomainAddress = mock {
        on { hex } doReturn MOCK_HEX_ADDRESS
    }
    private var mockAccount: DomainAccount = mock {
        on { address } doReturn mockAddress
    }
    private var mockAccounts: List<DomainAccount> = listOf(mockAccount)
    private var mMockAccountsAdapter: AccountsAdapter = mock {
        on { getAccounts() } doReturn mockAccounts
        on { newAccount(any()) } doReturn mockAccount
    }
    private var mockActiveAddress: ActiveAccountAddress = mock {
        on { get() } doReturn MOCK_HEX_ADDRESS
    }

    @Before
    fun setupEthereumAccountManagerKotlinTest() {
        mSubject = AccountsManager(mMockAccountsAdapter, mockActiveAddress)
    }

    @Test
    fun createAccount_callsNewAccountOnAccountManager() {
        val MOCK_PASSWORD = "password"

        mSubject.createActiveAccount(MOCK_PASSWORD)

        verify(mMockAccountsAdapter).newAccount(MOCK_PASSWORD)
        verify(mockActiveAddress).set(any())
    }

    @Test
    fun getActiveAccount_returnsExpectedAccount() {
        val actualAccount = mSubject.getActiveAccount()

        mockAccount shouldEqual actualAccount
    }
}