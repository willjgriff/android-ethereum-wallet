package com.github.willjgriff.ethereumwallet.ethereum.transaction

import com.github.willjgriff.ethereumwallet.data.model.DomainAddress
import com.github.willjgriff.ethereumwallet.data.model.DomainTransaction
import org.ethereum.geth.*
import java.math.BigInteger

/**
 * Created by williamgriffiths on 19/04/2017.
 */
class TransactionsAdapter(private val node: Node, private val ethereumClient: EthereumClient, private val context: Context) {

    // TODO: This may need to be replaced with currentBlock
//    private val currentBlock = ethereumClient.syncProgress(context).highestBlock

    fun getTransactionsInBlockRange(address: DomainAddress, fromBlock: Long, toBlock: Long): List<DomainTransaction> {
        // TODO: Fix this.
        if (node.peersInfo.size() > 0) {
            return fromBlock.rangeTo(toBlock)
                    .map { ethereumClient.getBlockByNumber(context, it) }
                    .flatMap { block -> getTransactionsForBlock(block) }
                    .filter { it.from.hex == address.hex || it.from.hex == address.hex }
                    .map { createDomainTransaction(it) }
        } else {
            return listOf()
        }
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