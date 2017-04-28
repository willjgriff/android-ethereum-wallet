package com.github.willjgriff.ethereumwallet.ethereum.address.balance

import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.ethereum.common.Denomination
import com.github.willjgriff.ethereumwallet.ethereum.common.fromWeiTo
import com.github.willjgriff.ethereumwallet.utils.androidIoSchedule
import com.github.willjgriff.ethereumwallet.utils.replayConnect
import io.reactivex.Observable
import java.math.BigInteger
import java.util.concurrent.TimeUnit

/**
 * Created by williamgriffiths on 15/04/2017.
 */
class AddressBalance(private val addressBalanceAdapter: AddressBalanceAdapter, private val addressManager: AddressManager) {

    private val EMISSION_INTERVAL_SECONDS = 1L
    private val storedAddressBalances: MutableMap<String, Observable<String>> = mutableMapOf()
    private val storedPendingAddressBalances: MutableMap<String, Observable<String>> = mutableMapOf()

    fun getBalanceAtActiveAddress(): Observable<String> {
        val activeAddress = addressManager.getActiveAddress().hex
        return storedAddressBalances.getOrPut(activeAddress,
                { getBigIntReplayObservableForFunc { addressBalanceAdapter.getBalanceAt(activeAddress) } })
    }

    fun getPendingBalanceAtActiveAddress(): Observable<String> {
        val activeAddress = addressManager.getActiveAddress().hex
        return storedPendingAddressBalances.getOrPut(activeAddress,
                { getBigIntReplayObservableForFunc { addressBalanceAdapter.getPendingBalanceAt(activeAddress) } })
    }

    private fun getBigIntReplayObservableForFunc(function: () -> BigInteger): Observable<String> = Observable
            .interval(EMISSION_INTERVAL_SECONDS, TimeUnit.SECONDS)
            .map { function }
            .map { it.invoke() }
            .map { it.fromWeiTo(Denomination.ETHER) }
            .map { it.toString() }
            .distinctUntilChanged()
            .replayConnect(1)
            .androidIoSchedule()
}