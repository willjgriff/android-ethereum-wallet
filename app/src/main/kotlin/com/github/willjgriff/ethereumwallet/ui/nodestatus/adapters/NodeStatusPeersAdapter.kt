package com.github.willjgriff.ethereumwallet.ui.nodestatus.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.willjgriff.ethereumwallet.ui.utils.inflate
import com.github.willjgriff.ethereumwallet.R
import kotlinx.android.synthetic.main.view_node_status_peer_item.view.*

/**
 * Created by williamgriffiths on 11/04/2017.
 */
class NodeStatusPeersAdapter : RecyclerView.Adapter<NodeStatusPeersAdapter.NodeStatusPeerViewHolder>() {

    private var peers: List<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeStatusPeerViewHolder {
        return NodeStatusPeerViewHolder(parent.inflate(R.layout.view_node_status_peer_item))
    }

    override fun getItemCount(): Int {
        return peers.size
    }

    override fun onBindViewHolder(holder: NodeStatusPeerViewHolder, position: Int) {
        holder.bind(peers.get(position))
    }

    fun setPeerStrings(peers: List<String>) {
        this.peers = peers
        notifyDataSetChanged()
    }

    class NodeStatusPeerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val peerDetails: TextView by lazy { itemView.view_node_status_peer_item_details }
        fun bind(peerString: String) {
            peerDetails.text = peerString
        }
    }
}