package com.github.willjgriff.ethereumwallet.ethereum.transactions

import com.github.willjgriff.ethereumwallet.ethereum.address.model.DomainAddress
import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.DomainBlock
import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.DomainTransaction
import io.reactivex.Observable
import io.reactivex.Single
import org.ethereum.geth.Block
import org.ethereum.geth.Context
import org.ethereum.geth.EthereumClient
import org.ethereum.geth.Transaction
import timber.log.Timber
import java.math.BigInteger
import java.util.concurrent.TimeUnit

/**
 * Created by williamgriffiths on 19/04/2017.
 */
class TransactionsAdapter(private val ethereumClient: EthereumClient, private val context: Context) {

    private val DEFAULT_TO_CURRENT_BLOCK_VALUE = -1L
    private val TRY_GET_BLOCK_NUMBER_INTERVAL_SECONDS = 1L

    fun getCurrentBlock(): Single<Long> {
        return Observable.interval(TRY_GET_BLOCK_NUMBER_INTERVAL_SECONDS, TimeUnit.SECONDS)
                .map { tryGetBlockByNumber() }
                .filter { it > 0 }
                .firstOrError()
    }

    private fun tryGetBlockByNumber(): Long = try {
        ethereumClient.getBlockByNumber(context, DEFAULT_TO_CURRENT_BLOCK_VALUE).number
    } catch (exception: Exception) {
        -1L
    }

    fun getTransactionsInBlockRange(address: DomainAddress, fromBlock: Long, numberOfBlocks: Long, blockSearchedFunc: (Long) -> Unit): Observable<DomainTransaction> =
            Observable
                    .rangeLong(0, numberOfBlocks)
                    .map { fromBlock - it }
                    .flatMap { getTransactionsForAddressInBlock(address, it, blockSearchedFunc) }

    fun getTransactionsForAddressInBlock(address: DomainAddress, blockNumber: Long, blockSearchedFunc: (Long) -> Unit): Observable<DomainTransaction> =
            Observable
                    .just(retryGetBlockByNumber(blockNumber))
                    .filterErroneousBlocks()
                    .doOnNext {
                        Timber.d("Block successfully found: ${it.number}")
                        blockSearchedFunc.invoke(it.number)
                    }
                    .flatMap { getTransactionsInBlockThatIncludeAddress(address, it) }

    /**
     * [EthereumClient.getBlockByNumber] may throw an error, currently unsure what scenario causes this.
     * Alternatively we catch the error and return a [DomainBlock] wrapper with a null [Block] field.
     * The [DomainBlock] is necessary as RxJava will error if we try to pass null through it. We then
     * filter the null blocks.
     */
    private fun retryGetBlockByNumber(blockNumber: Long): DomainBlock =
            try {
                DomainBlock(ethereumClient.getBlockByNumber(context, blockNumber))
            } catch (exception: Exception) {
                DomainBlock(null)
            }

    private fun Observable<DomainBlock>.filterErroneousBlocks() =
            filter { it.block != null }
                    .map { it.block!! }

    private fun getTransactionsInBlockThatIncludeAddress(address: DomainAddress, block: Block) =
            getTransactionsForBlock(block)
                    .filter { addressInTransaction(address, it) }
                    .map { createDomainTransaction(it, block) }

    private fun getTransactionsForBlock(block: Block): Observable<Transaction> =
            Observable
                    .rangeLong(0, block.transactions.size())
                    .map { block.transactions.get(it) }

    private fun addressInTransaction(address: DomainAddress, it: Transaction): Boolean {
        val from = tryStringFuncCatchEmpty { it.from.hex }
        val to = tryStringFuncCatchEmpty { it.to.hex }
//        Timber.d("Transaction from: $from to: $to")
        return from == address.hex || to == address.hex
    }

    private fun createDomainTransaction(transaction: Transaction, block: Block): DomainTransaction {
        val fromAddress = DomainAddress(tryStringFuncCatchEmpty { transaction.from.hex })
        val toAddress = DomainAddress(transaction.to.hex)
        val value = BigInteger(transaction.value.string())
        val time = block.time
        return DomainTransaction(fromAddress, toAddress, value, time)
    }

    private fun tryStringFuncCatchEmpty(func: () -> String): String =
            try {
                func.invoke()
            } catch (exception: Exception) {
                ""
            }
}
