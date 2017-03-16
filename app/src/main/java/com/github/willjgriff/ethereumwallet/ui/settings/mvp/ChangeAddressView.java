package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.wiljgriff.ethereumwallet.ethereum.account.delegates.AccountDelegate;

import java.util.List;

/**
 * Created by Will on 28/02/2017.
 */

public interface ChangeAddressView {

	void setAddresses(List<AccountDelegate> allAccounts);

	void closeScreen();
}
