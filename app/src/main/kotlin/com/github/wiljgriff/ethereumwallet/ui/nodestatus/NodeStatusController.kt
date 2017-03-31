package com.github.wiljgriff.ethereumwallet.ui.nodestatus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    private lateinit var headers: TextView

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
        peers = view.controller_transactions_peers
        headers = view.controller_transactions_text
        return view
    }

    override fun newHeader(header: Header) {
        val hexSubstring = header.hash.hex.substring(0, 10)
        headers.append("\n#${header.number}: ${hexSubstring}…")
    }

    override fun updatePeerInfos(peerInfos: PeerInfos) {
        peers.setText(peerInfos.size().toString())
    }
}