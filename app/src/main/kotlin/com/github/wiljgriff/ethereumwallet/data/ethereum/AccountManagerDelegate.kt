package com.github.wiljgriff.ethereumwallet.data.ethereum

import org.ethereum.geth.Account
import org.ethereum.geth.AccountManager
import org.ethereum.geth.Accounts

/**
 * Created by Will on 07/02/2017.
 */
class AccountManagerDelegate(val accountManager: AccountManager) {

    val accounts = AccountsDelegate(accountManager.accounts)
    fun newAccount(password: String) = AccountDelegate(accountManager.newAccount(password))
}

class AccountsDelegate(val accounts: Accounts) {

    fun size(): Long = accounts.size()
    fun get(position: Long): AccountDelegate = AccountDelegate(accounts.get(position))
}

class AccountDelegate(val account: Account) {

}