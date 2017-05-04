package com.github.willjgriff.ethereumwallet.ui.screens.transactions.di

import com.github.willjgriff.ethereumwallet.di.AppComponent
import com.github.willjgriff.ethereumwallet.di.AppInjector
import com.github.willjgriff.ethereumwallet.di.ControllerScope
import com.github.willjgriff.ethereumwallet.ui.screens.transactions.SelectBlockRangeAlertDialog
import com.github.willjgriff.ethereumwallet.ui.screens.transactions.TransactionsController
import dagger.Component

/**
 * Created by williamgriffiths on 11/04/2017.
 */
@Component(dependencies = arrayOf(AppComponent::class))
@ControllerScope
interface TransactionsComponent {

    fun inject(transactionsController: TransactionsController)

    fun inject(selectBlockRangeAlertDialog: SelectBlockRangeAlertDialog)
}

fun TransactionsController.injectPresenter() =
        transactionsComponent().inject(this)

fun SelectBlockRangeAlertDialog.injectPresenter() =
        transactionsComponent().inject(this)

private fun transactionsComponent(): TransactionsComponent {
    return DaggerTransactionsComponent.builder()
            .appComponent(AppInjector.appComponent)
            .build()
}