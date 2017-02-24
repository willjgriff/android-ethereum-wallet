package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.wiljgriff.ethereumwallet.data.ethereum.EthereumAccountManagerKotlin;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Will on 19/02/2017.
 *
 * Note this uses AutoFactory which means it can't be retained beyond the life of
 * the Controller / AlertDialog. Therefore we don't have to release references to
 * View containing objects or dispose of Observable Subscriptions.
 */
@AutoFactory
public class SettingsDeletePresenter extends BaseMvpPresenter<SettingsDeleteView> {

	private EthereumAccountManagerKotlin mEthereumAccountManager;
	private Observable<Object> mDeleteButton;
	private Observable<Object> mCancelButton;
	private Observable<Boolean> mPasswordValid;
	private Observable<String> mPasswordChanged;

	SettingsDeletePresenter(@Provided EthereumAccountManagerKotlin ethereumAccountManager,
	                        Observable<Object> deleteButton, Observable<Object> cancelButton,
	                        Observable<Boolean> passwordValid, Observable<String> passwordChanged) {
		mEthereumAccountManager = ethereumAccountManager;
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

		mPasswordChanged
			.subscribe(password -> mEthereumAccountManager.deleteActiveAccount(password));
	}

	private void setupDeleteButton(Observable<Object> deleteButton, Observable<Boolean> passwordValid, Observable<String> passwordChanged) {
		Observable<String> enteredPasswordObservable = passwordChanged
			.replay(1)
			.autoConnect();

		Observable<String> deleteObservable = deleteButton
			.withLatestFrom(passwordValid, (buttonClick, passwordIsValid) -> passwordIsValid)
			.filter(passwordIsValid -> passwordIsValid)
			.flatMap(passwordIsValid -> enteredPasswordObservable);

		// Show incorrect password error if the password is incorrect
		deleteObservable
			.map(validPassword -> mEthereumAccountManager.verifyPassword(validPassword))
			.filter(correctPassword -> !correctPassword)
			.subscribe(incorrectPassword -> getView().incorrectPasswordEntered());

		// Delete active account if the password is correct
		deleteObservable
			.filter(enteredPassword -> mEthereumAccountManager.verifyPassword(enteredPassword))
			.subscribe(enteredPassword -> {
				mEthereumAccountManager.deleteActiveAccount(enteredPassword);
				getView().addressDeleted();
				getView().closeDialog();
			});
	}
}
