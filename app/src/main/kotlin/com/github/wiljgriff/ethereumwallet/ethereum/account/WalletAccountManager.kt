package com.github.wiljgriff.ethereumwallet.ethereum.account

import com.github.wiljgriff.ethereumwallet.data.model.DomainAccount
import timber.log.Timber

/**
 * Created by Will on 06/02/2017.
 */
class WalletAccountManager(private val accountsAdapter: AccountsAdapter,
                           private val activeAccountAddress: ActiveAccountAddress) {

    fun createActiveAccount(password: String) {
        val newAccount = accountsAdapter.newAccount(password)
        activeAccountAddress.set(newAccount.address.hex)
    }

    fun getActiveAccountAddressHex() = getActiveAccount().address.hex

    fun getActiveAccount() = getAllAccounts()
            .filter { it.address.hex == activeAccountAddress.get() }
            .singleOrNull() ?: DomainAccount()

    fun hasAccount() = getAllAccounts().isNotEmpty()

    fun getAllAccounts(): List<DomainAccount> = accountsAdapter.getAccounts()

    fun deleteActiveAccount(password: String): Boolean {
        try {
            if (activeAccountAddress.hasActiveAddress()) {
                accountsAdapter.deleteAccount(getActiveAccount(), password)
                activeAccountAddress.deleteActiveAddress()
            }
        } catch (exception: Exception) {
            Timber.i(exception, "Account not deleted, password probably incorrect")
        }

        return !activeAccountAddress.hasActiveAddress()
    }

    fun setActiveAccount(account: DomainAccount) {
        activeAccountAddress.set(account.address.hex)
    }

}