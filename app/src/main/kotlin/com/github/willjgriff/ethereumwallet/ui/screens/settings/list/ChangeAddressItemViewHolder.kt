package com.github.willjgriff.ethereumwallet.ui.screens.settings.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.willjgriff.ethereumwallet.ethereum.address.model.DomainAddress
import kotlinx.android.synthetic.main.view_change_address_item.view.*

/**
 * Created by williamgriffiths on 04/05/2017.
 */
class ChangeAddressItemViewHolder(itemView: View,
                                  private val changeAddressItemListener: ChangeAddressItemViewHolder.ChangeAddressItemListener)
    : RecyclerView.ViewHolder(itemView) {

    private var address: TextView = itemView.view_change_address_item_address

    fun bind(domainAddress: DomainAddress) {
        address.text = domainAddress.hex
        itemView.setOnClickListener { _ -> changeAddressItemListener.addressItemClicked(domainAddress) }
    }

    interface ChangeAddressItemListener {
        fun addressItemClicked(domainAddress: DomainAddress)
    }
}