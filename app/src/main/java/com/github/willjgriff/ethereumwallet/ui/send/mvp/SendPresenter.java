package com.github.willjgriff.ethereumwallet.ui.send.mvp;

import com.github.wiljgriff.ethereumwallet.ethereum.account.EthereumAccountManagerKotlin;
import com.github.willjgriff.ethereumwallet.di.FunctionScope;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Will on 04/02/2017.
 */

@FunctionScope
public class SendPresenter extends BaseMvpPresenter<SendView> {

	private EthereumAccountManagerKotlin mEthereumAccountManager;
	private Observable<Object> mSendEther;

	@Inject
	public SendPresenter(EthereumAccountManagerKotlin ethereumAccountManager) {
		mEthereumAccountManager = ethereumAccountManager;
	}

	@Override
	public void viewReady() {
	}

	public void setObservables(Observable<CharSequence> recipientAddressObservable, Observable<CharSequence> sendAmountObservable, Observable<CharSequence> accountPasswordObservable, Observable<Object> sendObservable) {
		mSendEther = sendObservable;
		mSendEther.subscribe(o -> getView().toString());
	}

	@Override
	public void releaseViewReferences() {
		mSendEther = null;
	}
}
