package com.github.wiljgriff.ethereumwallet.data.ethereum

import org.ethereum.geth.Account
import java.util.*

/**
 * Created by Will on 06/02/2017.
 *
 * TODO: Consider putting everything in Try Catch block (why doesn't Kotlin need this but Java does?)
 */
class EthereumAccountManagerKotlin(var accountManager: AccountManagerDelegate, var activeAccountPosition: Long) {

    fun createAccount(password: String): AccountDelegate {
        activeAccountPosition = accountManager.accounts.size()
        return accountManager.newAccount(password)
    }

    fun getActiveAccount(): AccountDelegate? {
        var account: AccountDelegate? = null
        if (accountAvailableAtPosition(activeAccountPosition)) {
            account = accountManager.accounts.get(activeAccountPosition)
        }
        return account
    }

    fun hasAccount(): Boolean = accountAvailableAtPosition(0)

    private fun accountAvailableAtPosition(position: Long) =
            (accountManager.accounts.size() >= position
                    && accountManager.accounts.get(position) != null)

    fun getAllAccounts(): List<AccountDelegate> {
        var accountsList: List<AccountDelegate> = ArrayList()
        var accounts = accountManager.accounts

        for (position in 0..accounts.size() - 1) {
            accountsList.plus(accounts.size())
        }

        return accountsList
    }

}