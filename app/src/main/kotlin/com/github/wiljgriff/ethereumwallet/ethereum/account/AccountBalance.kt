package com.github.wiljgriff.ethereumwallet.ethereum.account

import com.github.wiljgriff.ethereumwallet.data.transformers.AndroidIoTransformer
import com.github.wiljgriff.ethereumwallet.ethereum.node.EthereumNode
import io.reactivex.Observable

/**
 * Created by williamgriffiths on 15/04/2017.
 */
class AccountBalance(private val ethereumNode: EthereumNode, private val walletAccountManager: WalletAccountManager) {

    private val storedAddressBalances: MutableMap<String, Observable<String>> = mutableMapOf()

    // TODO: Try to tidy this up.
    fun getBalanceAtActiveAddress(): Observable<String> {
        val activeAddress = walletAccountManager.getActiveAccountAddress().getAddress()

        val storedAddressBalance = storedAddressBalances.get(activeAddress.hex)
        val addressBalanceObservable = getBigIntReplayObservableForFunc { ethereumNode.getBalanceAt(activeAddress) }

        if (storedAddressBalance == null) {
            storedAddressBalances.put(activeAddress.hex, addressBalanceObservable)
            return addressBalanceObservable
        }

        return storedAddressBalance
    }

    fun getPendingBalanceAtActiveAddress(): Observable<String> {
        val activeAddress = walletAccountManager.getActiveAccountAddress().getAddress()
        return getBigIntReplayObservableForFunc { ethereumNode.getPendingBalanceAt(activeAddress) }
    }

    private fun <T> getBigIntReplayObservableForFunc(function: () -> T): Observable<String> = Observable
            .just(function)
            .map { it.invoke() }
            // TODO: Divide by a gazillion!
//            .map { it. }
            .map { it.toString() }
            .replay(1)
            .autoConnect()
            .compose(AndroidIoTransformer())
}