package com.github.wiljgriff.ethereumwallet.data.ethereum

import android.content.Context
import org.ethereum.geth.Geth

/**
 * Created by Will on 06/02/2017.
 */
class LightEthereumAccountConfig(val context: Context) : EthereumAccountConfig {

    override fun getFilePath(): String = context.getFilesDir() as String + "/ethereumAccount"

    override fun getCryptoScryptN(): Long = Geth.LightScryptN

    override fun getCryptoScryptP(): Long = Geth.LightScryptP

}