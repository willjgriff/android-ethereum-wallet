package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.wiljgriff.ethereumwallet.data.ethereum.EthereumAccountManagerKotlin;
import com.github.willjgriff.ethereumwallet.di.ControllerScope;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Will on 14/02/2017.
 */

@ControllerScope
public class SettingsPresenter extends BaseMvpPresenter<SettingsView> {

	private EthereumAccountManagerKotlin mEthereumAccountManager;

	@Inject
	SettingsPresenter(EthereumAccountManagerKotlin ethereumAccountManagerKotlin) {
		mEthereumAccountManager = ethereumAccountManagerKotlin;
	}

	@Override
	public void viewReady() {
		getAndSetActiveAddress();
	}

	public void setObservables(Observable<Object> newAccountButton, Observable<Object> deleteAddressButton) {
		newAccountButton.subscribe(clicked -> getView().openCreateAccountScreen());
		deleteAddressButton.subscribe(clicked -> getView().showDeleteAddressDialog());
	}

	public void updateActiveAccount() {
		// TODO: Add an animation when updating active address.
		getAndSetActiveAddress();
	}

	private void getAndSetActiveAddress() {
		String address = mEthereumAccountManager.getActiveAccount().getAddress().getHex();
		getView().setActiveAddress(address);
	}
}
