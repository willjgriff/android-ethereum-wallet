package com.github.wiljgriff.ethereumwallet.ethereum.account

import com.github.wiljgriff.ethereumwallet.data.model.DomainAccount
import org.ethereum.geth.Account
import org.ethereum.geth.Accounts
import org.ethereum.geth.KeyStore


/**
 * Created by williamgriffiths on 16/04/2017.
 */
class AccountsAdapter(val keyStore: KeyStore) {

    fun newAccount(password: String): DomainAccount {
        val newAccount = keyStore.newAccount(password)
        return DomainAccount.fromAccount(newAccount)
    }

    fun getAccounts(): List<DomainAccount> = keyStore.accounts.asList()
            .map { DomainAccount.fromAccount(it) }
            .toList()

    fun deleteAccount(activeAccount: DomainAccount, password: String) {
        val gethAccount = keyStore.accounts.asList().getGethAccountFromDomainAccount(activeAccount)
        keyStore.deleteAccount(gethAccount, password)
    }
}