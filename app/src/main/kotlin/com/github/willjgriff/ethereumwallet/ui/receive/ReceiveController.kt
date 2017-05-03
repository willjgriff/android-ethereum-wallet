package com.github.willjgriff.ethereumwallet.ui.receive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpControllerKotlin
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener
import com.github.willjgriff.ethereumwallet.ui.receive.di.injectNewReceivePresenter
import com.github.willjgriff.ethereumwallet.ui.receive.mvp.ReceivePresenter
import com.github.willjgriff.ethereumwallet.ui.receive.mvp.ReceiveView
import com.github.willjgriff.ethereumwallet.ui.utils.inflate
import kotlinx.android.synthetic.main.controller_receive.view.*
import javax.inject.Inject

/**
 * Created by williamgriffiths on 18/04/2017.
 */
class ReceiveController : BaseMvpControllerKotlin<ReceiveView, ReceivePresenter>(), ReceiveView {

    override val mvpView: ReceiveView
        get() = this
    @Inject lateinit override var presenter: ReceivePresenter

    lateinit var navigationToolbarListener: NavigationToolbarListener

    init {
        injectNewReceivePresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = container.inflate(R.layout.controller_receive)
        setNavigationToolbarListener()
        setupToolbarTitle()
        return view
    }

    private fun setNavigationToolbarListener() {
        if (targetController is NavigationToolbarListener) {
            navigationToolbarListener = targetController as NavigationToolbarListener
        }
    }

    private fun setupToolbarTitle() {
        navigationToolbarListener.setToolbarTitle(applicationContext?.getString(R.string.controller_receive_title))
    }

    override fun setReceiveAddress(address: String) {
        view?.controller_receive_ethereum_address?.text = address
    }

    override fun setPendingBalance(pendingBalance: String) {
        val ethBalance = applicationContext?.getString(R.string.controller_receive_eth_balance, pendingBalance)
        view?.controller_receive_pending_balance?.text = ethBalance
    }

    override fun setConfirmedBalance(confirmedBalance: String) {
        val ethBalance = applicationContext?.getString(R.string.controller_receive_eth_balance, confirmedBalance)
        view?.controller_receive_confirmed_balance?.text = ethBalance
        navigationToolbarListener.setBalance(ethBalance)
    }
}
