package com.github.wiljgriff.ethereumwallet.di.modules

import android.content.SharedPreferences
import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsBridge
import com.github.wiljgriff.ethereumwallet.ethereum.account.ActiveAccountAddress
import com.github.wiljgriff.ethereumwallet.ethereum.account.WalletAccountManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by williamgriffiths on 17/04/2017.
 */
@Module
class WalletAccountModule {

    @Provides
    @Singleton
    fun providesActiveAccountAddress(sharedPreferences: SharedPreferences) =
            ActiveAccountAddress(sharedPreferences)

    @Provides
    @Singleton
    fun providesEthereumAccountManager(accountsBridge: AccountsBridge,
                                       activeAccountAddress: ActiveAccountAddress) =
            WalletAccountManager(accountsBridge, activeAccountAddress)
}
