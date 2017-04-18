package com.github.willjgriff.ethereumwallet.ethereum.transaction

import com.github.willjgriff.ethereumwallet.data.model.DomainAddress
import com.github.willjgriff.ethereumwallet.data.model.DomainTransaction
import com.github.willjgriff.ethereumwallet.ethereum.account.asList
import com.github.willjgriff.ethereumwallet.ethereum.account.getGethAccountFromAddress
import org.ethereum.geth.*

/**
 * Created by williamgriffiths on 17/04/2017.
 */
class TransactionAdapter(private val keyStore: KeyStore,
                         private val ethereumClient: EthereumClient,
                         private val context: Context) {

    val STANDARD_TRANSACTION_GAS_LIMIT = 2100L

    fun submitTransaction(domainTransaction: DomainTransaction) {
        // Convert domainTransaction to ethereum Transaction
        ethereumClient.sendTransaction(context, null)
    }

    fun createSignedTransaction(fromAccount: DomainAddress, toAddressString: String,
                                password: String, amountLong: Long): Transaction {

        val toAddress = Address(toAddressString)
        val amount = BigInt(amountLong)
        val gasLimit = BigInt(STANDARD_TRANSACTION_GAS_LIMIT) // More than is required to execute the transaction on the Blockchain, 2100 from MyEtherWallet.
        val gasPrice = ethereumClient.suggestGasPrice(context) // The cost in Ether for each unit of Gas, in Wei, probably 20000000000
        val nonce = 1L // WHAT IS THIS, research

        // NewTransaction(nonce uint64, to common.Address, amount, gasLimit, gasPrice *big.Int, data []byte)
        val unsignedTransaction = Transaction(nonce, toAddress, amount, gasLimit, gasPrice, null)

        val gethAccount = keyStore.accounts.asList().getGethAccountFromAddress(fromAccount)
        val mainNetChainIdentifier = BigInt(1)

        return keyStore.signTxPassphrase(gethAccount, password, unsignedTransaction, mainNetChainIdentifier)
    }

}