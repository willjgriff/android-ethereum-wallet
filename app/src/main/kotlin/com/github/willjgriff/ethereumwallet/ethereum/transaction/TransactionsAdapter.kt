package com.github.willjgriff.ethereumwallet.ethereum.transaction

import com.github.willjgriff.ethereumwallet.ethereum.address.model.DomainAddress
import com.github.willjgriff.ethereumwallet.ethereum.transaction.model.DomainTransaction
import org.ethereum.geth.*
import timber.log.Timber
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Created by williamgriffiths on 19/04/2017.
 */
class TransactionsAdapter(private val node: Node, private val ethereumClient: EthereumClient, private val context: Context) {

    // TODO: This may need to be replaced with currentBlock
//    private val currentBlock = ethereumClient.syncProgress(context).highestBlock

    fun getTransactionsInBlockRange(address: DomainAddress, fromBlock: Long, toBlock: Long): List<DomainTransaction> {
        // TODO: Fix this.
        return fromBlock.rangeTo(toBlock)
                .map {
                    Timber.d("Blocknumber: $it")
                    retryGetBlockByNumber(it)
                }
                .flatMap { block -> getTransactionsForBlock(block) }
                .filter {
                    val from = tryFuncCatchEmpty { it.from.hex }
                    val to = tryFuncCatchEmpty { it.to.hex }
                    Timber.d("Transaction from: $from to: $to")
                    from == address.hex || to == address.hex
                }
                .map { createDomainTransaction(it) }

    }

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

    private fun getTransactionsForBlock(block: Block): List<Transaction> =
            0L.rangeTo(block.transactions.size() - 1)
                    .map { block.transactions.get(it) }

    private fun createDomainTransaction(transaction: Transaction): DomainTransaction {
        val toAddress = DomainAddress(transaction.to.hex)
        val value = BigInteger(transaction.value.string())
        return DomainTransaction(toAddress, value)
    }
}