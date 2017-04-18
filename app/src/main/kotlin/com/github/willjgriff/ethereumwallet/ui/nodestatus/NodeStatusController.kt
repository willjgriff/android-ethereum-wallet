package com.github.willjgriff.ethereumwallet.ui.nodestatus

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.wiljgriff.ethereumwallet.ui.nodestatus.di.DaggerNodeStatusComponent
import com.github.willjgriff.ethereumwallet.ui.utils.inflate
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.di.AppInjector
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController
import com.github.willjgriff.ethereumwallet.ui.nodestatus.adapters.NodeStatusHeadersAdapter
import com.github.willjgriff.ethereumwallet.ui.nodestatus.adapters.NodeStatusPeersAdapter
import com.github.willjgriff.ethereumwallet.ui.nodestatus.mvp.NodeStatusPresenter
import com.github.willjgriff.ethereumwallet.ui.nodestatus.mvp.NodeStatusView
import kotlinx.android.synthetic.main.controller_node_status.view.*
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
                .appComponent(AppInjector.appComponent)
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
        headers.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = headersAdapter
        }
        peersInfo.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = peersAdapter
        }
    }

    override fun newHeaderHash(headerHash: String) {
        headersAdapter.addHeaderHash(headerHash)
    }

    override fun updateNumberOfPeers(numberOfPeers: Long) {
        peers.text = "Peers: $numberOfPeers"
    }

    override fun setNodeDetails(nodeInfoString: String) {
        nodeDetails.text = nodeInfoString
    }

    override fun setSyncProgress(syncProgressString: String) {
        syncProgress.text = syncProgressString
    }

    override fun setPeerStrings(peers: List<String>) {
        peersAdapter.setPeerStrings(peers)
    }
}
