package com.github.wiljgriff.ethereumwallet.ui.nodestatus.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.wiljgriff.ethereumwallet.ui.utils.inflate
import com.github.willjgriff.ethereumwallet.R
import kotlinx.android.synthetic.main.view_node_status_header_item.view.*
import kotlinx.android.synthetic.main.view_node_status_peer_item.view.*

/**
 * Created by williamgriffiths on 11/04/2017.
 */
class NodeStatusHeadersAdapter : RecyclerView.Adapter<NodeStatusHeadersAdapter.NodeStatusHeaderViewHolder>() {

    private val headers: MutableList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeStatusHeaderViewHolder {
        return NodeStatusHeaderViewHolder(parent.inflate(R.layout.view_node_status_header_item))
    }

    override fun getItemCount(): Int {
        return headers.size
    }

    override fun onBindViewHolder(holder: NodeStatusHeaderViewHolder, position: Int) {
        holder.bind(headers.get(position))
    }

    fun addHeaderHash(headerHash: String) {
        headers.add(headerHash)
        notifyItemInserted(headers.size)
    }

    class NodeStatusHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerHash: TextView by lazy { itemView.view_node_status_header_item_hash }
        fun bind(headerHash: String) {
            this.headerHash.text = headerHash
        }
    }
}