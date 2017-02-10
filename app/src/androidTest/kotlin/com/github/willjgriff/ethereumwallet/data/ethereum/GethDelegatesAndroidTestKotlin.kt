package com.github.willjgriff.ethereumwallet.data.ethereum

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.github.wiljgriff.ethereumwallet.data.ethereum.delegates.AccountManagerDelegate
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBe
import org.ethereum.geth.AccountManager
import org.ethereum.geth.Geth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Will on 06/02/2017.
 */
@RunWith(AndroidJUnit4::class)
class GethDelegatesAndroidTestKotlin {

    private val PASSWORD = "password"
    private lateinit var subject: AccountManagerDelegate

    @Before
    fun setupEthereumManagerAndroidTestKotlin() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val keystoreLocation = appContext.getFilesDir() as String + "/keystore_location"
        subject = AccountManagerDelegate(AccountManager(keystoreLocation, Geth.LightScryptN, Geth.LightScryptP))
    }


    @Test
    fun getAccounts_withNewKeystoreReturnsAccountsWithSize0() {
        val accounts = subject.getAccounts()
        accounts.size() shouldEqual 0
    }

    @Test
    fun newAccount_shouldNotBeNull() {
        val account = subject.newAccount(PASSWORD)
        account shouldNotBe null
    }

    @Test
    fun getAccounts_withAccountShouldHaveSize1() {
        subject.newAccount(PASSWORD)
        val accounts = subject.getAccounts()
        accounts.size() shouldEqual 1
    }

}