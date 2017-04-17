package com.github.wiljgriff.ethereumwallet.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsBridge
import com.github.wiljgriff.ethereumwallet.ethereum.account.ActiveAccountAddress
import com.github.wiljgriff.ethereumwallet.ethereum.account.WalletAccountManager
import dagger.Module
import dagger.Provides
import org.ethereum.geth.Geth
import org.ethereum.geth.KeyStore
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by williamgriffiths on 17/04/2017.
 */
@Module
class WalletAccountModule {

    @Provides
    @Singleton
    @Named("key_store_location")
    fun providesKeyStoreLocation(context: Context): String {
        return context.filesDir.toString() + "/key_store_location/"
    }

    @Provides
    @Singleton
    fun providesKeyStore(@Named("key_store_location") keyStoreLocation: String): KeyStore {
        return KeyStore(keyStoreLocation, Geth.LightScryptN, Geth.LightScryptP)
    }

    @Provides
    @Singleton
    fun providesAccountsBridge(keyStore: KeyStore): AccountsBridge {
        return AccountsBridge(keyStore)
    }

    @Provides
    @Singleton
    fun providesActiveAccountAddress(sharedPreferences: SharedPreferences): ActiveAccountAddress {
        return ActiveAccountAddress(sharedPreferences)
    }

    @Provides
    @Singleton
    fun providesEthereumAccountManager(accountsBridge: AccountsBridge,
                                       activeAccountAddress: ActiveAccountAddress): WalletAccountManager {
        return WalletAccountManager(accountsBridge, activeAccountAddress)
    }
}
