package com.github.wiljgriff.ethereumwallet.ui.nodestatus

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.wiljgriff.ethereumwallet.ethereum.node.Ethereum
import com.github.wiljgriff.ethereumwallet.ui.nodestatus.adapters.NodeStatusHeadersAdapter
import com.github.wiljgriff.ethereumwallet.ui.nodestatus.adapters.NodeStatusPeersAdapter
import com.github.wiljgriff.ethereumwallet.ui.nodestatus.di.DaggerNodeStatusComponent
import com.github.wiljgriff.ethereumwallet.ui.nodestatus.mvp.NodeStatusPresenter
import com.github.wiljgriff.ethereumwallet.ui.nodestatus.mvp.NodeStatusView
import com.github.wiljgriff.ethereumwallet.ui.utils.inflate
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.di.AppInjector
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController
import kotlinx.android.synthetic.main.controller_node_status.view.*
import org.ethereum.geth.Header
import org.ethereum.geth.PeerInfos
import javax.inject.Inject

/**
 * Created by Will on 20/03/2017.
 */
class NodeStatusController : BaseMvpController<NodeStatusView, NodeStatusPresenter>(), NodeStatusView {

    private lateinit var nodeDetails: TextView
    private lateinit var syncProgress: TextView
    private lateinit var peers: TextView
    private lateinit var peersInfo: RecyclerView
    private lateinit var headers: RecyclerView

    private val peersAdapter: NodeStatusPeersAdapter = NodeStatusPeersAdapter()
    private val headersAdapter: NodeStatusHeadersAdapter = NodeStatusHeadersAdapter()

    @Inject lateinit var presenter: NodeStatusPresenter

    init {
        DaggerNodeStatusComponent.builder()
                .appComponent(AppInjector.INSTANCE.appComponent)
                .build()
                .inject(this)
    }

    override fun createPresenter() = presenter

    override fun getMvpView() = this

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = container.inflate(R.layout.controller_node_status)
        bindViews(view)
        setupRecyclerViews()
        return view
    }

    private fun bindViews(view: View) {
        peers = view.controller_node_status_peers
        peersInfo = view.controller_node_status_peers_list
        headers = view.controller_node_status_headers_list
        nodeDetails = view.controller_node_status_node_details
        syncProgress = view.controller_node_status_sync_progress
    }

    private fun setupRecyclerViews() {
        headers.layoutManager = LinearLayoutManager(applicationContext)
        headers.adapter = headersAdapter
        peersInfo.layoutManager = LinearLayoutManager(applicationContext)
        peersInfo.adapter = peersAdapter
    }

    override fun newHeader(header: Header) {
        headersAdapter.addHeaderHash(header.hash.hex)
    }

    override fun updatePeerInfos(peerInfos: PeerInfos) {
        peers.text = "Peers: ${peerInfos.size()}"
    }

    override fun setNodeDetails(nodeInfoString: String) {
        nodeDetails.text = nodeInfoString
    }

    override fun setSyncProgress(syncProgress: String) {
        this.syncProgress.text = syncProgress
    }

    override fun setPeerStrings(peers: List<String>) {
        peersAdapter.setPeerStrings(peers)
    }
}
