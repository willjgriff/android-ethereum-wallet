package com.github.wiljgriff.ethereumwallet.ui.nodestatus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.wiljgriff.ethereumwallet.ui.nodestatus.mvp.NodeStatusPresenter
import com.github.wiljgriff.ethereumwallet.ui.nodestatus.mvp.NodeStatusView
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController
import org.ethereum.geth.Header
import org.ethereum.geth.PeerInfos
import javax.inject.Inject

/**
 * Created by Will on 20/03/2017.
 */
class NodeStatusController : BaseMvpController<NodeStatusView, NodeStatusPresenter>(), NodeStatusView {

    private var peers: TextView? = null
    private var headers: TextView? = null


    lateinit var presenter: NodeStatusPresenter

    init {

    }

    override fun createPresenter(): NodeStatusPresenter {
        return presenter
    }

    override fun getMvpView(): NodeStatusView {
        return this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_transactions, container, false)
        peers = view.findViewById(R.id.controller_transactions_peers) as TextView?
        headers = view.findViewById(R.id.controller_transactions_text) as TextView?
        return view
    }

    override fun newHeader(header: Header) {
        headers?.append("\n#" + header.number + ": " + header.hash.hex.substring(0, 10) + "…")
    }

    override fun updatePeerInfos(peerInfos: PeerInfos) {
        peers?.append(peerInfos.size().toString() + " ")
    }
}