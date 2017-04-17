package com.github.wiljgriff.ethereumwallet.di.modules

import android.content.Context
import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsBridge
import com.github.wiljgriff.ethereumwallet.ethereum.node.NodeBridge
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
class GethBridgeModule {

    @Provides
    @Singleton
    @Named("ethereum_path")
    fun provideEthereumPath(context: Context) =
            context.filesDir.toString() + "/ethereum/"

    @Provides
    @Singleton
    fun provideEthereumNode(@Named("ethereum_path") ethereumPath: String) =
            NodeBridge(ethereumPath)

    @Provides
    @Singleton
    @Named("ethereum_key_store")
    fun providesKeyStorePath(context: Context) =
            context.filesDir.toString() + "/ethereum_key_store/"

    @Provides
    @Singleton
    fun providesKeyStore(@Named("ethereum_key_store") keyStoreLocation: String) =
            KeyStore(keyStoreLocation, Geth.LightScryptN, Geth.LightScryptP)

    @Provides
    @Singleton
    fun providesAccountsBridge(keyStore: KeyStore) =
            AccountsBridge(keyStore)

}