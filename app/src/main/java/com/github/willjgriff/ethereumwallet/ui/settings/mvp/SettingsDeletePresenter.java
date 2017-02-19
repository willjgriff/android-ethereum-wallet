package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.wiljgriff.ethereumwallet.data.ethereum.EthereumAccountManagerKotlin;
import com.github.willjgriff.ethereumwallet.di.ControllerScope;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Will on 19/02/2017.
 */

@ControllerScope
public class SettingsDeletePresenter extends BaseMvpPresenter<SettingsDeleteView> {

	private EthereumAccountManagerKotlin mEthereumAccountManager;
	private Observable<String> mPasswordObservable;

	@Inject
	public SettingsDeletePresenter(EthereumAccountManagerKotlin ethereumAccountManager) {
		mEthereumAccountManager = ethereumAccountManager;
	}

	@Override
	public void viewReady() {
	}

	public void setObservables(Observable<Object> dialogPositiveButtonObservable, Observable<Boolean> isTextValidObservable, Observable<String> passwordChangedObservable) {
		dialogPositiveButtonObservable
			.withLatestFrom(isTextValidObservable, (buttonClick, areAllValid) -> areAllValid)
			.filter(areAllValid -> areAllValid)
			.subscribe(aBoolean -> deleteActiveAccount());

		mPasswordObservable = passwordChangedObservable.replay(1).autoConnect();
	}

	private void deleteActiveAccount() {
		mPasswordObservable.subscribe(password -> {
			if (mEthereumAccountManager.verifyPassword(password)) {
				mEthereumAccountManager.deleteActiveAccount(password);
				getView().closeDialog();
			}
		});
	}
}
