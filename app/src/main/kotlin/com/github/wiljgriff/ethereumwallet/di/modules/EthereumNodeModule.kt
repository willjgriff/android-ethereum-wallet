package com.github.wiljgriff.ethereumwallet.di.modules

import android.content.Context
import com.github.wiljgriff.ethereumwallet.ethereum.node.EthereumDelegate
import com.github.wiljgriff.ethereumwallet.ethereum.node.NodeDetails
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by williamgriffiths on 15/04/2017.
 */
@Module
class EthereumNodeModule {

    @Provides
    @Singleton
    @Named("ethereum_path")
    fun provideEthereumPath(context: Context): String {
        return context.filesDir.toString() + "/ethereum/"
    }

    @Provides
    @Singleton
    fun provideEthereumNode(@Named("ethereum_path") ethereumPath: String): EthereumDelegate {
        return EthereumDelegate(ethereumPath)
    }

    @Provides
    @Singleton
    fun provideNodeDetails(ethereumDelegate: EthereumDelegate): NodeDetails {
        return NodeDetails(ethereumDelegate)
    }
}