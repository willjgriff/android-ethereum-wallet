package com.github.willjgriff.ethereumwallet.ui.transactions;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController;
import com.github.willjgriff.ethereumwallet.ui.navigation.NavigationToolbarListener;
import com.github.willjgriff.ethereumwallet.ui.transactions.mvp.TransactionsInjector;
import com.github.willjgriff.ethereumwallet.ui.transactions.mvp.TransactionsPresenter;
import com.github.willjgriff.ethereumwallet.ui.transactions.mvp.TransactionsView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Will on 28/01/2017.
 */

public class TransactionsController extends BaseMvpController<TransactionsView, TransactionsPresenter>
	implements TransactionsView {

	@Inject
	TransactionsPresenter mPresenter;

	public TransactionsController() {
		TransactionsInjector.INSTANCE.injectNewTransactionsPresenter(this);
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_transactions, container, false);
		setupToolbarTitle();
		return view;
	}

	private void setupToolbarTitle() {
		if (getTargetController() instanceof NavigationToolbarListener) {
			((NavigationToolbarListener) getTargetController())
				.setToolbarTitle(getApplicationContext().getString(R.string.controller_transactions_title));
		}
	}

	@Override
	protected TransactionsView getMvpView() {
		return this;
	}

	@Override
	protected TransactionsPresenter createPresenter() {
		return mPresenter;
	}
}
