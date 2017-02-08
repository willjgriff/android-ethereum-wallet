package com.github.wiljgriff.ethereumwallet.data.ethereum

import com.github.willjgriff.ethereumwallet.data.ethereum.EthereumAccountManager
import org.ethereum.geth.Account
import org.ethereum.geth.AccountManager
import org.ethereum.geth.Accounts
import java.util.*

/**
 * Created by Will on 06/02/2017.
 *
 * TODO: Probably put everything in Try Catch block (why doesn't Kotlin need this but Java does?)
 */
class EthereumAccountManagerKotlin(var ethereumAccountManager: AccountManagerDelegate, var activeAccountPosition: Long) {

    fun createAccount(password: String): AccountDelegate {
        activeAccountPosition = ethereumAccountManager.accounts.size()
        return ethereumAccountManager.newAccount(password)
    }

    fun getActiveAccount(): AccountDelegate {
        var account: AccountDelegate = AccountDelegate(Account())
        if (accountAvailableAtPosition(activeAccountPosition)) {
            account = ethereumAccountManager.accounts.get(activeAccountPosition)
        }
        return account
    }

    private fun accountAvailableAtPosition(position: Long) =
            (ethereumAccountManager.accounts.size() >= position
                    && ethereumAccountManager.accounts.get(position) != null)

    fun getAllAccounts(): List<AccountDelegate> {
        var accountsList: List<AccountDelegate> = ArrayList()
        var accounts = ethereumAccountManager.accounts

        for (position in 0..accounts.size() - 1) {
            accountsList.plus(accounts.size())
        }

        return accountsList
    }

    fun hasAccount(): Boolean = accountAvailableAtPosition(0)

}