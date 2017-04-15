package com.github.wiljgriff.ethereumwallet.di

import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountBalance
import com.github.wiljgriff.ethereumwallet.ethereum.account.WalletAccountManager
import com.github.wiljgriff.ethereumwallet.ethereum.node.EthereumNode
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by williamgriffiths on 15/04/2017.
 */
@Module
class AccountBalanceModule {

    @Provides
    @Singleton
    fun providesAccountBalance(ethereumNode: EthereumNode, walletAccountManager: WalletAccountManager): AccountBalance {
        return AccountBalance(ethereumNode, walletAccountManager)
    }
}