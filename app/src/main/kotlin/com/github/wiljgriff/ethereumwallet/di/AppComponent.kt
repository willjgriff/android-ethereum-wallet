package com.github.wiljgriff.ethereumwallet.di

import com.github.wiljgriff.ethereumwallet.di.modules.*
import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountBalance
import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsBridge
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
        GethBridgeModule::class,
        WalletAccountModule::class,
        AccountBalanceModule::class,
        EthereumNodeModule::class)
)
interface AppComponent {

    // Objects including use of Geth classes, ideally I would use some kind of scoping mechanism,
    // but I lost my patience trying to figure it out.
    fun provideNodeBridge(): NodeBridge

    fun provideAccountsBridge(): AccountsBridge

    // Objects independent of Geth classes
    fun provideNodeDetails(): NodeDetails

    fun provideAccountManager(): WalletAccountManager

    fun providesAccountBalance(): AccountBalance

    fun inject(mainActivity: MainActivity)
}