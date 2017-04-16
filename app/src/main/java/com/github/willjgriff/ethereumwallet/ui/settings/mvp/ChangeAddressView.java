package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.wiljgriff.ethereumwallet.data.model.DomainAccount;

import java.util.List;

/**
 * Created by Will on 28/02/2017.
 */

public interface ChangeAddressView {

	void setAddresses(List<DomainAccount> allAccounts);

	void closeScreen();
}
