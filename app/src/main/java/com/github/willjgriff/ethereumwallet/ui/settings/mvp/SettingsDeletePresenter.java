package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.wiljgriff.ethereumwallet.data.ethereum.EthereumAccountManagerKotlin;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Will on 19/02/2017.
 */

public class SettingsDeletePresenter extends BaseMvpPresenter<SettingsDeleteView> {

	private EthereumAccountManagerKotlin mEthereumAccountManager;

	@Inject
	public SettingsDeletePresenter(EthereumAccountManagerKotlin ethereumAccountManager) {
		mEthereumAccountManager = ethereumAccountManager;
	}

	@Override
	public void viewReady() {
	}

	public void setObservables(Observable<Object> deleteButton, Observable<Object> cancelButton,
	                           Observable<Boolean> passwordValid, Observable<String> passwordChanged) {

		setupDeleteButton(deleteButton, passwordValid, passwordChanged);

		cancelButton
			.subscribe(cancelClicked -> getView().closeDialog());

		passwordChanged
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
