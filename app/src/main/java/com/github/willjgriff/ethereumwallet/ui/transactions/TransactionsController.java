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

import org.ethereum.geth.Header;
import org.ethereum.geth.PeerInfos;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Will on 28/01/2017.
 */

public class TransactionsController extends BaseMvpController<TransactionsView, TransactionsPresenter>
	implements TransactionsView {

	@BindView(R.id.controller_transactions_peers)
	TextView mPeers;
	@BindView(R.id.controller_transactions_text)
	TextView mHeaders;

	@Inject
	TransactionsPresenter mPresenter;

	public TransactionsController() {
		TransactionsInjector.INSTANCE.injectNewTransactionsPresenter(this);
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
		View view = inflater.inflate(R.layout.controller_transactions, container, false);
		ButterKnife.bind(this, view);
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

	@Override
	public void newHeader(Header header) {
		mHeaders.append("\n#" + header.getNumber() + ": " + header.getHash().getHex().substring(0, 10) + "…");
	}

	@Override
	public void updatePeerInfos(PeerInfos peerInfos) {
		mPeers.append(peerInfos.size() + " ");
	}
}
