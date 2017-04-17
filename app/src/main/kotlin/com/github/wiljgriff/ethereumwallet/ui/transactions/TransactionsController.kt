package com.github.wiljgriff.ethereumwallet.ui.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.wiljgriff.ethereumwallet.di.AppInjector
import com.github.wiljgriff.ethereumwallet.ui.transactions.di.DaggerTransactionsComponent
import com.github.wiljgriff.ethereumwallet.ui.transactions.mvp.TransactionsPresenter
import com.github.wiljgriff.ethereumwallet.ui.transactions.mvp.TransactionsView
import com.github.wiljgriff.ethereumwallet.ui.utils.inflate
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener
import kotlinx.android.synthetic.main.controller_transactions.view.*
import javax.inject.Inject

/**
 * Created by williamgriffiths on 11/04/2017.
 */
class TransactionsController : BaseMvpController<TransactionsView, TransactionsPresenter>(), TransactionsView {

    private lateinit var navigationToolbarListener: NavigationToolbarListener
    @Inject lateinit var presenter: TransactionsPresenter

    init {
        DaggerTransactionsComponent.builder()
                .appComponent(AppInjector.appComponent)
                .build()
                .inject(this)
    }

    override fun getMvpView() = this

    override fun createPresenter() = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = container.inflate(R.layout.controller_transactions)
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
        navigationToolbarListener.setToolbarTitle(applicationContext?.getString(R.string.controller_transactions_title))
    }

    override fun setBalance(balanceAtAddress: String) {
        view?.controller_transactions_balance?.text = balanceAtAddress
        navigationToolbarListener.setBalance("ETH: $balanceAtAddress")
    }

    override fun setPendingBalance(pendingBalanceAtAddress: String) {
        view?.controller_transactions_pending_balance?.text = pendingBalanceAtAddress
    }
}
