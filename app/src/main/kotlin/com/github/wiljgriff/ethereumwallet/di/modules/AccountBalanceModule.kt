package com.github.wiljgriff.ethereumwallet.di.modules

import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountBalance
import com.github.wiljgriff.ethereumwallet.ethereum.account.WalletAccountManager
import com.github.wiljgriff.ethereumwallet.ethereum.node.EthereumBridge
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
    fun providesAccountBalance(ethereumBridge: EthereumBridge, walletAccountManager: WalletAccountManager): AccountBalance {
        return AccountBalance(ethereumBridge, walletAccountManager)
    }
}