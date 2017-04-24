package com.github.willjgriff.ethereumwallet.di

import com.github.willjgriff.ethereumwallet.EthereumWalletApplication
import com.github.willjgriff.ethereumwallet.di.modules.AppModule
import com.github.willjgriff.ethereumwallet.di.modules.EthereumModule
import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.ethereum.address.balance.AddressBalance
import com.github.willjgriff.ethereumwallet.ethereum.node.NodeDetails
import com.github.willjgriff.ethereumwallet.ethereum.transaction.TransactionManager
import com.github.willjgriff.ethereumwallet.ethereum.transactions.TransactionsManager
import com.github.willjgriff.ethereumwallet.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by williamgriffiths on 17/04/2017.
 */
@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        EthereumModule::class)
)
interface AppComponent {

    fun provideNodeDetails(): NodeDetails

    fun provideAccountManager(): AddressManager

    fun providesAccountBalance(): AddressBalance

    fun providesTransactionManager(): TransactionManager

    fun providesTransactionsManager(): TransactionsManager

    fun inject(mainActivity: MainActivity)

    fun inject(ethereumWalletApplication: EthereumWalletApplication)
}