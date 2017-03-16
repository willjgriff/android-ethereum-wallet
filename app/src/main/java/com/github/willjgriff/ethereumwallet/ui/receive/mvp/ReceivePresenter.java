package com.github.willjgriff.ethereumwallet.ui.receive.mvp;

import com.github.wiljgriff.ethereumwallet.ethereum.account.EthereumAccountManagerKotlin;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

/**
 * Created by Will on 01/03/2017.
 */

public class ReceivePresenter extends BaseMvpPresenter<ReceiveView> {

	private EthereumAccountManagerKotlin mEthereumAccountManager;

	@Inject
	public ReceivePresenter(EthereumAccountManagerKotlin ethereumAccountManager) {
		mEthereumAccountManager = ethereumAccountManager;
	}

	@Override
	public void viewReady() {
		getView().setReceiveAddress(mEthereumAccountManager.getActiveAccountAddressHex());
	}
}
