package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.wiljgriff.ethereumwallet.data.model.DomainAccount;
import com.github.wiljgriff.ethereumwallet.ethereum.account.WalletAccountManager;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

/**
 * Created by Will on 28/02/2017.
 */

public class ChangeAddressPresenter extends BaseMvpPresenter<ChangeAddressView> {

	private WalletAccountManager mWalletAccountManager;

	@Inject
	public ChangeAddressPresenter(WalletAccountManager walletAccountManager) {
		mWalletAccountManager = walletAccountManager;
	}

	@Override
	public void viewReady() {
		getView().setAddresses(mWalletAccountManager.getAllAccounts());
	}

	public void onAddressItemClicked(DomainAccount domainAccount) {
		mWalletAccountManager.setActiveAccount(domainAccount);
		getView().closeScreen();
	}
}
