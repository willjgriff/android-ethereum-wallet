package com.github.wiljgriff.ethereumwallet.ethereum.account

import com.github.wiljgriff.ethereumwallet.data.transformers.AndroidIoTransformer
import com.github.wiljgriff.ethereumwallet.ethereum.node.EthereumNode
import io.reactivex.Observable
import org.ethereum.geth.BigInt
import java.math.BigInteger

/**
 * Created by williamgriffiths on 15/04/2017.
 */
class AccountBalance(private val ethereumNode: EthereumNode, private val walletAccountManager: WalletAccountManager) {

    private val WEI_TO_ETHER_DIVISOR = "1000000000000000000"
    private val storedAddressBalances: MutableMap<String, Observable<String>> = mutableMapOf()

    fun getBalanceAtActiveAddress(): Observable<String> {
        val activeAddress = walletAccountManager.getActiveAccountAddress().getAddress()
        return storedAddressBalances.getOrPut(activeAddress.hex,
                { getBigIntReplayObservableForFunc { ethereumNode.getBalanceAt(activeAddress) } })
    }

    fun getPendingBalanceAtActiveAddress(): Observable<String> {
        val activeAddress = walletAccountManager.getActiveAccountAddress().getAddress()
        return getBigIntReplayObservableForFunc { ethereumNode.getPendingBalanceAt(activeAddress) }
    }

    private fun getBigIntReplayObservableForFunc(function: () -> BigInt): Observable<String> = Observable
            .just(function)
            .map { it.invoke() }
            .map { BigInteger(it.string()) }
            .map { it.div(BigInteger(WEI_TO_ETHER_DIVISOR)) }
            .map { it.toString() }
            .replay(1)
            .autoConnect()
            .compose(AndroidIoTransformer())
}