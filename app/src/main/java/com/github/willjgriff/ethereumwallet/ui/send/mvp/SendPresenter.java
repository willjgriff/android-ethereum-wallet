package com.github.willjgriff.ethereumwallet.ui.send.mvp;

import com.github.willjgriff.ethereumwallet.data.ethereum.EthereumManager;
import com.github.willjgriff.ethereumwallet.di.ControllerScope;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.internal.functions.ObjectHelper;

/**
 * Created by Will on 04/02/2017.
 */

@ControllerScope
public class SendPresenter extends BaseMvpPresenter<SendView> {

	private EthereumManager mEthereumManager;
	private Observable<Object> mSendEther;

	@Inject
	public SendPresenter(EthereumManager ethereumManager) {
		mEthereumManager = ethereumManager;
	}

	@Override
	protected void viewReady() {

	}


	public void setObservables(Observable<CharSequence> recipientAddressObservable, Observable<CharSequence> sendAmountObservable, Observable<CharSequence> accountPasswordObservable, Observable<Object> sendObservable) {

		mSendEther = sendObservable;
	}
}
