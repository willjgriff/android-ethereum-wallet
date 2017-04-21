package com.github.willjgriff.ethereumwallet.ethereum.account

import com.github.willjgriff.ethereumwallet.ethereum.address.model.DomainAddress
import com.github.willjgriff.ethereumwallet.ethereum.address.ActiveAddress
import com.github.willjgriff.ethereumwallet.ethereum.address.AddressAdapter
import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
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
class AddressManagerTest {

    private lateinit var mSubject: AddressManager;

    private var MOCK_HEX_ADDRESS = "0x349j8w983"
    private var mockAddress: DomainAddress = mock {
        on { hex } doReturn MOCK_HEX_ADDRESS
    }
    private var mockAccounts: List<DomainAddress> = listOf(mockAddress)
    private var mMockAddressAdapter: AddressAdapter = mock {
        on { getAddresses() } doReturn mockAccounts
        on { newAddress(any()) } doReturn mockAddress
    }
    private var mMockActiveAddress: ActiveAddress = mock {
        on { getHex() } doReturn MOCK_HEX_ADDRESS
    }

    @Before
    fun setupEthereumAccountManagerKotlinTest() {
        mSubject = AddressManager(mMockAddressAdapter, mMockActiveAddress)
    }

    @Test
    fun createAccount_callsNewAccountOnAccountManager() {
        val MOCK_PASSWORD = "password"

        mSubject.createActiveAddress(MOCK_PASSWORD)

        verify(mMockAddressAdapter).newAddress(MOCK_PASSWORD)
        verify(mMockActiveAddress).setHex(any())
    }

    @Test
    fun getActiveAccount_returnsExpectedAccount() {
        val actualAddress = mSubject.getActiveAddress()

        mockAddress shouldEqual actualAddress
    }
}