package com.github.wiljgriff.ethereumwallet.data.ethereum

/**
 * Created by Will on 06/02/2017.
 */
interface EthereumAccountConfig {

    fun getFilePath(): String

    fun getCryptoScryptN(): Long

    fun getCryptoScryptP(): Long
}