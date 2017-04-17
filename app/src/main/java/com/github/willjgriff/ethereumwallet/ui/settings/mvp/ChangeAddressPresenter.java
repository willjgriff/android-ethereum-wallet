package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.wiljgriff.ethereumwallet.data.model.DomainAccount;
import com.github.wiljgriff.ethereumwallet.ethereum.account.AccountsManager;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

/**
 * Created by Will on 28/02/2017.
 */

public class ChangeAddressPresenter extends BaseMvpPresenter<ChangeAddressView> {

	private AccountsManager mAccountsManager;

	@Inject
	public ChangeAddressPresenter(AccountsManager accountsManager) {
		mAccountsManager = accountsManager;
	}

	@Override
	public void viewReady() {
		getView().setAddresses(mAccountsManager.getAllAccounts());
	}

	public void onAddressItemClicked(DomainAccount domainAccount) {
		mAccountsManager.setActiveAccount(domainAccount);
		getView().closeScreen();
	}
}
