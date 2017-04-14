package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountManager;
import com.github.willjgriff.ethereumwallet.di.FunctionScope;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Will on 14/02/2017.
 *
 * Adding the Dagger Scope means we will inject the same Presenter each time we need it
 * until the Dagger Component is invalidated.
 */

@FunctionScope
public class SettingsPresenter extends BaseMvpPresenter<SettingsView> {

	private AccountManager mAccountManager;

	@Inject
	SettingsPresenter(AccountManager accountManager) {
		mAccountManager = accountManager;
	}

	@Override
	public void viewReady() {
		getAndSetActiveAddress();
	}

	public void setObservables(Observable<Object> newAccountButton, Observable<Object> changeAddressButton, Observable<Object> deleteAddressButton) {
		addDisposable(newAccountButton.subscribe(clicked -> getView().openCreateAccountScreen()));
		addDisposable(changeAddressButton.subscribe(clicked -> getView().openChangeAddressScreen()));
		addDisposable(deleteAddressButton.subscribe(clicked -> getView().showDeleteAddressDialog()));
	}

	private void getAndSetActiveAddress() {
		String address;
		if (mAccountManager.getActiveAccount() != null) {
			address = mAccountManager.getActiveAccount().getAddress().getHex();
			getView().setActiveAddress(address);
		} else {
			getView().setAddressDeleted();
		}
	}
}
