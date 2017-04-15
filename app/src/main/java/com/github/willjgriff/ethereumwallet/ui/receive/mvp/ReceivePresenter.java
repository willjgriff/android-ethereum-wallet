package com.github.willjgriff.ethereumwallet.ui.receive.mvp;

import com.github.wiljgriff.ethereumwallet.ethereum.account.WalletAccountManager;
import com.github.willjgriff.ethereumwallet.mvp.BaseMvpPresenter;

import javax.inject.Inject;

/**
 * Created by Will on 01/03/2017.
 */

public class ReceivePresenter extends BaseMvpPresenter<ReceiveView> {

	private WalletAccountManager mWalletAccountManager;

	@Inject
	public ReceivePresenter(WalletAccountManager walletAccountManager) {
		mWalletAccountManager = walletAccountManager;
	}

	@Override
	public void viewReady() {
		getView().setReceiveAddress(mWalletAccountManager.getActiveAccountAddressHex());
	}
}
