package com.github.willjgriff.ethereumwallet.ethereum.transactions

import com.github.willjgriff.ethereumwallet.ethereum.address.model.DomainAddress
import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.DomainTransaction
import io.reactivex.Observable
import org.ethereum.geth.Block
import org.ethereum.geth.Context
import org.ethereum.geth.EthereumClient
import org.ethereum.geth.Transaction
import timber.log.Timber
import java.math.BigInteger

/**
 * Created by williamgriffiths on 19/04/2017.
 */
class TransactionsAdapter(private val ethereumClient: EthereumClient, private val context: Context) {

    fun getTransactionsInBlockRange(address: DomainAddress, fromBlock: Long, numberOfBlocks: Long, blockSearchedFunc: (Long) -> Unit): Observable<DomainTransaction> =
            Observable
                    .rangeLong(0, numberOfBlocks)
                    .map { fromBlock - it }
                    .map {
                        Timber.d("Block number: $it")
                        retryGetBlockByNumber(it)
                    }
                    .doOnNext { blockSearchedFunc.invoke(it.number) }
                    .flatMap { getTransactionsInBlockIncludingAddress(address, it) }

    private fun getTransactionsInBlockIncludingAddress(address: DomainAddress, block: Block): Observable<DomainTransaction> {
        return getTransactionsForBlock(block)
                .filter { addressInTransaction(address, it) }
                .map { createDomainTransaction(it, block) }
    }

    private fun addressInTransaction(address: DomainAddress, it: Transaction): Boolean {
        val from = tryFuncCatchEmpty { it.from.hex }
        val to = tryFuncCatchEmpty { it.to.hex }
        Timber.d("Transaction from: $from to: $to")
        return from == address.hex || to == address.hex
    }

    private fun getTransactionsForBlock(block: Block): Observable<Transaction> =
            Observable
                    .rangeLong(0, block.transactions.size())
                    .map { block.transactions.get(it) }

    private fun retryGetBlockByNumber(blockNumber: Long): Block =
            try {
                ethereumClient.getBlockByNumber(context, blockNumber)
            } catch (exception: Exception) {
                Block()
            }

    private fun tryFuncCatchEmpty(func: () -> String): String =
            try {
                func.invoke()
            } catch (exception: Exception) {
                ""
            }

    private fun createDomainTransaction(transaction: Transaction, block: Block): DomainTransaction {
        val fromAddress = DomainAddress(tryFuncCatchEmpty { transaction.from.hex })
        val toAddress = DomainAddress(transaction.to.hex)
        val value = BigInteger(transaction.value.string())
        val time = block.time
        return DomainTransaction(fromAddress, toAddress, value, time)
    }
}