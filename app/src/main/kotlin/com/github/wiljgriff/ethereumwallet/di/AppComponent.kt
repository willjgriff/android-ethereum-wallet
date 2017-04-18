package com.github.wiljgriff.ethereumwallet.di

import com.github.wiljgriff.ethereumwallet.di.modules.AppModule
import com.github.wiljgriff.ethereumwallet.di.modules.EthereumModule
import com.github.wiljgriff.ethereumwallet.ethereum.account.balance.AccountBalance
import com.github.wiljgriff.ethereumwallet.ethereum.account.AddressManager
import com.github.wiljgriff.ethereumwallet.ethereum.node.NodeDetails
import com.github.willjgriff.ethereumwallet.EthereumWalletApplication
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

    fun providesAccountBalance(): AccountBalance

    fun inject(mainActivity: MainActivity)

    fun inject(ethereumWalletApplication: EthereumWalletApplication)
}