package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.willjgriff.ethereumwallet.di.ControllerScope;
import com.github.willjgriff.ethereumwallet.ethereum.address.AddressManager;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Will on 14/02/2017.
 *
 * Adding the Dagger Scope means we will inject the same Presenter each time we need it
 * until the Dagger Component is invalidated.
 */

@ControllerScope
public class SettingsPresenter extends BaseMvpPresenter<SettingsView> {

	private AddressManager mAddressManager;

	@Inject
	SettingsPresenter(AddressManager addressManager) {
		mAddressManager = addressManager;
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
		if (!mAddressManager.getActiveAddress().getHex().equals("NO_ADDRESS_HEX")) {
			address = mAddressManager.getActiveAddress().getHex();
			getView().setActiveAddress(address);
		} else {
			getView().setAddressDeleted();
		}
	}
}
