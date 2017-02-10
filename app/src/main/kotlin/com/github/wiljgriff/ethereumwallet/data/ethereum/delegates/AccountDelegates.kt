package com.github.wiljgriff.ethereumwallet.data.ethereum.delegates

import org.ethereum.geth.Account
import org.ethereum.geth.AccountManager
import org.ethereum.geth.Accounts

/**
 * Created by Will on 07/02/2017.
 */
class AccountManagerDelegate(private val accountManager: AccountManager) {

    fun getAccounts() = AccountsDelegate(accountManager.accounts)
    fun newAccount(password: String) = AccountDelegate(accountManager.newAccount(password))
}

class AccountsDelegate(private val accounts: Accounts) {

    fun size(): Long = accounts.size()
    fun get(position: Long): AccountDelegate? = AccountDelegate(accounts.get(position))
}

class AccountDelegate(private val account: Account) {

}