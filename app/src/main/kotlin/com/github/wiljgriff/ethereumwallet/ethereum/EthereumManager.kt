package com.github.wiljgriff.ethereumwallet.ethereum

import android.content.Context
import android.preference.PreferenceManager
import com.github.wiljgriff.ethereumwallet.ethereum.account.balance.AccountBalance
import com.github.wiljgriff.ethereumwallet.ethereum.account.ActiveAccountAddress
import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsManager
import com.github.wiljgriff.ethereumwallet.ethereum.adapters.GethAdapterFactory
import com.github.wiljgriff.ethereumwallet.ethereum.node.NodeDetails

/**
 * Created by williamgriffiths on 17/04/2017.
 *
 * This class should not include any objects from the chosen Ethereum implementation, currently Geth.
 * Those objects should be configured and injected into Adapters in the [GethAdapterFactory].
 * The objects the [EthereumManager] returns should only use these adapters and no Ethereum related objects.
 */
class EthereumManager(context: Context) {

    private val nodeFilePath = context.filesDir.toString() + "/ethereum_node/"
    private val keyStoreFilePath = context.filesDir.toString() + "/ethereum_key_store/"

    private val gethAdapterFactory = GethAdapterFactory(nodeFilePath, keyStoreFilePath)
    private val activeAccountAddress = ActiveAccountAddress(PreferenceManager.getDefaultSharedPreferences(context))

    val nodeDetails = NodeDetails(gethAdapterFactory.nodeDetailsAdapter, gethAdapterFactory.accountBalanceAdapter)
    val walletAccountManager = AccountsManager(gethAdapterFactory.accountsAdapter, activeAccountAddress)
    val accountBalance = AccountBalance(gethAdapterFactory.accountBalanceAdapter, walletAccountManager)

    fun startEthereum() {
        gethAdapterFactory.startSync()
    }
}