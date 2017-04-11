package com.github.wiljgriff.ethereumwallet.ui.transactions.di

import com.github.wiljgriff.ethereumwallet.ui.transactions.TransactionsController
import com.github.willjgriff.ethereumwallet.di.AppComponent
import com.github.willjgriff.ethereumwallet.di.FunctionScope
import dagger.Component

/**
 * Created by williamgriffiths on 11/04/2017.
 */
@Component(dependencies = arrayOf(AppComponent::class))
@FunctionScope
interface TransactionsComponent {

    fun inject(transactionsController: TransactionsController)
}