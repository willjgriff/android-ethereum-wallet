package com.github.willjgriff.ethereumwallet.ethereum.transactions.storage

import android.content.Context
import android.preference.PreferenceManager
import com.github.willjgriff.ethereumwallet.data.SharedPreferencesManager
import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.BlockRange
import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.DomainTransaction
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Created by williamgriffiths on 24/04/2017.
 */
class SharedPrefsTransactionsStorage(context: Context): TransactionsStorage {

    private val TRANSACTIONS_KEY: String = "com.github.willjgriff.ethereumwallet.ethereum.transactions.storage.SharedPrefsTransactionsStorage;TRANSACTIONS_KEY"
    private val BLOCKS_SEARCHED_KEY = "com.github.willjgriff.ethereumwallet.ethereum.transactions.storage.SharedPrefsTransactionsStorage;BLOCKS_SEARCHED_KEY"

    private val gson: Gson = GsonBuilder().create()
    private val sharedPreferencesManager = SharedPreferencesManager(PreferenceManager.getDefaultSharedPreferences(context), gson)

    private val listDomainTransactionsType: TypeToken<List<DomainTransaction>> = object: TypeToken<List<DomainTransaction>>() {}
    private val transactions: MutableList<DomainTransaction> = sharedPreferencesManager
            .readComplexObjectFromPreferences(TRANSACTIONS_KEY, listDomainTransactionsType)?.toMutableList() ?: mutableListOf()

    private val listBlockRangesType: TypeToken<List<BlockRange>> = object: TypeToken<List<BlockRange>>() {}
    private var blocksSearched: List<BlockRange> = sharedPreferencesManager
            .readComplexObjectFromPreferences(BLOCKS_SEARCHED_KEY, listBlockRangesType) ?: listOf()

    override fun storeTransaction(transaction: DomainTransaction) {
        transactions.add(transaction)
        sharedPreferencesManager.writeObjectToPreferences(TRANSACTIONS_KEY, transactions)
    }

    override fun getStoredTransactions(): List<DomainTransaction> {
        return transactions
    }

    override fun storeBlocksSearched(blocksSearched: List<BlockRange>) {
        this.blocksSearched = blocksSearched
        sharedPreferencesManager.writeObjectToPreferences(BLOCKS_SEARCHED_KEY, this.blocksSearched)
    }

    override fun getBlocksSearched(): List<BlockRange> {
        return blocksSearched
    }

    override fun deleteStoredData() {
        transactions.clear()
        blocksSearched = listOf()
        sharedPreferencesManager.removeObjectFromPrefs(TRANSACTIONS_KEY)
        sharedPreferencesManager.removeObjectFromPrefs(BLOCKS_SEARCHED_KEY)
    }
}