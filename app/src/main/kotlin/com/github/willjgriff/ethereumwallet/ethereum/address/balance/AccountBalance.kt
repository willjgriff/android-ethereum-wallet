package com.github.willjgriff.ethereumwallet.ethereum.address.balance

import com.github.willjgriff.ethereumwallet.data.transformers.AndroidIoTransformer
import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.ethereum.address.balance.AccountBalanceAdapter
import io.reactivex.Observable
import java.math.BigInteger

/**
 * Created by williamgriffiths on 15/04/2017.
 */
class AccountBalance(private val accountBalanceAdapter: AccountBalanceAdapter, private val addressManager: AddressManager) {

    private val WEI_TO_ETHER_DIVISOR = "1000000000000000000"
    private val storedAddressBalances: MutableMap<String, Observable<String>> = mutableMapOf()

    fun getBalanceAtActiveAddress(): Observable<String> {
        val activeAddress = addressManager.getActiveAddress().hex
        return storedAddressBalances.getOrPut(activeAddress,
                { getBigIntReplayObservableForFunc { accountBalanceAdapter.getBalanceAt(activeAddress) } })
    }

    fun getPendingBalanceAtActiveAddress(): Observable<String> {
        val activeAddress = addressManager.getActiveAddress().hex
        return getBigIntReplayObservableForFunc { accountBalanceAdapter.getPendingBalanceAt(activeAddress) }
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