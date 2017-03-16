package com.github.willjgriff.ethereumwallet.ui.createaccount.mvp;

import com.github.wiljgriff.ethereumwallet.ethereum.account.EthereumAccountManagerKotlin;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import io.reactivex.Observable;

/**
 * Created by Will on 03/02/2017.
 */

@AutoFactory
public class CreateAccountPresenter extends BaseMvpPresenter<CreateAccountView> {

	private EthereumAccountManagerKotlin mEthereumAccountManager;
	private Observable<String> mPassword;
	private Observable<Boolean> mValidPassword;
	private Observable<Object> mSubmitButtonShare;

	CreateAccountPresenter(@Provided EthereumAccountManagerKotlin ethereumAccountManager,
	                       Observable<String> passwordObservable,
	                       Observable<Boolean> passwordValid,
	                       Observable<Object> submitButtonObservable) {

		mEthereumAccountManager = ethereumAccountManager;
		mPassword = passwordObservable;
		mValidPassword = passwordValid;
		mSubmitButtonShare = submitButtonObservable;
	}

	@Override
	public void viewReady() {
		setupSubmitObservable();
	}

	private void setupSubmitObservable() {
		// For adding a confirm password field.
//		Observable<Boolean> validCombined = Observable
//			.combineLatest(mValidMessage, mValidPhone, mValidEmail,
//				(messageValid, phoneValid, emailValid) -> messageValid && phoneValid && emailValid)
//			.distinctUntilChanged();

		mSubmitButtonShare
			.withLatestFrom(mValidPassword, (buttonClick, areAllValid) -> areAllValid)
			.filter(areAllValid -> areAllValid)
			.subscribe(aBoolean -> validPasswordSubmitted());
	}

	private void validPasswordSubmitted() {
		mPassword.subscribe(password -> {
			mEthereumAccountManager.createAccount(password.toString());
			getView().openWallet();
		});
	}
}
