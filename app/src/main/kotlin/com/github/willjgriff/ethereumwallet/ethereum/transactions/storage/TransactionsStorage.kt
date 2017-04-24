package com.github.willjgriff.ethereumwallet.ethereum.transactions.storage

import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.BlockRange
import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.DomainTransaction

/**
 * Created by williamgriffiths on 24/04/2017.
 *
 * TODO: We may want to extend this to save transactions and blocks searched for different addresses
 */
public interface TransactionsStorage {

    fun storeTransaction(transaction: DomainTransaction)

    fun getStoredTransactions(): List<DomainTransaction>

    fun storeBlocksSearched(blocksSearched: List<BlockRange>)

    fun getBlocksSearched(): List<BlockRange>

    fun deleteStoredData()

}