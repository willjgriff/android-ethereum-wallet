package com.github.willjgriff.ethereumwallet.ui.receive.mvp;

import com.github.willjgriff.ethereumwallet.ethereum.account.AddressManager;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

/**
 * Created by Will on 01/03/2017.
 */

public class ReceivePresenter extends BaseMvpPresenter<ReceiveView> {

	private AddressManager mAddressManager;

	@Inject
	public ReceivePresenter(AddressManager addressManager) {
		mAddressManager = addressManager;
	}

	@Override
	public void viewReady() {
		getView().setReceiveAddress(mAddressManager.getActiveAddress().getHex());
	}
}
