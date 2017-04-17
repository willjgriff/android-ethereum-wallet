package com.github.wiljgriff.ethereumwallet.di.modules

import com.github.wiljgriff.ethereumwallet.ethereum.node.NodeBridge
import com.github.wiljgriff.ethereumwallet.ethereum.node.NodeDetails
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by williamgriffiths on 15/04/2017.
 */
@Module
class EthereumNodeModule {

    @Provides
    @Singleton
    fun provideNodeDetails(nodeBridge: NodeBridge): NodeDetails {
        return NodeDetails(nodeBridge)
    }

//    @Provides
//    @Singleton
//    fun provideNodeBridge() {
//        return
//    }
}