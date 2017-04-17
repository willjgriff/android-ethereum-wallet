package com.github.willjgriff.ethereumwallet.ui.send.mvp;

import com.github.wiljgriff.ethereumwallet.di.ControllerScope;
import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsManager;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Will on 04/02/2017.
 */

@ControllerScope
public class SendPresenter extends BaseMvpPresenter<SendView> {

	private AccountsManager mAccountsManager;
	private Observable<Object> mSendEther;

	@Inject
	public SendPresenter(AccountsManager accountsManager) {
		mAccountsManager = accountsManager;
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
