package com.github.wiljgriff.ethereumwallet.ethereum

import android.content.Context
import android.preference.PreferenceManager
import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountBalance
import com.github.wiljgriff.ethereumwallet.ethereum.account.ActiveAccountAddress
import com.github.wiljgriff.ethereumwallet.ethereum.account.WalletAccountManager
import com.github.wiljgriff.ethereumwallet.ethereum.node.NodeDetails

/**
 * Created by williamgriffiths on 17/04/2017.
 *
 * This class should not include any objects from chosen Ethereum implementation, currently Geth.
 */
class EthereumManager(context: Context) {

    private val nodeFilePath = context.filesDir.toString() + "/ethereum_node/"
    private val keyStoreFilePath = context.filesDir.toString() + "/ethereum_key_store/"

    private val gethBridge = GethAdapterFactory(nodeFilePath, keyStoreFilePath)
    private val activeAccountAddress = ActiveAccountAddress(PreferenceManager.getDefaultSharedPreferences(context))

    val nodeDetails = NodeDetails(gethBridge.nodeAdapter)
    val walletAccountManager = WalletAccountManager(gethBridge.accountsAdapter, activeAccountAddress)
    val accountBalance = AccountBalance(gethBridge.nodeAdapter, walletAccountManager)

    fun startEthereum() {
        gethBridge.startSync()
    }
}