package com.github.wiljgriff.ethereumwallet.ethereum.account.balance

import com.github.wiljgriff.ethereumwallet.data.transformers.AndroidIoTransformer
import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsManager
import io.reactivex.Observable
import java.math.BigInteger

/**
 * Created by williamgriffiths on 15/04/2017.
 */
class AccountBalance(private val accountBalanceAdapter: AccountBalanceAdapter, private val accountsManager: AccountsManager) {

    private val WEI_TO_ETHER_DIVISOR = "1000000000000000000"
    private val storedAddressBalances: MutableMap<String, Observable<String>> = mutableMapOf()

    fun getBalanceAtActiveAddress(): Observable<String> {
        val activeAddress = accountsManager.getActiveAccountAddress()
        return storedAddressBalances.getOrPut(activeAddress,
                { getBigIntReplayObservableForFunc { accountBalanceAdapter.getBalanceAt(activeAddress) } })
    }

    fun getPendingBalanceAtActiveAddress(): Observable<String> {
        val activeAddress = accountsManager.getActiveAccountAddress()
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