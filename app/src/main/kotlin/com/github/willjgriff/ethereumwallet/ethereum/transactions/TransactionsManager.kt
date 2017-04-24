package com.github.willjgriff.ethereumwallet.ethereum.transactions

import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.DomainTransaction
import com.github.willjgriff.ethereumwallet.ethereum.transactions.storage.TransactionsStorage
import com.github.willjgriff.ethereumwallet.extensions.androidIoSchedule
import io.reactivex.Observable

/**
 * Created by williamgriffiths on 19/04/2017.
 */
class TransactionsManager(private val transactionsStorage: TransactionsStorage,
                          private val transactionsAdapter: TransactionsAdapter,
                          private val addressManager: AddressManager) {

    private val TRANSACTION_SEARCH_FROM_BLOCK = 3563500L
    private val NUMBER_OF_BLOCKS_TO_SEARCH = 200L

    val blocksSearchedLogger = BlocksSearchedLogger(transactionsStorage)
    val transactionsObservable: Observable<DomainTransaction> by lazy {
        getTransactions()
    }

    // This should continue until either the transaction block to search to has been reached or the page max has been reached.
    private fun getTransactions(): Observable<DomainTransaction> {
        val storedTransactions = Observable.fromIterable(transactionsStorage.getStoredTransactions())
        val searchForTransactions = searchBlocksForTransactions()
        return storedTransactions.mergeWith(searchForTransactions)
    }

    private fun searchBlocksForTransactions(): Observable<DomainTransaction> {
        val address = addressManager.getActiveAddress()
        val blocksToSearch = blocksSearchedLogger.getBlocksToSearchFromTopBlock(TRANSACTION_SEARCH_FROM_BLOCK, NUMBER_OF_BLOCKS_TO_SEARCH)

        return Observable
                .fromIterable(blocksToSearch)
                .flatMap {
                    transactionsAdapter.getTransactionsInBlockRange(address, it.upperBlock, it.rangeExclusive,
                            { blockSearched: Long -> blocksSearchedLogger.blockSearched(blockSearched) })
                }
                .doOnNext { transactionsStorage.storeTransaction(it) }
                // Distinct shouldn't be necessary provided the blocksSearchedLogger works correctly
//                .distinct()
                .androidIoSchedule()
                .replay()
                .autoConnect()
    }
}