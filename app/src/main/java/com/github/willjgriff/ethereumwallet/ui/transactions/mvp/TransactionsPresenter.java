package com.github.willjgriff.ethereumwallet.ui.transactions.mvp;

import com.github.wiljgriff.ethereumwallet.ethereum.node.Ethereum;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Will on 29/01/2017.
 */

public class TransactionsPresenter extends BaseMvpPresenter<TransactionsView> {

	private Ethereum mEthereum;

	@Inject
	public TransactionsPresenter(Ethereum ethereum) {
		mEthereum = ethereum;
	}

	@Override
	public void viewReady() {
		addDisposable(mEthereum.getBlockHeaderObservable()
			.subscribe(header -> getView().newHeader(header),
				throwable -> Timber.e(throwable)));

		addDisposable(mEthereum.getPeersInfo()
			.subscribe(peerInfos -> getView().updatePeerInfos(peerInfos),
				throwable -> Timber.e(throwable)));
	}
}
