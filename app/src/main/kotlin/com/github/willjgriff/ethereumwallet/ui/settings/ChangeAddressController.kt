package com.github.willjgriff.ethereumwallet.ui.settings

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.ethereum.address.model.DomainAddress
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpControllerKotlin
import com.github.willjgriff.ethereumwallet.ui.settings.di.injectPresenter
import com.github.willjgriff.ethereumwallet.ui.settings.list.ChangeAddressAdapter
import com.github.willjgriff.ethereumwallet.ui.settings.list.ChangeAddressItemViewHolder
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.ChangeAddressPresenter
import com.github.willjgriff.ethereumwallet.ui.settings.mvp.ChangeAddressView
import kotlinx.android.synthetic.main.controller_settings_change_address.view.*
import javax.inject.Inject

/**
 * Created by williamgriffiths on 04/05/2017.
 */

class ChangeAddressController : BaseMvpControllerKotlin<ChangeAddressView, ChangeAddressPresenter>(),
        ChangeAddressView, ChangeAddressItemViewHolder.ChangeAddressItemListener {

    override val mvpView: ChangeAddressView
        get() = this
    @Inject override lateinit var presenter: ChangeAddressPresenter

    private var addressesAdapter: ChangeAddressAdapter = ChangeAddressAdapter(this)

    init {
        injectPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.controller_settings_change_address, container, false)
        setupAddressesList(view)
        return view
    }

    private fun setupAddressesList(view: View) {
        view.controller_settings_change_address_recycler_view.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = addressesAdapter
        }
    }

    override fun setAddresses(allAccounts: List<DomainAddress>) {
        addressesAdapter.accounts = allAccounts
    }

    override fun closeScreen() {
        router.popCurrentController()
    }

    override fun addressItemClicked(domainAddress: DomainAddress) {
        presenter.onAddressItemClicked(domainAddress)
    }
}