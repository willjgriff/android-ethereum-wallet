package com.github.willjgriff.ethereumwallet.ui.transactions;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.willjgriff.ethereumwallet.R;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpController;

import org.ethereum.geth.AccountManager;
import org.ethereum.geth.EthereumClient;
import org.ethereum.geth.Geth;

/**
 * Created by Will on 28/01/2017.
 */

public class TransactionsController extends BaseMvpController<TransactionsView, TransactionsPresenter>
	implements TransactionsView {

	@Override
	protected TransactionsView getMvpView() {
		return this;
	}

	@Override
	protected TransactionsPresenter createPresenter() {
		return new TransactionsPresenter();
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		return inflater.inflate(R.layout.controller_transactions, container, false);
	}

	@Override
	protected void onAttach(@NonNull View view) {
		super.onAttach(view);


	}
}
