package com.github.willjgriff.ethereumwallet.di.modules

import android.content.Context
import com.github.willjgriff.ethereumwallet.ethereum.EthereumManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by williamgriffiths on 17/04/2017.
 */
@Module
class EthereumModule {

    @Provides
    @Singleton
    fun provideEthereumManager(context: Context) =
            EthereumManager(context)

    @Provides
    @Singleton
    fun provideNodeDetails(ethereumManager: EthereumManager) =
            ethereumManager.nodeDetails

    @Provides
    @Singleton
    fun provideWalletAccountManager(ethereumManager: EthereumManager) =
            ethereumManager.addressManager

    @Provides
    @Singleton
    fun provideAccountBalance(ethereumManager: EthereumManager) =
            ethereumManager.accountBalance

    @Provides
    @Singleton
    fun provideTransactionManager(ethereumManager: EthereumManager) =
            ethereumManager.transactionManager

    @Provides
    @Singleton
    fun provideTransactionsManager(ethereumManager: EthereumManager) =
            ethereumManager.transactionsManager

}