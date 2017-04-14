package com.github.wiljgriff.ethereumwallet.ui.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.wiljgriff.ethereumwallet.ui.transactions.di.DaggerTransactionsComponent
import com.github.wiljgriff.ethereumwallet.ui.transactions.mvp.TransactionsPresenter
import com.github.wiljgriff.ethereumwallet.ui.transactions.mvp.TransactionsView
import com.github.wiljgriff.ethereumwallet.ui.utils.inflate
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.di.AppInjector
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener
import kotlinx.android.synthetic.main.controller_transactions.view.*
import javax.inject.Inject

/**
 * Created by williamgriffiths on 11/04/2017.
 */
class TransactionsController : BaseMvpController<TransactionsView, TransactionsPresenter>(), TransactionsView {

    @Inject
    lateinit var presenter: TransactionsPresenter

    init {
        DaggerTransactionsComponent.builder()
                .appComponent(AppInjector.INSTANCE.appComponent)
                .build()
                .inject(this)
    }

    override fun getMvpView() = this

    override fun createPresenter() = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = container.inflate(R.layout.controller_transactions)
        setupToolbarTitle()
        return view
    }

    private fun setupToolbarTitle() {
        val targetController = targetController
        if (targetController is NavigationToolbarListener) {
            targetController.setToolbarTitle(applicationContext?.getString(R.string.controller_transactions_title))
        }
    }

    override fun setBalance(balanceAtAddress: String) {
        view?.controller_transactions_balance?.text = balanceAtAddress
    }
}
