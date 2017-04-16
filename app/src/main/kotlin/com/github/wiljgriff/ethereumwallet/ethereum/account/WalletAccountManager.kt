package com.github.wiljgriff.ethereumwallet.ethereum.account

import com.github.wiljgriff.ethereumwallet.ethereum.account.delegates.AccountDelegate
import com.github.wiljgriff.ethereumwallet.ethereum.account.delegates.AccountManagerDelegate
import timber.log.Timber

/**
 * Created by Will on 06/02/2017.
 */
class WalletAccountManager(private val accountManager: AccountManagerDelegate,
                           private val activeAccountAddress: ActiveAccountAddress) {

    private val DEFAULT_BLANK_HEX = ""

    fun createActiveAccount(password: String) {
        val newAccount = accountManager.newAccount(password)
        activeAccountAddress.set(newAccount.getAddress().getHex())
    }

    fun getActiveAccountAddressHex() = getActiveAccount()?.getAddress()?.getHex() ?: DEFAULT_BLANK_HEX

    fun getActiveAccount() = getAllAccounts()
            .filter { it.getAddress().getHex() == activeAccountAddress.get() }
            .singleOrNull()

    fun hasAccount() = getAllAccounts().isNotEmpty()

    fun getAllAccounts(): List<AccountDelegate> {
        val accounts = accountManager.getAccounts()
        return 0L.rangeTo(accounts.size() - 1)
                .map { accounts.get(it) }
                .toList()
    }

    fun deleteActiveAccount(password: String): Boolean {
        try {
            if (activeAccountAddress.hasActiveAddress()) {
                accountManager.deleteAccount(getActiveAccount(), password)
                activeAccountAddress.deleteActiveAddress()
            }
        } catch (exception: Exception) {
            Timber.i(exception, "Account not deleted, password probably incorrect")
        }

        return !activeAccountAddress.hasActiveAddress()
    }

    fun setActiveAccount(account: AccountDelegate) {
        activeAccountAddress.set(account.getAddress().getHex())
    }

}