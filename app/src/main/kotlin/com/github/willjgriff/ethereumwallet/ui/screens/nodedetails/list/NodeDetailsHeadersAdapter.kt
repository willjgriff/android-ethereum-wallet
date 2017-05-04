package com.github.willjgriff.ethereumwallet.ui.screens.nodedetails.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.ui.utils.inflate
import kotlinx.android.synthetic.main.view_node_status_header_item.view.*

/**
 * Created by williamgriffiths on 11/04/2017.
 */
class NodeDetailsHeadersAdapter(private val adapterListener: NodeStatusHeadersAdapterListener)
    : RecyclerView.Adapter<NodeDetailsHeadersAdapter.NodeStatusHeaderViewHolder>() {

    private val headers: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeStatusHeaderViewHolder =
            NodeStatusHeaderViewHolder(parent.inflate(R.layout.view_node_status_header_item))

    override fun getItemCount(): Int =
            headers.size

    override fun onBindViewHolder(holder: NodeStatusHeaderViewHolder, position: Int) =
            holder.bind(headers[position])

    fun addHeaderHash(headerHash: String) {
        headers.add(headerHash)
        notifyItemInserted(headers.size - 1)
        adapterListener.headerInserted(headers.size - 1)
    }

    class NodeStatusHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerHash: TextView by lazy { itemView.view_node_status_header_item_hash }
        fun bind(headerHash: String) {
            this.headerHash.text = headerHash
        }
    }

    interface NodeStatusHeadersAdapterListener {
        fun headerInserted(position: Int)
    }
}