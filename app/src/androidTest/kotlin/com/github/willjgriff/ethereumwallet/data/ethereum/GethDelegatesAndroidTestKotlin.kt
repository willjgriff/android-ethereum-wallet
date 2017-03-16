package com.github.willjgriff.ethereumwallet.data.ethereum

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.github.wiljgriff.ethereumwallet.ethereum.account.delegates.AccountManagerDelegate
import org.ethereum.geth.AccountManager
import org.ethereum.geth.Geth
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

/**
 * Created by Will on 06/02/2017.
 */
@RunWith(AndroidJUnit4::class)
class GethDelegatesAndroidTestKotlin {

    private val PASSWORD = "password"
    private lateinit var subject: AccountManagerDelegate

    private val appContext = InstrumentationRegistry.getTargetContext()
    private val keystoreLocation = appContext.getFilesDir().toString() + "/keystore_location/"

    @Before
    fun setupEthereumManagerAndroidTestKotlin() {
        subject = AccountManagerDelegate(AccountManager(keystoreLocation, Geth.LightScryptN, Geth.LightScryptP))
    }

    @After
    fun cleanEthereumManagerAndroidTestKotlin() {
        File(keystoreLocation).deleteRecursively()
    }

    @Test
    fun getAccounts_withNewKeystoreReturnsAccountsWithSize0() {
        val accounts = subject.getAccounts()
        assertEquals(0, accounts.size())
    }

    @Test
    fun newAccount_shouldNotBeNull() {
        val account = subject.newAccount(PASSWORD)
        assertNotNull(account)
    }

    @Test
    fun getAccounts_withAccountShouldHaveSize1() {
        subject.newAccount(PASSWORD)
        val accounts = subject.getAccounts()
        assertEquals(1, accounts.size())
    }

    @Test
    fun getAccountAtPosition1_returnsAccountAtPosition1WithSameFilePath() {
        val account1Path = subject.newAccount(PASSWORD).getFile()
        val account2Path = subject.newAccount(PASSWORD).getFile()

        val actualAccountPath = subject.getAccounts().get(1).getFile();

        assertEquals(account2Path, actualAccountPath)
        assertNotEquals(account1Path, actualAccountPath)
    }

    @Test
    fun getAddressHex_isTheSameAfterFetchingFromAccountManager() {
        val address = subject.newAccount(PASSWORD).getAddress().getHex()
        val fetched = subject.getAccounts().get(0).getAddress().getHex()
        assertEquals(address, fetched)
    }

    @Test
    fun accountsSize_isCorrectAfterAddingAndDeletingAccounts() {
        val account1 = subject.newAccount(PASSWORD)
        subject.newAccount(PASSWORD)
        subject.deleteAccount(account1, PASSWORD)

        val actualSize = subject.getAccounts().size()

        assertEquals(1, actualSize)
    }
}