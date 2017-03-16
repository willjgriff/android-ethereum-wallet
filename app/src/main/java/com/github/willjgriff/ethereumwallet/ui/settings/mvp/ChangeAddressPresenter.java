package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.wiljgriff.ethereumwallet.ethereum.account.EthereumAccountManagerKotlin;
import com.github.wiljgriff.ethereumwallet.ethereum.account.delegates.AccountDelegate;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

/**
 * Created by Will on 28/02/2017.
 */

public class ChangeAddressPresenter extends BaseMvpPresenter<ChangeAddressView> {

	private EthereumAccountManagerKotlin mEthereumAccountManagerKotlin;

	@Inject
	public ChangeAddressPresenter(EthereumAccountManagerKotlin ethereumAccountManagerKotlin) {
		mEthereumAccountManagerKotlin = ethereumAccountManagerKotlin;
	}

	@Override
	public void viewReady() {
		getView().setAddresses(mEthereumAccountManagerKotlin.getAllAccounts());
	}

	public void onAddressItemClicked(AccountDelegate accountDelegate) {
		mEthereumAccountManagerKotlin.setActiveAccount(accountDelegate);
		getView().closeScreen();
	}
}
