package com.github.wiljgriff.ethereumwallet.ui.nodestatus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.wiljgriff.ethereumwallet.ethereum.node.Ethereum
import com.github.wiljgriff.ethereumwallet.ui.nodestatus.mvp.NodeStatusPresenter
import com.github.wiljgriff.ethereumwallet.ui.nodestatus.mvp.NodeStatusView
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController
import org.ethereum.geth.Header
import org.ethereum.geth.PeerInfos

/**
 * Created by Will on 20/03/2017.
 */
class NodeStatusController : BaseMvpController<NodeStatusView, NodeStatusPresenter>(), NodeStatusView {

    internal var mPeers: TextView? = null
    internal var mHeaders: TextView? = null

    override fun createPresenter(): NodeStatusPresenter {
        return NodeStatusPresenter(Ethereum(applicationContext?.filesDir.toString() + "Hola"))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_transactions, container, false)
        mPeers = view.findViewById(R.id.controller_transactions_peers) as TextView?
        mHeaders = view.findViewById(R.id.controller_transactions_text) as TextView?
        return view
    }

    override fun getMvpView(): NodeStatusView {
        return this
    }

    override fun newHeader(header: Header) {
        mHeaders?.append("\n#" + header.number + ": " + header.hash.hex.substring(0, 10) + "…")
    }

    override fun updatePeerInfos(peerInfos: PeerInfos) {
        mPeers?.append(peerInfos.size().toString() + " ")
    }
}