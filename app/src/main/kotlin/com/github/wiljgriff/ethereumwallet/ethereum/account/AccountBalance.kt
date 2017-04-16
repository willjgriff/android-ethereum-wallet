package com.github.wiljgriff.ethereumwallet.ethereum.account

import com.github.wiljgriff.ethereumwallet.data.transformers.AndroidIoTransformer
import com.github.wiljgriff.ethereumwallet.ethereum.node.EthereumDelegate
import io.reactivex.Observable
import java.math.BigInteger

/**
 * Created by williamgriffiths on 15/04/2017.
 */
class AccountBalance(private val ethereumDelegate: EthereumDelegate, private val walletAccountManager: WalletAccountManager) {

    private val WEI_TO_ETHER_DIVISOR = "1000000000000000000"
    private val storedAddressBalances: MutableMap<String, Observable<String>> = mutableMapOf()

    fun getBalanceAtActiveAddress(): Observable<String> {
        val activeAddress = walletAccountManager.getActiveAccountAddressHex()
        return storedAddressBalances.getOrPut(activeAddress,
                { getBigIntReplayObservableForFunc { ethereumDelegate.getBalanceAt(activeAddress) } })
    }

    fun getPendingBalanceAtActiveAddress(): Observable<String> {
        val activeAddress = walletAccountManager.getActiveAccountAddressHex()
        return getBigIntReplayObservableForFunc { ethereumDelegate.getPendingBalanceAt(activeAddress) }
    }

    private fun getBigIntReplayObservableForFunc(function: () -> BigInteger): Observable<String> = Observable
            .just(function)
            .map { it.invoke() }
            .map { it.div(BigInteger(WEI_TO_ETHER_DIVISOR)) }
            .map { it.toString() }
            .replay(1)
            .autoConnect()
            .compose(AndroidIoTransformer())
}