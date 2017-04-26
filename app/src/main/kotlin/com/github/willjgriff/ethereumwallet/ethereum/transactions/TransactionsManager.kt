package com.github.willjgriff.ethereumwallet.ethereum.transactions

import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager
import com.github.willjgriff.ethereumwallet.ethereum.node.NewBlockHeaderAdapter
import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.DomainTransaction
import com.github.willjgriff.ethereumwallet.ethereum.transactions.storage.TransactionsStorage
import com.github.willjgriff.ethereumwallet.extensions.androidIoSchedule
import io.reactivex.Observable

/**
 * Created by williamgriffiths on 19/04/2017.
 */
class TransactionsManager(private val transactionsStorage: TransactionsStorage,
                          private val transactionsAdapter: TransactionsAdapter,
                          private val newBlockHeaderAdapter: NewBlockHeaderAdapter,
                          private val addressManager: AddressManager) {

    private val NUMBER_OF_BLOCKS_TO_SEARCH = 500L

    val blocksSearchedLogger = BlocksSearchedLogger(transactionsStorage)
    val transactionsObservable: Observable<DomainTransaction> by lazy {
        getTransactions()
    }

    /**
     * Note that [DomainTransaction]'s are emitted in the order they are found, not in order of when they were made.
     * The caller must order the transactions themselves if necessary, eg by time.
     *
     * TODO: This should continue until either the transaction block to search to has been reached or the page
     * max has been reached. Also, we should probably only search to the block when the address was created.
     */
    private fun getTransactions(): Observable<DomainTransaction> {
        val storedTransactions = Observable.fromIterable(transactionsStorage.getStoredTransactions())
        val unstoredPastTransactions = getPastBlocksTransactions()
        val unstoredFutureTransactions = getCurrentBlockTransactions()
        return storedTransactions
                .mergeWith(unstoredPastTransactions)
                .mergeWith(unstoredFutureTransactions)
                .androidIoSchedule()
                .replay()
                .autoConnect()
    }

    private fun getCurrentBlockTransactions(): Observable<DomainTransaction> {
        val address = addressManager.getActiveAddress()
        return newBlockHeaderAdapter
                .newBlockHeaderObservable
                .flatMap {
                    transactionsAdapter.getTransactionsForAddressInBlock(address, it.blockNumber,
                            { blocksSearchedLogger.blockSearched(it) })
                }
                .doOnNext { transactionsStorage.storeTransaction(it) }
    }

    private fun getPastBlocksTransactions(): Observable<DomainTransaction> {
        val currentBlock = transactionsAdapter.getCurrentBlock()
        return currentBlock
                .toObservable()
                .flatMap { searchAndStoreTransactions(it) }
    }

    private fun searchAndStoreTransactions(currentBlock: Long): Observable<DomainTransaction> {
        val address = addressManager.getActiveAddress()
        val blocksToSearch = blocksSearchedLogger.getBlocksToSearchFromTopBlock(currentBlock, NUMBER_OF_BLOCKS_TO_SEARCH)
        return Observable
                .fromIterable(blocksToSearch)
                .flatMap {
                    transactionsAdapter.getTransactionsInBlockRange(address, it.upperBlock, it.rangeExclusive,
                            { blocksSearchedLogger.blockSearched(it) }
                    )
                }
                .doOnNext { transactionsStorage.storeTransaction(it) }
    }
}