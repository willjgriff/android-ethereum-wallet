package com.github.willjgriff.ethereumwallet.ui.transactions

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.DomainTransaction
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener
import com.github.willjgriff.ethereumwallet.ui.transactions.adapters.TransactionsAdapter
import com.github.willjgriff.ethereumwallet.ui.transactions.di.injectNewTransactionsPresenter
import com.github.willjgriff.ethereumwallet.ui.transactions.mvp.TransactionsPresenter
import com.github.willjgriff.ethereumwallet.ui.transactions.mvp.TransactionsView
import com.github.willjgriff.ethereumwallet.ui.utils.inflate
import kotlinx.android.synthetic.main.controller_transactions.view.*
import javax.inject.Inject

/**
 * Created by williamgriffiths on 11/04/2017.
 */
class TransactionsController : BaseMvpController<TransactionsView, TransactionsPresenter>(), TransactionsView {

    private val TRANSACTIONS_LIST_ITEM_TOP_BOTTOM_PADDING_DP = 16
    private val TRANSACTIONS_LIST_ITEM_LEFT_RIGHT_PADDING_DP = 8
    private val transactionsAdapter: TransactionsAdapter = TransactionsAdapter()
    @Inject lateinit var presenter: TransactionsPresenter

    init {
        injectNewTransactionsPresenter()
    }

    override fun getMvpView() = this

    override fun createPresenter() = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = container.inflate(R.layout.controller_transactions)
        setNavigationToolbarListener()
        setupTransactionsList(view)
        return view
    }

    private fun setNavigationToolbarListener() {
        val navigationToolbarListener = targetController
        if (navigationToolbarListener is NavigationToolbarListener) {
            navigationToolbarListener.setToolbarTitle(applicationContext?.getString(R.string.controller_transactions_title))
        }
    }

    private fun setupTransactionsList(view: View) {
        view.controller_transactions_recycler_view.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = transactionsAdapter
            addItemDecoration(EvenPaddingDecorator(TRANSACTIONS_LIST_ITEM_TOP_BOTTOM_PADDING_DP, TRANSACTIONS_LIST_ITEM_LEFT_RIGHT_PADDING_DP))
        }
    }

    override fun addTransaction(transaction: DomainTransaction) {
        transactionsAdapter.addTransaction(transaction)
    }
}
