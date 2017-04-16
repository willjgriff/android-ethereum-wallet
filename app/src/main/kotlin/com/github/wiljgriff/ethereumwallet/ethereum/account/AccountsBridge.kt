package com.github.wiljgriff.ethereumwallet.ethereum.account

import com.github.wiljgriff.ethereumwallet.data.model.DomainAccount
import org.ethereum.geth.Account
import org.ethereum.geth.AccountManager

/**
 * Created by williamgriffiths on 16/04/2017.
 */
class AccountsBridge(val accountManager: AccountManager) {

    fun newAccount(password: String): DomainAccount {
        val newAccount = accountManager.newAccount(password)
        return DomainAccount.fromAccount(newAccount)
    }

    fun getAccounts(): List<DomainAccount> {
        return getGethAccountsAsList()
                .map { DomainAccount.fromAccount(it) }
                .toList()
    }

    fun deleteAccount(activeAccount: DomainAccount, password: String) {
        val gethAccount = getGethAccountsAsList()
                .filter { it.address.hex == activeAccount.address.hex }
                .singleOrNull()
        accountManager.deleteAccount(gethAccount, password)
    }

    private fun getGethAccountsAsList(): List<Account> {
        val accounts = accountManager.accounts
        return 0L.rangeTo(accounts.size() - 1)
                .map { accounts.get(it) }
    }
}