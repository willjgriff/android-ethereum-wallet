package com.github.wiljgriff.ethereumwallet.ethereum.transaction

import com.github.wiljgriff.ethereumwallet.data.ethereum.DomainTransaction
import com.github.wiljgriff.ethereumwallet.data.model.DomainAccount
import com.github.wiljgriff.ethereumwallet.ethereum.account.asList
import com.github.wiljgriff.ethereumwallet.ethereum.account.getGethAccountFromDomainAccount
import org.ethereum.geth.*
import java.math.BigInteger

/**
 * Created by williamgriffiths on 17/04/2017.
 */
class TransactionAdapter(private val keyStore: KeyStore,
                         private val ethereumClient: EthereumClient,
                         private val context: Context) {

    fun getSuggestedGasPrice() = BigInteger(ethereumClient.suggestGasPrice(context).string())

    fun submitTransaction(domainTransaction: DomainTransaction) {
        // Convert domainTransaction to ethereum Transaction
        ethereumClient.sendTransaction(context, null)
    }

    fun createSignedTransaction(fromAccount: DomainAccount, toAddressString: String,
                                password: String, amountLong: Long): Transaction {

        val toAddress = Address(toAddressString)
        val amount = BigInt(amountLong)
        // Should use EthereumClient.suggestGas/estimateGas
        val gasLimit = BigInt(2100) // More than is required to execute the transaction on the Blockchain, 2100 from MyEtherWallet.
        val gasPrice = BigInt(20000000000) // The cost in Ether for each unit of Gas, in Wei
        val nonce = 1L // WHAT IS THIS, research

        // NewTransaction(nonce uint64, to common.Address, amount, gasLimit, gasPrice *big.Int, data []byte)
        val unsignedTransaction = Transaction(nonce, toAddress, amount, gasLimit, gasPrice, null)

        val gethAccount = keyStore.accounts.asList().getGethAccountFromDomainAccount(fromAccount)
        val mainNetChainIdentifier = BigInt(1)

        return keyStore.signTxPassphrase(gethAccount, password, unsignedTransaction, mainNetChainIdentifier)
    }

}