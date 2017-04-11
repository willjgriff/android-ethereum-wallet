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

    private lateinit var peers: TextView
    private lateinit var peersInfo: RecyclerView
    private lateinit var headers: RecyclerView
    private lateinit var nodeDetails: TextView
    private lateinit var syncProgress: TextView
    private val peersAdapter: NodeStatusPeersAdapter = NodeStatusPeersAdapter()
    private val headersAdapter: NodeStatusHeadersAdapter = NodeStatusHeadersAdapter()

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
        val view = container.inflate(R.layout.controller_node_status)
        bindViews(view)
        setupNodeDetails()
        setupPeerDetails()
        setupHeadersList()
        return view
    }

    private fun bindViews(view: View) {
        peers = view.controller_node_status_peers
        peersInfo = view.controller_node_status_peers_list
        headers = view.controller_node_status_headers_list
        nodeDetails = view.controller_node_status_node_details
        syncProgress = view.controller_node_status_sync_progress
    }

    private fun setupNodeDetails() {
        nodeDetails.text = ethereum.getNodeInfoString()
        ethereum.getSyncProgressString()
                .subscribe { syncProgress.text = it }
    }

    private fun setupPeerDetails() {
        peersInfo.adapter = peersAdapter
        peersInfo.layoutManager = LinearLayoutManager(applicationContext)
        ethereum.getNodePeerInfoStrings()
                .subscribe { peersAdapter.setPeerStrings(it) }
    }

    private fun setupHeadersList() {
        headers.layoutManager = LinearLayoutManager(applicationContext)
        headers.adapter = headersAdapter
    }

    override fun newHeader(header: Header) {
        headersAdapter.addHeaderHash(header.hash.hex)
    }

    override fun updatePeerInfos(peerInfos: PeerInfos) {
        peers.text = "Peers: ${peerInfos.size()}"
    }
}