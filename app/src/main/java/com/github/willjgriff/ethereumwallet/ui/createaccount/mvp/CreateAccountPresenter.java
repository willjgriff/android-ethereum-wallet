package com.github.willjgriff.ethereumwallet.ui.createaccount.mvp;

import com.github.willjgriff.ethereumwallet.data.ethereum.EthereumManager;
import com.github.willjgriff.ethereumwallet.di.ControllerScope;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;
import javax.inject.Scope;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by Will on 03/02/2017.
 */

@ControllerScope
public class CreateAccountPresenter extends BaseMvpPresenter<CreateAccountView> {

	private EthereumManager mEthereumManager;
	private Observable<CharSequence> mPassword;
	private Observable<Boolean> mValidPassword;
	private Observable<Object> mSubmitButtonShare;

	@Inject
	CreateAccountPresenter(EthereumManager ethereumManager) {
		mEthereumManager = ethereumManager;
	}

	public void setObservables(Observable<CharSequence> passwordObservable, Observable<Object> submitButtonObservable) {
		mPassword = passwordObservable.replay(1).autoConnect();
		mValidPassword = passwordObservable
			.map(password -> password.length() > 0)
			.distinctUntilChanged();
		mSubmitButtonShare = submitButtonObservable.share();
	}

	@Override
	protected void viewReady() {
		setupPasswordObservable();
		setupSubmitObservable();
	}

	private void setupPasswordObservable() {
		mValidPassword
			// Hide the error when a valid result is received
			.filter(isValid -> isValid)
			.subscribe(isValid -> hidePasswordError());

		Observable<Boolean> hotValidPassword = mValidPassword.publish().autoConnect();
		Observable<Boolean> skipUntilValid = hotValidPassword
			// Skip until the field has a valid result
			.skipUntil(hotValidPassword.filter(isValid -> isValid));

		Observable<Boolean> validSubmitFlatMap = mSubmitButtonShare
			// Output the valid field observable when the send button is clicked
			.flatMap(aVoid -> mValidPassword);

		Observable.merge(skipUntilValid, validSubmitFlatMap)
			// Checks the input is invalid and shows error
			.filter(isValid -> !isValid)
			.subscribe(isNotValid -> showPasswordError());
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
			mEthereumManager.createAccount(password.toString());
			getView().openWallet();
		});
	}

	private void hidePasswordError() {
		getView().hidePasswordError();
	}

	private void showPasswordError() {
		getView().showPasswordError();
	}
}
