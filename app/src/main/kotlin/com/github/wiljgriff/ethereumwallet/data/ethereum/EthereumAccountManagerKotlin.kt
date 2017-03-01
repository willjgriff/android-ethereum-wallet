package com.github.wiljgriff.ethereumwallet.data.ethereum

import com.github.wiljgriff.ethereumwallet.data.ethereum.delegates.AccountDelegate
import com.github.wiljgriff.ethereumwallet.data.ethereum.delegates.AccountManagerDelegate
import timber.log.Timber

/**
 * Created by Will on 06/02/2017.
 *
 * TODO: Consider putting everything in Try Catch block (why doesn't Kotlin need this but Java does?)
 */
class EthereumAccountManagerKotlin(val accountManager: AccountManagerDelegate, val activeAccountAddress: ActiveAccountAddress) {

    fun createAccount(password: String): AccountDelegate {
        val newAccount = accountManager.newAccount(password)
        activeAccountAddress.set(newAccount.getAddress().getHex())
        return newAccount
    }

    fun getActiveAccountAddressHex() = getActiveAccount()?.getAddress()?.getHex()

    fun getActiveAccount() = getAllAccounts()
            .filter { it.getAddress().getHex() == activeAccountAddress.get() }
            .singleOrNull()

    fun hasAccount() = getAllAccounts()
            .isNotEmpty()

    fun getAllAccounts(): List<AccountDelegate> {
        val accountsList: MutableList<AccountDelegate> = mutableListOf()
        val accounts = accountManager.getAccounts()
        for (position in 0..accounts.size() - 1) {
            accountsList.add(accounts.get(position))
        }
        return accountsList
    }

    fun deleteActiveAccount(password: String): Boolean {
        try {
            if (getActiveAccount() != null) {
                accountManager.deleteAccount(getActiveAccount(), password)
                activeAccountAddress.set("")
            }
        } catch (exception: Exception) {
            Timber.i(exception, "Account not deleted, password probably incorrect")
        }
        return getActiveAccount() == null
    }

    fun setActiveAccount(account: AccountDelegate) {
        activeAccountAddress.set(account.getAddress().getHex())
    }

}