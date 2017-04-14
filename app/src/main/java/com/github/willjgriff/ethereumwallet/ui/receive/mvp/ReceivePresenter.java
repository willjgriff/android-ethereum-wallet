package com.github.willjgriff.ethereumwallet.ui.receive.mvp;

import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountManager;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

/**
 * Created by Will on 01/03/2017.
 */

public class ReceivePresenter extends BaseMvpPresenter<ReceiveView> {

	private AccountManager mAccountManager;

	@Inject
	public ReceivePresenter(AccountManager accountManager) {
		mAccountManager = accountManager;
	}

	@Override
	public void viewReady() {
		getView().setReceiveAddress(mAccountManager.getActiveAccountAddressHex());
	}
}
