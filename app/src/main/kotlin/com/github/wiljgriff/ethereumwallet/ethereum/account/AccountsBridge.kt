package com.github.wiljgriff.ethereumwallet.ethereum.account

import com.github.wiljgriff.ethereumwallet.data.model.DomainAccount
import org.ethereum.geth.*


/**
 * Created by williamgriffiths on 16/04/2017.
 */
class AccountsBridge(val keyStore: KeyStore) {

    fun newAccount(password: String): DomainAccount {
        val newAccount = keyStore.newAccount(password)
        return DomainAccount.fromAccount(newAccount)
    }

    fun getAccounts(): List<DomainAccount> = getGethAccountsAsList()
            .map { DomainAccount.fromAccount(it) }
            .toList()

    fun deleteAccount(activeAccount: DomainAccount, password: String) {
        val gethAccount = getGethAccountFromDomainAccount(activeAccount)
        keyStore.deleteAccount(gethAccount, password)
    }

    private fun getGethAccountFromDomainAccount(activeAccount: DomainAccount): Account? = getGethAccountsAsList()
            .filter { it.address.hex == activeAccount.address.hex }
            .singleOrNull()

    private fun getGethAccountsAsList(): List<Account> {
        val accounts = keyStore.accounts
        return 0L.rangeTo(accounts.size() - 1)
                .map { accounts.get(it) }
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

        val gethAccount = getGethAccountFromDomainAccount(fromAccount)
        val mainNetChainIdentifier = BigInt(1)

        return keyStore.signTxPassphrase(gethAccount, password, unsignedTransaction, mainNetChainIdentifier)
    }
}