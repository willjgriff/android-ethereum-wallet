package com.github.willjgriff.ethereumwallet.ui.settings.mvp;

import com.github.wiljgriff.ethereumwallet.data.model.DomainAddress;

import java.util.List;

/**
 * Created by Will on 28/02/2017.
 */

public interface ChangeAddressView {

	void setAddresses(List<DomainAddress> allAccounts);

	void closeScreen();
}
