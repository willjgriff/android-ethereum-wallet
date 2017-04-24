package com.github.willjgriff.ethereumwallet.ethereum.transaction

import com.github.willjgriff.ethereumwallet.ethereum.address.model.DomainAddress
import com.github.willjgriff.ethereumwallet.ethereum.transaction.model.SendTransaction
import com.github.willjgriff.ethereumwallet.ethereum.address.asList
import com.github.willjgriff.ethereumwallet.ethereum.address.getGethAccountFromAddress
import org.ethereum.geth.*

/**
 * Created by williamgriffiths on 17/04/2017.
 */
class TransactionAdapter(private val keyStore: KeyStore,
                         private val ethereumClient: EthereumClient,
                         private val context: Context) {

    val STANDARD_TRANSACTION_GAS_LIMIT = 2100L
    val MAIN_NET_CHAIN_ID_NUMBER = 1L

    fun submitTransaction(sendTransaction: SendTransaction, fromAddress: DomainAddress, password: String) {
        val unsignedTransaction = getUnsignedTransaction(sendTransaction.amount, fromAddress, sendTransaction.toAddress)
        val signedTransaction = signTransaction(fromAddress, password, unsignedTransaction)

        ethereumClient.sendTransaction(context, signedTransaction)
    }

    private fun signTransaction(fromAccount: DomainAddress, password: String, unsignedTransaction: Transaction): Transaction {
        val gethAccount = keyStore.accounts.asList().getGethAccountFromAddress(fromAccount)
        val mainNetChainIdentifier = BigInt(MAIN_NET_CHAIN_ID_NUMBER)

        return keyStore.signTxPassphrase(gethAccount, password, unsignedTransaction, mainNetChainIdentifier)
    }

    private fun getUnsignedTransaction(amountLong: Long, fromAddress: DomainAddress, toAddressString: String): Transaction {
        val gethAccount = keyStore.accounts.asList().getGethAccountFromAddress(fromAddress)
        val nonce = ethereumClient.getPendingNonceAt(context, gethAccount.address) // The nonce increases by one for each transaction
        val toAddress = Address(toAddressString)
        val amount = BigInt(amountLong)
        val gasLimit = BigInt(STANDARD_TRANSACTION_GAS_LIMIT) // More than is required to execute the transaction on the Blockchain, 2100 from MyEtherWallet
        val gasPrice = ethereumClient.suggestGasPrice(context) // The cost in Ether for each unit of Gas, in Wei, probably 20000000000

        // Transaction(nonce uint64, to common.Address, amount, gasLimit, gasPrice *big.Int, data []byte)
        return Transaction(nonce, toAddress, amount, gasLimit, gasPrice, null)
    }

}