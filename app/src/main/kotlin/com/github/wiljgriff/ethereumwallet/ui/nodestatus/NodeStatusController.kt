package com.github.wiljgriff.ethereumwallet.ui.nodestatus

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.wiljgriff.ethereumwallet.ethereum.node.Ethereum
import com.github.wiljgriff.ethereumwallet.ui.nodestatus.di.DaggerNodeStatusComponent
import com.github.wiljgriff.ethereumwallet.ui.nodestatus.mvp.NodeStatusPresenter
import com.github.wiljgriff.ethereumwallet.ui.nodestatus.mvp.NodeStatusView
import com.github.wiljgriff.ethereumwallet.ui.utils.inflate
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.di.AppInjector
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController
import kotlinx.android.synthetic.main.controller_node_status.view.*
import kotlinx.android.synthetic.main.controller_node_status_2.view.*
import org.ethereum.geth.Header
import org.ethereum.geth.PeerInfos
import javax.inject.Inject

/**
 * Created by Will on 20/03/2017.
 */
class NodeStatusController : BaseMvpController<NodeStatusView, NodeStatusPresenter>(), NodeStatusView {

    private lateinit var peers: TextView
    private lateinit var peersInfo: RecyclerView
    private lateinit var headers: RecyclerView
    private lateinit var nodeDetails: TextView
    private lateinit var syncProgress: TextView

    @Inject lateinit var presenter: NodeStatusPresenter
    @Inject lateinit var ethereum: Ethereum

    init {
        DaggerNodeStatusComponent.builder()
                .appComponent(AppInjector.INSTANCE.appComponent)
                .build()
                .inject(this)
    }

    override fun createPresenter() = presenter

    override fun getMvpView() = this

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = container.inflate(R.layout.controller_node_status_2)
        bindViews(view)
        showNodeDetails()
        showPeerDetails()
        return view
    }

    private fun bindViews(view: View) {
        peers = view.controller_node_status_peers
        peersInfo = view.controller_node_status_peers_list
        headers = view.controller_node_status_headers_list
        nodeDetails = view.controller_node_status_node_details
        syncProgress = view.controller_node_status_sync_progress
    }

    private fun showPeerDetails() {
        ethereum.getNodePeersInfoString()
//                .subscribe { peersInfo.text = it }
    }

    private fun showNodeDetails() {
        nodeDetails.text = ethereum.getNodeInfoString()

        ethereum.getSyncProgressString()
                .subscribe { syncProgress.text = it }
    }

    override fun newHeader(header: Header) {
        val hexSubstring = header.hash.hex.substring(0, 10)
//        headers.append("\n#${header.number}: ${hexSubstring}…")
    }

    override fun updatePeerInfos(peerInfos: PeerInfos) {
        peers.setText("Peers: ${peerInfos.size()}")
    }
}