package com.github.willjgriff.ethereumwallet.ui.nodedetails.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.ui.utils.inflate
import kotlinx.android.synthetic.main.view_node_status_peer_item.view.*

/**
 * Created by williamgriffiths on 11/04/2017.
 */
class NodeDetailsPeersAdapter : RecyclerView.Adapter<NodeDetailsPeersAdapter.NodeStatusPeerViewHolder>() {

    var peers: List<String> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeStatusPeerViewHolder =
            NodeStatusPeerViewHolder(parent.inflate(R.layout.view_node_status_peer_item))

    override fun getItemCount(): Int =
            peers.size

    override fun onBindViewHolder(holder: NodeStatusPeerViewHolder, position: Int) =
            holder.bind(peers[position])

    class NodeStatusPeerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val peerDetails: TextView by lazy { itemView.view_node_status_peer_item_details }
        fun bind(peerString: String) {
            peerDetails.text = peerString
        }
    }
}