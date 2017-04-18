package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.willjgriff.ethereumwallet.data.model.DomainAddress;
import com.github.willjgriff.ethereumwallet.ethereum.account.AddressManager;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

/**
 * Created by Will on 28/02/2017.
 */

public class ChangeAddressPresenter extends BaseMvpPresenter<ChangeAddressView> {

	private AddressManager mAddressManager;

	@Inject
	public ChangeAddressPresenter(AddressManager addressManager) {
		mAddressManager = addressManager;
	}

	@Override
	public void viewReady() {
		getView().setAddresses(mAddressManager.getAllAddresses());
	}

	public void onAddressItemClicked(DomainAddress domainAddress) {
		mAddressManager.setActiveAccount(domainAddress);
		getView().closeScreen();
	}
}
