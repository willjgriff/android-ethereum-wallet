package com.github.willjgriff.ethereumwallet.ui.screens.transactions.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.DomainTransaction
import com.github.willjgriff.ethereumwallet.ui.screens.transactions.viewholders.TransactionViewHolder
import com.github.willjgriff.ethereumwallet.ui.utils.inflate

/**
 * Created by williamgriffiths on 19/04/2017.
 */
class TransactionsAdapter : RecyclerView.Adapter<TransactionViewHolder>() {

    var transactions: MutableList<DomainTransaction> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) =
            holder.bind(transactions[position])

    override fun getItemCount(): Int =
            transactions.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder =
            TransactionViewHolder(parent.inflate(R.layout.view_transaction_item))

    fun addTransaction(transaction: DomainTransaction) {
        transactions.add(transaction)
        transactions.sortByDescending { it.time }
        notifyDataSetChanged()
    }
}