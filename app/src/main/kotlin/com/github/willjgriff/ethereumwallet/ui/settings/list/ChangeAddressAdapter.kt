package com.github.willjgriff.ethereumwallet.ui.settings.list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.ethereum.address.model.DomainAddress
import com.github.willjgriff.ethereumwallet.ui.settings.list.ChangeAddressAdapter.ChangeAddressType.HEADER
import com.github.willjgriff.ethereumwallet.ui.settings.list.ChangeAddressAdapter.ChangeAddressType.ITEM
import com.github.willjgriff.ethereumwallet.ui.settings.list.ChangeAddressItemViewHolder.ChangeAddressItemListener
import com.github.willjgriff.ethereumwallet.ui.utils.inflate

/**
 * Created by williamgriffiths on 04/05/2017.
 */
class ChangeAddressAdapter(private val changeAddressItemListener: ChangeAddressItemListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val HEADER_OFFSET = 1

    var accounts: List<DomainAddress> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    internal enum class ChangeAddressType {
        HEADER,
        ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        when (ChangeAddressType.values()[viewType]) {
            HEADER -> return ChangeAddressHeaderViewHolder(parent.inflate(R.layout.view_change_address_header))
            ITEM -> return ChangeAddressItemViewHolder(parent.inflate(R.layout.view_change_address_item), changeAddressItemListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChangeAddressItemViewHolder) {
            holder.bind(accounts[position - HEADER_OFFSET])
        }
    }

    override fun getItemCount(): Int {
        return accounts.size + HEADER_OFFSET
    }

    override fun getItemViewType(position: Int): Int =
            when (position) {
                0 -> HEADER.ordinal
                else -> ITEM.ordinal
            }
}