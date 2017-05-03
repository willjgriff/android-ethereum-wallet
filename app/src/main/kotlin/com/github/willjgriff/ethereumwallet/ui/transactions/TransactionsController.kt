package com.github.willjgriff.ethereumwallet.ui.transactions

import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.DomainTransaction
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpControllerKotlin
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener
import com.github.willjgriff.ethereumwallet.ui.transactions.adapters.TransactionsAdapter
import com.github.willjgriff.ethereumwallet.ui.transactions.di.injectNewTransactionsPresenter
import com.github.willjgriff.ethereumwallet.ui.transactions.mvp.TransactionsPresenter
import com.github.willjgriff.ethereumwallet.ui.transactions.mvp.TransactionsView
import com.github.willjgriff.ethereumwallet.ui.utils.inflate
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.controller_transactions.view.*
import kotlinx.android.synthetic.main.view_controller_transactions_search_range_dialog.view.*
import javax.inject.Inject

/**
 * Created by williamgriffiths on 11/04/2017.
 */
class TransactionsController : BaseMvpControllerKotlin<TransactionsView, TransactionsPresenter>(),
        TransactionsView {

    override val mvpView: TransactionsView
        get() = this
    @Inject lateinit override var presenter: TransactionsPresenter

    private val TRANSACTIONS_LIST_ITEM_TOP_BOTTOM_PADDING_DP = 16
    private val TRANSACTIONS_LIST_ITEM_LEFT_RIGHT_PADDING_DP = 8
    private val transactionsAdapter: TransactionsAdapter = TransactionsAdapter()

    init {
        injectNewTransactionsPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = container.inflate(R.layout.controller_transactions)
        setNavigationToolbarListener()
        bindViews(view)
        setupTransactionsList(view)
        return view
    }

    private fun bindViews(view: View) {
        val clearTxsButton = view.controller_transactions_clear_transactions
        val selectSearchRange = view.controller_transactions_search_in_range
        presenter.setObservables(RxView.clicks(clearTxsButton), RxView.clicks(selectSearchRange))
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

    override fun clearTransactions() {
        transactionsAdapter.transactions = mutableListOf()
    }

    // TODO: This should be extracted into a separate AlertDialog extending class and appropriate restrictions placed on the input
    override fun displayRangeDialog() {
        val rangeFieldsView: View = activity?.layoutInflater?.inflate(R.layout.view_controller_transactions_search_range_dialog, null)!!
        val rangeDialog = AlertDialog
                .Builder(activity!!)
                .setView(rangeFieldsView)
                .setPositiveButton(applicationContext?.getString(R.string.common_ok), { dialog, _ -> dialog.dismiss() })
                .setTitle("Select a block range")
                .show()

        rangeFieldsView.controller_transactions_select_range_upper_block
    }
}
