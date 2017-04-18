package com.github.willjgriff.ethereumwallet.ui.receive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController
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
class ReceiveController : BaseMvpController<ReceiveView, ReceivePresenter>(), ReceiveView {

    @Inject
    lateinit var receivePresenter: ReceivePresenter

    init {
        injectNewReceivePresenter()
    }

    override fun getMvpView() = this

    override fun createPresenter() = receivePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = container.inflate(R.layout.controller_receive)
        setupToolbarTitle()
        return view
    }

    private fun setupToolbarTitle() {
        val targetController = targetController
        if (targetController is NavigationToolbarListener) {
            targetController.setToolbarTitle(applicationContext?.getString(R.string.controller_receive_title))
        }
    }

    override fun setReceiveAddress(address: String) {
        view?.controller_receive_ethereum_address?.text = address
    }
}