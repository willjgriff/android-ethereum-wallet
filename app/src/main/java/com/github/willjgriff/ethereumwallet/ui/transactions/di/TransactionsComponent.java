package com.github.willjgriff.ethereumwallet.ui.transactions.di;

import com.github.willjgriff.ethereumwallet.di.AppComponent;
import com.github.willjgriff.ethereumwallet.di.FunctionScope;
import com.github.willjgriff.ethereumwallet.ui.transactions.TransactionsController;

import dagger.Component;

/**
 * Created by Will on 29/01/2017.
 */

@Component(modules = TransactionsModule.class, dependencies = AppComponent.class)
@FunctionScope
public interface TransactionsComponent {

	void inject(TransactionsController transactionsController);
}
