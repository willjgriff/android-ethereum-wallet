package com.github.willjgriff.ethereumwallet.ui.transactions.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.willjgriff.ethereumwallet.ethereum.common.Denomination
import com.github.willjgriff.ethereumwallet.ethereum.common.fromWeiTo
import com.github.willjgriff.ethereumwallet.ethereum.transaction.model.DomainTransaction
import kotlinx.android.synthetic.main.view_transaction_item.view.*
import java.text.DateFormat
import java.util.*

/**
 * Created by williamgriffiths on 19/04/2017.
 */
class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val EPOCH_MULTIPLIER = 1000
    val toAddress: TextView = itemView.view_transaction_item_to_address
    val value: TextView = itemView.view_transaction_item_value
    val time: TextView = itemView.view_transaction_item_time

    fun bind(transaction: DomainTransaction) {
        toAddress.text = transaction.toAddress.hex

        val etherValue = transaction.value.fromWeiTo(Denomination.ETHER)
        value.text = String.format(Locale.getDefault(), etherValue.toString())

        val dateFromLong = Date(transaction.time * EPOCH_MULTIPLIER)
        val formattedDateString = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.UK).format(dateFromLong)
        time.text = formattedDateString
    }
}