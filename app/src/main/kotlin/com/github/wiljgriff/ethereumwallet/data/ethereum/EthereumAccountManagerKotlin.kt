package com.github.wiljgriff.ethereumwallet.data.ethereum

import com.github.wiljgriff.ethereumwallet.data.ethereum.delegates.AccountDelegate
import com.github.wiljgriff.ethereumwallet.data.ethereum.delegates.AccountManagerDelegate
import org.ethereum.geth.Account
import java.util.*

/**
 * Created by Will on 06/02/2017.
 *
 * TODO: Consider putting everything in Try Catch block (why doesn't Kotlin need this but Java does?)
 */
class EthereumAccountManagerKotlin(var accountManager: AccountManagerDelegate, var activeAccountPosition: Long) {

    fun createAccount(password: String): AccountDelegate {
        activeAccountPosition = accountManager.getAccounts().size()
        return accountManager.newAccount(password)
    }

    fun getActiveAccount(): AccountDelegate? {
        var account: AccountDelegate? = null
        if (accountAvailableAtPosition(activeAccountPosition)) {
            account = accountManager.getAccounts().get(activeAccountPosition)
        }
        return account
    }

    // Instr test
    fun hasAccount(): Boolean = accountAvailableAtPosition(0)

    private fun accountAvailableAtPosition(position: Long) =
            (accountManager.getAccounts().size() >= position
                    && accountManager.getAccounts().get(position) != null)

    fun getAllAccounts(): List<AccountDelegate> {
        var accountsList: List<AccountDelegate> = ArrayList()
        var accounts = accountManager.getAccounts()

        for (position in 0..accounts.size() - 1) {
            accountsList.plus(accounts.size())
        }

        return accountsList
    }

}