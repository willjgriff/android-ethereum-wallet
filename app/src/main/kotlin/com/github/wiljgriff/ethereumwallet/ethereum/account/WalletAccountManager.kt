package com.github.wiljgriff.ethereumwallet.ethereum.account

import com.github.wiljgriff.ethereumwallet.ethereum.account.delegates.AccountDelegate
import com.github.wiljgriff.ethereumwallet.ethereum.account.delegates.AccountManagerDelegate
import timber.log.Timber

/**
 * Created by Will on 06/02/2017.
 */
class WalletAccountManager(private val accountManager: AccountManagerDelegate,
                           private val activeAccountAddress: ActiveAccountAddress) {

    fun createAccount(password: String): AccountDelegate {
        val newAccount = accountManager.newAccount(password)
        activeAccountAddress.set(newAccount.getAddress().getHex())
        return newAccount
    }

    fun getActiveAccountAddressHex() = getActiveAccount()?.getAddress()?.getHex()

    fun getActiveAccount() = getAllAccounts()
            .filter { it.getAddress().getHex() == activeAccountAddress.get() }
            .first()

    fun getActiveAccountAddress() = getActiveAccount().getAddress()

    fun hasAccount() = getAllAccounts()
            .isNotEmpty()

    fun getAllAccounts(): List<AccountDelegate> {
        val accountsList = mutableListOf<AccountDelegate>()
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