package com.github.wiljgriff.ethereumwallet.ethereum.node

import android.content.Context

/**
 * Created by Will on 16/03/2017.
 */
class EthereumClient (context: Context) {

    // TODO: Move to dagger construction.
    val ethereumFileLocation = context.filesDir.toString() + "/ethereum/"

    fun start() {

    }
    
}

//fun doSome() {
//    var eth = EthereumClient(context = new AnCont)
//    eth.
//}