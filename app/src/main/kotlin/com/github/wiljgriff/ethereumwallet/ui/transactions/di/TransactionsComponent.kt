package com.github.wiljgriff.ethereumwallet.ui.transactions.di

import com.github.wiljgriff.ethereumwallet.di.AppComponent
import com.github.wiljgriff.ethereumwallet.di.ControllerScope
import com.github.wiljgriff.ethereumwallet.ui.transactions.TransactionsController
import dagger.Component

/**
 * Created by williamgriffiths on 11/04/2017.
 */
@Component(dependencies = arrayOf(AppComponent::class))
@ControllerScope
interface TransactionsComponent {

    fun inject(transactionsController: TransactionsController)
}