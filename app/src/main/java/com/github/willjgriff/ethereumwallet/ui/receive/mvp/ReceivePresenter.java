package com.github.willjgriff.ethereumwallet.ui.receive.mvp;

import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsManager;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

/**
 * Created by Will on 01/03/2017.
 */

public class ReceivePresenter extends BaseMvpPresenter<ReceiveView> {

	private AccountsManager mAccountsManager;

	@Inject
	public ReceivePresenter(AccountsManager accountsManager) {
		mAccountsManager = accountsManager;
	}

	@Override
	public void viewReady() {
		getView().setReceiveAddress(mAccountsManager.getActiveAccountAddress());
	}
}
