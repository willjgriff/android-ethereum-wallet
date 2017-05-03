package com.github.willjgriff.ethereumwallet.ui.transactions

import android.content.Context
import android.content.DialogInterface
import com.github.willjgriff.ethereumwallet.R
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpAlertDialog
import com.github.willjgriff.ethereumwallet.ui.transactions.di.injectPresenter
import com.github.willjgriff.ethereumwallet.ui.transactions.mvp.SelectBlockRangePresenter
import com.github.willjgriff.ethereumwallet.ui.transactions.mvp.SelectBlockRangeView
import com.github.willjgriff.ethereumwallet.ui.utils.inflate
import javax.inject.Inject

/**
 * Created by williamgriffiths on 03/05/2017.
 */
class SelectBlockRangeAlertDialog(context: Context) : BaseMvpAlertDialog<SelectBlockRangeView, SelectBlockRangePresenter>(context),
        SelectBlockRangeView {

    override val mvpView: SelectBlockRangeView
        get() = this
    @Inject lateinit override var presenter: SelectBlockRangePresenter

    init {
        injectPresenter()
        setupAppearance()
    }

    private fun setupAppearance() {
        setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.common_ok)) { dialog, _ -> dialog.dismiss()}
        setTitle("Select a block range")

        val rangeFieldsView = context.inflate(R.layout.view_controller_transactions_search_range_dialog)

        setView(rangeFieldsView)
    }
}