package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsManager;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import io.reactivex.Observable;

/**
 * Created by Will on 19/02/2017.
 * <p>
 * Note this uses AutoFactory which means it can't be retained beyond the life of
 * the Controller / AlertDialog. Therefore we don't have to release references to
 * View containing objects or dispose of Observable Subscriptions.
 */
@AutoFactory
public class DeleteAddressPresenter extends BaseMvpPresenter<DeleteAddressView> {

	private AccountsManager mAccountsManager;
	private Observable<Object> mDeleteButton;
	private Observable<Object> mCancelButton;
	private Observable<Boolean> mPasswordValid;
	private Observable<String> mPasswordChanged;

	DeleteAddressPresenter(@Provided AccountsManager accountsManager,
	                       Observable<Object> deleteButton, Observable<Object> cancelButton,
	                       Observable<Boolean> passwordValid, Observable<String> passwordChanged) {
		mAccountsManager = accountsManager;
		mDeleteButton = deleteButton;
		mCancelButton = cancelButton;
		mPasswordValid = passwordValid;
		mPasswordChanged = passwordChanged;
	}

	@Override
	public void viewReady() {
		setupDeleteButton(mDeleteButton, mPasswordValid, mPasswordChanged);

		mCancelButton
			.subscribe(cancelClicked -> getView().closeDialog());
	}

	private void setupDeleteButton(Observable<Object> deleteButton, Observable<Boolean> passwordValid, Observable<String> passwordChanged) {
		Observable<String> enteredPasswordObservable = passwordChanged
			.replay(1)
			.autoConnect();

		Observable<Boolean> deleteObservable = deleteButton
			.withLatestFrom(passwordValid, (buttonClick, passwordIsValid) -> passwordIsValid)
			.filter(passwordIsValid -> passwordIsValid)
			.flatMap(passwordIsValid -> enteredPasswordObservable)
			.map(validPassword -> mAccountsManager.deleteActiveAccount(validPassword))
			.share();

		// Show incorrect password error if the password is incorrect
		deleteObservable
			.filter(accountDeleted -> !accountDeleted)
			.subscribe(incorrectPassword -> {
				getView().incorrectPasswordEntered();
				getView().closeDialog();
			});

		// Delete active account if the password is correct
		deleteObservable
			.filter(accountDeleted -> accountDeleted)
			.subscribe(enteredPassword -> {
				getView().addressDeleted();
				getView().closeDialog();
			});
	}
}
