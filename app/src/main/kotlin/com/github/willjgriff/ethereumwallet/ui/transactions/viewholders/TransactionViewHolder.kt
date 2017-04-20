package com.github.willjgriff.ethereumwallet.ui.transactions.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.willjgriff.ethereumwallet.ethereum.transaction.model.DomainTransaction
import kotlinx.android.synthetic.main.view_transaction_item.view.*
import java.util.*

/**
 * Created by williamgriffiths on 19/04/2017.
 */
class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val toAddress: TextView = itemView.view_transaction_item_to_address
    val value: TextView = itemView.view_transaction_item_value

    fun bind(transaction: DomainTransaction) {
        toAddress.text = transaction.toAddress.hex
        val valueString = transaction.value.toString()
        value.text = String.format(Locale.getDefault(), valueString)
    }
}