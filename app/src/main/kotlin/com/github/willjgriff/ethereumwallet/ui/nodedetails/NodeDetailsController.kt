package com.github.willjgriff.ethereumwallet.ui.nodedetails

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.di.AppInjector
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpControllerKotlin
import com.github.willjgriff.ethereumwallet.ui.nodedetails.list.NodeDetailsHeadersAdapter
import com.github.willjgriff.ethereumwallet.ui.nodedetails.list.NodeDetailsHeadersAdapter.NodeStatusHeadersAdapterListener
import com.github.willjgriff.ethereumwallet.ui.nodedetails.list.NodeDetailsPeersAdapter
import com.github.willjgriff.ethereumwallet.ui.nodedetails.di.DaggerNodeDetailsComponent
import com.github.willjgriff.ethereumwallet.ui.nodedetails.mvp.NodeDetailsPresenter
import com.github.willjgriff.ethereumwallet.ui.nodedetails.mvp.NodeDetailsView
import com.github.willjgriff.ethereumwallet.ui.utils.inflate
import kotlinx.android.synthetic.main.controller_node_status.view.*
import javax.inject.Inject

/**
 * Created by Will on 20/03/2017.
 */
class NodeDetailsController : BaseMvpControllerKotlin<NodeDetailsView, NodeDetailsPresenter>(),
        NodeDetailsView, NodeStatusHeadersAdapterListener {

    override val mvpView: NodeDetailsView
        get() = this
    @Inject lateinit override var presenter: NodeDetailsPresenter

    private lateinit var nodeDetails: TextView
    private lateinit var syncProgress: TextView
    private lateinit var peers: TextView
    private lateinit var peersInfo: RecyclerView
    private lateinit var headers: RecyclerView

    private val mPeersAdapter: NodeDetailsPeersAdapter = NodeDetailsPeersAdapter()
    private val mHeadersAdapter: NodeDetailsHeadersAdapter = NodeDetailsHeadersAdapter(this)

    init {
        DaggerNodeDetailsComponent.builder()
                .appComponent(AppInjector.appComponent)
                .build()
                .inject(this)
    }

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
            adapter = mHeadersAdapter
        }
        peersInfo.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = mPeersAdapter
        }
    }

    override fun headerInserted(position: Int) {
        headers.scrollToPosition(position)
    }

    override fun newHeaderHash(headerHash: String) {
        mHeadersAdapter.addHeaderHash(headerHash)
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
        mPeersAdapter.peers = peers
    }
}
