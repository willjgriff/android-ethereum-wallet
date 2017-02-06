package com.github.wiljgriff.ethereumwallet.data.ethereum

import org.ethereum.geth.Account
import org.ethereum.geth.AccountManager
import timber.log.Timber

/**
 * Created by Will on 06/02/2017.
 */
class EthereumAccountManagerKotlin(accountManagerConfig: EthereumAccountConfig) {

    private var ethereumAccountManager: AccountManager

    init {
        ethereumAccountManager = AccountManager(accountManagerConfig.getFilePath(),
                accountManagerConfig.getCryptoScryptN(), accountManagerConfig.getCryptoScryptP())
    }

    fun getAccount(): Account {
        var account: Account = Account()
        try {
            if (ethereumAccountManager.accounts.size() > 0 && ethereumAccountManager.accounts.get(0) != null) {
                account = ethereumAccountManager.accounts.get(0)
            }
        } catch (exception: Exception) {
            Timber.e(exception, "Error getting Ethereum account from Account Manager")
        }
        return account
    }

    fun hasAccount(): Boolean = ethereumAccountManager.accounts.size() > 0 && ethereumAccountManager.accounts.get(0) != null
}