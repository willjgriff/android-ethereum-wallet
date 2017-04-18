package com.github.willjgriff.ethereumwallet.ui.transactions.di

import com.github.willjgriff.ethereumwallet.ui.transactions.TransactionsController
import com.github.willjgriff.ethereumwallet.di.AppComponent
import com.github.willjgriff.ethereumwallet.di.ControllerScope
import dagger.Component

/**
 * Created by williamgriffiths on 11/04/2017.
 */
@Component(dependencies = arrayOf(AppComponent::class))
@ControllerScope
interface TransactionsComponent {

    fun inject(transactionsController: TransactionsController)
}