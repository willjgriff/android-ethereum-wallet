package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountManager;
import com.github.wiljgriff.ethereumwallet.ethereum.account.delegates.AccountDelegate;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

/**
 * Created by Will on 28/02/2017.
 */

public class ChangeAddressPresenter extends BaseMvpPresenter<ChangeAddressView> {

	private AccountManager mAccountManager;

	@Inject
	public ChangeAddressPresenter(AccountManager accountManager) {
		mAccountManager = accountManager;
	}

	@Override
	public void viewReady() {
		getView().setAddresses(mAccountManager.getAllAccounts());
	}

	public void onAddressItemClicked(AccountDelegate accountDelegate) {
		mAccountManager.setActiveAccount(accountDelegate);
		getView().closeScreen();
	}
}
