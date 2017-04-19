package com.github.willjgriff.ethereumwallet.ui.transactions.di

import com.github.willjgriff.ethereumwallet.di.AppComponent
import com.github.willjgriff.ethereumwallet.di.AppInjector
import com.github.willjgriff.ethereumwallet.di.ControllerScope
import com.github.willjgriff.ethereumwallet.ui.transactions.TransactionsController
import dagger.Component

/**
 * Created by williamgriffiths on 11/04/2017.
 */
@Component(dependencies = arrayOf(AppComponent::class))
@ControllerScope
interface TransactionsComponent {

    fun inject(transactionsController: TransactionsController)
}

fun TransactionsController.injectNewTransactionsPresenter() =
        DaggerTransactionsComponent.builder()
                .appComponent(AppInjector.appComponent)
                .build()
                .inject(this)