package com.github.wiljgriff.ethereumwallet.di

import com.github.wiljgriff.ethereumwallet.di.modules.AccountBalanceModule
import com.github.wiljgriff.ethereumwallet.di.modules.AppModule
import com.github.wiljgriff.ethereumwallet.di.modules.EthereumNodeModule
import com.github.wiljgriff.ethereumwallet.di.modules.WalletAccountModule
import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountBalance
import com.github.wiljgriff.ethereumwallet.ethereum.account.WalletAccountManager
import com.github.wiljgriff.ethereumwallet.ethereum.node.NodeBridge
import com.github.wiljgriff.ethereumwallet.ethereum.node.NodeDetails
import com.github.willjgriff.ethereumwallet.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by williamgriffiths on 17/04/2017.
 */
@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        WalletAccountModule::class,
        EthereumNodeModule::class,
        AccountBalanceModule::class))
interface AppComponent {

    fun provideEthereumNode(): NodeBridge

    fun provideNodeDetails(): NodeDetails

    fun provideAccountManager(): WalletAccountManager

    fun provideAccountBalance(): AccountBalance

    fun inject(mainActivity: MainActivity)
}