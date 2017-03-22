package com.github.willjgriff.ethereumwallet.ui.transactions.mvp;

import com.github.willjgriff.ethereumwallet.di.AppInjector;
import com.github.willjgriff.ethereumwallet.di.invalidation.ComponentInvalidator;
import com.github.willjgriff.ethereumwallet.ui.transactions.TransactionsController;
import com.github.willjgriff.ethereumwallet.ui.transactions.di.DaggerTransactionsComponent;
import com.github.willjgriff.ethereumwallet.ui.transactions.di.TransactionsComponent;

/**
 * Created by Will on 16/03/2017.
 */

public enum TransactionsInjector implements ComponentInvalidator {

	INSTANCE;

	private TransactionsComponent mTransactionsComponent;

	public void injectNewTransactionsPresenter(TransactionsController transactionsController) {
		getComponent().inject(transactionsController);
	}

	private TransactionsComponent getComponent() {
		if (mTransactionsComponent == null) {
			mTransactionsComponent = DaggerTransactionsComponent
				.builder()
				.appComponent(AppInjector.INSTANCE.getAppComponent())
				.build();
		}
		return mTransactionsComponent;
	}

	@Override
	public void invalidateComponent() {
		mTransactionsComponent = null;
	}
}
