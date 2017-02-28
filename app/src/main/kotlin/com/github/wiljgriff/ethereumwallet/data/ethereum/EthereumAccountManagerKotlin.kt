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
class EthereumAccountManagerKotlin(var accountManager: AccountManagerDelegate, var activeAccountAddress: ActiveAccountAddress) {

    fun createAccount(password: String): AccountDelegate {
        val newAccount = accountManager.newAccount(password)
        activeAccountAddress.set(newAccount.getAddress().getHex())
        return newAccount
    }

    // TODO: make more efficient.
    fun getActiveAccount(): AccountDelegate? {
        var account: AccountDelegate? = null
        if (accountAvailableWithAddress(activeAccountAddress.get())) {
            return getAllAccounts().filter { it.getAddress().getHex() == activeAccountAddress.get() }
                    .single()
        }
        return account
    }

    fun hasAccount(): Boolean = getAllAccounts().isNotEmpty()

    private fun accountAvailableWithAddress(position: String): Boolean {
        return getAllAccounts()
                .filter { it.getAccount().address.hex == position }
                .map { true }
                .single()
    }

    fun getAllAccounts(): List<AccountDelegate> {
        val accountsList: List<AccountDelegate> = ArrayList()
        val accounts = accountManager.getAccounts()

        for (position in 0..accounts.size() - 1) {
            accountsList.plus(accounts.get(position))
        }

        return accountsList
    }

    fun verifyPassword(password: String): Boolean {
        // TODO: To be implemented
        return true;
    }

    fun deleteActiveAccount(password: String) {
        // TODO: To be implemented
    }

}